package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class photoAdapter extends RecyclerView.Adapter<photoAdapter.ViewHolder> {


   private List<photoModel> photoList;
   private Context con;

    public photoAdapter(List<photoModel> photoList, Context con) {
        this.photoList = photoList;
        this.con = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

         photoModel temModel=photoList.get(i);
         Picasso.with(con).load(temModel.getImageLink()).error(R.drawable.brimage).into(viewHolder.photocard);

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView photocard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photocard=itemView.findViewById(R.id.photoImageView);
        }
    }

}
