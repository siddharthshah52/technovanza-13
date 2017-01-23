package com.gotechno.technovanza13;

import static com.gotechno.technovanza13.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.gotechno.technovanza13.CommonUtilities.SENDER_ID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class NewLogin extends Activity{
	Button newRegistration;
	private ProgressDialog pDialog;
	UserData userdata;
	private TextView nameText,genderText,locationText,cellText,clgText,branchText,emailText,idText,passText;
	private EditText nameEdit,genderEdit,locationEdit,clgEdit,branchEdit,cellEdit,emailEdit,idEdit,passEdit;
	JSONParser jsonParser=new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static String url_create_product = "http://technovanza.org/app_new_reg.php";
	AsyncTask<Void, Void, Void> mRegisterTask;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    static final String EXTRA_MESSAGE = "message";
    boolean isFetched;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_login);
		newRegistration=(Button)findViewById(R.id.new_register_me_button);
		newRegistration.setOnClickListener(newRegisterListener);
		
		nameEdit=(EditText)findViewById(R.id.new_name_edit);
		genderEdit=(EditText)findViewById(R.id.new_gender_edit);
		locationEdit=(EditText)findViewById(R.id.new_location_edit);
		clgEdit=(EditText)findViewById(R.id.new_college_edit);
		branchEdit=(EditText)findViewById(R.id.new_branch_edit);
		cellEdit=(EditText)findViewById(R.id.new_contact_edit);
		emailEdit=(EditText)findViewById(R.id.new_email_edit);
		idEdit=(EditText)findViewById(R.id.new_technoid_edit);
		passEdit=(EditText)findViewById(R.id.new_technopass_edit);
	}
	
	private boolean validate() {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String email=emailEdit.getText().toString();
		boolean b = email.matches(EMAIL_REGEX);
		if(!b) {
			Toast.makeText(getApplicationContext(), "Please fill E-mail address correctly", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(cellEdit.getText().toString().length()<10){
			Toast.makeText(getApplicationContext(), "Please fill Cell number correctly", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(cellEdit.getText().toString().charAt(0)=='0' && cellEdit.getText().toString().length()<11){
			Toast.makeText(getApplicationContext(), "Please fill Cell number correctly", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(cellEdit.getText().toString().length()>14) {
			Toast.makeText(getApplicationContext(), "Please fill Cell number correctly", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private String SHAHash(String md5) {
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
	
	private OnClickListener newRegisterListener = new OnClickListener() {
        public void onClick(View v) {
        	userdata=new UserData();
        	
        	if(nameEdit.getText().toString().equalsIgnoreCase("") || genderEdit.getText().toString().equalsIgnoreCase("") || cellEdit.getText().toString().equalsIgnoreCase("") || clgEdit.getText().toString().equalsIgnoreCase("") || branchEdit.getText().toString().equalsIgnoreCase("") || emailEdit.getText().toString().equalsIgnoreCase("") || locationEdit.getText().toString().equalsIgnoreCase("") || idEdit.getText().toString().equalsIgnoreCase("") || passEdit.getText().toString().equalsIgnoreCase("")) {
        		Toast.makeText(getApplicationContext(), "Please fill all the required fields", Toast.LENGTH_SHORT).show();
        		return;
        	}
        	if(!validate()) {
        		Toast.makeText(getApplicationContext(), "Please fill all the required fields", Toast.LENGTH_SHORT).show();
        		return;
        	}
        	userdata.technoID=idEdit.getText().toString();        	
        	userdata.name=nameEdit.getText().toString();
        	userdata.email=emailEdit.getText().toString();
        	userdata.collegeName=clgEdit.getText().toString();
        	userdata.branchName=branchEdit.getText().toString();
        	userdata.cellNo=cellEdit.getText().toString();
        	userdata.location=locationEdit.getText().toString();
        	userdata.gender=genderEdit.getText().toString();
        	
        	
        	//userdata.SHAHash=SHAHash(userdata.firstName+userdata.lastName+userdata.fbUserName);
        	//Data.showRegistrationMenu=false;
	        isFetched=true;
	         
	          Log.i("MYLOG","Saving File...");
                try {
					//BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(getActivity().getFilesDir()+File.separator+"MyFile.txt")));
                	Log.i("MYLOG","File path : " + getApplicationContext().getFilesDir()+File.separator+Data.userDataFileName);
					ObjectOutputStream objOStream=new ObjectOutputStream(new FileOutputStream(new File(getApplicationContext().getFilesDir()+File.separator+Data.userDataFileName)) );
					objOStream.writeObject(userdata);
					objOStream.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            new RegisterToDb().execute();
            
        }
    };
	
    class RegisterToDb extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(NewLogin.this);
            pDialog.setMessage("Registering to Database...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            */
            pDialog=ProgressDialog.show(NewLogin.this, "", "Registering to Database...",false,true);
            
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", userdata.technoID));
            params.add(new BasicNameValuePair("name", userdata.name));
            params.add(new BasicNameValuePair("email", userdata.email));
            params.add(new BasicNameValuePair("college_name", userdata.collegeName));
            params.add(new BasicNameValuePair("techno_pass", SHAHash(passEdit.getText().toString())));
            params.add(new BasicNameValuePair("branch", userdata.branchName));
            params.add(new BasicNameValuePair("contact_no", userdata.cellNo));
            params.add(new BasicNameValuePair("location", userdata.location));
            params.add(new BasicNameValuePair("gender", userdata.gender));
            
            String captcha=generateCaptcha(6).toUpperCase();
            params.add(new BasicNameValuePair("captcha",captcha));
            Log.i("MYLOG","CAPTCHA : " + captcha);
            // getting JSON Object
            // Note that create product url accepts POST method
            Log.i("MYLOG","making httprequest...");
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,"POST", params);
            
            
 
            // check log cat for response
            //Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(getActivity(), AllProductsActivity.class);
                    //startActivity(i);
                	Log.i("MYLOG","Success!");
                	Data.showRegistrationMenu=false;
                	//setWidgetVisibility(View.INVISIBLE);
                	//welcomeNote.setText("You have successfully registered!");
                	//Toast.makeText(getActivity(), "Thank you for registering!", Toast.LENGTH_SHORT).show();
                    // closing this screen
                    //finish();
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
            pDialog.dismiss();
            registerForPush();
            Toast.makeText(getApplicationContext(), "Thank you for registering!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), userdata.fbUserName, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private void registerForPush() {
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(getApplicationContext(),"Internet Connection Error","Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        
        GCMRegistrar.checkManifest(getApplicationContext());
        
        getApplicationContext().registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
        
        final String regId = GCMRegistrar.getRegistrationId(getApplicationContext());
        if (regId.equals("")) {
            // Registration is not present, register now with GCM           
            GCMRegistrar.register(getApplicationContext(), SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
                // Skips registration.              
                Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = getApplicationContext();
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
    
    private String generateCaptcha(int len) {
    	String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	Random rnd = new Random();
    	StringBuilder sb = new StringBuilder( len );
    	   for( int i = 0; i < len; i++ ) 
    	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
    	   return sb.toString();
    	
    }
}
