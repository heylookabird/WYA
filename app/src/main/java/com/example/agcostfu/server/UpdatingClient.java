package com.example.agcostfu.server;
/*
* Client to send location data from user's phone to the server.
*
 */
public class UpdatingClient extends Client {

	double lo, la;
	public UpdatingClient(String n, double lon, double lat) {
		super();
		lo = lon;
		la = lat;
		init(n);
	}
	
	@Override
	protected String getRequest(){
		return "userUpdate ," + parentNum +", null, null, " + lo + " , " + la;
	}

}
