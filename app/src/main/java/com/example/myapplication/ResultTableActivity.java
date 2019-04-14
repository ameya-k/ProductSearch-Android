package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button wishBtn;
    private int size;
    String prodTitle;
    ProgressBar pb;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_table);

        wishBtn=findViewById(R.id.wishbtn);
        pb=findViewById(R.id.progressBarResult);
        tv=findViewById(R.id.showProductsText);
        pb.setVisibility(View.VISIBLE);
        pb.setVisibility(View.VISIBLE);


        Intent intent=getIntent();
        final ProgressDialog spinwheel=new ProgressDialog(this);

        //spinwheel.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        //spinwheel.setMessage("Searching Products...");

        //spinwheel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //Starting the recycler view process here
        productRecyclerView=findViewById(R.id.productrecyclerview);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        productslist=new ArrayList<>();



        String nodeUrl=intent.getStringExtra("url");
        prodTitle=intent.getStringExtra("kw");
        Log.i("Url in result activty",nodeUrl);
        //spinwheel.show();
        StringRequest resultRequest=new StringRequest(Request.Method.GET, nodeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    //sspinwheel.dismiss();
                try {


                    JSONObject resObj=new JSONObject(response);
                    Log.i("Result json",resObj.toString());
                    pb.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
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

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






    }


    //Logic to implement on back button
    @Override
    public boolean onSupportNavigateUp(){
        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
        return true;
    }

    private void constructRecylerView(JSONObject resObj) {

        try {

            JSONArray resArray=resObj.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).
                    getJSONArray("searchResult").getJSONObject(0).getJSONArray("item");
            JSONObject jb=resArray.getJSONObject(0);
            size=resArray.length();
            TextView reshead=findViewById(R.id.resultCountText);
            String text="Showing "+size+ " results for "+prodTitle;

            reshead.setText(text);
            
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

                String itemid=newObj.getJSONArray("itemId").get(0).toString();


                productRecyclerList list=new productRecyclerList(imgUrl,title,zip,shipping,cond,price,itemid);
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
