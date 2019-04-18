package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class wishlist extends Fragment {


    private RecyclerView wishRecyclerView;
    private wishAdapter wishadp;

    private List<productRecyclerList> wishlist_array;
    View v;
    SharedPreferences sp;
    Gson gson;
    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wishlist_layout, container, false);

        wishRecyclerView = v.findViewById(R.id.wishlistRecyclerView);
        wishRecyclerView.setHasFixedSize(true);
        wishRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        wishlist_array = new ArrayList<>();
        wishadp = new wishAdapter(wishlist_array, getActivity());


        onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                // Implementation
                List<productRecyclerList> list =
                        sp.getAll().keySet().
                                stream().
                                map(w -> sp.getString(w, "")).
                                map(w -> gson.fromJson(w, productRecyclerList.class)).collect(Collectors.toList());
                wishlist_array.clear();
                wishlist_array.addAll(list);
                wishadp.setWishlist(wishlist_array);
                wishadp.notifyDataSetChanged();


            }
        };


        wishRecyclerView.setAdapter(wishadp);


         sp = v.getContext().getSharedPreferences("WISHLIST_ITEMS", Context.MODE_PRIVATE);
         //sp.edit().clear().commit();
        sp.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);


        gson = new Gson();
        Map<String, ?> hm = sp.getAll();

        List<productRecyclerList> list =
                sp.getAll().keySet().stream().map(w -> sp.getString(w, "")).map(w -> gson.fromJson(w, productRecyclerList.class)).collect(Collectors.toList());
        wishlist_array.addAll(list);
        wishadp.notifyDataSetChanged();
        System.out.println("count" + wishlist_array.size());
        System.out.println(wishlist_array);


        return v;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();

        List<productRecyclerList> list =
                sp.getAll().keySet().stream().map(w -> sp.getString(w, "")).map(w -> gson.fromJson(w, productRecyclerList.class)).collect(Collectors.toList());
        Log.i("List size",""+list.size());
        ((wishAdapter) wishadp).setWishlist(list);
        sp.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        wishadp.notifyDataSetChanged();

    }

}
