package com.example.agcostfu.server;

public class ClearClient extends Client{

	public ClearClient(String n){
		super(n);
	}
	
	@Override
	protected String getRequest() {
		return "clear 1111";
	}
}
