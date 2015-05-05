package com.example.agcostfu.main;

//import java.awt.image.BufferedImage;
import java.io.FileInputStream;

//import javax.imageio.ImageIO;

public class PictureNode {
	double longitude, latitude;
	//BufferedImage image;
	String poster;
	String chat;
	String title;
	String serverPath;
	
	public PictureNode(String path, double lon, double lat) {
		chat = "";
		longitude = lon;
		latitude = lat;
		/*try {
			image = ImageIO.read(new FileInputStream(path));//TODO figure out what inputstream to use
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/*public PictureNode(BufferedImage image, String user, String title, double lon, double lat){
		this.image = image;
		setTitle(title);
		poster = user;
		longitude = lon;
		latitude = lat;
	}
	
	
	public BufferedImage getImage(){
		return image;
	}
	*/
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return title;
	}
	
	public String getChat(){
		return chat;
	}
	
	public double getLong(){
		return longitude;
	}
	
	public double getLat(){
		return latitude;
	}
	
	public void addToChat(String str){
		chat.concat(str + "\n");
	}

	public String getServerLocation() {
		return null;
	}
}