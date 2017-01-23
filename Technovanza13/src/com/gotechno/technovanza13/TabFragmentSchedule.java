package com.gotechno.technovanza13;

import java.util.ArrayList;
import java.util.Locale;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class TabFragmentSchedule extends SherlockFragment{
	private ListView lv;
	private int autobots = Color.rgb(176, 23, 31);
	private int eureka = Color.rgb(72, 61, 139);
	private int manageria = Color.rgb(131, 139, 139);
	private int imech = Color.rgb(0, 139, 69);
	private int ibuild = Color.YELLOW;
	private int icode = Color.rgb(156, 102, 31);
	private int impulse = Color.rgb(173, 255, 47);
	private int fun = Color.rgb(255, 265, 0);
	private int lectures = Color.MAGENTA;
	private int workshops = Color.CYAN;
	
	private ArrayList<Event> events1 = new ArrayList<Event>();
	private ArrayList<Event> events2 = new ArrayList<Event>();
	private ArrayList<Event> events3 = new ArrayList<Event>();
	//Day 1
	String[] time1 = {"10am - 6pm", "10am - 6pm", "10am - 6pm",
						"11am - 7pm", "10am - 6pm", "10am - 6pm", "10am - 6pm", "9am - 6pm", "11am - 4pm", "11am - 4pm",
						"10am - 5pm(Round 1)", "11am - 6pm",
						"10am - 5pm", 
						"10am - 5pm(Round 1)-AL203",
						"10am - 6pm(Round 1)", "10am - 6pm(Round 1)", "10am - 6pm",
						"11am - 5pm", "11am - 4pm", "11am - 2pm", "10am - 5pm", "11am - 1pm, 3pm - 5pm", "10am - 5pm", "11am - 4pm",
						"10am - 5pm", "10am - 5pm", "10am - 5pm", "10am - 5pm",
						"9am - 11am", "3:30pm - 5pm",
						"10am - 5pm", "10am - 5pm"};
	
	String[] event1 = {"V.J. Robotics Challenge", "Robowars", "Monster Arena", 
						"TPP", "X-CON", "HSW", "Junior X-CON", "RCBO", "Contraption", "School Robotics Challenge",
						"SCAM","Wallstreet",
						"Fast & Furious",
						"Drive Thru",
						"CWay", "Java Guru", "Mission SQL",
						"Text-O-Mania", "Tricky Tronix", "Click N Capture", "Tech Charades", "LAN Gaming", "Tech Quiz", "Google Junkie",
						"Techno Drift", "Aqua Soccer", "MOP", "Laser Tag",
						"Inauguration", "Praveen",
						"Soft Skills Development", "Augmented Reality"};
	
	String[] place1 = {"Quad", "Quad", "Garden",
						"DEP1", "Exhibition Tent", "Exhibition Tent", "Exhibition Tent", "Textile Hall", "AL003", "Football Ground", 
						"Quad", "E Cell Corridor",
						"Football Ground", 
						"AL002",
						"MCA Lab", "Comps Dept. Lab1", "Comps Dept. Lab2",
						"Comps Dept. Lab3", "Quad, AL203", "Quad Reg Desk", "AL303", "Hostel LAN Room", "AL204", "Comps Dept. Lab3",
						"Quad", "Near Comps Corridor", "Quad", "Football Ground",
						"Audi", "Audi",
						"MSH", "DL001"};
	
	int[] eventType1 = {autobots, autobots, autobots, 
						eureka, eureka, eureka, eureka, eureka, eureka, eureka,
						manageria, manageria, 
						imech,
						ibuild,
						icode, icode, icode,
						impulse, impulse, impulse, impulse, impulse, impulse, impulse, 
						fun, fun, fun, fun,
						lectures, lectures,
						workshops, workshops};
	
	
	//Day 2
	String[] time2 = {"10am - 6pm", "10am - 6pm", "10am - 6pm", "10am - 4pm", 
			"10am - 6pm", "10am - 6pm", "10am - 6pm", "8am - 6pm", "11am - 4pm", "11am - 4pm",
			"10am - 5pm(Round 2)", "9am - 5pm, 5pm - 7pm", "10am - 4pm", "10am - 4pm, 4:30pm - 7:30pm", 
			"10am - 5pm", "10am - 5pm", 
			"10am - 5pm", "10am - 5pm(Round 2)",
			"10am - 6pm", "10am - 2pm", 
			"10am - 5pm", "10am - 4pm", "10am - 5pm", "11am - 1pm, 3pm - 5pm", "10am - 5pm", "11am - 5pm",
			"10am - 5pm", "10am - 5pm", "10am - 5pm", "10am - 5pm",
			"10:30am - 12pm", "1pm - 2:30pm",
			"10am - 5pm", "10am - 5pm"};

	String[] event2 = {"V.J. Robotics Challenge", "Robowars", "Monster Arena", "Wall-E", 
			"X-CON", "HSW", "Junior X-CON", "RCBO", "Contraption", "School Robotics Challenge",
			"SCAM", "Hire", "Freakonomics", "Webpreneur",
			"Fast & Furious", "Climb-e-Rope",
			"Bridge The Gap", "Drive Thru",
			"Code Swap", "Techno Hunt",  
			"Text-O-Mania", "Tricky Tronix", "Tech Charades", "LAN Gaming", "Tech Quiz", "Google Junkie",
			"Techno Drift", "Aqua Soccer", "MOP", "Laser Tag",
			"Overclocking", "Agnelo Rajesh TOSB",
			"Augmented Reality", "Mobile Marketing"};

String[] place2 = {"Quad", "Quad", "Garden", "AL007",
			"Exhibition Tent", "Exhibition Tent", "Exhibition Tent", "Textile Hall", "AL003", "Football Ground", 
			"Quad", "VJTI Campus, MSH", "DEP1",  "E Cell Corridor, DEP1",
			"Football Ground", "Quad",
			"AL102", "AL002",
			"Comps Dept. Lab1", "Comps Platform",
			"Comps Dept. Lab3", "Quad, AL203", "AL303", "Hostel LAN Room", "AL204", "Comps Dept. Lab3",
			"Quad", "Near Comps Corridor", "Quad", "Football Ground",
			"Audi", "Audi",
			"DL001", "DL002"};

int[] eventType2 = {autobots, autobots, autobots, autobots, 
			eureka, eureka, eureka, eureka, eureka, eureka,
			manageria, manageria, manageria, manageria, 
			imech, imech, 
			ibuild, ibuild,
			icode, icode, 
			impulse, impulse, impulse, impulse, impulse, impulse, 
			fun, fun, fun, fun,
			lectures, lectures,
			workshops, workshops};

	
	//Day3
String[] time3 = {"10am - 6pm", "10am - 6pm", "10am - 6pm", "10am - 2pm", 
		"10am - 5pm", "10am - 5pm", "10am - 5pm", "11am - 4pm", "11am - 4pm",
		"11am - 6pm", "2pm - 6pm", "9am - 1pm", 
		"10am - 5pm",
		
		"10am - 6pm", 
		"10am - 5pm", "10am - 4pm", "10am - 5pm", "11am -1pm, 3pm - 5pm", "10am - 5pm", "11am - 5pm", 
		"10am - 5pm", "10am - 5pm", "10am - 5pm", "10am - 5pm",
		"10:30am - 12pm", "3:30pm - 5pm",
		"10am - 5pm"};

String[] event3 = {"V.J. Robotics Challenge", "Robowars", "Monster Arena", "Wall-E", 
		"X-CON", "HSW", "Junior X-CON", "Contraption", "School Robotics Challenge",
		"Unicus", "Consultant", "BizQuiz", 
		"Fast & Furious",
		
		"Ultimate Coder",
		"Text-O-Mania", "Tricky Tronix", "Tech Charades", "LAN Gaming", "Tech Quiz", "Google Junkie",
		"Techno Drift", "Aqua Soccer", "MOP", "Laser Tag",
		"Chaityanya Nadkarni", "Shantanu Bhagwat (Skype)",
		"Mobile Marketing"};

String[] place3 = {"Quad", "Quad", "Garden", "AL007",
		"Exhibition Tent", "Exhibition Tent", "Exhibition Tent", "AL003", "Football Ground", 
		"MSH", "DEP1", "DEP1",
		"Football Ground", 
		
		"Comps Platform",
		"Comps Dept. Lab3", "Quad, AL203","AL303", "Hostel LAN Room", "AL204", "Comps Dept. Lab3",
		"Quad", "Near Comps Corridor", "Quad", "Football Ground",
		"Audi", "Audi",
		"DL002"};

int[] eventType3 = {autobots, autobots, autobots, autobots, 
		eureka, eureka, eureka, eureka, eureka,
		manageria, manageria, manageria, manageria,  
		imech,
		
		icode, 
		impulse, impulse, impulse, impulse, impulse, impulse, 
		fun, fun, fun, fun,
		lectures, lectures,
		workshops};

	View myView;
	int x;
	private EditText searchbox;
	private CustomList adapter;
	
	public static TabFragmentSchedule newInstance(int pos) {
		TabFragmentSchedule fragment = new TabFragmentSchedule();
		//Log.d("position", "the position is "+pos);
		Bundle args = new Bundle();
		args.putInt("position", pos);
		fragment.setArguments(args);
		//Log.d("EVENTTTTTTTTTTTTTTTT","Inside the newInstance");
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myView = inflater.inflate(R.layout.activity_schedule, container, false);
		searchbox =(EditText) myView.findViewById(R.id.searchbox);
		lv = (ListView)myView.findViewById(R.id.list);
		x= getArguments().getInt("position");
		Event eventObj;
		
		for(int i=0; i<time1.length; i++){
			eventObj= new Event(time1[i], event1[i], place1[i], eventType1[i]);
			events1.add(eventObj);
		}
		
		for(int i=0; i<time2.length; i++){
			eventObj= new Event(time2[i], event2[i], place2[i], eventType2[i]);
			events2.add(eventObj);
		}
		
		for(int i=0; i<time3.length; i++){
			eventObj= new Event(time3[i], event3[i], place3[i], eventType3[i]);
			events3.add(eventObj);
		}
		
		switch(x){
		// use different arrays in each case for the adapter
		case 0:adapter = new CustomList(getActivity(), events1);lv.setAdapter(adapter);break;
		case 1:adapter = new CustomList(getActivity(), events2);lv.setAdapter(adapter);break;
		case 2:adapter = new CustomList(getActivity(), events3);lv.setAdapter(adapter);break;
		}
		
    	searchbox.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = searchbox.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
    		
    	});
		return myView;
	}
}