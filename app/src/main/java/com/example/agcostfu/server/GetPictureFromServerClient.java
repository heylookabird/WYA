package com.example.agcostfu.server;

//import java.awt.image.BufferedImage;
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
    ArrayList<PictureNode> pictures;
	public GetPictureFromServerClient(String n){
        super();
        pictures = new ArrayList<PictureNode>();
        init(n);
	}
	
	@Override
	public String getRequest(){
		return "getPhotos , " + parentNum;
	}


    public ArrayList<PictureNode> getPictures(){
        return pictures;
    }

	/*@Override
	protected void moreActions(){
		try{
			BufferedImage img = ImageIO.read(socket.getInputStream());

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
			
		}
	}*/
}
