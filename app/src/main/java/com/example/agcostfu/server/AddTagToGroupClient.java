package com.example.agcostfu.server;

/**
 * Client to send Tag data to the server.
 */
public class AddTagToGroupClient extends Client{
    String tagTitle, lon, lat;
    public AddTagToGroupClient(String n, String tagTitle, String lat, String lon){
        this.tagTitle = tagTitle;
        this.lat = lat;
        this.lon = lon;
        init(n);
    }

    @Override
    protected String getRequest(){
        return "addTagToGroup ," + parentNum + "," + tagTitle + ", ," + lat + "," + lon;
    }
}
