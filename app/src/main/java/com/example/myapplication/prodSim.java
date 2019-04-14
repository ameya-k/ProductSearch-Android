package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class prodSim extends Fragment {

productRecyclerList pro;
Boolean itemsPresent;


View v;

Spinner sortOrderSpinner,sortTypeSpinner;

private RecyclerView simrecycler;
private simItemAdapter recyclerAdapter;
private List<simItemModel> displayItemsList;
private List<simItemModel> copyList;


    public prodSim() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment prodSim.
     */
    // TODO: Rename and change types and number of parameters
    public static prodSim newInstance(String param1, String param2) {
        prodSim fragment = new prodSim();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemsPresent=true;
        pro=getArguments().getParcelable("firstData");
        Log.i("title in sim:",pro.getTitle());
        v=inflater.inflate(R.layout.fragment_prod_sim, container, false);
        populateSpinners(v,pro);

        simrecycler=v.findViewById(R.id.simItemsRecyclerView);
        simrecycler.setHasFixedSize(true);
        simrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        displayItemsList=new ArrayList<>();
        copyList=new ArrayList<>();

        recyclerAdapter=new simItemAdapter(displayItemsList,getContext());

        createSimItemsRecyclerView();


        return v;
    }

    private void createSimItemsRecyclerView() {

        String simItemsUrl="http://ameyanodemodule-dot-ameyabk117-angularweb8.appspot.com/similarItemsCall/"+pro.getItem_id();

        StringRequest request=new StringRequest(Request.Method.GET, simItemsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject simresponse=null;
                try {
                    simresponse=new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    v.findViewById(R.id.nosimitemsview).setVisibility(View.VISIBLE);
                    itemsPresent=false;
                }

                JSONArray simItemsArray = null;

                try {
                    simItemsArray=simresponse.getJSONObject("getSimilarItemsResponse").
                            getJSONObject("itemRecommendations").getJSONArray("item");


//                    if(simItemsArray.length()==0){
//                        v.findViewById(R.id.nosimitemsview).setVisibility(View.VISIBLE);
//                    }
//
//                    for (int i=0;i<simItemsArray.length();i++){
//                        String imgurl="";
//                        String prtitle="";
//                        String prshipping="";
//                        String prdaysleft="";
//                        String prprice="";
//
//                        JSONObject oneItem=simItemsArray.getJSONObject(i);
//
//                        imgurl=oneItem.getString("imageURL");
//
//
//                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    v.findViewById(R.id.nosimitemsview).setVisibility(View.VISIBLE);
                    itemsPresent=false;
                }

                if(simItemsArray.length()==0){
                        v.findViewById(R.id.nosimitemsview).setVisibility(View.VISIBLE);
                        itemsPresent=false;
                   }

                if(itemsPresent){

                    for (int i=0;i<simItemsArray.length();i++){
                        String imgurl="";
                        String prtitle="";
                        String prshipping="";
                        String prdaysleft="";
                        String prprice="";
                        String redUrl="";

                        JSONObject oneItem= null;
                        try {
                            oneItem = simItemsArray.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            imgurl="N/A";
                            prtitle="N/A";
                            prshipping="N/A";
                            prdaysleft="N/A";
                            prprice="N/A";
                        }

                        try {
                            imgurl=oneItem.getString("imageURL");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            imgurl="N/A";
                        }

                        try {
                            prtitle=oneItem.getString("title");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            prtitle="N/A";
                        }



                        try {
                            prshipping = oneItem.getJSONObject("shippingCost").getString("__value__");
                            if(prshipping.equalsIgnoreCase("0")||prshipping.equalsIgnoreCase("0.00")){
                                prshipping="Free Shipping";
                            }
                            else{
                                prshipping="$"+prshipping;
                            }
                        }catch (JSONException je){
                            prshipping="N/A";
                        }


                        try {
                            String dleft=oneItem.getString("timeLeft");
                            int index=dleft.indexOf('D');

                            prdaysleft=dleft.substring(1,index);

                            if("1".equals(prdaysleft)||"0".equals(prdaysleft)){
                                prdaysleft=prdaysleft+" day left";

                            }
                            else{
                                prdaysleft=prdaysleft+" days left";
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            prdaysleft="N/A";
                        }


                        try {
                            prprice="$"+oneItem.getJSONObject("buyItNowPrice").getString("__value__");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            prprice="N/A";
                        }

                        try {
                            redUrl=oneItem.getString("viewItemURL");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            redUrl="N/A";
                        }


                        simItemModel newItem=new simItemModel(prtitle,prshipping,prdaysleft,prprice,imgurl,redUrl);
                        displayItemsList.add(newItem);
                    }

//                    recyclerAdapter=new simItemAdapter(displayItemsList,getContext());
                    simrecycler.setAdapter(recyclerAdapter);

recyclerAdapter.setSimItemsList(displayItemsList);


                }
                copyList.addAll(displayItemsList);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq= Volley.newRequestQueue(getContext());
        rq.add(request);

    }

    private void populateSpinners(View v, productRecyclerList pro) {
       sortTypeSpinner=v.findViewById(R.id.sortTypeSpinner);
       sortOrderSpinner=v.findViewById(R.id.sortOrderSpinner);
//        ArrayAdapter<CharSequence> adp=ArrayAdapter.createFromResource(this.getActivity(),R.array.categoryArray,android.R.layout.simple_spinner_item);
//        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn.setAdapter(adp);

        ArrayAdapter<CharSequence> adp=ArrayAdapter.createFromResource(getActivity(),R.array.sortTypeArray,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sortOrderAdp=ArrayAdapter.createFromResource(getActivity(),R.array.sortOrder,android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortOrderAdp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortTypeSpinner.setAdapter(adp);
        sortOrderSpinner.setAdapter(sortOrderAdp);
        sortTypeSpinner.setSelection(0);
        sortOrderSpinner.setSelection(0);
        sortOrderSpinner.setEnabled(false);


        sortTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    sortOrderSpinner.setEnabled(true);
                }
                if(position==0){
                    sortOrderSpinner.setEnabled(false);
                    //deep copy
                    recyclerAdapter.setSimItemsList(copyList);
                }
                else if(position==1){
                    sortByName();
                    if(sortOrderSpinner.getSelectedItemPosition()==1){
                        Collections.reverse(displayItemsList);
                    }
                    recyclerAdapter.setSimItemsList(displayItemsList);
                }
                else if(position==2){
                    sortByPrice();
                    if(sortOrderSpinner.getSelectedItemPosition()==1){
                        Collections.reverse(displayItemsList);
                    }
                    recyclerAdapter.setSimItemsList(displayItemsList);
                }
                else if(position==3){
                    sortByDaysLeft();
                    if(sortOrderSpinner.getSelectedItemPosition()==1){
                        Collections.reverse(displayItemsList);
                    }
                    recyclerAdapter.setSimItemsList(displayItemsList);
                }
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(sortTypeSpinner.getSelectedItemPosition()==0){

                    //deep copy
                    recyclerAdapter.setSimItemsList(copyList);

                }
                else if(sortTypeSpinner.getSelectedItemPosition()==1){
                    sortByName();
                    if(position==1){
                        Collections.reverse(displayItemsList);
                    }
                    recyclerAdapter.setSimItemsList(displayItemsList);


                }
                else if(sortTypeSpinner.getSelectedItemPosition()==2){
                    sortByPrice();
                    if(position==1){
                        Collections.reverse(displayItemsList);
                    }
                    recyclerAdapter.setSimItemsList(displayItemsList);

                }
                else{
                    sortByDaysLeft();
                    if(position==1){
                        Collections.reverse(displayItemsList);
                    }
                    recyclerAdapter.setSimItemsList(displayItemsList);
                }
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void sortByPrice() {

        Collections.sort(displayItemsList,(item1,item2)->{

            String s1=item1.getPrice();
            String s2=item2.getPrice();
            if(!("N/A".equals(s1))){
                s1=s1.substring(1);
            }
            else{
                s1="0";
            }
            if(!("N/A".equals(s2))){
                s2=s2.substring(1);
            }
            else{
                s2="0";
            }


            if(Float.parseFloat(s1)>Float.parseFloat(s2)){
                return 1;
            }
            else if(Float.parseFloat(s1)<Float.parseFloat(s2)){
                return -1;
            }
            else{
                return 0;
            }



        });


    }

    private void sortByName() {


        Collections.sort(displayItemsList,(item1,item2)->item1.getProd_title().compareTo(item2.getProd_title()));

    }

    private void sortByDaysLeft(){


        Collections.sort(displayItemsList,(item1,item2)->{

            String s1=item1.getDays_left();
            String s2=item2.getDays_left();

            if(!(("N/A").equals(s1))){
                int index=s1.indexOf('d');
                s1=s1.substring(0,index).trim();
            }
            else {
                s1="0";
            }

            if(!(("N/A").equals(s2))){
                int index=s2.indexOf('d');
                s2=s2.substring(0,index).trim();
            }
            else {
                s2="0";
            }

            if(Integer.parseInt(s1)>Integer.parseInt(s2)){
                return 1;
            }
            else if(Integer.parseInt(s1)<Integer.parseInt(s2)){
                return -1;
            }
            else{
                return 0;
            }



        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
