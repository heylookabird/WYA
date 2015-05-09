package com.example.agcostfu.server;

import java.io.IOException;

/**
 * Created by AGCOSTFU on 5/9/2015.
 */
public class GetGroupPictureLocationClient extends Client{
    public GetGroupPictureLocationClient(String n) {
        super(n);
    }

    @Override
    protected String getRequest() {
        return "updatePictures , " + this.parentNum;
    }

    @Override
    protected void moreActions() {

        try {
            String in = threadInput.readLine();
            while (in != null) {
                if(in.endsWith("/./."))
                    break;

                System.out.println(in);
                info = info + " " + in;
                in = threadInput.readLine();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
