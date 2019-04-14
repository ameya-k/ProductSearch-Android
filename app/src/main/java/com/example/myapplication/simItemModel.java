package com.example.myapplication;

public class simItemModel {

    String prod_title;
    String shipping_cost;
    String days_left;
    String price;
    String image_link;

    public String getRedirect_url() {
        return redirect_url;
    }

    String redirect_url;


    public simItemModel(String prod_title, String shipping_cost, String days_left, String price, String image_link,String redirect_url) {
        this.prod_title = prod_title;
        this.shipping_cost = shipping_cost;
        this.days_left = days_left;
        this.price = price;
        this.image_link = image_link;
        this.redirect_url=redirect_url;
    }

    public String getProd_title() {
        return prod_title;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public String getDays_left() {
        return days_left;
    }

    public String getPrice() {
        return price;
    }

    public String getImage_link() {
        return image_link;
    }
}
