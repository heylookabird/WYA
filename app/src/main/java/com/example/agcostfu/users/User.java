package com.example.agcostfu.users;

import java.io.PrintWriter;
import java.util.Scanner;

import com.example.agcostfu.server.Server;

public class User {
	private String username;
	private String phonenumber;
	
	private int currentGroupIndex;

	private Group group;

	double longitude;
	double latitude;

	public User() {
        username = "";
	}

	public void setUsername(String name) {
		username = name;
	}

	public String getUserName() {
		return username;
	}

	public void setPhoneNumber(String number) {
		phonenumber = number;
	}

	public String getPhoneNumber() {
		return phonenumber;
	}

	public void setWorldPoint(double lat, double lon) {
		longitude = lon;
		latitude = lat;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group g) {
		group = g;
		currentGroupIndex = Server.activeGroups.indexOf(g);
	}
	
	public int getGroupIndex(){
		return currentGroupIndex;
	}

	public double getLong() {
		return longitude;
	}

	public double getLat() {
		return latitude;
	}
}
