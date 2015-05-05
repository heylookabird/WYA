package com.example.agcostfu.main;


import java.util.Scanner;

import com.example.agcostfu.server.AddToChatClient;
import com.example.agcostfu.server.AddToGroupClient;
import com.example.agcostfu.server.Client;
import com.example.agcostfu.server.CreateGroupClient;
import com.example.agcostfu.server.GetGroupChatClient;
import com.example.agcostfu.server.GetGroupLocationClient;
import com.example.agcostfu.server.UpdatingClient;
import com.example.agcostfu.users.User;

public class TestUser {

	public static void main(String[] args){
		User user1 = new User();
		
		user1.setUsername("Harjit");
		user1.setPhoneNumber("123456");
		
		Client create = new CreateGroupClient("Test", user1.getPhoneNumber(), user1.getUserName());
		
		//Client invite = new InviteClient(user1.getPhoneNumber(), "1234");
		
		User user2 = new User();
		user2.setUsername("Vince");
		user2.setPhoneNumber("1234");
		
		
		//vince added to group
		Client getInv = new AddToGroupClient(user1.getPhoneNumber(), user2.getPhoneNumber(), user2.getUserName());
		
		Client updateLoc = new UpdatingClient(user1.getPhoneNumber(), 40, 50);
		
		new UpdatingClient(user2.getPhoneNumber(), 60,70);
		
		new GetGroupLocationClient(user1.getPhoneNumber());
		
		User brooke = new User();
		brooke.setUsername("brooke");
		brooke.setPhoneNumber("8182698043");
		
		new AddToGroupClient(user1.getPhoneNumber(), brooke.getPhoneNumber(), brooke.getUserName());
		
		new UpdatingClient(brooke.getPhoneNumber(), 100, 55);
		
		User john = new User();
		john.setUsername("john");
		john.setPhoneNumber("911");
		
		new AddToGroupClient(brooke.getPhoneNumber(), john.getPhoneNumber(), john.getUserName());
		
		new UpdatingClient(john.getPhoneNumber(), 500, 33);
		
		User joe = new User();
		joe.setUsername("joe");
		joe.setPhoneNumber("40880280");
		
		new AddToGroupClient(john.getPhoneNumber(), joe.getPhoneNumber(), joe.getUserName());
		
		new UpdatingClient(joe.getPhoneNumber(), 400, 567);
		
		
		new GetGroupLocationClient(joe.getPhoneNumber());
		
		
		new AddToChatClient(user1.getPhoneNumber(), user1.getUserName(), "Hey whats up");
		
		new AddToChatClient(brooke.getPhoneNumber(), brooke.getUserName(), "HBIC");
		
		new AddToChatClient(joe.getPhoneNumber(), joe.getUserName(), "What is HBIC?");
		
		new GetGroupChatClient(user1.getPhoneNumber());
		
		new UpdatingClient(brooke.getPhoneNumber(), 100, 300);
		new UpdatingClient(user1.getPhoneNumber(), 250, 10);
		new UpdatingClient(john.getPhoneNumber(), 0, 50);
		new UpdatingClient(joe.getPhoneNumber(), 50, 200);

		new GetGroupLocationClient(joe.getPhoneNumber());
	}
}
