package com.example.agcostfu.server;
/*
* Client to get location data of other group members from the server.
*
 */

import java.io.IOException;

public class GetGroupLocationClient extends Client {

	public GetGroupLocationClient(String n) {
		super(n);
	}

	@Override
	protected String getRequest() {
		return "updateGroup ," + this.parentNum;
	}

	@Override
	protected void moreActions() {

		try {
			String in = threadInput.readLine();
			while (in != null) {
				if(in.endsWith("/./."))
					break;
				
				//System.out.println(in);
				info = info + " " + in;
				in = threadInput.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
