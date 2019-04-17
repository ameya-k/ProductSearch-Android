package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class wishAdapter extends RecyclerView.Adapter<wishAdapter.ViewHolder> {

    private List<wishModel> wishlist;
    private Context con;

    public wishAdapter(List<wishModel> wishlist, Context con) {
        this.wishlist = wishlist;
        this.con = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final wishModel prlist=wishlist.get(i);

        Picasso.with(con).load(prlist.getWish_image_url()).into(viewHolder.wishproductImage);
        viewHolder.wishproductTitle.setText(prlist.getWish_title());
        viewHolder.wishproductPrice.setText(prlist.getWish_product_price());
        viewHolder.wishproductCondition.setText(prlist.getWish_product_condition());
        viewHolder.wishproductShipping.setText(prlist.getWish_product_shipping());
        viewHolder.wishproductZip.setText(prlist.getWish_product_zip());


    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView wishproductImage;
        public TextView wishproductTitle;
        public TextView wishproductZip;
        public TextView wishproductShipping;
        public TextView wishproductCondition;
        public TextView wishproductPrice;
        public LinearLayout wishlLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishproductImage=itemView.findViewById(R.id.wishProductImageView);
            wishproductTitle=itemView.findViewById(R.id.wishProductTitleTextView);
            wishproductZip=itemView.findViewById(R.id.wishProductZipTextView);
            wishproductShipping=itemView.findViewById(R.id.wishProductShippingTextView);
            wishproductCondition=itemView.findViewById(R.id.wishProductConditionTextView);
            wishproductPrice=itemView.findViewById(R.id.wishProductPriceTextView);
            wishlLayout=itemView.findViewById(R.id.wishlistContainer);


        }
    }
}
