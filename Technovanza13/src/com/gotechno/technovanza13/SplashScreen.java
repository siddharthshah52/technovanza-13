package com.gotechno.technovanza13;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashScreen extends Activity {
Thread t;
boolean b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		b=true;
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                               // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_screen);
		 t = new Thread(){
			public void run() {
				try{
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(b){
					Intent intent = new Intent(SplashScreen.this,MainActivity.class);
				    startActivity(intent);
					}
				}
			};
		};
		t.start();
	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	b=false;
finish();	
}
}
