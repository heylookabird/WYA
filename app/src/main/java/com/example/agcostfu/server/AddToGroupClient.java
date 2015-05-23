package com.example.agcostfu.server;
/*
* Client to add a new User to a Group to the server.
*
 */
public class AddToGroupClient extends Client{

	String t, name;
	public AddToGroupClient(String n, String invited){
		super();
		t = invited;
		init(n);
	}
	
	public AddToGroupClient(String n, String invited,
			String userName) {
		super();
		t = invited;
		name = userName;
		init(n);
	}

	@Override
	public String getRequest(){
		return "addToGroup ," + parentNum + "," + t + "," + name;
	}//			command			adders #			new guys #	new guys name
}
