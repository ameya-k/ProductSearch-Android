package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

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
import java.util.List;

public class ResultTableActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;

    private RecyclerView.Adapter adapter;
    private List<productRecyclerList> productslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_table);

        Intent intent=getIntent();
        final ProgressDialog spinwheel=new ProgressDialog(this);
        spinwheel.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        spinwheel.setMessage("Searching Products...");

        spinwheel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Starting the recycler view process here
        productRecyclerView=findViewById(R.id.productrecyclerview);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        productslist=new ArrayList<>();



        String nodeUrl=intent.getStringExtra("url");
        Log.i("Url in result activty",nodeUrl);
        spinwheel.show();
        StringRequest resultRequest=new StringRequest(Request.Method.GET, nodeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    spinwheel.dismiss();
                try {


                    JSONObject resObj=new JSONObject(response);
                    Log.i("Result json",resObj.toString());

                    constructRecylerView(resObj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        rq.add(resultRequest);








    }

    private void constructRecylerView(JSONObject resObj) {

        try {

            JSONArray resArray=resObj.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).
                    getJSONArray("searchResult").getJSONObject(0).getJSONArray("item");
            JSONObject jb=resArray.getJSONObject(0);
            Log.i("demo json",""+jb.getJSONArray("title").get(0).toString());



            for(int i=0;i<resArray.length();i++){
                JSONObject newObj=resArray.getJSONObject(i);

                //Surround each with try catch
                String imgUrl="";
                try {
                    imgUrl = newObj.getJSONArray("galleryURL").get(0).toString();
                }catch (JSONException je){

                }
                String title="";
                try {
                    title = newObj.getJSONArray("title").get(0).toString();
                }catch (JSONException je){
                    title="N/A";
                }
                String zip="";
                try {
                    zip = "Zip: "+ newObj.getJSONArray("postalCode").get(0).toString();
                } catch (JSONException je){
                    zip="Zip: "+"N/A";
                }
                String shipping="";
                try {
                    shipping = newObj.getJSONArray("shippingInfo").getJSONObject(0).
                            getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__");
                }catch (JSONException je){
                    shipping="N/A";
                }
                if(shipping.equalsIgnoreCase("0")||shipping.equalsIgnoreCase("0.0")){
                    shipping="Free Shipping";
                }
                else{
                    shipping="$"+shipping;
                }
                //Log.i("ship value",shipping);
                String cond;
                try{
                cond=newObj.getJSONArray("condition").getJSONObject(0).
                        getJSONArray("conditionDisplayName").get(0).toString();
                }catch (JSONException je){
                    cond="N/A";
                }
               // Log.i("cond",cond);
                String price="";
                try {
                    price = "$" + newObj.getJSONArray("sellingStatus").getJSONObject(0).
                            getJSONArray("currentPrice").getJSONObject(0).getString("__value__");
                }catch (JSONException je){
                    price="N/A";
                }
                productRecyclerList list=new productRecyclerList(imgUrl,title,zip,shipping,cond,price);
                productslist.add(list);
                //productRecyclerList newList=new productRecyclerList()
            }
            adapter=new productRecyclerAdapter(productslist,getApplicationContext());
            productRecyclerView.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
