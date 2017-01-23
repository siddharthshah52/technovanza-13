package com.gotechno.technovanza13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class IrcMain extends Activity{
	EditText text;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.irc_main);
		
		button=(Button)findViewById(R.id.techno_page_button);
		button.setOnClickListener(buttonListener);
		text=(EditText)findViewById(R.id.editText1);
		text.setText("");
		
	}
	
	 private OnClickListener buttonListener = new OnClickListener() {
	        public void onClick(View v) {
	        	String nick=text.getText().toString();
	        	Data.nick=nick;
	        	Intent termIntent=new Intent(getApplicationContext(),BubblesActivity.class);
        		startActivity(termIntent);
        		text.setText("");
        		Log.i("MYLOG","Data.nick is : " + Data.nick);
        		finish();
	        }
	    };  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
