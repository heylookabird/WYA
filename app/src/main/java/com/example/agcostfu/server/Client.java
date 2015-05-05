package com.example.agcostfu.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
	String parentNum, info;
	PrintWriter output;
	BufferedReader threadInput;
	ArrayList<String> loaded;
	Socket socket;
	public static void main(String[] args){
	//	String str = args[0];
		
		//if(str == null){
			String str = "" + Math.random() * 100;
		//}
		new Client(str);
	}
	
	public Client(){}
	public Client(String n){
		init(n);
	}
	
	protected void init(String n){
		info="";
		//create scanner for Client input
				//Scanner scanner = new Scanner(System.in);
				loaded = new ArrayList<String>();
				String serverip = "50.174.193.13";
				//String serverip = "localhost";
				parentNum = n;
				try{
					//create new socket at IP address serverip in its 10000
					socket = new Socket(serverip, 80);

					//create IO streams for client
					//output to client's system
					//input from server
					output = new PrintWriter(socket.getOutputStream(), true);
					threadInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					//waits for server to send a string to client confirming connection
					//acts the same exact way System.in does in that it wait for input from a stream
					//before continuing
					String inputString = threadInput.readLine();
					System.out.println(inputString);
					
					//verified connection..do shit here

					
					//while(true){
						//wait for client input and when it is there, continue
						String userInput = getRequest();
						//print userInput
						output.println(userInput);
						moreActions();
						
						socket.close();

					//}
				}catch(Exception e){
					//scanner.close();
					e.printStackTrace();
				}
	}
	
	protected void loadStrings(){
		
	}
	
	//place for extra stuff
	protected void moreActions(){
		
	}
	
	public String getInfoFromRequest(){
		return info;
	}
	
	protected String getRequest() {
		return "Example parent";
	}
	
	//method that return info in form of string to the user
	protected String interpretServerInfo(String serverinfo){
		return serverinfo;
	}
	
/*	protected String getInput(){
		String input;
	}*/
}
