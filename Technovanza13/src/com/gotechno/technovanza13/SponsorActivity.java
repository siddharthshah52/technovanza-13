package com.gotechno.technovanza13;

//import info.androidhive.expandablelistview.ExpandableListAdapter;
//import info.androidhive.expandablelistview.R;

//import info.androidhive.expandablelistview.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;
//import com.ak.credila.QuestionaireActivity;

public class SponsorActivity extends Activity {
  
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader = new ArrayList<String>();
	String[]weblink = new String[50];
	HashMap<String, String> weblinks =new HashMap<String,String>();
	
	HashMap<String, List<String>> jsonmap = new HashMap<String, List<String>>();

	
	
	
	JSONParser jParser=new JSONParser();
	String url ="http://www.mydbproj.freeiz.com/sponsorlist.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sponsor);
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		
		new Getdata().execute();

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		
		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				int j=0;	
			    //String url = ;
				Uri uri=null;
				String nullstr = "null";
			String nourl=jsonmap.get(listDataHeader.get(groupPosition)).get(childPosition);	
			   String url= weblinks.get(jsonmap.get(listDataHeader.get(groupPosition)).get(childPosition)).trim();
			   Log.d("URL","i am here out "+url);
			   if(!url.equalsIgnoreCase(nullstr))// && url != null)					
			   {
				Log.d("URL","i am here"+url);	
				uri = Uri.parse(url);
				Intent i = new Intent(Intent.ACTION_VIEW);
				//i.putExtra(SearchManager.QUERY,);
				i.setData(uri);
				startActivity(i);				
			   }
				else {
					Log.d("URL","i am here too"+url);
					Toast.makeText(getApplicationContext(),"No url link is available",Toast.LENGTH_SHORT).show();
				}
				//Toast.makeText(getApplicationContext(),"Selected"+url,Toast.LENGTH_SHORT).show();
				
				return true;
			}
		});
	}

		class Getdata extends AsyncTask<String, String, String> {

		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		ProgressDialog	pDialog=null;
		@Override

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SponsorActivity.this);
			pDialog.setMessage("Loading sponsors. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			//pDialog.dismiss();
			listDataHeader.add("Title");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			//params.add(new BasicNameValuePair("category",QuestionaireActivity.this.getIntent().getStringExtra("cat")));
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);
			JSONArray sponsors;
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());
			String header=" ",value=" ",link=" ";
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// products found
					// Getting Array of Products
					sponsors = json.getJSONArray("sponsors");

					// looping through All Products
					for (int i = 0; i < sponsors.length(); i++) {
						JSONObject c = sponsors.getJSONObject(i);
                        
						// Storing each json item in variable
						header = c.getString("Header");
						value = c.getString("Child");
						link=c.getString("url");
						weblinks.put(value,link);
						
						List<String> list = jsonmap.get(header);
						if(list == null) {
							list  = new ArrayList<String>();
							jsonmap.put(header, list);
						}
						list.add(value);
						
					}    
					
					//Gettting the data from the hashmap and putting inside the list//
					    
					Set<String> keys= jsonmap.keySet();
					
					for(String key:keys)
					{
						if(!key.equals("Title")){
							listDataHeader.add(key);
							
						}
					  
					}

					
					
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
			// dismiss the dialog after getting all products
			//pDialog.dismiss();
			// updating UI from Background Thread
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					listAdapter = new ExpandableListAdapter(getApplicationContext(),listDataHeader,jsonmap);

					//setting list adapter
					expListView.setAdapter(listAdapter);
				
				}
			});
               
		}
	
	
	}
}	

