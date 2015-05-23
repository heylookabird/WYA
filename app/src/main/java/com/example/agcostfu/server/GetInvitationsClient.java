package com.example.agcostfu.server;
/*
* Client to receives any Invitations from groups.
*
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GetInvitationsClient extends Client {

    public GetInvitationsClient(String n) {
        super(n);
    }

    @Override
    protected String getRequest() {
        return "getInvitations , " + this.parentNum;
    }

    @Override
    public void moreActions() {
        try {
            info = threadInput.readLine();
            //System.out.println(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getInvites() {
        ArrayList<String> ret = new ArrayList<String>();

        try {
            StringTokenizer tok = new StringTokenizer(info, ",");

            while (tok.hasMoreTokens()) {
                String token = tok.nextToken();
                if (token.length() > 9)
                    ret.add(token);
                if (ret.size() == 3)
                    break;

            }
        } catch (Exception e) {

        }
        return ret;
    }

}
