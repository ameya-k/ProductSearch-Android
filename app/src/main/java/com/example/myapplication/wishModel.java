package com.example.myapplication;

public class wishModel {


    private String wish_image_url;
    private String wish_title;
    private String wish_product_zip;
    private String wish_product_shipping;
    private String wish_product_condition;
    private String wish_product_price;
    private String wish_item_id;

    public wishModel(String wish_image_url, String wish_title, String wish_product_zip, String wish_product_shipping, String wish_product_condition, String wish_product_price, String wish_item_id) {
        this.wish_image_url = wish_image_url;
        this.wish_title = wish_title;
        this.wish_product_zip = wish_product_zip;
        this.wish_product_shipping = wish_product_shipping;
        this.wish_product_condition = wish_product_condition;
        this.wish_product_price = wish_product_price;
        this.wish_item_id = wish_item_id;
    }


    public String getWish_image_url() {
        return wish_image_url;
    }

    public String getWish_title() {
        return wish_title;
    }

    public String getWish_product_zip() {
        return wish_product_zip;
    }

    public String getWish_product_shipping() {
        return wish_product_shipping;
    }

    public String getWish_product_condition() {
        return wish_product_condition;
    }

    public String getWish_product_price() {
        return wish_product_price;
    }

    public String getWish_item_id() {
        return wish_item_id;
    }
}
