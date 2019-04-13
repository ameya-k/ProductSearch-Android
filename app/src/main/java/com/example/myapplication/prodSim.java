package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class prodSim extends Fragment {

productRecyclerList pro;
View v;

Spinner sortOrderSpinner,sortTypeSpinner;

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

        pro=getArguments().getParcelable("firstData");
        Log.i("title in sim:",pro.getTitle());
        v=inflater.inflate(R.layout.fragment_prod_sim, container, false);
        populateSpinners(v,pro);



        return v;
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
