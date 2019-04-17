package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class wishlist extends Fragment {


    private RecyclerView wishRecyclerView;
    private RecyclerView.Adapter wishAdapter;

    private List<wishModel> wishlist_array;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.wishlist_layout, container, false);

        wishRecyclerView=v.findViewById(R.id.wishlistRecyclerView);
        wishRecyclerView.setHasFixedSize(true);
        wishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        wishlist_array=new ArrayList<>();

//        wishModel w=new wishModel("https://pixabay.com/photos/rose-blue-flower-bloom-romance-165819/","temp","temp","temp",
//                "temp","temp","temp");
//
//        wishModel w1=new wishModel("https://pixabay.com/photos/rose-blue-flower-bloom-romance-165819/","temp","temp","temp",
//                "temp","temp","temp");
//        wishlist_array.add(w);
////        wishlist_array.add(w1);







        wishAdapter=new wishAdapter(wishlist_array,getContext());
        wishRecyclerView.setAdapter(wishAdapter);
        wishRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return v;

    }
}
