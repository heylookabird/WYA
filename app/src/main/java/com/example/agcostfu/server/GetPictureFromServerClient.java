package com.example.agcostfu.server;

//import java.awt.image.BufferedImage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.util.ArrayList;

//import javax.imageio.ImageIO;

import com.example.agcostfu.main.PictureNode;


public class GetPictureFromServerClient extends Client{
    PictureNode pictures;
    double lon, lat;
	public GetPictureFromServerClient(String n, double lat, double lon){
        super();
        this.lat = lat;
        this.lon = lon;
        init(n);
	}
	
	@Override
	public String getRequest(){
		return "getPhotos , " + parentNum + " , " + " , " + lat + " , " + lon;
	}


    public PictureNode getPictures(){
        return pictures;
    }

	@Override
	protected void moreActions(){

        String current, user = "", title = "", la = "", lo = "";

        byte []array = null;
        try {
            while((current = threadInput.readLine()) != null){
                int commas = 0;
                for(int i = 0; i < current.length(); i++){
                    String temp ="";
                    if(current.charAt(i) != ','){
                        temp = temp + current.charAt(i);
                    }else{
                        if(commas == 0){
                            array = temp.getBytes();
                        }else if(commas == 1){
                            user = temp;
                        } else if(commas == 2){
                            title = temp;
                        }else if(commas == 3){
                            la = temp;
                        }else if(commas == 4){
                            lo = temp;
                        }

                        commas++;

                        temp ="";

                    }
                }
            }

            Bitmap image = BitmapFactory.decodeByteArray(array, 0, array.length);
            pictures = new PictureNode(image, user, title, Double.parseDouble(la), Double.parseDouble(lo));
        } catch (IOException e) {
            e.printStackTrace();
        }
		/*try{
			Bitmap img = BitmapFactory.decodeStream(socket.getInputStream());


            int commas = 0, i = 0;
            String strings[] = new String[5];
            String input = threadInput.readLine();
            String current = "";
            while(commas < 4){
                if(input.charAt(i) != ','){
                    current = current + input.charAt(i);
                }else if(i == input.length() - 1){
                    strings[4] = current;
                    commas++;
                }else {
                    current = "";
                    commas++;
                }
                i++;
            }

            PictureNode node = new PictureNode(img, strings[0], strings[1], Double.parseDouble(strings[3]), Double.parseDouble(strings[4]));
            pictures.add(node);

            //hopefully recursive
            moreActions();
        }catch(Exception e){
			
		}*/
	}
}
