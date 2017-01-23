package com.gotechno.technovanza13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
//import com.androidquery.service.MarketService;


public class MainActivity extends SherlockActivity {
	Dialog ad1;
	private Point p;
	//private boolean showRegistrationMenu;
	public CheckBox dontShowAgain;
	public static final String PREFS_NAME = "MyPrefsFile1";
	SherlockActivity mActivity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		MarketService ms = new MarketService(this);
  //  ms.level(MarketService.MINOR).checkVersion();
		//ms.locale(Locale.getDefault().toString()).force(true).level(MarketService.MINOR).checkVersion();
		setTitle(R.string.techno);
		
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.gotechno.technovanza13", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
			    MessageDigest md = MessageDigest.getInstance("SHA");
			    md.update(signature.toByteArray());
			    String sign=Base64.encodeToString(md.digest(), Base64.DEFAULT);
			    Log.e("MY KEY HASH:", sign);
			    //Toast.makeText(getApplicationContext(),sign, Toast.LENGTH_LONG).show();
			}
			} catch (NameNotFoundException e) {

			} catch (NoSuchAlgorithmException e) {

			}
		
		ImageButton ib1=(ImageButton)findViewById(R.id.imageButton1);
		ImageButton ib2=(ImageButton)findViewById(R.id.imageButton2);
		ImageButton ib3=(ImageButton)findViewById(R.id.imageButton3);
		ImageButton ib4=(ImageButton)findViewById(R.id.imageButton4);
		ImageButton ib5=(ImageButton)findViewById(R.id.imageButton5);
		Button b1=(Button)findViewById(R.id.sponsor);
		Button b2=(Button)findViewById(R.id.schedule);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SponsorActivity.class);
				startActivity(i);
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), StyledTabsSchedule.class);
				startActivity(i);
			}
		});
		ib1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(MainActivity.this, StyledTabs.class);
				Intent i = new Intent(getApplicationContext(), StyledTabs.class);
				startActivity(i);
				//Toast.makeText(getApplicationContext(),(ImageButton) v).getText()+"was clicked", Toast.LENGTH_SHORT).show();	
			}
		});
			ib2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,GalleryView.class);
			    startActivity(intent);		
			}
		});
			
		ib3.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
					//TODO Auto-generated method stub
					
				Intent facebookIntent = getOpenFacebookIntent(MainActivity.this);
				startActivity(facebookIntent);				
			}
		});
ib4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(MainActivity.this, StyledTabs.class);
				
				Intent i = new Intent(getApplicationContext(), StyledTabs_house.class);
				
				startActivity(i);
					
			}
		});
		
