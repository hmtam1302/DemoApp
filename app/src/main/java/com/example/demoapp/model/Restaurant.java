package com.example.demoapp.model;

public class Restaurant {
    private int ID;
    private String mName;
    private String mLogoName;
    private String mDiscription;
    private String mRating;

    public Restaurant(String mName, String mLogoName, String mDiscription, String mRating) {
        this.mName = mName;
        this.mLogoName = mLogoName;
        this.mDiscription = mDiscription;
        this.mRating = mRating;
    }

    public Restaurant() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getLogoName() {
        return mLogoName;
    }

    public void setLogoName(String mLogoName) {
        this.mLogoName = mLogoName;
    }

    public String getDiscription() {
        return mDiscription;
    }

    public void setDiscription(String mDiscription) {
        this.mDiscription = mDiscription;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String mRating) {
        this.mRating = mRating;
    }
}
