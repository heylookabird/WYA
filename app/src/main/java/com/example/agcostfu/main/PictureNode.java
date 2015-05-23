package com.example.agcostfu.main;
/*This class is used to store couple Bitmaps with location data and handle all data related to pictures/tags.
*
*
 */

import android.graphics.Bitmap;



public class PictureNode {
	double longitude, latitude;
	Bitmap image;
	String poster;
	String chat;
	String title;
	
	public PictureNode(String path, double lon, double lat) {
		chat = "";
		longitude = lon;
		latitude = lat;
	}
	
	public PictureNode(Bitmap image, String user, String title, double lat, double lon){
		this.image = image;
		setTitle(title);
		poster = user;
		longitude = lon;
		latitude = lat;
	}

	
	public Bitmap getImage(){
		return image;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
	
	public String getChat(int index){
		return chat;
	}
	
	public double getLong(){
		return longitude;
	}
	
	public double getLat(){
		return latitude;
	}
	
	public void addToChat(String str){
		chat.concat(str + "\n");
	}

}
