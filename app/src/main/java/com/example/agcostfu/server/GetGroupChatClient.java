package com.example.agcostfu.server;

import java.io.IOException;

import com.example.agcostfu.users.User;

public class GetGroupChatClient extends Client {

	public GetGroupChatClient(String n) {
		super(n);
	}

	@Override
	public String getRequest() {
		return "getGroupChat , " + parentNum;
	}

	@Override
	protected void moreActions() {

		try {
			String in = threadInput.readLine();
			while (in != null) {
				if(in.endsWith("/./."))
					break;
				
				System.out.println(in);
				info = info + "\n" + in;
				in = threadInput.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
