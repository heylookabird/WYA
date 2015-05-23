package com.example.agcostfu.wya;
/*
Helper class to help manage location data.


 */
public class Location {
    private int _id;
    private double _lat;
    private double _lng;
    private String name;
    private String address;
    private String date;

    public Location() {

    }

    public Location(int id, double lat, double lng) {
        this._id = id;
        this._lat = lat;
        this._lng = lng;
    }

    public Location(double lat, double lng) {
        this._lat = lat;
        this._lng = lng;
    }

    public Location(double lat, double lng, String date){
        this._lat = lat;
        this._lng = lng;
        this.date = date;
    }

    // constructor to generate all the SQLite parameters for a new location
    public Location(int id, double lat, double lng,
                    String name, String address, String date) {
        this._id = id;
        this._lat = lat;
        this._lng = lng;
        this.name = name;
        this.address = address;
        this.date = date;
    }

    // constructor to generate all the SQLite parameters for a new location
    public Location(double lat, double lng,
                    String name, String address, String date) {
        this._lat = lat;
        this._lng = lng;
        this.name = name;
        this.address = address;
        this.date = date;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setLatitude(double lat) {
        this._lat = lat;
    }

    public double getLatitude() {
        return this._lat;
    }

    public void setLongitude(double lng) {
        this._lng = lng;
    }

    public double getLongitude() {
        return this._lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
}