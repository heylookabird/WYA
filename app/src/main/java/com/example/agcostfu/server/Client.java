package com.example.agcostfu.server;
/**
 * Parent Class that every Client extends from to follow processes involved with connecting to host
 * computer. Override getRequest to send request to the server, and moreActions to handle any
 * output from the server.
 *
 */
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

    public Client() {
    }

    public Client(String n) {
        init(n);
    }

    /*
    * Initializes server actions but must be called after any important information is received
    * from children
    *
     */
    protected void init(String n) {
        info = "";
        loaded = new ArrayList<String>();
        String serverip = "50.174.193.13";
        parentNum = n;
        try {
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



            //wait for client input and when it is there, continue
            String userInput = getRequest();
            //print userInput
            output.println(userInput);
            moreActions();

            socket.close();

            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Overriden by children to interpret data that was sent back from server
     */
    protected void moreActions() {

    }

    /**
     *
     * @return
     * Raw information sent from server in the form of a String
     */
    public String getInfoFromRequest() {
        return info;
    }

    /**
     *
     * @return
     * Request that server receives in the format that the server needs. Overriden by all subclasses
     */
    protected String getRequest() {
        return "Example parent";
    }
}
