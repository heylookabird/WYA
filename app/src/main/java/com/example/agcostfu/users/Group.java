package com.example.agcostfu.users;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.agcostfu.main.PictureNode;

public class Group {
	String name, password, id;
	int expiration;
	private ArrayList<String> chat;
	private ArrayList<User> members, blocked;
	private ArrayList<String> invited;
	private ArrayList<PictureNode> pictures;
	public int line = 0;

	public Group(User admin, String name, String password,
			ArrayList<String> invitednumbers) {
		members = new ArrayList<User>();
		blocked = new ArrayList<User>();
		pictures = new ArrayList<PictureNode>();
		this.password = password;
		this.name = name;
		this.invited = invitednumbers;
		expiration = Calendar.DAY_OF_YEAR;
		members.add(admin);
		admin.setGroup(this);
		invited = new ArrayList<String>();

	}

	public Group(String groupname, String adminName, String adminNum, String id) {
		members = new ArrayList<User>();
		blocked = new ArrayList<User>();
		pictures = new ArrayList<PictureNode>();

		name = groupname;
		User admin = new User();
		admin.setUsername(adminName);
		admin.setPhoneNumber(adminNum);

		members.add(admin);
		admin.setGroup(this);
		chat = new ArrayList<String>();
		invited = new ArrayList<String>();
		
		this.id = id;
	}
	
	public String getID(){
		return id;
	}

	public static ArrayList<String> getGroupInfo(Group g) {
		ArrayList<String> info = new ArrayList<String>();
		for (int i = 0; i < g.members.size(); i++) {
			User u = g.members.get(i);
			info.add(u.getUserName() + " " + u.getPhoneNumber() + " "
					+ u.getLong() + " " + u.getLat());
		}
		return info;
	}

	public boolean addPicture(PictureNode pic) {
		return pictures.add(pic);
	}

	public void invite(String user) {
		invited.add(user);
	}

	public boolean checkInvited(String user) {
		return invited.contains(user);
	}

	public int size() {
		return members.size();
	}

	public User getUser(int index) {
		return members.get(index);
	}

	public void block(User user) {
		blocked.add(user);
	}

	public boolean setPassword(String pw) {
		this.password = pw;
		return true;
	}

	public boolean checkPassword(String string) {
		return string.equals(password);
	}

	public boolean addToGroup(User user) {
		if (user.getGroup() != null) {
			user.setGroup(this);
		}

		System.out.println(user + " already belongs to a group");
		return false;
	}

	public void addToGroup(String invited2, String invitedName) {
		User n = new User();
		n.setUsername(invitedName);
		n.setPhoneNumber(invited2);
		n.setGroup(this);
		members.add(n);
	}

	public void addToChat(String senderName, String message) {
		String node = senderName + ": " + message;
		chat.add(node);

	}

	public ArrayList<String> getGroupChat() {
		return chat;
	}

	public ArrayList<PictureNode> getPictureNodes() {
		return pictures;
	}
}
