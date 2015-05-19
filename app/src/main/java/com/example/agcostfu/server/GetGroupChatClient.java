package com.example.agcostfu.server;

import java.io.IOException;

import com.example.agcostfu.users.User;

public class GetGroupChatClient extends Client {
    int loc;
	public GetGroupChatClient(String n, int index) {

        super();
        loc = index;
        init(n);

	}

	@Override
	public String getRequest() {
		return "getGroupChat , " + parentNum + " ," + loc;
	}

	@Override
	protected void moreActions() {

		try {
			String in = threadInput.readLine();
			while (in != null) {
				if(in.endsWith("/./."))
					break;
				info = info + '\n' + in;
				in = threadInput.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
