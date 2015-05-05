package com.example.agcostfu.server;

public class InviteClient extends Client {

	String invited;
	public InviteClient(String n, String invited) {
		super();
		this.invited = invited;
		init(n);
	}

	@Override
	public String getRequest() {
		return "inviteUserToGroup ," + this.parentNum + " , " + invited;
	}

}
