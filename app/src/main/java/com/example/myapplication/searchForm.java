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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;


public class searchForm extends Fragment {

    CheckBox enableBox;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.searchform_layout,container,false);

        //Method to populate spinner
        populateSpinner(v);

        //Hiding and unhiding formfields based on checkbox click

        enableBox=v.findViewById(R.id.nearbyBox);
        final Group hgroup=v.findViewById(R.id.hiddenGroup);
        enableBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hgroup.setVisibility(View.VISIBLE);
                }
                else {
                    hgroup.setVisibility(View.GONE);
                }

            }
        });




        return v;

    }

    private void populateSpinner(View v) {

        Spinner spn=v.findViewById(R.id.category);
        ArrayAdapter<CharSequence> adp=ArrayAdapter.createFromResource(this.getActivity(),R.array.categoryArray,android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adp);

    }

}
