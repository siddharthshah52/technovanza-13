package com.gotechno.technovanza13;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.view.Menu;

import org.schwering.irc.lib.IRCConnection;

public class BubblesActivity extends Activity {
	public static DiscussArrayAdapter adapter;
	public static ListView lv;
	//private LoremIpsum ipsum;
	private EditText editText1;
	private Button sendButton;
	private static Random random;
	IRCConnection connection;
	private ProgressDialog progressBar;
	private boolean isConnectedToServer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		random = new Random();
		//ipsum = new LoremIpsum();
		isConnectedToServer=false;
		lv = (ListView) findViewById(R.id.listView1);
		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
		lv.setAdapter(adapter);
		sendButton=(Button)findViewById(R.id.techno_page_button);
		sendButton.setOnClickListener(sendListener);
		
		editText1 = (EditText) findViewById(R.id.editText1);
		/*editText1.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				//editText1.isFocused() ||
				//editText1.isInTouchMode()
				//if (editText1.isSelected()){
					lv.smoothScrollToPosition(adapter.getCount());
				//}
			}
		});*/
		/*editText1.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//editText1.requestFocus();
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					connection.doPrivmsg("#techno13", editText1.getText().toString());
					adapter.add(new OneComment(false, editText1.getText().toString()));
					//editText1.requestFocus();
					editText1.setText("");
					//editText1.requestFocus();
					return true;
				}
				return false;
			}
		});*/
		editText1.setOnEditorActionListener(new OnEditorActionListener() {        

			 @Override
		        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		            if(actionId == EditorInfo.IME_ACTION_SEND){
		            	connection.doPrivmsg("#techno13", editText1.getText().toString());
						adapter.add(new OneComment(false, editText1.getText().toString()));
						lv.smoothScrollToPosition(adapter.getCount());
		                editText1.setText("");
		                editText1.requestFocus();
		                InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
		                imm.showSoftInput(editText1, 0);
		                //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		            }
		            return true;
		        }
		});
		editText1.setImeOptions(EditorInfo.IME_ACTION_SEND);
		
		//Check whether Internet connection is available..
		if(isNetworkAvailable()){
			//new Login().execute();
			connectToIRC();
		}else {
			Toast.makeText(getApplicationContext(),"Please make sure you are connected to the Internet", Toast.LENGTH_LONG).show();
		}
		
	}
	
	//Checks and returns whether network is available
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private void connectToIRC() {
		progressBar = new ProgressDialog(this);
		progressBar.setCancelable(true);
		progressBar.setMessage("Connecting to Server...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progressBar.setProgress(0);
		//progressBar.setMax(100);
		progressBar.show();

		new Thread(new Runnable() {
		  public void run() {
			  new Login().execute();
			  while(true) {
				  //sleep for 1 second..
				  try {
					  Thread.sleep(1000);
				  }catch(InterruptedException e) {
				  }
				  // ok, connected..
				  if (isConnectedToServer) {
					  //close the progress bar dialog
					  break;
				  }
			  }
			  progressBar.dismiss();
		  }
		}).start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu m) {
		getMenuInflater().inflate(R.menu.irc_menu, m);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.part_channel:
				quitConfirm();
			break;
				
		}
		return true;
		
	}
	
	private OnClickListener sendListener = new OnClickListener() {
        public void onClick(View v) {
        	connection.doPrivmsg("#techno13", editText1.getText().toString());
        	//text.append("\nMe : "+sendText.getText().toString());
        	adapter.add(new OneComment(false, editText1.getText().toString()));
        	lv.smoothScrollToPosition(adapter.getCount());
        	editText1.setText("");
        	
        }
    };
    
    private void quitConfirm() {
    	AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);     
		alt_bld.setMessage("This will log you out of Server and you won't be able to receive messages anymore. Log out?");
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
			//super.onBackPressed();
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(isNetworkAvailable()) {
					new Disconnect().execute();
				}
				finish();
			}
		});
		alt_bld.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { // TODO Auto-generated method stub dialog.cancel();
		} }); 
		alt_bld.show();
    }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		quitConfirm();
	}

	public static void addBubble(String bubText) {
		adapter.add(new OneComment(true,bubText));
		lv.smoothScrollToPosition(adapter.getCount());
	}


	private static int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	
	
	private class Login extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	Log.i("MYLOG","Inside asynctask");
        	connection=new IRCConnection("irc.freenode.net",new int[] {6667},"",Data.nick,Data.nick,Data.nick);
        	 connection.addIRCEventListener(new MyListener("")); 
        	 connection.setDaemon(true);
        	 connection.setColors(false); 
        	 connection.setPong(true);
        	 Log.i("MYLOG","now inside try");
        	//Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
    		try {
    			connection.connect();
    			connection.doJoin("#techno13");
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			Log.i("MYLOG","Exception in conection");
    			//Toast.makeText(getApplicationContext(), "IOE", Toast.LENGTH_SHORT).show();
    		}
    		while (!connection.isConnected()) {
    			try {
    				Thread.sleep(500);
    			}catch(InterruptedException e){
    				
    			}
    		}
    		if (connection.isConnected()) {
    			//Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
    		}else {
    			//Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_SHORT).show();
    		}
    		Log.i("MYLOG","finished asynctask");
    		isConnectedToServer=true;
    		return "executed";
        }      

        @Override
        protected void onPostExecute(String result) {
              //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
        	//new Operation().execute();
        	//text.setText(st);
        	//Log.i("MYLOG", "length : " + st.length());
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        	//text.setText(st);
        }
	}	
	
	private class Disconnect extends AsyncTask<String, Void, String> {

	      @Override
	      protected String doInBackground(String... params) {
	    	connection.doPart("#techno13","Parting #techno13...");
	    	connection.close();
	  		return "executed";
	      }      

	      @Override
	      protected void onPostExecute(String result) {
	    	  
	      }

	      @Override
	      protected void onPreExecute() {
	      }

	      @Override
	      protected void onProgressUpdate(Void... values) {
	      	//text.setText(st);
	      }
		}
}