package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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

       final productRecyclerList prlist=productslist.get(i);

       //we set the values of elements from layout from our list of class objects

        //use picasso to load image here
        Glide.with(con).load(prlist.getImage_url()).error(R.drawable.brimage).into(viewHolder.productImage);
        viewHolder.productTitle.setText(prlist.getTitle());
        viewHolder.productPrice.setText(prlist.getProduct_price());
        viewHolder.productCondition.setText(prlist.getProduct_condition());
        viewHolder.productShipping.setText(prlist.getProduct_shipping());
        viewHolder.productZip.setText(prlist.getProduct_zip());
        SharedPreferences sp=con.getSharedPreferences("WISHLIST_ITEMS",Context.MODE_PRIVATE);
        if(sp.getString(prlist.getItem_id(),"N/A").equals("N/A")){

          // viewHolder.wishBtn.setBackground((Drawable)R.drawable.addwishlist);
            viewHolder.wishBtn.setBackground(con.getResources().getDrawable(R.drawable.addwishlist));
        }
        else{
            viewHolder.wishBtn.setBackground(con.getResources().getDrawable(R.drawable.removewishlist));
        }



        viewHolder.lLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(v.getContext(),prodDetailsActivity.class);
               i.putExtra("firstData",prlist);
               v.getContext().startActivity(i);
            }
        });


        viewHolder.wishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Item Added to wishlist:", prlist.getItem_id());
                SharedPreferences sp=v.getContext().getSharedPreferences("WISHLIST_ITEMS",Context.MODE_PRIVATE);

                //remove from wishlist
                if(!(sp.getString(prlist.getItem_id(),"N/A").equals("N/A"))){
                    sp.edit().remove(prlist.getItem_id()).commit();
                    viewHolder.wishBtn.setBackground(v.getContext().getDrawable(R.drawable.addwishlist));
                    Log.i("Xx Removed item:",prlist.getItem_id());
                    Log.i("XXpref listXX", String.valueOf(sp.getAll()));
                    Toast.makeText(con,prlist.getTitle().substring(0,prlist.getTitle().length()-10)+"..."+" removed from wishlist",Toast.LENGTH_SHORT).show();


                }
                else{
                    Gson g=new Gson();
                    String json=g.toJson(prlist);
                    sp.edit().putString(prlist.getItem_id(),json).commit();

                    viewHolder.wishBtn.setBackground(v.getContext().getDrawable(R.drawable.removewishlist));
                    Toast.makeText(con,prlist.getTitle().substring(0,prlist.getTitle().length()-10)+"..."+" added to wishlist",Toast.LENGTH_SHORT).show();
                    Log.i("XXAdded item:",prlist.getItem_id());
                    Log.i("XXpref listXX", String.valueOf(sp.getAll()));
                }

                //add object to wishlist
//                Gson g=new Gson();
//                String json=g.toJson(prlist);
//                sp.edit().putString(prlist.getItem_id(),json).commit();


//                String j=sp.getString(prlist.getItem_id(),"n/a");
//                Log.i("from shared preference:",j);
            }
        });





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
        public LinearLayout lLayout;
        public Button wishBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImageView);
            productTitle=itemView.findViewById(R.id.productTitleView);
            productZip=itemView.findViewById(R.id.productZipView);
            productShipping=itemView.findViewById(R.id.productShippingView);
            productCondition=itemView.findViewById(R.id.productConditionView);
            productPrice=itemView.findViewById(R.id.productPriceView);
            lLayout=itemView.findViewById(R.id.cardLinear);
            wishBtn=itemView.findViewById(R.id.wishbtn);

        }
    }
}
