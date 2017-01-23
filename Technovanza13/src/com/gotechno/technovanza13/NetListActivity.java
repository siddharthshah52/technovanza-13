package com.gotechno.technovanza13;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
 
import com.gotechno.technovanza13.ImageTextListAdapter.ImageTextListItem;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Arrays;
 
/**
 * An {@link Activity} that provides a "jumping-off point" to all other areas of the application via a simple main menu.
 * 
 * @author lukehorvat
 * 
 */
public class NetListActivity extends ListActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
 
        ImageTextListItem ircItem = new MainMenuListItem(R.string.irc, R.drawable.irc, IrcMain.class);
        ImageTextListItem fbPageItem = new MainMenuListItem(R.string.visit_techno_page, R.drawable.facebook, TechnoFeedActivity.class);
        //ImageTextListItem savedTestResultsListItem = new MainMenuListItem(R.string.main_menu_saved_test_results, R.drawable.saved_test_results_image, SavedTestResultsActivity.class);
        //ImageTextListItem aboutListItem = new MainMenuListItem(R.string.main_menu_about, R.drawable.about_image, AboutActivity.class);
 
        setListAdapter(new ImageTextListAdapter(this, Arrays.asList(ircItem, fbPageItem)));
    }
 
    @Override
    public void onListItemClick(ListView parent, View v, int position, long id)
    {
        MainMenuListItem selectedListItem = (MainMenuListItem) getListView().getItemAtPosition(position);
        if(selectedListItem.getTextResourceId()==R.string.irc) {
        	//check of User data already exists...
        	String path=getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator + Data.userDataFileName;
        	Log.i("MYLOG","File name while retrieving : " + path);
        	File file=new File(path);
        	if(file.exists()) {
        		
        		//File exists already, alert user and exit the FB login process...
        		//Open UserDetails and get user's name to show user...
        		UserData userData=null;
        		try {
					ObjectInputStream objIStream=new ObjectInputStream(new FileInputStream(new File(getApplicationContext().getFilesDir()+File.separator+Data.userDataFileName)));
					userData=(UserData) objIStream.readObject();
					objIStream.close();
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		/*AlertDialog.Builder alt_bld = new AlertDialog.Builder(this); 
        		if(userData!=null) {
        			alt_bld.setMessage("You have already logged in as "+ userData.firstName);
        			alt_bld.setCancelable(false);
        			alt_bld.setNeutralButton("OK", new OnClickListener() { public void onClick(DialogInterface dialog, int which) { // TODO Auto-generated method stub
        			} });
        			alt_bld.show();
        		}
    			*/
        		//String nick=text.getText().toString();
	        	Data.nick=userData.name;
	        	Intent termIntent=new Intent(getApplicationContext(),BubblesActivity.class);
        		startActivity(termIntent);
        		Log.i("MYLOG","Data.nick is : " + Data.nick);
        		finish();
        	}else {
        		if(!isNetworkAvailable()) {
        			Toast.makeText(getApplicationContext(),"Please make sure that you are connected to the Internet", Toast.LENGTH_LONG).show();
        		}else {
        		//	Prompt the user of details and proceed upon agreement...
        			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);     
    				alt_bld.setMessage("You first need to register yourself.Do u want to register?");
    				alt_bld.setCancelable(false);
    				alt_bld.setPositiveButton("Yes", new OnClickListener() { public void onClick(DialogInterface dialog, int which) { // TODO Auto-generated method stub
    					Intent intent=new Intent(getApplicationContext(),NewLogin.class);
    					startActivity(intent);
    					finish();
    				} });
    				alt_bld.setNegativeButton("No", new OnClickListener() { public void onClick(DialogInterface dialog, int which) { // TODO Auto-generated method stub dialog.cancel();
    				} }); 
    				alt_bld.show();
        		}
        	}
        }else if (selectedListItem.getTextResourceId()==R.string.visit_techno_page) {
        	if(!isNetworkAvailable()) {
        		Toast.makeText(getApplicationContext(),"Please make sure that you are connected to the Internet", Toast.LENGTH_LONG).show();
        	}else {
        	//startActivity(new Intent(this, selectedListItem.getActivityClass()));
        		Intent facebookIntent = getOpenFacebookIntent(NetListActivity.this);
        		startActivity(facebookIntent);
        		finish();
        	}
        }
        //startActivity(new Intent(this, selectedListItem.getActivityClass()));
        //finish();
    }
    
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
    
    public static Intent getOpenFacebookIntent(Context context) {

		   try {
		    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/136220994671"));
		   } catch (Exception e) {
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/136220994671"));
		   }
	}
    
 
    private static class MainMenuListItem extends ImageTextListItem
    {
        private Class<? extends Activity> mActivityClass;
 
        public MainMenuListItem(int textResourceId, int imageResourceId, Class<? extends Activity> activityClass)
        {
            super(textResourceId, imageResourceId);
 
            setActivityClass(activityClass);
        }
 
        public Class<? extends Activity> getActivityClass()
        {
            return mActivityClass;
        }
 
        public void setActivityClass(Class<? extends Activity> activityClass)
        {
            mActivityClass = activityClass;
        }
    }
}