package com.example.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class zipAutoCall {

    private static zipAutoCall mInstance;

    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public zipAutoCall(Context ctx){
        this.mCtx=ctx;
        //mRequestQueue
    }


    public static synchronized zipAutoCall getInstance(Context context){
        if(mInstance==null){
            mInstance=new zipAutoCall(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            mRequestQueue=Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public<T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

    public static void make(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url =
                "http://api.geonames.org/postalCodeSearchJSON?username=ameya_k&country=US&maxRows=5&postalcode_startsWith=" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        zipAutoCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }











    }
