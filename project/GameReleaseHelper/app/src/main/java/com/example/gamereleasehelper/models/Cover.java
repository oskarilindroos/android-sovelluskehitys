package com.example.gamereleasehelper.models;

import com.google.gson.annotations.SerializedName;

public class Cover {
    private long id;
    @SerializedName("image_id")
    private String imageID;

    public long getID() {
        return id;
    }
    public void setID(long value) {
        this.id = value;
    }
    public String getImageID() {
        return imageID;
    }
    public void setImageID(String value) {
        this.imageID = value;
    }
}
