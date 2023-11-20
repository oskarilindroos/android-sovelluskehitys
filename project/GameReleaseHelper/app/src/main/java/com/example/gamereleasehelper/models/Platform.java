package com.example.gamereleasehelper.models;

import com.google.gson.annotations.SerializedName;

public class Platform {
    private long id;
    private String abbreviation;

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String value) {
        this.abbreviation = value;
    }
}
