package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.List;

public class simItemAdapter extends RecyclerView.Adapter<simItemAdapter.ViewHolder> {

    private List<simItemModel> simItemsList;
    private Context con;

    public simItemAdapter(List<simItemModel> simItemsList, Context con) {
        this.simItemsList = simItemsList;
        this.con = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simitems_single_item_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final simItemModel specCard=simItemsList.get(i);

            viewHolder.simtitle.setText(specCard.getProd_title());
            viewHolder.simShipping.setText(specCard.getShipping_cost());
            viewHolder.simDaysLeft.setText(specCard.getDays_left());
            viewHolder.simPrice.setText(specCard.getPrice());

            Picasso.with(con).load(specCard.getImage_link()).into(viewHolder.simimage);


            viewHolder.cardcontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      String url=specCard.getRedirect_url();
                      Uri redirection=Uri.parse(url);
                      Intent i=new Intent(Intent.ACTION_VIEW,redirection);
                      con.startActivity(i);

                }
            });



    }

    @Override
    public int getItemCount() {
        return simItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView simimage;
        public TextView  simtitle;
        public TextView  simShipping;
        public TextView  simDaysLeft;
        public TextView  simPrice;
        public LinearLayout cardcontainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simimage=itemView.findViewById(R.id.simitemscardimage);
            simtitle=itemView.findViewById(R.id.simprodtitleview);
            simShipping=itemView.findViewById(R.id.simprodshippingview);
            simDaysLeft=itemView.findViewById(R.id.simproddaysleftview);
            simPrice=itemView.findViewById(R.id.simprodpriceview);
            cardcontainer=itemView.findViewById(R.id.simcardlayout);
        }
    }

    public List<simItemModel> getSimItemsList() {
        return simItemsList;
    }

    public void setSimItemsList(List<simItemModel> simItemsList) {
        this.simItemsList = simItemsList;
    }
}
