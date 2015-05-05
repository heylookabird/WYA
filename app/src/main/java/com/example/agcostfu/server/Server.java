package com.example.agcostfu.server;

//import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

//import javax.imageio.ImageIO;

import com.example.agcostfu.main.PictureNode;
import com.example.agcostfu.threads.ClientThread;
import com.example.agcostfu.users.Group;
import com.example.agcostfu.users.User;

/*
 * So far its a chat server
 * Server example used can be found at drenguin.hubpages.com/hub/How-to-create-a-server-in-Java
 * 
 * Still trying to figure out what exactly to do to set up a server
 */
public class Server {
	public static void main(String[] args) {
		new Server();
	}

	public static ArrayList<Group> activeGroups;
	public static int n = 0;
	public Server() {
		activeGroups = new ArrayList<Group>();
		try {
			// open a ServerSocket at port 10000
			ServerSocket serSocket = new ServerSocket(80);
			System.out.println("Server started at : " + new Date());
			// while server is still running, allow more threads to join
			while (true) {
				// creates a new socket and the program waits until server
				// accepts
				// it
				Socket socket = serSocket.accept();
				ClientThread client = new ClientThread(socket);

				new Thread(client).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void println(String string) {
		System.out.println(string);
	}

	public static void invokeAction(Socket s, String call,
			String number, String username, String gname, String lon,
			String lat, PictureNode node) throws Exception {
		PrintWriter clientout = new PrintWriter(s.getOutputStream(), true);

		System.out.println("Action invoked " + call);
		try {
			Method m;
			if(call.startsWith("clear")){
				activeGroups.clear();
				System.out.println("cleared arrays");
			}
			if (call.startsWith("inviteUserToGroup")) {
				m = Server.class.getMethod("inviteUserToGroup", String.class,
						String.class);
				clientout.println(m.invoke(call, number, username));
			} else if (call.startsWith("addToGroup")) {
				m = Server.class.getMethod("addToGroup", String.class,
						String.class, String.class);
				clientout.println(m.invoke(call, number, username, gname));
			} else if (call.startsWith("getInvitations")) {
				m = Server.class.getMethod("getInvitations", String.class);
				clientout.println(m.invoke(call, number));
			} else if (call.startsWith("addToChat")) {
				m = Server.class.getMethod("addToChat", String.class,
						String.class, String.class);
				clientout.println(m.invoke(call, number, username, gname));
			} else if (call.startsWith("userUpdate")) {
				m = Server.class.getDeclaredMethod("userUpdate", String.class,
						String.class, String.class);
				clientout.println(m.invoke(call, number, lon, lat));
			} else if (call.startsWith("getGroupChat")) {
				m = Server.class.getMethod("getGroupChat", String.class);

				Object obj = m.invoke(call, number);

				if (obj instanceof ArrayList<?>) {
					ArrayList<String> strings = (ArrayList<String>) m.invoke(
							call, number);

					for (int i = 0; i < strings.size(); i++) {
						clientout.println(strings.get(i));

						if (i == strings.size() - 1)
							clientout.println("/./.");
					}

				}
			} /*else if(call.startsWith("getPhotos")){
				m = Server.class.getMethod("getPhotos", String.class);
				
				//get file locations for server pictures based on user
				Object obj = m.invoke(call, number);
				
				if(obj instanceof ArrayList){
					ArrayList<PictureNode> pics = (ArrayList<PictureNode>) obj;
					
					for(int i = 0; i < pics.size(); i++){
						try{
                            BufferedImage im = pics.get(i).getImage();
                            ImageIO.write(im, "JPEG", s.getOutputStream());

                            String extra = searchForUser(number).getUserName() + " , " + pics.get(i).getTitle() + " , " + pics.get(i).getLong() + " , " + pics.get(i).getLat() + " , " + pics.get(i).getChat();
                            clientout.println(extra);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}*/else if (call.startsWith("addPictureToGroup")) {
			} else if (call.startsWith("updateGroup")) {
				m = Server.class.getMethod("updateGroup", String.class);
				//clientout.println(m.invoke(call, number));
				Object obj = m.invoke(call, number);
				
				if(obj instanceof ArrayList){
					ArrayList<String> strings = (ArrayList<String>) m.invoke(
							call, number);

					for (int i = 0; i < strings.size(); i++) {
						clientout.println(strings.get(i));

						if (i == strings.size() - 1)
							clientout.println("/./.");
					}
				}
			} else if (call.startsWith("makeNewGroup")) {
				m = Server.class.getMethod("makeNewGroup", String.class,
						String.class, String.class);
				clientout.println(m.invoke(call, gname, username, number));
			} else if (call.startsWith("searchForUser")) {
				m = Server.class.getMethod("searchForUser", String.class);
				clientout.println(m.invoke(call, number));
			}
			// Method method = Server.class.getMethod(call,
			// Server.getParameterTypes());
		} catch (Exception e) {
			clientout.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static ArrayList<PictureNode> getPhotos(String number){
		ArrayList<String> fileNames = new ArrayList<String>();
		String id = searchForUser(number).getGroup().getID();
		
		ArrayList<PictureNode> p = searchForUser(number).getGroup().getPictureNodes();

		
		return p;
	}

	public static ArrayList<String> getGroupChat(String number) {
		return searchForUser(number).getGroup().getGroupChat();
	}
	
	/*public static boolean uploadPhotoToGroup(Socket socket, String number, String chat, String lon, String lat){
		boolean success = false;
		String groupID = searchForUser(number).getGroup().getID();
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			BufferedImage img = ImageIO.read(dis);
			//FileOutputStream fout = new FileOutputStream(serverloc);
			*//*int i;
			while((i = dis.read()) > -1){
				fout.write(i);
			}
			
			fout.flush();
			fout.close();*//*
			dis.close();
			success = true;
			
			addPhotoToGroup(number, img, chat, lon, lat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}*/

	/*private static void addPhotoToGroup(String number, BufferedImage img,
			String chat, String lon, String lat) {
		PictureNode pic = new PictureNode(img, number, chat, Double.parseDouble(lon), Double.parseDouble(lat));
		searchForUser(number).getGroup().addPicture(pic);
	}*/

	public static void addToChat(String senderNum, String senderName,
			String message) {
		User chat = searchForUser(senderNum);
		chat.getGroup().addToChat(senderName, message);
	}

	// called in the method above...when called through invoke use call,
	// inviter, invitee
	public static boolean inviteUserToGroup(String inviter, String invited) {
		User inv = searchForUser(inviter);
		inv.getGroup().invite(invited);

		return true;
	}

	public static boolean addToGroup(String inviter, String invited,
			String invitedName) {
		User inv = searchForUser(inviter);
		if (inv != null)
			inv.getGroup().addToGroup(invited, invitedName);
		else
			return false;

		return true;
	}

	public static String getInvitations(String number) {
		String numbers = null;

		for (int i = 0; i < activeGroups.size(); i++) {
			if (activeGroups.get(i).checkInvited(number)){
				User admin = activeGroups.get(i).getUser(0);
				if (numbers != null) {
					numbers.concat(", " + admin.getUserName() + " number: "
							+ admin.getPhoneNumber());
				}else{
					numbers = admin.getUserName() + " number: "
							+ admin.getPhoneNumber();
				}
				
			}
		}

		return numbers;
	}

	public static boolean userUpdate(String number, String lon, String lat) {
		User found = searchForUser(number);
		if (found != null)
			found.setWorldPoint(Double.parseDouble(lon),
					Double.parseDouble(lat));

		return found != null;
	}

	private static boolean addPictureToGroup(String user, PictureNode pic) {
		return searchForUser(user).getGroup().addPicture(pic);
	}

	public static ArrayList<String> updateGroup(String number) {
		Group group = searchForUser(number).getGroup();

		return Group.getGroupInfo(group);
	}

	public static String unloadData() {
		String data = "";

		/*for (int i = 0; i < activeGroups.size(); i++) {
			data.concat(Group.getGroupInfo(activeGroups.get(i)));
		}*/

		return data;
	}

	public static Group makeNewGroup(String groupname, String adminName,
			String adminNum) {
		Group g = new Group(groupname, adminName, adminNum, ""+n);
		addGroup(g);
		n++;

		return g;
	}

	public static ArrayList<Group> getGroupData() {
		return activeGroups;
	}

	private static boolean addGroup(Group g) {
		return activeGroups.add(g);
	}

	public static User searchForUser(String number) {
		if (activeGroups != null) {
			for (int i = 0; i < activeGroups.size(); i++) {
				Group g = activeGroups.get(i);
				for (int j = 0; j < g.size(); j++) {
					if (g.getUser(j).getPhoneNumber().contains(number)) {
						return g.getUser(j);
					}
				}
			}

			System.out.println("failed to find " + number);
		}

		return null;
	}

}
