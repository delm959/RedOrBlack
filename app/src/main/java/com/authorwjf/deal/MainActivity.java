package com.authorwjf.deal;




import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;

import android.graphics.PorterDuff;

import com.authorwjf.redorblack.R;

public class MainActivity extends Activity implements  AnimationListener {

	private Animation animation1;
	private Animation animation2;

	private int resID;
	private int prevResID;
	private int prev2ResID;
	private int soundToPlay;
	private String randCard;

	Cards cardDeck = new Cards();
	Sounds sounds = new Sounds();
	Transitions trans = new Transitions();

	private ImageView cardImageView;
	private ImageView prevCardImageView;
	private ImageView prevCard2ImageView;
	private ImageButton soundButton;
	public static ImageButton instButton;
	private Button button1;
	private Button button2;
	private Button button3;

	private TextView counter;
	private TextView text;
	private Toast toast;
	
	private boolean instOn = false;
	private boolean stage1 = false;
	private boolean stage2 = false;
	private boolean stage3 = false;
	private boolean doub = false;
	private boolean restart = false;
	
	int max = 0;
	int min = 0;
	int current = 0;
	int previous = 0;
	int previous2 = 0;

	final PopupWindow popupWindow = new PopupWindow();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);

		
		cardImageView = (ImageView) findViewById(R.id.imageView1);
		prevCardImageView = (ImageView)findViewById(R.id.imageView3);
		prevCard2ImageView = (ImageView)findViewById(R.id.imageView2);
		soundButton = (ImageButton) findViewById(R.id.imageButton1);
		instButton = (ImageButton) findViewById(R.id.imageButton2);
		counter = (TextView)findViewById(R.id.textView1);
		text = (TextView)findViewById(R.id.textView2);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		//int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		
		counter.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width*0.10));
		text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (width*0.045));
		//counter.setTextSize(24 * height/width);
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		
		
		
		

		
		animation1 = AnimationUtils.loadAnimation(this, R.anim.slide_out_card);
		animation1.setAnimationListener(this);
		animation2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_card);
		animation2.setAnimationListener(this);
		
	
		redandBlackButtons();
		
		instructions();
		
		cardDeck.cardsToArray();
		
		sounds.getCont(this);
		
		soundButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				trans.transition = (TransitionDrawable) findViewById(R.id.imageButton1).getBackground();

				if(Sounds.soundOn == true){
					Sounds.soundOn = false;
					trans.transition.startTransition(800);
				}else{
					Sounds.soundOn = true;
					trans.transition.reverseTransition(800);
				}

			}
		});


		
	}
	
	public void redandBlackButtons(){
		
		
		
		button1.setOnClickListener(new OnClickListener(){
	
			@Override
			public void onClick(View v) {

				v.setEnabled(false);
				button2.setEnabled(false);
				button3.setEnabled(false);
				
				if(button1.getText().equals("NEW GAME")) newGame();
				else{
					
					//cardImageView.clearAnimation();
					cardImageView.setAnimation(animation1);
					cardImageView.startAnimation(animation1);
					buttonStart();
					redButton();

				}
				if(toast != null) toast.cancel();
				
			}
		});
		
		button2.setOnClickListener(new OnClickListener(){
	
			@Override
			public void onClick(View v) {

				v.setEnabled(false);
				button1.setEnabled(false);
				button3.setEnabled(false);
				
				//cardImageView.clearAnimation();
				cardImageView.setAnimation(animation1);
				cardImageView.startAnimation(animation1);
				buttonStart();
				blackButton();
				if(toast != null) toast.cancel();
			}
			
		});
		
		button3.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
		
				v.setEnabled(false);
				button1.setEnabled(false);
				button2.setEnabled(false);
				
				//cardImageView.clearAnimation();
				cardImageView.setAnimation(animation1);
				cardImageView.startAnimation(animation1);
				buttonStart();
				doubleButton();
				if(toast != null) toast.cancel();
			}
			
		});
		
		button3.setClickable(false);
		button3.setPressed(true);
	}
	
	public void buttonStart(){

		if(Cards.deckSize == 0){
			Cards.deckSize = 52;
			counter.setText(String.valueOf(Cards.deckSize));
			cardDeck.cardsToArray();
			Cards.secondPack = true;
			}
				
			int random = (int)(Math.random()*Cards.deckSize);
			randCard = cardDeck.getCards().get(random);
				
			cardDeck.getPreviousCards().add(randCard);
			int numOfPrevCards = cardDeck.getPreviousCardsSize();
				
			cardDeck.getCards().remove(random);
			Cards.deckSize--;
				
			resID = getResources().getIdentifier(randCard, "drawable", getPackageName());
			
				
			
			counter.setText(String.valueOf(Cards.deckSize));
				
			if(stage1){
				if(Cards.deckSize < 51 || Cards.secondPack) {
					
					current = Integer.valueOf(randCard.substring(1));
					previous = Integer.valueOf(cardDeck.getPreviousCards().get(numOfPrevCards-2).substring(1));
						
					if(stage2){
						if(Cards.deckSize < 50 || Cards.secondPack){
							previous2 = Integer.valueOf(cardDeck.getPreviousCards().get(numOfPrevCards-3).substring(1));
				
							if(previous > previous2){ max = previous; min = previous2;}
							if(previous2 > previous){ max = previous2; min = previous;}
						}
					}
				}
			}
				
			if(Cards.deckSize < 51 || Cards.secondPack){
				prevResID = getResources().getIdentifier(cardDeck.getPreviousCards().get(numOfPrevCards-2), "drawable", getPackageName());
				prevCardImageView.setImageResource(prevResID);
			}
			if(Cards.deckSize < 50 || Cards.secondPack){
				prev2ResID = getResources().getIdentifier(cardDeck.getPreviousCards().get(numOfPrevCards-3), "drawable", getPackageName());
				prevCard2ImageView.setImageResource(prev2ResID);
			}
		
	}
	
	public void redButton(){

			if(button1.getText().equals("IN") && current < max && current > min){
					
				youWin();
			}
				
			else if(button1.getText().equals("LOWER") && current < previous){
					
				inOrOut();
			}
				
			else if(randCard.contains("h") && button1.getText().equals("RED") || randCard.contains("d") && button1.getText().equals("RED")){
					
				higherOrLower();
					
			}else{
					
				youLose();
			}
	}
	
	public void blackButton(){

	
			if(button2.getText().equals("OUT") && current > max || button2.getText().equals("OUT") && current < min){
					
				youWin();	
			}
			else if(button2.getText().equals("HIGHER") && current > previous){
				
				inOrOut();
			}
			else if(randCard.contains("c") && button2.getText().equals("BLACK") || randCard.contains("s") && button2.getText().equals("BLACK")){
					
				higherOrLower();
			}
			else{
				
				youLose();	
			}
	}
	
	public void doubleButton(){

		doub = true;
		if(button2.getText().equals("OUT")){
			if(current == previous || current == previous2){
				youWin();
			}else{
				youLose();
			}
				
		}
		//else if(button2.getText().equals("HIGHER")){
		//	if(current == previous){
		//		inOrOut();
		//	}else{
		//		youLose();
		//	}
			
		//}
		
		
}

	
	public void youWin(){

		soundToPlay = 0;
		stage3 = true;
	}
	
	public void inOrOut(){
		soundToPlay = 3;
		stage2 = true;
	}
	
	public void higherOrLower(){
		soundToPlay = 3;
		
		stage1 = true;
	}

	public void youLose(){
		soundToPlay = 2;
		stage1 = false;
		stage2 = false;
		stage3 = false;
	}
	

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animation1) {
					
			cardImageView.setImageResource(resID);
			//cardImageView.clearAnimation();
			cardImageView.setAnimation(animation2);
			cardImageView.startAnimation(animation2);

			if(Sounds.soundOn) sounds.playSound(soundToPlay);
			
			//System.out.println("123456789");
			
		} else {
			//System.out.println("123456");
			//if(!restart)
				changeText();
			
			//Cards.isBackOfCardShowing=!Cards.isBackOfCardShowing;
			button1.setEnabled(true);
			//if(!stage3)
			button2.setEnabled(true);
			button3.setEnabled(true);

			restart = false;
		}
		
	}
	
	public void changeText(){

		if(!stage1){
			if(!restart){
			if(toast == null || toast.getView().getWindowVisibility() != View.VISIBLE){
				if(!doub){
					
					toast = Toast.makeText(this, "DRINK!!", Toast.LENGTH_SHORT);
					
				}
				else{
					toast = Toast.makeText(this, "DRINK HEAPS!!", Toast.LENGTH_SHORT);
				}
			
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			doub = false;
			}
			
			
			LinearLayout linearLayout = (LinearLayout) toast.getView();
			TextView messageTextView = (TextView) linearLayout.getChildAt(0);
			messageTextView.setTextSize(25);
			}
			
			button1.setText("RED");
			button2.setText("BLACK");
			
			

			button3.setClickable(false);
			button3.setPressed(true);
			
		}
		else if(stage3){
			
			if(!doub){
				
				toast = Toast.makeText(this, "YOU WIN!", Toast.LENGTH_LONG);
				
			}
			else{
				toast = Toast.makeText(this, "YOU WIN! DEALER DRINK!", Toast.LENGTH_LONG);
			}

			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
				
			doub = false;
			LinearLayout linearLayout = (LinearLayout) toast.getView();
			TextView messageTextView = (TextView) linearLayout.getChildAt(0);
			messageTextView.setTextSize(25);
			
			button1.setText("NEW GAME");
			button2.setVisibility(View.GONE);
			button3.setClickable(false);
			button3.setPressed(true);
			
		}
		else if(stage2){
			if(doub){
				toast = Toast.makeText(this, "DEALER DRINK!", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				LinearLayout linearLayout = (LinearLayout) toast.getView();
				TextView messageTextView = (TextView) linearLayout.getChildAt(0);
				messageTextView.setTextSize(25);
				doub = false;
			}
			

			button1.setText("IN");
			button2.setText("OUT");
			button3.setClickable(true);
			button3.setPressed(false);
		}
			
		
		else if(stage1){
			button1.setText("LOWER");
			button2.setText("HIGHER");
			
		}
	}
	
	
	public void newGame(){
		
		restart = true;
		soundToPlay = 1;
		if(Sounds.soundOn) sounds.playSound(1);
		
		resID = getResources().getIdentifier("card_back3", "drawable", getPackageName());
		cardImageView.setImageResource(resID);
		//cardImageView.clearAnimation();
		cardImageView.setAnimation(animation2);
		cardImageView.startAnimation(animation2);
		Cards.deckSize = 52;
		Cards.secondPack = false;
		counter.setText(String.valueOf(Cards.deckSize));
		cardDeck.cardsToArray();
		cardDeck.getPreviousCards().clear();
		((ImageView)findViewById(R.id.imageView2)).setImageDrawable(null);
		((ImageView)findViewById(R.id.imageView3)).setImageDrawable(null);
	    
		button2.setVisibility(View.VISIBLE);
	    button1.setText("RED");
		button2.setText("BLACK");
		//button2.setClickable(true);
		
		stage1 = false;
		stage2 = false;
		stage3 = false;
		
		
	}
	
	
	
	
	
	

	
	//-------------Instructions-------------------------------------------------------------------------
	
	public void instructions() {

		instButton.setOnClickListener(new Button.OnClickListener() {

		@Override
		 public void onClick(View v) {

		     trans.transition = (TransitionDrawable) findViewById(R.id.imageButton2).getBackground();
		     
		     if(!instOn){
		    	 	    	
		    	ScrollView popupView = (ScrollView) getLayoutInflater().inflate(R.layout.instructions, null);

		    	instOn = true;
		    	
		    	trans.transition.startTransition(800);
		    	
		    	popupWindow.setAnimationStyle(R.style.Animation);
		    	popupWindow.setContentView(popupView);
		    	   
		    	Display display2 = getWindowManager().getDefaultDisplay();
		    	int height = (int)(display2.getHeight()*0.8);
		    	int width = (int)(display2.getWidth()*0.9);
		    	
		    	
		     
		    	popupWindow.setHeight(height);
		    	popupWindow.setWidth(width);      

		    	button1.setClickable(false);
		    	button2.setClickable(false);
		    	button3.setClickable(false);
		    	soundButton.setClickable(false);
		    	
		     	popupWindow.showAtLocation(findViewById(R.id.imageButton2), Gravity.CENTER, 0, 0);

		     }else{    
		    	 	instOn = false;
					trans.transition.reverseTransition(800);
					popupWindow.dismiss();
					button1.setClickable(true);
			    	button2.setClickable(true);
			    	button3.setClickable(true);
			    	soundButton.setClickable(true);
					
				}

		      }
		   });
	}
	
	// ---------------------When exiting ----------------------------------------------------------------------------------
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        exitByBackKey();
			button3.setClickable(false);
			button3.setPressed(true);
	        
	        return true;
	    }else{
			button3.setClickable(false);
			button3.setPressed(true);
	    	return super.onKeyDown(keyCode, event);
	    }
	}
	


	protected void exitByBackKey() {
		
		if(!instOn){
			if(toast != null) toast.cancel();
		
				
		
	    AlertDialog alert = new AlertDialog.Builder(this)
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
	        					if(!stage1 || !stage2){
	        						button3.setClickable(false);
	        						button3.setPressed(true);
	        					}
	        					else{
	        						button3.setClickable(true);
	        						button3.setPressed(false);
	        					}
	                       }
	    })
	    .setNeutralButton("Restart", new DialogInterface.OnClickListener() {

	        // do something when the button is clicked
	        public void onClick(DialogInterface arg0, int arg1) {
	        	
	        	newGame();
	                       }
	    })
	      .show();
	    	

	    alert.setOnCancelListener(new DialogInterface.OnCancelListener()
	    {
	        @Override
	        public void onCancel(DialogInterface dialog)
	        {
	        	if(!stage1 || !stage2){
					button3.setClickable(false);
					button3.setPressed(true);
				}
				else{
					button3.setClickable(true);
					button3.setPressed(false);
				}
	        }
	    });
	   
		}else{
			instOn = false;
			trans.transition.reverseTransition(800);
			popupWindow.dismiss();
			button1.setClickable(true);
	    	button2.setClickable(true);
	    	soundButton.setClickable(true);
	    	
		}
		 

	}
	




	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

}
