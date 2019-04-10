package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class searchForm extends Fragment {

    CheckBox enableBox;
    Button searchBtn;
    EditText keyword;
    RadioButton zipBtn;
    EditText zipText;
    TextView kerror;
    TextView zerror;
    boolean zipFault;
    boolean kFault;
    Button clr;
    String spinnerValue;
    String curloc;
    Spinner categ;
    CheckBox nw;
    CheckBox used;
    CheckBox unspecified;
    CheckBox free;
    CheckBox local;
    CheckBox nbox;
    EditText mileT;
    RadioButton curBtn;
    HashMap<String,String> categMap;
    RadioGroup rg;



    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.searchform_layout,container,false);

        enableBox=v.findViewById(R.id.nearbyBox);
        final Group hgroup=v.findViewById(R.id.hiddenGroup);
        searchBtn=v.findViewById(R.id.submitBtn);
        keyword=(EditText)v.findViewById(R.id.kword);
        zipBtn=v.findViewById(R.id.zip_loc);
        zipText=v.findViewById(R.id.zipcode);
        kerror=v.findViewById(R.id.kworderror);
        zerror=v.findViewById(R.id.ziperror);
        zerror.setVisibility(View.INVISIBLE);
        nbox=v.findViewById(R.id.nearbyBox);
        zipFault=false;
        kFault=false;
        clr=v.findViewById(R.id.clearBtn);
        categ=v.findViewById(R.id.category);
        nw=v.findViewById(R.id.newBox);
        used=v.findViewById(R.id.usedBox);
        unspecified=v.findViewById(R.id.unspecifiedBox);
        free=v.findViewById(R.id.freeBox);
        local=v.findViewById(R.id.localBox);
        mileT=v.findViewById(R.id.miles);
        curBtn=v.findViewById(R.id.current_loc);
        rg=v.findViewById(R.id.radioGroup);


        StringRequest iprequest=new StringRequest(Request.Method.GET, "http://ip-api.com/json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject ipobject=new JSONObject(response);
                    curloc=ipobject.getString("zip");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq= Volley.newRequestQueue(getActivity());
        rq.add(iprequest);

        //Method to populate spinner and create hashmap for it
        populateSpinner(v);
        createSpinnerMap();

        categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue=categMap.get(parent.getItemAtPosition(position).toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerValue=categMap.get("All");
            }
        });




        //Hiding and unhiding formfields based on checkbox click
        enableBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hgroup.setVisibility(View.VISIBLE);
                    zerror.setVisibility(View.INVISIBLE);
                }
                else {
                    hgroup.setVisibility(View.GONE);
                }

            }
        });

        //On click listener for search button
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the API
                if(validateForm(keyword,zipBtn,zipText)){

                    if(kerror.getVisibility()==View.VISIBLE){
                        kerror.setVisibility(View.INVISIBLE);
                    }
                   if(zerror.getVisibility()==View.VISIBLE){
                       zerror.setVisibility(View.GONE);
                   }


//                    Toast successToast=Toast.makeText(getActivity(),"Valid form",Toast.LENGTH_SHORT);
//                    successToast.show();

                    String callUrl=constructSearchTableUrl();

                    Intent goToresult=new Intent(getActivity(),ResultTableActivity.class);
                    goToresult.putExtra("url",callUrl);
                    startActivity(goToresult);

                    Log.i("url call:",callUrl);


                }
                //Error displaying
                else{

                    //make a toast
                    Toast errorToast=Toast.makeText(getActivity(),"Please fix all fields with errors",Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        });

        //clr.setOnClickListener(new View.);

        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearForm();
            }

        });




        return v;

    }

    private void createSpinnerMap() {
        categMap=new HashMap<String,String>();
        categMap.put("All","-1");
        categMap.put("Art","550");
        categMap.put("Baby","2984");
        categMap.put("Books","267");
        categMap.put("Clothing,shoes&Accessories","11450");
        categMap.put("Computers,Tablets&Networking","58058");
        categMap.put("Health&Beauty","26395");
        categMap.put("Music","11233");
        categMap.put("Video Games&Consoles","1249");
    }

    private String constructSearchTableUrl() {
        String url="http://ameyanodemodule-dot-ameyabk117-angularweb8.appspot.com/ebaySearchTable/";
        url+="kword="+keyword.getText().toString();
        url+="&category="+spinnerValue;
        url+="&conditionNew="+nw.isChecked();
        url+="&conditionUsed="+used.isChecked();
        url+="&conditionUnspecified="+unspecified.isChecked();
        url+="&shippingLocal="+local.isChecked();
        url+="&shippingFree="+free.isChecked();
        url+="&distance="+mileT.getText().toString();
        url+="&zipcode="+zipText.getText().toString();
        if(curBtn.isChecked()){
            url+="&location=current";
        }
        else{
            url+="&location=zip";
        }

        url+="&currentlocation="+curloc;
        return url;


    }

    private void clearForm() {

        keyword.setText("");
        categ.setSelection(0);
        nw.setChecked(false);
        used.setChecked(false);
        unspecified.setChecked(false);
        local.setChecked(false);
        free.setChecked(false);
        nbox.setChecked(false);
        mileT.setText("");
        curBtn.setChecked(true);
        zipBtn.setChecked(false);
        zipText.setText("");

        zerror.setVisibility(View.GONE);
        kerror.setVisibility(View.INVISIBLE);
    }

    private boolean validateForm(EditText keyword,RadioButton zipBtn,EditText zipText) {

//        EditText keyword=v.findViewById(R.id.kword);
//        RadioButton zipBtn=v.findViewById(R.id.zip_loc);
//        EditText zipText=v.findViewById(R.id.zipcode);
        if(keyword.getText().toString()==""||keyword.getText().toString().trim().length()==0){
            kFault=true;
            kerror.setVisibility(View.VISIBLE);


        }
        else {
            kerror.setVisibility(View.INVISIBLE);
            kFault=false;
        }

        if(nbox.isChecked() &&zipBtn.isChecked()&& (zipText.getText().toString()==""||zipText.getText().toString().trim().length()==0) ){
            zerror.setVisibility(View.VISIBLE);
            zipFault=true;


        }
        else {
            zerror.setVisibility(View.GONE);
            zipFault=false;
        }

        if(kFault||zipFault){
            return false;
        }




        return true;
    }

    private void populateSpinner(View v) {

        Spinner spn=v.findViewById(R.id.category);
        ArrayAdapter<CharSequence> adp=ArrayAdapter.createFromResource(this.getActivity(),R.array.categoryArray,android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adp);

    }

}
