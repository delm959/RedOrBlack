package com.authorwjf.deal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;


import com.authorwjf.redorblack.R;

public class HomePage extends Activity {
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
	setContentView(R.layout.home_page);
	
	ImageView homePage1 = (ImageView) findViewById(R.id.homePageImageView1);
	ImageView homePage2 = (ImageView) findViewById(R.id.homePageImageView2);
	
	
	AlphaAnimation  blinkAnimation1= new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
	blinkAnimation1.setDuration(2500);
	blinkAnimation1.setStartOffset(1200);
	blinkAnimation1.setInterpolator(new LinearInterpolator()); // do not alter animation rate
	blinkAnimation1.setRepeatCount(1000); // Repeat animation infinitely
	blinkAnimation1.setRepeatMode(Animation.REVERSE);
	
	
	AlphaAnimation  blinkAnimation2= new AlphaAnimation(0, 1); // Change alpha from fully visible to invisible
	blinkAnimation2.setDuration(2500);
	blinkAnimation2.setStartOffset(1200);
	blinkAnimation2.setInterpolator(new LinearInterpolator()); // do not alter animation rate
	blinkAnimation2.setRepeatCount(1000); // Repeat animation infinitely
	blinkAnimation2.setRepeatMode(Animation.REVERSE);
	 

	homePage1.startAnimation(blinkAnimation1);
	homePage2.startAnimation(blinkAnimation2);

	homePage1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {

			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			finish();
			
		
		}
	});
		
	homePage2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				finish();
				
			
			}
		});
	
	}
	
	
	
	// ---------------------When exiting ----------------------------------------------------------------------------------
	
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if (keyCode == KeyEvent.KEYCODE_BACK) {
		        exitByBackKey();

		        return true;
		    }
		    return super.onKeyDown(keyCode, event);
		}

		protected void exitByBackKey() {

		    AlertDialog alertbox = new AlertDialog.Builder(this)
		    .setMessage("Do you want to exit?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	
		        public void onClick(DialogInterface arg0, int arg1) {

		            finish();
		            System.exit(0);

		        }
		    })
		    .setNegativeButton("No", new DialogInterface.OnClickListener() {

		        // do something when the button is clicked
		        public void onClick(DialogInterface arg0, int arg1) {
		                       }
		    })
		      .show();

		}
		
}
