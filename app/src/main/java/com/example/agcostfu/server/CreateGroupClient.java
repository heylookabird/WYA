package com.example.agcostfu.server;


public class CreateGroupClient extends Client{

	String gname, user;
	public CreateGroupClient(String groupname, String n, String user) {
		super();
		gname = groupname;
		this.user = user;
		init(n);
	}

	
	@Override
	protected String getRequest(){
		return "makeNewGroup , " + parentNum + " , "+ user + " , " +  gname;
	}
	
}
