package com.example.agcostfu.server;

import java.io.IOException;
import java.io.PrintWriter;

public class GetInvitationsClient extends Client{

	public GetInvitationsClient(String n){
		super(n);
	}
	
	@Override
	protected String getRequest(){
		return "getInvitations , " + this.parentNum;
	}
	
	@Override
	public void moreActions(){
		try {
		info = threadInput.readLine();
		System.out.println(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
