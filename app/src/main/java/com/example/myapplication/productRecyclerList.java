package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class productRecyclerList implements Parcelable {

    private String image_url;
    private String title;
    private String product_zip;
    private String product_shipping;
    private String product_condition;
    private String product_price;
    private String item_id;

    public productRecyclerList(String image_url, String title, String product_zip, String product_shipping,
                               String product_condition, String product_price,String item_id) {
        this.image_url = image_url;
        this.title = title;
        this.product_zip = product_zip;
        this.product_shipping = product_shipping;
        this.product_condition = product_condition;
        this.product_price = product_price;
        this.item_id=item_id;
    }



    public String getItem_id() {
        return item_id;
    }



    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getProduct_zip() {
        return product_zip;
    }

    public String getProduct_shipping() {
        return product_shipping;
    }



    public String getProduct_condition() {
        return product_condition;
    }

    public String getProduct_price() {
        return product_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(image_url);
        dest.writeString(title);
        dest.writeString(product_zip);
        dest.writeString(product_shipping);
        dest.writeString(product_condition);
        dest.writeString(product_price);
        dest.writeString(item_id);
    }


    protected productRecyclerList(Parcel in) {
        image_url = in.readString();
        title = in.readString();
        product_zip = in.readString();
        product_shipping = in.readString();
        product_condition = in.readString();
        product_price = in.readString();
        item_id = in.readString();
    }

    public static final Creator<productRecyclerList> CREATOR = new Creator<productRecyclerList>() {
        @Override
        public productRecyclerList createFromParcel(Parcel in) {
            return new productRecyclerList(in);
        }

        @Override
        public productRecyclerList[] newArray(int size) {
            return new productRecyclerList[size];
        }
    };
}
