package com.example.hp.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets {

    public static HashMap<Integer,BufferedImage> map = new HashMap<>();

    public static void init(){
	    	map.put(0,ImageLoader.loadImage("/tiles/0.png"));
        	for(int i=1;i<=2048;i*=2) {
        		String path = "/tiles/"+i+".png";
        		map.put(i, ImageLoader.loadImage(path));
        	}
    }
    
}
