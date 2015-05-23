package com.example.agcostfu.main;

/**
 * Class used to store and handle Tags in the app
 */
public class Tag {

    double longitude, latitude;
    String tag;

    public Tag(String t, double lat, double lon){
        tag = t;
        longitude = lon;
        latitude = lat;
    }

    public double getLon() {
        return longitude;
    }

    public double getLat(){
        return latitude;
    }

    public String getTag(){
        return tag;
    }

    public void setLon(double n){
        longitude = n;
    }

    public void setLat(double n){
        latitude = n;
    }

}
