package com.gotechno.technovanza13;

import static com.gotechno.technovanza13.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.gotechno.technovanza13.CommonUtilities.SENDER_ID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExistingLogin extends Activity {
	Button retrieveAccount;
	private ProgressDialog checkDialog;
	private String userName,passHash;
	JSONParser jsonParser=new JSONParser();
	private static String url_create_product = "http://techno.comuf.com/register_me.php";
	private static String url_check_exists = "http://techno.comuf.com/user_exists.php";
	private static String url_get_account = "http://technovanza.org/app_recover_account.php";
	private static final String TAG_SUCCESS = "success";
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	AsyncTask<Void, Void, Void> mRegisterTask;
	static final String EXTRA_MESSAGE = "message";
	UserData userdata;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.existing_account);
		retrieveAccount=(Button)findViewById(R.id.get_account_button);
		retrieveAccount.setOnClickListener(retrieveListener);
	}
	
	public String SHAHash(String md5) {
	 	   try {
	 	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	 	        byte[] array = md.digest(md5.getBytes());
	 	        StringBuffer sb = new StringBuffer();
	 	        for (int i = 0; i < array.length; ++i) {
	 	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	 	       }
	 	        return sb.toString();
	 	    } catch (java.security.NoSuchAlgorithmException e) {
	 	    }
	 	    return null;
	 	}
	
	private OnClickListener retrieveListener = new OnClickListener() {
        public void onClick(View v) {
        	EditText tempId=(EditText)findViewById(R.id.exiting_technoid_edit);
        	EditText tempPass=(EditText)findViewById(R.id.existing_technopass_edit);
        	if(tempId.getText().toString().length()==0 || tempPass.getText().toString().length()==0){
        		Toast.makeText(getApplicationContext(),"Plase fill the credentials properly", Toast.LENGTH_LONG).show();
        	}
        	userName=tempId.getText().toString();
        	passHash=SHAHash(tempPass.getText().toString());
        	new retrieveFromDb(passHash).execute();
        }
    };
    
    
    class retrieveFromDb extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
		private String passHash;
		private boolean recovered;
		private int recoverReturn;
		retrieveFromDb(String pass) {
			Log.i("MYLOG","typed hash : " + pass);
			recovered=false;
			passHash=pass;
		}
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*checkDialog = new ProgressDialog(getApplicationContext());
            checkDialog.setMessage("Retrieving existing account...");
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable(false);
            checkDialog.show();*/
            checkDialog=ProgressDialog.show(ExistingLogin.this, "", "Retrieving the account...",false,true);
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", userName));
            params.add(new BasicNameValuePair("passHash", passHash));
            // getting JSON Object
            // Note that create product url accepts POST method
            Log.i("MYLOG","making httprequest...");
            JSONObject json = jsonParser.makeHttpRequest(url_get_account,
                    "POST", params);
 
            // check log cat for response
            //Log.d("Create Response", json.toString());
            
 
            // check for success tag
            try {
                recoverReturn = json.getInt(TAG_SUCCESS);
 
                if (recoverReturn == 1) {
                	Log.i("MYLOG","Success...");
                	userdata=new UserData();
                	
                	userdata.technoID=json.getString("username");
                	userdata.name=json.getString("name");
                	userdata.email=json.getString("email");
                	userdata.collegeName=json.getString("college");
                	userdata.branchName=json.getString("course");
                	userdata.cellNo=json.getString("mobileno");
                	userdata.location=json.getString("city");
                	userdata.gender=json.getString("gender");
                	
                	Log.i("MYDATA ", userdata.technoID + " " + userdata.name);
                	                	
                	Log.i("MYLOG","Saving File...");
	                try {
						//BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(getApplicationContext().getFilesDir()+File.separator+"MyFile.txt")));
	                	Log.i("MYLOG","File path : " + getApplicationContext().getFilesDir()+File.separator+Data.userDataFileName);
						ObjectOutputStream objOStream=new ObjectOutputStream(new FileOutputStream(new File(getApplicationContext().getFilesDir()+File.separator+Data.userDataFileName)) );
						objOStream.writeObject(userdata);
						objOStream.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                recovered=true;
	                Data.showRegistrationMenu=false;
                	
                } else {
                	Log.i("MYLOG","Failure...");
                	
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            checkDialog.dismiss();
            if(recovered) {
            	Toast.makeText(getApplicationContext(), "Your Techno account has been successfully recovered..", Toast.LENGTH_LONG).show();
            	Log.i("MYLOG","before registerForPush()");
                registerForPush();
                Log.i("MYLOG","after registerForPush()");
            }else {
            	Toast.makeText(getApplicationContext(), "Couldn't recover your account..", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(getApplicationContext(), userdata.fbUserName, Toast.LENGTH_SHORT).show();
            
            finish();
            //Toast.makeText(getApplicationContext(), "Thank you for registering!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), userdata.fbUserName, Toast.LENGTH_SHORT).show();
            //getApplicationContext().finish();
        }
    }

    private void registerForPush() {
		cd = new ConnectionDetector(getApplicationContext().getApplicationContext());
		 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(getApplicationContext().getApplicationContext(),"Internet Connection Error","Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        
        GCMRegistrar.checkManifest(getApplicationContext());
        
        getApplicationContext().registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
        
        final String regId = GCMRegistrar.getRegistrationId(getApplicationContext().getApplicationContext());
        if (regId.equals("")) {
            // Registration is not present, register now with GCM           
            GCMRegistrar.register(getApplicationContext().getApplicationContext(), SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(getApplicationContext().getApplicationContext())) {
                // Skips registration.              
                Toast.makeText(getApplicationContext().getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = getApplicationContext().getApplicationContext();
                mRegisterTask = new AsyncTask<Void, Void, Void>() {
 
                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context,userdata.technoID, regId);
                        return null;
                    }
 
                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }
 
                };
                mRegisterTask.execute(null, null, null);
            }
        }
    }
    
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
             
            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */
             
            // Showing received message
            //lblMessage.append(newMessage + "\n");           
            Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
             
            // Releasing wake lock
            WakeLocker.release();
        }

    };
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
