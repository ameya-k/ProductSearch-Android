package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
    CheckBox nbox;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.searchform_layout,container,false);

        //Method to populate spinner
        populateSpinner(v);

        //Hiding and unhiding formfields based on checkbox click

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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the API
                if(validateForm(keyword,zipBtn,zipText)){
                    //kerror.setVisibility(View.INVISIBLE);
                    //starting changes now
                    if(kerror.getVisibility()==View.VISIBLE){
                        kerror.setVisibility(View.INVISIBLE);
                    }
                   if(zerror.getVisibility()==View.VISIBLE){
                       zerror.setVisibility(View.GONE);
                   }


                    Toast successToast=Toast.makeText(getActivity(),"Valid form",Toast.LENGTH_SHORT);
                    successToast.show();

                }
                //Error displaying
                else{

                    //make a toast
//                    if(kFault){
//                        kerror.setVisibility(View.VISIBLE);
//                        kFault=false;
//                    }
//
//                    if(zipFault){
//                        zerror.setVisibility(View.VISIBLE);
//                        zipFault=false;
//                    }


                    Toast errorToast=Toast.makeText(getActivity(),"Please fix all fields with errors",Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        });




        return v;

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
