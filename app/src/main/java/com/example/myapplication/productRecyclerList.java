package com.example.myapplication;

public class productRecyclerList {

    private String image_url;
    private String title;
    private String product_zip;
    private String product_shipping;
    private String product_condition;
    private String product_price;

    public productRecyclerList(String image_url, String title, String product_zip, String product_shipping, String product_condition, String product_price) {
        this.image_url = image_url;
        this.title = title;
        this.product_zip = product_zip;
        this.product_shipping = product_shipping;
        this.product_condition = product_condition;
        this.product_price = product_price;
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
}
