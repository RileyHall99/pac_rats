package com.example.pacrat_good_empty;

import android.graphics.Bitmap;

public class individual_collection_items {
    public Bitmap item_photo;
    public String item_released;
    public String item_purchased;
    public String item_description;
    public String item_name;

    public individual_collection_items(String name , String released ,String purchased , Bitmap photo , String desc ){

        this.item_photo = photo;
        this.item_released = released;
        this.item_purchased = purchased;
        this.item_description = desc;
        this.item_name = name;

    }



}
