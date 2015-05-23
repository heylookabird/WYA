package com.example.agcostfu.server;
/*
* Client to send Picture data to the server.
*
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class AddPictureNodeToGroupClient extends Client {
	String fl, chat;
	double lon, lat;
    byte []array;

	public AddPictureNodeToGroupClient(String number, String fileLoc, String chat,
			double lon, double lat) {
		fl = fileLoc;
		this.lon = lon;
		this.lat = lat;
		this.chat = chat;

        Bitmap bitmap = BitmapFactory.decodeFile(fl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,  90, baos);
        array = baos.toByteArray();
        try {
            baos.close();
        }catch(Exception e){

        }
        init(number);
	}

	@Override
	public String getRequest() {
		return "upload ," + array.toString() + " ," + parentNum + " ," + chat + " ,"
				+ lon + " ," + lat;
	}
}
