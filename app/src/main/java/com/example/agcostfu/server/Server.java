package com.example.agcostfu.server;

//import java.awt.image.BufferedImage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
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
import com.example.agcostfu.main.Tag;
import com.example.agcostfu.threads.ClientThread;
import com.example.agcostfu.users.Group;
import com.example.agcostfu.users.User;

/*
 * Server to store, send, and manage Groups of Users and their locations.
 * All calls to Server are made through ClientThread.java so that we know what request was made.
 * Inefficient and implementation will be changed soon.
 */
public class Server {
	public static void main(String[] args) {
		new Server();
	}

	public static ArrayList<Group> activeGroups;

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

	public static void invokeAction(Socket s, String call,
			String number, String username, String gname, String lat,
			String lon, PictureNode node) throws Exception {
		PrintWriter clientout = new PrintWriter(s.getOutputStream(), true);

		System.out.println("Action invoked " + call + ", number: " + number + ", user: " + username);
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
				m = Server.class.getDeclaredMethod("getGroupChat", String.class, String.class);
				Object obj = m.invoke(call, number, username);

				if (obj instanceof ArrayList<?>) {
					ArrayList<String> strings = (ArrayList<String>) obj;

					for (int i = 0; i < strings.size(); i++) {
						clientout.println(strings.get(i));

					}
                    clientout.println("/./.");

				}
			} else if(call.startsWith("getPhotos")){
				m = Server.class.getMethod("getPhotos", String.class, String.class, String.class);
				//get file locations for server pictures based on user
				Object obj = m.invoke(call, number, lat, lon);
					PictureNode pic = (PictureNode) obj;
                        Bitmap bit = pic.getImage();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                        byte array[] = baos.toByteArray();
                        clientout.println(array + ", " +Server.searchForUser(number) + ", " + pic.getTitle() + ", " + pic.getLat() + ", " + pic.getLong());

			}else if (call.startsWith("addPictureToGroup")) {
			} else if (call.startsWith("updateGroup")) {
				m = Server.class.getMethod("updateGroup", String.class);
				//clientout.println(m.invoke(call, number));
				Object obj = m.invoke(call, number);
				
				if(obj instanceof ArrayList){
					ArrayList<String> strings = (ArrayList<String>) m.invoke(
							call, number);

					for (int i = 0; i < strings.size(); i++) {
						clientout.println(strings.get(i));
                        System.out.println(strings.get(i));
					}

                    clientout.println("/./.");
				}
			}else if (call.startsWith("updatePictures")) {
                m = Server.class.getMethod("updatePictures", String.class);
                //clientout.println(m.invoke(call, number));
                Object obj = m.invoke(call, number);

                if(obj instanceof ArrayList){
                    ArrayList<String> strings = (ArrayList<String>) m.invoke(
                            call, number);

                    for (int i = 0; i < strings.size(); i++) {
                        clientout.println(strings.get(i));
                    }

                    clientout.println("/./.");
                }
            } else if (call.startsWith("makeNewGroup")) {
				m = Server.class.getMethod("makeNewGroup", String.class,
						String.class, String.class);
				clientout.println(m.invoke(call, gname, username, number));
			} else if (call.startsWith("searchForUser")) {
				m = Server.class.getMethod("searchForUser", String.class);
				clientout.println(m.invoke(call, number));
			}else if(call.startsWith("addTagToGroup")){
                m = Server.class.getMethod("addTagToGroup", String.class, String.class, String.class, String.class);
                clientout.println(m.invoke(call, number, username, lat, lon));
            }
			// Method method = Server.class.getMethod(call,
			// Server.getParameterTypes());
		} catch (Exception e) {
			clientout.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static PictureNode getPhotos(String number, String lat, String lon){
		ArrayList<String> fileNames = new ArrayList<String>();
		
		PictureNode p = searchForUser(number).getGroup().getPicture(Double.parseDouble(lat), Double.parseDouble(lon));

		
		return p;
	}

	public static ArrayList<String> getGroupChat(String number, String index) {
        int i = Integer.parseInt(index);

		return searchForUser(number).getGroup().getGroupChat(i);
	}

    public static boolean addTagToGroup(String number, String tagTitle, String lat, String lon){
        boolean success = false;

        searchForUser(number).getGroup().addTag(new Tag(tagTitle, Double.parseDouble(lat), Double.parseDouble(lon)));
        success = true;
        return success;
    }
	
	public static boolean uploadPhotoToGroup(byte[] array, String number, String chat, String lat, String lon){
		boolean success = false;

	    Bitmap img = BitmapFactory.decodeByteArray(array, 0, array.length);

        PictureNode pic = new PictureNode(img, number, chat, Double.parseDouble(lat), Double.parseDouble(lon));

        addPictureToGroup(number, pic);
		success = true;
		
		return success;
	}

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
                System.out.println("Found " + number);
				User admin = activeGroups.get(i).getUser(0);
				if (numbers != null) {
					numbers = numbers.concat("," + admin.getPhoneNumber());
				}else{
					numbers = admin.getPhoneNumber();
				}
				
			}
		}

        System.out.println("numbers: " + numbers);

		return numbers;
	}

	public static boolean userUpdate(String number, String lat, String lon) {
        boolean check = "+19162716749".contains(number);
        System.out.println("check: " + check);
		User found = searchForUser(number);
		if (found != null)
			found.setWorldPoint(Double.parseDouble(lat),
					Double.parseDouble(lon));

		return found != null;
	}

	private static boolean addPictureToGroup(String user, PictureNode pic) {
		return searchForUser(user).getGroup().addPicture(pic);
	}

	public static ArrayList<String> updateGroup(String number) {
		Group group = searchForUser(number).getGroup();

		return Group.getGroupInfo(group);
	}

    public static ArrayList<String> updatePictures(String number){
        ArrayList<String> locations = new ArrayList<String>();
        Group group = searchForUser(number).getGroup();

        ArrayList<PictureNode> pictures = group.getPictureNodes();
        ArrayList<Tag> tags = group.getTags();

        for(int i = 0; i < pictures.size(); i++){
            PictureNode p = pictures.get(i);
            String curr = p.getTitle() + " " + p.getLat() + " " + p.getLong();
            locations.add(curr);
        }

        for(int i = 0; i < tags.size(); i++){
            Tag p = tags.get(i);
            String curr = p.getTag() + " " + p.getLat() + " " + p.getLon();
            locations.add(curr);
        }

    return locations;

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
		Group g = new Group(groupname, adminName, adminNum);

/*        for(int i =0; i < activeGroups.size(); i++){
            for(int j = 0; j < activeGroups.get(i).size(); j++) {
                if (activeGroups.get(i).getUser(j).getPhoneNumber().startsWith(adminNum)){
                    return g;
                }

            }
        }*/
		addGroup(g);


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
                    System.out.println("currNumber: " + g.getUser(j).getPhoneNumber());

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
