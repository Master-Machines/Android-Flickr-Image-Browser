package com.dev.ds.coloradomountains.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoFeed {
    @Expose
    public String title;

    @Expose
    public String link;

    @Expose
    public Photo[] items;
}
