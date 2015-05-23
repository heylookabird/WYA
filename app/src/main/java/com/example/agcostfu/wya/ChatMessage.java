package com.example.agcostfu.wya;
/*
Helper class for ChatArrayAdapter that stores whether to render on user's side.


 */
public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}