package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class wishAdapter extends RecyclerView.Adapter<wishAdapter.ViewHolder> {

    private List<productRecyclerList> wishlist;
    Gson gson;

    public void setWishlist(List<productRecyclerList> wishlist) {
        this.wishlist = wishlist;
    }

    public List<productRecyclerList> getWishlist() {
        return wishlist;
    }

    private Context con;

    public wishAdapter(List<productRecyclerList> wishlist, Context con) {
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
//
        final productRecyclerList prlist=wishlist.get(i);


        Glide.with(con).load(prlist.getImage_url()).error(R.drawable.brimage).into(viewHolder.wishproductImage);
        viewHolder.wishproductTitle.setText(prlist.getTitle());
        viewHolder.wishproductPrice.setText(prlist.getProduct_price());
        viewHolder.wishproductCondition.setText(prlist.getProduct_condition());
        viewHolder.wishproductShipping.setText(prlist.getProduct_shipping());
        viewHolder.wishproductZip.setText(prlist.getProduct_zip());

        SharedPreferences sp=con.getSharedPreferences("WISHLIST_ITEMS",Context.MODE_PRIVATE);

            viewHolder.wishBtnThis.setBackground(con.getResources().getDrawable(R.drawable.removewishlist));


            viewHolder.wishBtnThis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Log.v("abc","clciked");

                    sp.edit().remove(prlist.getItem_id()).commit();
                    Toast.makeText(con,prlist.getTitle().substring(0,prlist.getTitle().length()-10)+"..."+ "removed from wishlist",Toast.LENGTH_SHORT).show();


                }
            });
            viewHolder.wishlLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(v.getContext(),prodDetailsActivity.class);
                    i.putExtra("firstData",prlist);
                    v.getContext().startActivity(i);
                }
            });




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
        public Button wishBtnThis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishproductImage=itemView.findViewById(R.id.wishProductImageView);
            wishproductTitle=itemView.findViewById(R.id.wishProductTitleTextView);
            wishproductZip=itemView.findViewById(R.id.wishProductZipTextView);
            wishproductShipping=itemView.findViewById(R.id.wishProductShippingTextView);
            wishproductCondition=itemView.findViewById(R.id.wishProductConditionTextView);
            wishproductPrice=itemView.findViewById(R.id.wishProductPriceTextView);
            wishlLayout=itemView.findViewById(R.id.wishlistContainer);
            wishBtnThis=itemView.findViewById(R.id.wishbtn_wishlist);


        }
    }
}
