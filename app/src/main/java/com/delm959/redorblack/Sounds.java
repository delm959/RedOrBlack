package com.delm959.redorblack;

import java.io.IOException;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;


public class Sounds  {
	
	final MediaPlayer mp = new MediaPlayer();
	
	public static String [] sounds = {"Sound Effect 13.mp3", "Sound Effect 9.mp3", "Sound Effect 15.mp3", "Sound Effect 14.mp3"};
	
	public static boolean soundOn = true;

	AssetManager context;
	AssetFileDescriptor afd;

	public void getCont(Context c) {
	       context = c.getAssets();
	}
	
	
	public void playSound(int n){
	
		if(mp.isPlaying())
		        {  
		            mp.stop();
		        } 

		        try {
		            mp.reset();
		            afd = context.openFd(sounds[n]);
		            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
		            mp.prepare();
		            mp.start();
		        } catch (IllegalStateException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

	}

}
