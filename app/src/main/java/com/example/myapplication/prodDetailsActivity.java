package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

import java.net.URLEncoder;

public class prodDetailsActivity extends AppCompatActivity implements prodInfo.OnMessageSendListener {

    Toolbar tb;
    String fbhref;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String nodeRequestUrl;
    //productRecyclerList obj;
    Bundle obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(3);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.getTabAt(0).setIcon(R.drawable.pinfoicon);
        tabLayout.getTabAt(1).setIcon(R.drawable.shipunselect);
        tabLayout.getTabAt(2).setIcon(R.drawable.googleunslected);
        tabLayout.getTabAt(3).setIcon(R.drawable.simunselect);



        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        tb=findViewById(R.id.toolbarProduct);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    tabLayout.getTabAt(0).setIcon(R.drawable.pinfoicon);
                }
                if(tab.getPosition()==1){
                    tabLayout.getTabAt(1).setIcon(R.drawable.shiptabicon);
                }
                if(tab.getPosition()==2){
                    tabLayout.getTabAt(2).setIcon(R.drawable.gicon);

                }
                if(tab.getPosition()==3){
                    tabLayout.getTabAt(3).setIcon(R.drawable.simicon);
                }

                super.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                if(tab.getPosition()==0){
                        tabLayout.getTabAt(0).setIcon(R.drawable.infounselect);
                }
                if(tab.getPosition()==1){
                        tabLayout.getTabAt(1).setIcon(R.drawable.shipunselect);
                }
                if(tab.getPosition()==2){
                        tabLayout.getTabAt(2).setIcon(R.drawable.googleunslected);

                }
                if(tab.getPosition()==3){
                        tabLayout.getTabAt(3).setIcon(R.drawable.simunselect);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });





       //obj=getIntent().getExtras().getParcelable("firstData");
        //Log.i("Data from first:",""+obj.getTitle());
        obj=getIntent().getExtras();

        final productRecyclerList tem=obj.getParcelable("firstData");
        setSupportActionBar(tb);
        tb.setTitle(tem.getTitle());

        findViewById(R.id.fbbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.facebook.com/dialog/share?app_id= 1028804487317941&display=popup"+"&quote="+"Buy "+ URLEncoder.encode(tem.getTitle());
                url+=" at "+tem.getProduct_price()+"&href="+fbhref+"&hashtag=%23CSCI571Spring2019Ebay";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //nodeRequestUrl=" S
        // "+obj.getItem_id();
        //callItemDetails(nodeRequestUrl);





    }

//    private void callItemDetails(String nodeRequestUrl) {
//
//        StringRequest request=new StringRequest(Request.Method.GET, nodeRequestUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject job=new JSONObject(response);
//                    Log.i("hello","inhere");
//                    Log.i("Item details JSON:",job.toString());
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue rq= Volley.newRequestQueue(this);
//        rq.add(request);
//
//
//    }


    //Logic to implement on back button
    @Override
    public boolean onSupportNavigateUp(){
//        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageSend(String message) {

        try {
            JSONObject job=new JSONObject(message);
            fbhref=job.getJSONObject("Item").getString("ViewItemURLForNaturalSearch");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ViewPager pager=findViewById(R.id.container);
        FragmentPagerAdapter adp=(FragmentPagerAdapter)pager.getAdapter();
          prodShip shipfragment=(prodShip)adp.instantiateItem(pager,1);
          shipfragment.setItemData(message);

        Log.i("XXXXX","pre");

        FragmentPagerAdapter photadp=(FragmentPagerAdapter)pager.getAdapter();
        prodPhoto photofragment=(prodPhoto)photadp.instantiateItem(pager,2);
        photofragment.setItemDetails(message);


        Log.i("XXXXX","post");

          Log.i("Data in Activity",message);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_prod_details, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f=null;
            switch (position){
                case 0:
                   f=new prodInfo();
                   // Bundle b=new Bundle();
                   // b.putString("nodeUrl",nodeRequestUrl);

                    f.setArguments(obj);

                    break;
                case 1:
                    f=new prodShip();

                    f.setArguments(obj);
                    break;
                case 2:
                    f=new prodPhoto();
                    f.setArguments(obj);
                    break;
                case 3:
                   f=new prodSim();
                   f.setArguments(obj);
                    break;
            }
            return f;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
