package com.example.agcostfu.server;

public class AddPictureNodeToGroupClient extends Client {
	String fl, chat;
	double lon, lat;

	public AddPictureNodeToGroupClient(String n, String fileLoc, String chat,
			double lon, double lat) {
		fl = fileLoc;
		this.lon = lon;
		this.lat = lat;
		this.chat = chat;
	}

	@Override
	public String getRequest() {
		return "upload , " + fl + " , " + parentNum + " , " + chat + " , "
				+ lon + " , " + lat;
	}
}
