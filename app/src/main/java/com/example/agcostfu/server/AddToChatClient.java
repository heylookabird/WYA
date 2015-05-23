package com.example.agcostfu.server;

/*
* Client to send chat data to the server.
*
 */
public class AddToChatClient extends Client{

	String name, mess;
	public AddToChatClient(String n, String name, String message){
		this.name = name;
		mess = message;
		init(n);
	}
	
	@Override
	protected String getRequest(){
		return "addToChat ," + parentNum + "," + name + "," + mess;
	}
	
}
