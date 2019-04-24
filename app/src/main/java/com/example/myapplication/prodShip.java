package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.wssholmes.stark.circular_score.CircularScoreView;

import org.json.JSONException;
import org.json.JSONObject;

public class prodShip extends Fragment {
    LayoutInflater inflater;
    productRecyclerList pro;
    String itemData;
    View v;
    Boolean ship_sold,ship_info,ship_return;

    public prodShip() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment prodShip.
     */
    // TODO: Rename and change types and number of parameters
    public static prodShip newInstance(String param1, String param2) {
        prodShip fragment = new prodShip();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void setItemData(String iD){
        Log.i("data in  ship",iD);
        this.itemData=iD;

////      TextView tv= v.findViewById(R.id.shipchatextview);
////      tv.setText(iD);
////      Log.i("from ship text",tv.getText().toString());
//
//        Log.i("data from ist ship",pro.getProduct_shipping());

        ship_sold=constructSoldBySection(iD,pro,v);
        ship_info=constructShipInfo(iD,pro,v);
        ship_return=constructShipReturn(iD,pro,v);


        if(!(ship_sold||ship_info||ship_return)){
            //Set no results here
            TextView nores=v.findViewById(R.id.noresshipview);
            nores.setVisibility(View.VISIBLE);



        }





    }

    private Boolean constructShipReturn(String iD, productRecyclerList pro, View v) {

        Boolean flag_policy,flag_returns,flag_refund,flag_shippedBy;

        TextView policytext=v.findViewById(R.id.policytextvalue);
        TextView rwithintext=v.findViewById(R.id.returnswithintextvalue);
        TextView reftext=v.findViewById(R.id.refundtextvalue);
        TextView shipbytext=v.findViewById(R.id.shippedbytextvalue);
        TableRow policyrow=v.findViewById(R.id.policyrow);
        TableRow retwithinrow=v.findViewById(R.id.returnsrow);
        TableRow refundrow=v.findViewById(R.id.refundrow);
        TableRow shippedbyrow=v.findViewById(R.id.shippedbyrow);

        try{


            JSONObject itemDetObj=new JSONObject(iD);

            String policyvalue="";
            try{

                policyvalue=itemDetObj.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("ReturnsAccepted");
                policytext.setText(policyvalue);
                flag_policy=true;


            }catch (JSONException je){
                flag_policy=false;
                policyrow.setVisibility(View.GONE);
            }

            String returnswithinvalue="";

            try{
                returnswithinvalue=itemDetObj.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("ReturnsWithin");
                rwithintext.setText(returnswithinvalue);
                flag_returns=true;

            }catch (JSONException je){
                flag_returns=false;
                retwithinrow.setVisibility(View.GONE);
            }

            String refmode="";
            try{
                refmode=itemDetObj.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("Refund");
                reftext.setText(refmode);
                flag_refund=true;
            }
            catch (JSONException je){
                flag_refund=false;
                refundrow.setVisibility(View.GONE);
            }

            String shippedbyname;
            try{
                shippedbyname=itemDetObj.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("ShippingCostPaidBy");
                shipbytext.setText(shippedbyname);
                flag_shippedBy=true;

            }catch (JSONException je){
                flag_shippedBy=false;
                shippedbyrow.setVisibility(View.GONE);
            }



            if(!(flag_policy||flag_returns||flag_refund||flag_shippedBy)){
                v.findViewById(R.id.shipreturnheading).setVisibility(View.GONE);
                v.findViewById(R.id.shipreturntab).setVisibility(View.GONE);
            }

            return flag_policy||flag_refund||flag_returns||flag_shippedBy;

        }catch (JSONException je){
            v.findViewById(R.id.shipreturnheading).setVisibility(View.GONE);
            v.findViewById(R.id.shipreturntab).setVisibility(View.GONE);
            return false;
        }




    }

    private Boolean constructShipInfo(String iD, productRecyclerList pro, View v) {

        Boolean flag_shipcost,flag_globship,flag_handtime,flag_condition;

        try{
            JSONObject itemDetObj=new JSONObject(iD);
            TextView shipcost=v.findViewById(R.id.shipcosttextviewvalue);
            TextView globship=v.findViewById(R.id.globalshippingtextvalue);
            TextView handtime=v.findViewById(R.id.handlingtimetextvalue);
            TextView cond=v.findViewById(R.id.shipconditiontextvalue);
            TableRow shipcostrow=v.findViewById(R.id.shippingcostrow);
            TableRow globrow=v.findViewById(R.id.globalshipppingrow);
            TableRow handrow=v.findViewById(R.id.handlingtimerow);
            TableRow condrow=v.findViewById(R.id.conditionrow);

            if("N/A".equals(pro.getProduct_shipping())){
                flag_shipcost=false;
                shipcostrow.setVisibility(View.GONE);
            }
            else {
                shipcost.setText(pro.getProduct_shipping());
                flag_shipcost=true;
            }

            String globshiptext;
            try {
                String temp=itemDetObj.getJSONObject("Item").getString("GlobalShipping");
                if("false".equals(temp)){
                    globshiptext="Yes";
                    flag_globship=true;
                }
                else{
                    globshiptext="No";
                    flag_globship=true;
                }
                globship.setText(globshiptext);
            }
            catch (JSONException j){

                flag_globship=false;
                globrow.setVisibility(View.GONE);

            }

            String handletime="";

            try{
                handletime=itemDetObj.getJSONObject("Item").getString("HandlingTime");

                //Log.i("handletime here",handletime);
                if("0".equals(handletime)|| "1".equals(handletime)){
                    handtime.setText(handletime+"day");
                }
                else{
                    handtime.setText(handletime+"days");
                }

                flag_handtime=true;

            }catch (JSONException je){
                flag_handtime=false;
                handrow.setVisibility(View.GONE);
            }

            String condition="";

            try{
               condition=itemDetObj.getJSONObject("Item").getString("ConditionDisplayName");
               Log.i("Getting condition",condition);
               cond.setText(itemDetObj.getJSONObject("Item").getString("ConditionDisplayName"));
               flag_condition=true;
            }
            catch (JSONException je){
                flag_condition=false;
                condrow.setVisibility(View.GONE);

            }

            if(!(flag_condition||flag_globship||flag_handtime||flag_shipcost)){
                v.findViewById(R.id.shipinfotable).setVisibility(View.GONE);
                v.findViewById(R.id.shipinfoheading).setVisibility(View.GONE);
            }

            return flag_condition||flag_globship||flag_handtime||flag_shipcost;









        }catch (JSONException je){
            v.findViewById(R.id.shipinfotable).setVisibility(View.GONE);
            v.findViewById(R.id.shipinfoheading).setVisibility(View.GONE);
            return false;
        }

    }

    private Boolean constructSoldBySection(String iD, productRecyclerList pro, View v) {
        Boolean flag_storename,flag_feedbackscore,flag_popularity,flag_star;
        try {
            JSONObject itemDetObj=new JSONObject(iD);
            TextView sname=v.findViewById(R.id.storenametextvalue);
            TextView fscore=v.findViewById(R.id.feedbackscoretextvalue);
            TableRow fscorerow=v.findViewById(R.id.feedbackscorerow);
            TableRow snamerow=v.findViewById(R.id.storenamerow);
            TableRow poprow=v.findViewById(R.id.popularityrow);
            CircularScoreView cv=v.findViewById(R.id.circ_progress);


            String storeName="";

            try{
                storeName=itemDetObj.getJSONObject("Item").getJSONObject("Storefront").getString("StoreName");
                sname.setText(storeName);
                flag_storename=true;

            }catch (JSONException je){

                flag_storename=false;
                snamerow.setVisibility(View.GONE);

            }

            String feedscore="";
            try {
                feedscore=itemDetObj.getJSONObject("Item").getJSONObject("Seller").getString("FeedbackScore");
                fscore.setText(feedscore);
                flag_feedbackscore=true;
            }
            catch (JSONException j){
                flag_feedbackscore=false;
                fscorerow.setVisibility(View.GONE);
            }

            String poppercent;
            try {
                poppercent=itemDetObj.getJSONObject("Item").getJSONObject("Seller").getString("PositiveFeedbackPercent");
                Log.i("Pop percent",poppercent);
                int score=(int)Float.parseFloat(poppercent);
                cv.setScore(score);

                flag_popularity=true;

            }catch (JSONException j){
                flag_popularity=false;
                poprow.setVisibility(View.GONE);
                Log.i("No pop percent","found");

            }

            String feedback_star_color;
            ImageView starimg=v.findViewById(R.id.starimage);
            TableRow starrow=v.findViewById(R.id.feedbackstarrow);
            try{

                int index=itemDetObj.getJSONObject("Item").getJSONObject("Seller").getString("FeedbackRatingStar").indexOf("Shooting");
                if(index!=-1){
                    feedback_star_color=itemDetObj.getJSONObject("Item").getJSONObject("Seller").getString("FeedbackRatingStar").substring(0,index);
                }
                else{
                    feedback_star_color=itemDetObj.getJSONObject("Item").getJSONObject("Seller").getString("FeedbackRatingStar");
                }

                if(index==-1){
                   // Drawable d=getContext().getResources().getDrawable(R.drawable.star_circle);
                  //  d.setTint(feedback_star_color);
                    Drawable d= ContextCompat.getDrawable(getContext(),R.drawable.star_circle_outline);
                    Drawable wd= DrawableCompat.wrap(d);
                    if(feedback_star_color.equals("Yellow")){
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Yellow));
                    }
                    else if(feedback_star_color.equals("Blue")){
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Blue));
                    }
                    else if(feedback_star_color.equals("Green")){
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Green));
                    }
                    else if(feedback_star_color.equals("Turquoise")){
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Turquoise));
                    }
                    else if(feedback_star_color.equals("Red")){
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Red));
                    }
                    else if(feedback_star_color.equals("Purple")){
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Purple));
                    }
                    else {
                        DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),R.color.Black));

                    }
                    //DrawableCompat.setTint(wd,ContextCompat.getColor(getContext(),));

                    starimg.setImageResource(R.drawable.star_circle_outline);
                }
                else{
                    Drawable d1= ContextCompat.getDrawable(getContext(),R.drawable.star_circle);
                    Drawable wd1= DrawableCompat.wrap(d1);
                    if(feedback_star_color.equals("Yellow")){
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Yellow));
                    }
                    else if(feedback_star_color.equals("Blue")){
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Blue));
                    }
                    else if(feedback_star_color.equals("Green")){
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Green));
                    }
                    else if(feedback_star_color.equals("Turquoise")){
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Turquoise));
                    }
                    else if(feedback_star_color.equals("Red")){
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Red));
                    }
                    else if(feedback_star_color.equals("Purple")){
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Purple));
                    }
                    else {
                        DrawableCompat.setTint(wd1,ContextCompat.getColor(getContext(),R.color.Black));

                    }
                    starimg.setImageResource(R.drawable.star_circle);
                }


                flag_star=true;






            }catch (JSONException je){
                flag_star=false;
                starrow.setVisibility(View.GONE);
            }

            if(!(flag_storename||flag_feedbackscore||flag_popularity||flag_star)){
                v.findViewById(R.id.soldbyheading).setVisibility(View.GONE);
                v.findViewById(R.id.tabsoldby).setVisibility(View.GONE);
            }


            return flag_storename|flag_feedbackscore||flag_popularity||flag_star;

        } catch (JSONException e) {
            e.printStackTrace();
            v.findViewById(R.id.soldbyheading).setVisibility(View.GONE);
            v.findViewById(R.id.tabsoldby).setVisibility(View.GONE);
            return false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pro=getArguments().getParcelable("firstData");
        Log.i("title in shipping:",pro.getTitle());
        v=inflater.inflate(R.layout.fragment_prod_ship, container, false);
        //Log.i("item data in ship",itemData);
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
