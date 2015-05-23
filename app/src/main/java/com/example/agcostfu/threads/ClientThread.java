package com.example.agcostfu.threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import com.example.agcostfu.main.PictureNode;
import com.example.agcostfu.server.Server;

public class ClientThread implements Runnable {

	Socket tSocket;
	PrintWriter output;

	public ClientThread(Socket socket) {
		tSocket = socket;

	}

	// client actions
	@Override
	public void run() {



		try {

			output = new PrintWriter(tSocket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					tSocket.getInputStream()));

			// output on the client computer that they have connected
			output.println("You have connected at: " + new Date());

			// Loop that runs server functions
			// while (true) {
			String clientInput = input.readLine();
			// if chatInput == null, user disconnected because otherwise
			// it would wait
			// and never return null
			// final communication with server
			if (clientInput != null) {
				interpretClientRequest(clientInput);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void interpretClientRequest(String input) throws Exception {
		ArrayList<String> com = new ArrayList<String>();
		String curr = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != ',') {
				curr = curr + input.charAt(i);
			} else {
				com.add(curr);
				curr = "";
			}
		}

		com.add(curr);


		String command = com.get(0);
		String originnum = com.get(1);
		String un = null, groupn = null;
		String lon = null, lat = null;
		PictureNode pic = null;
		if (com.size() > 2)
			un = com.get(2);

		if (com.size() > 3)
			groupn = com.get(3);

		if (com.size() > 4) {
			lat = com.get(4);
			lon = com.get(5);
		}

		
		//if upload, use un for the file location and groupn for comments to photo
		if (command.startsWith("upload")) {
			sendPhotoToServer(originnum, un, groupn, lat, lon);
		} else
			Server.invokeAction(tSocket, command, originnum, un, groupn, lat,
					lon, null);
	}
	

	private void sendPhotoToServer(String array, String originnum, String chat, String lat, String lon) {
	    byte[] bytes = array.getBytes();

        Server.uploadPhotoToGroup(bytes, originnum, chat, lat, lon);


	}

}
