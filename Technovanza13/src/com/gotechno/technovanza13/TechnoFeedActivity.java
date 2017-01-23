package com.gotechno.technovanza13;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class TechnoFeedActivity extends SherlockActivity {
	ListView myList;
	ArrayAdapter<String> adapter;
	String[] listContents;
	ArrayList<String> feedList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_feed_layout);
		myList=(ListView)findViewById(R.id.listView1);
		feedList=new ArrayList<String>();
		new Feed().execute();
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.fb_feed_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case R.id.refresh_feed:
				new Feed().execute();
				break;
			case R.id.technopageitem: 
				Intent facebookIntent = getOpenFacebookIntent(TechnoFeedActivity.this);
				startActivity(facebookIntent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static Intent getOpenFacebookIntent(Context context) {

		   try {
		    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/136220994671"));
		   } catch (Exception e) {
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/136220994671"));
		   }
	}
	
	
	private class Feed extends AsyncTask<String, Void, String> {

	      @Override
	      protected String doInBackground(String... params) {
	    	URL url;
	  		try {
	  			url = new URL ("http://www.facebook.com/feeds/page.php?format=rss20&id=136220994671");
	  			RssFeed feed=RssReader.read(url);
	  			String rssTitle,rssContent;
	  			ArrayList <RssItem> rssItems=feed.getRssItems();
	  			int i=0;
	  			for(RssItem rssItem : rssItems ) {
	  				rssTitle=rssItem.getTitle();
	  				
	  				//rssContent=rssItem.getDescription();
	  				if(rssTitle!=null && rssTitle.length()>30) {
	  					Log.i("MYLOG : " + i + " Title : ",rssTitle);
	  					Log.i("MYLOG","Adding this title");
	  					feedList.add(rssTitle);
	  				}
	  				/*if(rssContent!=null) {
	  					Log.i("MYLOG : " + i + " Content : ",rssContent);
	  				}*/
	  				i++;
	  				Log.i("MYLOG","NEXT FOR..");
	  			}
	  		} catch (MalformedURLException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		} catch (SAXException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  		//feedList.toArray(listContents);
	  		//listContents=new String[((String[])feedList.toArray()).length];
	  		listContents=new String[feedList.size()];
	  		listContents=feedList.toArray(listContents);
	  		//listContents=(String[])feedList.toArray();
	  		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,listContents);
	  		//adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,R.id.list_content,listContents);
	  		/*for(String s:listContents) {
	  			Log.i("Repeating : ",s);
	  		}*/
	  		Log.i("MYLOG","returning...");
	  		return "executed";
	      }      
	      
	      @Override
	      protected void onPostExecute(String result) {
	    	  
		  		myList.setAdapter(adapter);
		  		myList.setBackgroundColor(Color.BLACK);
		  		myList.setClickable(false);
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
