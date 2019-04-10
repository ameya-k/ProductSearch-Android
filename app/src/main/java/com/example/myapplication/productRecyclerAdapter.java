package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class productRecyclerAdapter extends RecyclerView.Adapter<productRecyclerAdapter.ViewHolder> {


    private List<productRecyclerList> productslist;
    private Context con;

    public productRecyclerAdapter(List<productRecyclerList> productslist, Context con) {
        this.productslist = productslist;
        this.con = con;
    }

    @NonNull
    @Override


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productrecycleritem_layout,viewGroup,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

       productRecyclerList prlist=productslist.get(i);

       //we set the values of elements from layout from our list of class objects

        //use picasso to load image here
        Picasso.with(con).load(prlist.getImage_url()).into(viewHolder.productImage);
        viewHolder.productTitle.setText(prlist.getTitle());
        viewHolder.productPrice.setText(prlist.getProduct_price());
        viewHolder.productCondition.setText(prlist.getProduct_condition());
        viewHolder.productShipping.setText(prlist.getProduct_shipping());
        viewHolder.productZip.setText(prlist.getProduct_zip());





    }

    @Override
    public int getItemCount() {
        return productslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //these are the elements from the actual layout

        public ImageView productImage;
        public TextView productTitle;
        public TextView productZip;
        public TextView productShipping;
        public TextView productCondition;
        public TextView productPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImageView);
            productTitle=itemView.findViewById(R.id.productTitleView);
            productZip=itemView.findViewById(R.id.productZipView);
            productShipping=itemView.findViewById(R.id.productShippingView);
            productCondition=itemView.findViewById(R.id.productConditionView);
            productPrice=itemView.findViewById(R.id.productPriceView);
        }
    }
}
