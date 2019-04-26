package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class prodInfo extends Fragment {

    productRecyclerList pro;

    ProgressBar spinbar;
    TextView showprodview;
    private LayoutInflater linflater;
    Boolean pr_images_sec,pr_title_sec,pr_price_sec,pr_highlights_sec,pr_spec_sec;
    String brand="default";
    Boolean itemspecificspresent;
    RelativeLayout toplayout;
    //Made changes here
   OnMessageSendListener messageSendListener;
    public interface OnMessageSendListener{

        public void onMessageSend(String message);

    }


    public prodInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment prodInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static prodInfo newInstance(String param1, String param2) {
        prodInfo fragment = new prodInfo();
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
        View v=inflater.inflate(R.layout.fragment_prod_info, container, false);

       // String nodeUrl=getArguments().getString("nodeUrl");
        pro=getArguments().getParcelable("firstData");
        Log.i("title in prod info",pro.getTitle());
        spinbar=v.findViewById(R.id.progressBarProdInfo);
        showprodview=v.findViewById(R.id.showProdInfoText);
        spinbar.setVisibility(View.VISIBLE);
        showprodview.setVisibility(View.VISIBLE);
        linflater=LayoutInflater.from(getActivity());
        toplayout=v.findViewById(R.id.toplayout);
        toplayout.setVisibility(View.INVISIBLE);
        //hidgroup=v.findViewById(R.id.hidgroup);
        callNodeItemDetails(pro, v);
        //check no results condition here




        return v;
    }

//Made changes here
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        messageSendListener=(OnMessageSendListener) activity;
    }

    private void callNodeItemDetails(final productRecyclerList pro, final View v) {

        String callURL="http://ameyanodemodule-dot-ameyabk117-angularweb8.appspot.com/itemDetailsCall/"+pro.getItem_id();
        Log.i("iturlxxx",callURL);

        StringRequest request=new StringRequest(Request.Method.GET, callURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                spinbar.setVisibility(View.GONE);
                showprodview.setVisibility(View.GONE);
                toplayout.setVisibility(View.VISIBLE);

                JSONObject iobj= null;
                try {
                    iobj = new JSONObject(response);

                    //MAde changes here
                    messageSendListener.onMessageSend(iobj.toString());
                    pr_images_sec=constructHorizontalView(iobj, v,pro);
                    pr_title_sec=constructTitleSection(iobj,v,pro);
                    pr_price_sec=constructPriceSection(iobj,v,pro);
                    pr_highlights_sec=constructHighlightSection(iobj,v,pro);
                    pr_spec_sec=constructSpecificsSection(iobj,v,pro);

                    if(!(pr_images_sec||pr_title_sec||pr_price_sec||pr_highlights_sec|pr_spec_sec)){
                        TextView nores=v.findViewById(R.id.noresview);
                        nores.setVisibility(View.VISIBLE);
                    }
                   // Log.i("images sec",""+pr_images_sec);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Constructing various layout components


               //Log.i("itemdetails",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq= Volley.newRequestQueue(getContext());
        rq.add(request);



    }

    private Boolean constructSpecificsSection(JSONObject iobj, View v, productRecyclerList pro) {

        TextView specview=v.findViewById(R.id.specifictextview);

        String specifics_string="";
        specview.setText("");
        if(!("default".equals(brand))){
            specifics_string+="\u2022"+brand+"\n\n";
        }

        try {
            JSONArray ispecifics=iobj.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList");

            for(int i=0;i<ispecifics.length();i++){
                if(!(ispecifics.getJSONObject(i).getString("Name").equals("Brand"))){
                    specifics_string+="\u2022"+ispecifics.getJSONObject(i).getJSONArray("Value").get(0)+"\n\n";

                }

            }
            Log.i("Full text",specifics_string);
            specview.setText(specifics_string);
            Log.i("xxxxx",specview.getText().toString());
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            specview.setVisibility(View.GONE);
            v.findViewById(R.id.linspeclayout).setVisibility(View.GONE);
            return false;
        }


    }

    private Boolean constructHighlightSection(JSONObject iobj, View v, productRecyclerList pro) {

        TextView subtextvalue=v.findViewById(R.id.subtextviewvalue);
        TextView pricetextvalue=v.findViewById(R.id.pricetxtviewvalue);
        TextView brandtextvalue=v.findViewById(R.id.brandtextviewvalue);
        LinearLayout highlayout=v.findViewById(R.id.highlightslayout);
        TableLayout hightab=v.findViewById(R.id.tabhighlight);
        TableRow subtitlerow=v.findViewById(R.id.subtitlerow);
        TableRow pricerow=v.findViewById(R.id.pricerow);
        TableRow brandrow=v.findViewById(R.id.brandrow);
        Boolean flagsubtitle,flagprice,flagbrand=false;




        String subtitle;
        try {
            subtitle=iobj.getJSONObject("Item").getString("Subtitle");
            subtextvalue.setText(subtitle);
            flagsubtitle=true;


            //return false;
        } catch (JSONException e) {
            e.printStackTrace();
            subtitlerow.setVisibility(View.GONE);
            flagsubtitle=false;

        }

        if(("N/A").equals(pro.getProduct_price())){
            flagprice=false;
            pricerow.setVisibility(View.GONE);
        }
        else{
            pricetextvalue.setText(pro.getProduct_price());
            flagprice=true;
        }

        try {
            JSONArray itemspecifics=iobj.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList");

            for(int i=0;i<itemspecifics.length();i++){
                if(itemspecifics.getJSONObject(i).getString("Name").equals("Brand")){
                    brand=itemspecifics.getJSONObject(i).getJSONArray("Value").getString(0);
                    flagbrand=true;
                }
            }
            if(brand.equals("default")){
                flagbrand=false;
                brandrow.setVisibility(View.GONE);
            }else{
                brandtextvalue.setText(brand);
            }

        } catch (JSONException e) {
            itemspecificspresent=false;
            flagbrand=false;
            brandrow.setVisibility(View.GONE);
            e.printStackTrace();
        }

        if(!(flagsubtitle||flagprice||flagbrand)){
            hightab.setVisibility(View.GONE);
            highlayout.setVisibility(View.GONE);
        }

        return flagsubtitle||flagprice||flagbrand;

    }

    private Boolean constructPriceSection(JSONObject iobj, View v, productRecyclerList pro) {

//        TextView tv=v.findViewById(R.id.)
        TextView pr=v.findViewById(R.id.prodprice);
        TextView sh=v.findViewById(R.id.prodship);
        Log.i("pro price:",pro.getProduct_price());
        Log.i("pro ship",pro.getProduct_shipping());
        if(pro.getProduct_price().equals("N/A") && pro.getProduct_shipping().equals("N/A")){

            pr.setVisibility(View.GONE);
            sh.setVisibility(View.GONE);

            return false;

        }
        if(pro.getProduct_price().equals("N/A") && !pro.getProduct_shipping().equals("N/A")){
            pr.setVisibility(View.GONE);
            if(pro.getProduct_shipping().equals("Free Shipping")){
                sh.setText("With "+pro.getProduct_shipping());
            }
            else{
                sh.setText("With "+pro.getProduct_shipping()+" shipping");
            }

        }
        if(pro.getProduct_shipping().equals("N/A") && !pro.getProduct_price().equals("N/A")){
            sh.setVisibility(View.GONE);
            pr.setText(pro.getProduct_price());
        }
        if(!pro.getProduct_shipping().equals("N/A") && !pro.getProduct_price().equals("N/A")){
            pr.setText(pro.getProduct_price());
            if(("Free Shipping").equals(pro.getProduct_shipping())){
                sh.setText("With "+pro.getProduct_shipping());

            }
            else{
                sh.setText("With "+pro.getProduct_shipping()+" shipping");
            }
        }

        return true;
    }

    private Boolean constructTitleSection(JSONObject iobj, View v,productRecyclerList pro) {

        TextView titview=v.findViewById(R.id.prodTitle);

        if(pro.getTitle()!=""){
            titview.setText(pro.getTitle());
            return true;
        }

        return false;
    }

    private boolean constructHorizontalView(JSONObject response, View v,productRecyclerList pro) {

        try {

            if(response.getJSONObject("Item").getJSONArray("PictureURL").length()!=0){
                ArrayList<String> picArray=new ArrayList<>();
                JSONArray pcarray=response.getJSONObject("Item").getJSONArray("PictureURL");
                //Log.i("Horiz response",pcarray.getString(0));
//                for(int i=0;i<pcarray.length();i++){
//                    picArray.add(pcarray.getString(0));
//                    Log.i("Pic string",pcarray.getString(i));
//                }

                HorizontalScrollView hscroll=v.findViewById(R.id.hscrollview);
                LinearLayout hscrolllayout = v.findViewById(R.id.hscrolllayout);
                //Log.i("pcarray size",""+pcarray.length());
                for(int i=0;i<pcarray.length();i++){
                    View vw=linflater.inflate(R.layout.horizimagelayout,hscrolllayout,false);
                    ImageView im=vw.findViewById(R.id.imagehorizview);
//                    Log.i("getiing from pic array",picArray.get(i));
                    //Add try catch if image not found

                    //use fit
//                    Log.i("Loading image",""+(i+1));


                    Glide.with(getActivity()).load(pcarray.getString(i)).fitCenter().listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {



                          return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).error(R.drawable.brimage).into(im);
                    hscrolllayout.addView(im);

                }

                return true;
            }
            else{
                v.findViewById(R.id.hscrollview).setVisibility(View.GONE);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            v.findViewById(R.id.hscrollview).setVisibility(View.GONE);
            return false;
           // Toast.makeText(getActivity(),"view gone",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
