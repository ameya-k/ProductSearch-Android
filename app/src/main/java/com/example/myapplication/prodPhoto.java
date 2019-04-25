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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class prodPhoto extends Fragment {


    productRecyclerList pro;
    View v;

    private RecyclerView photoRecyclerView;
    private RecyclerView.Adapter photoAdapter;
    String itemDetailsData;
    private List<photoModel> photoItems;
    private TextView nophotview;

    public prodPhoto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment prodPhoto.
     */
    // TODO: Rename and change types and number of parameters
    public static prodPhoto newInstance(String param1, String param2) {
        prodPhoto fragment = new prodPhoto();
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

        pro=getArguments().getParcelable("firstData");
        Log.i("XXXXX","in oncreateview");

        v=inflater.inflate(R.layout.fragment_prod_photo, container, false);


        photoRecyclerView=v.findViewById(R.id.photoRecyclerView);
        nophotview=v.findViewById(R.id.noPhotView);




        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void setItemDetails(String dataFromActivity){
        Log.i("XXXXX","in setItemdetails");

        this.itemDetailsData=dataFromActivity;
        Log.i("Item details-photo",itemDetailsData);


        photoRecyclerView.setHasFixedSize(true);
        photoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        photoItems=new ArrayList<>();
        loadRecyclerView(dataFromActivity);



    }

    private void loadRecyclerView(String dataFromACtivity) {
        String title="";
        try {
            JSONObject objFromActivity=new JSONObject(dataFromACtivity);
            title=objFromActivity.getJSONObject("Item").getString("Title");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        String googleUrl="http://ameyanodemodule-dot-ameyabk117-angularweb8.appspot.com/googleCall/"+ URLEncoder.encode(title);
        Log.i("goog",googleUrl);
        StringRequest request=new StringRequest(Request.Method.GET, googleUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject photoresult=new JSONObject(response);
                    JSONArray items = photoresult.getJSONArray("items");

                    for(int i=0;i<items.length();i++){
                        String url=items.getJSONObject(i).getString("link");
                        Log.i("Photo url",url);
                        photoModel model=new photoModel(url);
                        photoItems.add(model);
                    }

                    photoAdapter=new photoAdapter(photoItems,getContext());
                    photoRecyclerView.setAdapter(photoAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    nophotview.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq= Volley.newRequestQueue(getContext());
        rq.add(request);


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