ib5.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Intent i = new Intent(MainActivity.this, StyledTabs.class);
		
		Intent i = new Intent(getApplicationContext(), StyledTabsSocial.class);
		
		startActivity(i);
			
	}
});
		if(isAlreadyRegistered()) {
			Data.showRegistrationMenu=false;
		}else {
			Data.showRegistrationMenu=true;
			//Toast.makeText(getApplicationContext(),"You haven't yet registered yourself. Please register to be able to participate in events.", Toast.LENGTH_SHORT).show();
			
			
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
	        LayoutInflater adbInflater = LayoutInflater.from(this);
	        View eulaLayout = adbInflater.inflate(R.layout.dont_show_again, null);
	        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
	        adb.setView(eulaLayout);
	        adb.setCancelable(false);
	        adb.setTitle("Attention");
	        adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
	        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                String checkBoxResult = "NOT checked";
	                if (dontShowAgain.isChecked())
	                    checkBoxResult = "checked";
	                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	                SharedPreferences.Editor editor = settings.edit();
	                editor.putString("skipMessage", checkBoxResult);
	                // Commit the edits!
	                editor.commit();
	                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://misha.beshkin.lv/android-alertdialog-with-checkbox/")));
	                return;
	            }
	        });

	        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                String checkBoxResult = "NOT checked";
	                if (dontShowAgain.isChecked())
	                    checkBoxResult = "checked";
	                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	                SharedPreferences.Editor editor = settings.edit();
	                editor.putString("skipMessage", checkBoxResult);
	                // Commit the edits!
	                editor.commit();
	                return;
	            }
	        });
	        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	        String skipMessage = settings.getString("skipMessage", "NOT checked");
	        if (!skipMessage.equals("checked"))
	            adb.show();
		}
		mActivity.supportInvalidateOptionsMenu();
		
		if(isFirstUse()) {
			onFirstUse();
		}
		
		ActionBar ab = getSupportActionBar();
		p = new Point();
        p.x = 100;
        p.y = 100;
		
	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	 @SuppressLint("NewApi")
	@Override
	    public void onResume() {
	    	super.onResume();
	    	invalidateOptionsMenu();
	    }
	
    // The method that displays the popup.

	  private void showPopup(final Activity context, Point p, int layoutId) {

          Rect rectgle= new Rect();
          Window window= getWindow();
          window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
          int StatusBarHeight= rectgle.top;
          int contentViewTop=
              window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
          int TitleBarHeight= contentViewTop - StatusBarHeight;
          Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
          int popupWidth = display.getWidth();
          int popupHeight = (display.getHeight()-StatusBarHeight);

          // Inflate the popup_layout.xml
          LinearLayout viewGroup = (LinearLayout) context
                          .findViewById(R.id.popupLinearLayout);
          LayoutInflater layoutInflater = (LayoutInflater) context
                          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //View layout = layoutInflater.inflate(R.layout.activity_popup, viewGroup);
          View layout = layoutInflater.inflate(layoutId, viewGroup);

          // Creating the PopupWindow
          final PopupWindow popup = new PopupWindow(context);
          popup.setContentView(layout);
          popup.setWidth(popupWidth);
          popup.setHeight(popupHeight);
          popup.setFocusable(true);
          popup.setAnimationStyle(R.style.PopupWindowAnimation);

          // Some offset to align the popup a bit to the right, and a bit down,
          // relative to button's position.

          int OFFSET_X = 0;
          int OFFSET_Y = 0;
          // Clear the default translucent background
          popup.setBackgroundDrawable(new BitmapDrawable());
          // Displaying the popup at the specified location, + offsets.
          popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
                          + OFFSET_Y);

          // Getting a reference to Close button, and close the popup when
          // clicked.
          Button close = (Button) layout.findViewById(R.id.close);
          TextView devList=(TextView)layout.findViewById(R.id.technoDevList);
          devList.setText(Html.fromHtml("Siddharth Shah<br>Shaarad Dalvi<br>Vinil Jain<br>Kunal Shah<br>Sahil Shah<br>Rishabh Sharma<br>Rishikesh Pange<br>Chirag Jainv<br>Akshay C Shah (Comps)<br>Akshay C Shah (IT)"));
          close.setOnClickListener(new OnClickListener() {
                  @Override
                  public void onClick(View v) {
                          popup.dismiss();
                  }
          });
  }
	  
    public static Intent getOpenFacebookIntent(Context context) {

		   try {
		    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/136220994671"));
		   } catch (Exception e) {
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/136220994671"));
		   }
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		if(!Data.showRegistrationMenu) {
			MenuItem item=menu.findItem(R.id.reg_via_fb);
			item.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case R.id.reg_via_fb:
				//User hasn't yet registered. If had he, it wouldn't have been shown..confirm registration by user and proceed to facebook login
				AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);     
    			alt_bld.setMessage("Do you already have an existing Techno Account?");
    			alt_bld.setCancelable(false);
    			alt_bld.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent existingIntent=new Intent(getApplicationContext(),ExistingLogin.class);
	    				startActivity(existingIntent);
	    				if(isAlreadyRegistered()) {
	    						Data.showRegistrationMenu=false;
	    				}
	    				mActivity.supportInvalidateOptionsMenu();
	                	////finish();
					}
    			});
    			alt_bld.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					Intent newIntent=new Intent(getApplicationContext(),NewLogin.class);
	    				startActivity(newIntent);
	    				if(isAlreadyRegistered()) {
	    						Data.showRegistrationMenu=false;
	    				}
	    				mActivity.supportInvalidateOptionsMenu();
    				} 
    			}); 
    			alt_bld.show();
				//Intent loginIntent = new Intent(this,LoginListActivity.class);
				//this.startActivity(loginIntent);
				break;
			case R.id.netmenu:
				if(!isNetworkAvailable()) {
					Toast.makeText(getApplicationContext(),"Please make sure that you are connected to the Internet", Toast.LENGTH_LONG).show();
					break;
				}
				//LOAD A LIST HAVING IRC AND TECHNO FB PAGE LINK
				Intent feedIntent = new Intent(this,NetListActivity.class);
				this.startActivity(feedIntent);
				break;
			//case R.id.settings:
				//break;
			case R.id.about_us:
				showPopup(this,p,R.layout.activity_popup);
				break;
		}
		/*if (item.getItemId()==R.id.search) {}
		else if (item.getItemId()==R.id.refresh) {}
		else if (item.getItemId()==R.id.action_accout_state) {}
		else if (item.getItemId()==R.id.settings) {}
		else if (item.getItemId()==R.id.about_us) {}
		*/
		return super.onOptionsItemSelected(item);
	}
	
	private boolean isAlreadyRegistered() {
		//check of User data already exists...
    	String path=getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator + Data.userDataFileName;
    	Log.i("MYLOG","File name while retrieving : " + path);
    	File file=new File(path);
    	if(file.exists()) {
    		return true;
    	}
    	return false;
	}
	
	private boolean isFirstUse() {
		String path=getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator + Data.firstUseFileName;
    	Log.i("MYLOG","File name while retrieving : " + path);
    	File file=new File(path);
    	if(file.exists()) {
    		return false;
    	}
    	return true;
	}
	
	private void onFirstUse() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setTitle("Thank you for downloading!");
		alt_bld.setMessage("Via this app, you can get the latest Techno news, register for events and ask your queries.");
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Welcome", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Intent intent=new Intent(getApplicationContext(),FbLoginAct.class);
				//startActivity(intent);
				//showRegistrationMenu=false;	
				//invalidateOptionsMenu();
            	//finish();
			}
		});
		alt_bld.show();
		
		String path=getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator + Data.firstUseFileName;
		File file=new File(path);
		PrintWriter pw=null;
		try {
			pw = new PrintWriter(file);
			pw.write(" ");
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
}