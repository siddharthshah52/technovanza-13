package com.gotechno.technovanza13;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

public class TabFragmentSocial extends Fragment{
	RelativeLayout r;
	static int w,x;
	private static final String TAG_SUCCESS = "success";
	private static final String[] CONTENT = new String[] { "Pratigya", "Mission Mumbai"  };
	
public static TabFragmentSocial newInstance(int pos, int width) {
		TabFragmentSocial fragment = new TabFragmentSocial();
		//Log.d("position", "the position is "+pos);
		w = width;
		Bundle args = new Bundle();
		args.putInt("position", pos);
		fragment.setArguments(args);
		//Log.d("EVENTTTTTTTTTTTTTTTT","Inside the newInstance");
		return fragment;
	}

	View myView;
	String s;
	ArrayList<String> groupItem = new ArrayList<String>();
	ArrayList<Object> childItem = new ArrayList<Object>();

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	myView = inflater.inflate(R.layout.activity_social, container, false);
	r =(RelativeLayout)myView.findViewById(R.id.rr);
	ExpandableListView expandbleLis=(ExpandableListView)myView.findViewById(R.id.elv2);
	 expandbleLis.setDividerHeight(2);
	 expandbleLis.setIndicatorBounds(w-75, w);
	 expandbleLis.setClickable(true);
	 expandbleLis.setCacheColorHint(Color.TRANSPARENT);
	 x = getArguments().getInt("position");
	  switch(x){
	  case 0:
		  r.setBackgroundResource(R.drawable.logo_p);
		  setGroupData(true);
		  setChildGroupData(true);
		  break;
	  case 1:
		  r.setBackgroundResource(R.drawable.logo_m);
		  setGroupData(false);
		  setChildGroupData(false);
	  }
	  NewAdapterSocial mNewAdapter = new NewAdapterSocial(groupItem, childItem);
	  mNewAdapter
	  .setInflater(
	    (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
	    getActivity());
	  expandbleLis.setAdapter(mNewAdapter);
	  //expandbleLis.setOnChildClickListener(this);
	return myView;
}
public void setGroupData(boolean flag) {
	if(flag){
	  groupItem.add("About Us"); 
	  groupItem.add("Roadmap 2012-2013");
	  groupItem.add("Syllabus");
	}
	else{
		  groupItem.add("Overview"); 
		  groupItem.add("Objectives");
		  groupItem.add("Umeeed");
		  groupItem.add("Clean Mumbai Drive");
		  groupItem.add("Khushiyan Bato");
		  groupItem.add("Techie @ 60!");
	}
}	
public void setChildGroupData(boolean flag) {
	if(flag){
		/**
		   * Add Data For About us
		   */
		  ArrayList<String> child = new ArrayList<String>();
		  s="TECHNOVANZA is VJTI's annual technical festival and 'PRATIGYA' is our initiative"+
		  "to generate curosity about technology in young minds of school students and leave"+
		  " a social imprint on their minds.It is our initiative to make technology percolate"+
		  " down from universities to the younger minds at the school level itself and "+
		  "inculcate in students the interest to understand and improve technology, to "+
		  "generate curiosity and to introduce  bright young minds to the marvels of engineering.";
		  child.add(s);
		  childItem.add(child);

		  /**
		   * Add Data For roadmap 
		   */
		  child = new ArrayList<String>();
		  s="Education knows no geographical boundaries. To take our reach beyond the "+
		  "schools of Mumbai alone we have planned to record the sessions we conduct "+
		  "locally and distribute them in the form of DVDs. We also plan to continue "+
		  "the follow-up sessions started in the previous year as they elicited a "+
		  "heart-warming response. Also on the cards are sessions for students of the "+
		  "tenth grade to help them appear for their board exams by teaching them memory"+
		  " enhancement techniques. Vedic Maths sessions too have been planned for the"+
		  " school children. Our aim is to make the sessions more interactive "+
		  "to have a lasting impact.";
		  child.add(s);
		  childItem.add(child);
		  /**
		   * Add Data for syllabus
		   */
		  child = new ArrayList<String>();
		  s="We are planning to conduct the following sessions this year:\n"+
		  "A]    One Day Sessions(2 hrs)\n     This consists of Videos of\n"+
		  "     1)Engineering marvels in the world which have made our life easier\n"+
		  "     2)Explanation of various appliances and future technology\n"+
		  "     3)The journey of a product through its manufacturing process from the raw "+
		  "material to the finishedproduct making use of only machines\n"+
		  "     4)Real life heroes and how attitude and determination define success\n"+
		  "     5)The technologies that that few of us are aware of but leave us awestruck"+
		  " when we hear about them\n"+"B]    Follow-Up Session (2 hrs)\n"+
		  "     We will explain the fundamentals of energy forms like Light, Heat and Sound with"+
		  " the help of videos, presentations, models and experiments.";
		  child.add(s);
		  childItem.add(child);
	}
	else{
		/**
		   * Add Data For overview
		   */
		  ArrayList<String> child = new ArrayList<String>();
		  s="With a view to take up the job of beginning to set things right,"+
			"we initiated an event called ‘MISSION MUMBAI’ last year as a part of"+
			" our Techno- Management Event, Technovanza. Bringing about awareness amongst"+
			" students about the problems that a "+"Mumbaikar faces on a daily bases was"+
			" the major objective. Our next step being moving beyond the college premises"+
			", we affiliated with an NGO called OASIS (Organisation of Aware Saviours "+
			"in Society; A Regd. Environmental NGO of Aware Action since 1997) and took"+
			" up a project named “Environmental education- the grassroots of change”, "+
			"since the subject of environment is an important one and it is important that"+
			"it is instilled in every citizen.";
		  child.add(s);
		  childItem.add(child);

		  /**
		   * Add Data For objectives 
		   */
		  child = new ArrayList<String>();
		  s="This year, Mission Mumbai comes in with completely new initiative. Last "+
		  "year, we focused on ‘Environment Conservation’. This year, however, we have"+
		  " five events planned. Three of these, stand true to Technovanza’s motto of "+
		  "‘Taking Technology to the Society’ and the rest of the two are our community"+
		  "service initiatives. We solemnly believe that one must give back to the"+ 
		  "society just asmuch as it gives to you and Mission Mumbai is a platform"+
		  " we provide to our students for them to do their bit to at least make "+
		  "this city a better place to live.";
		  child.add(s);
		  childItem.add(child);
		  /**
		   * Add Data for umeed
		   */
		  child = new ArrayList<String>();
		  s="Blind Navigation system designed by the students of VJTI during "+
		    "Technovanza, will be taken to a blind school to show them the "+
		    "working of the system. The drive will be conducted so as to keep "+
		    "the hope alive within these children. Today, the blind navigation"+
		    " systems are still in the research stage. However, by the time they"+
		    " grow, it would have surely surpassed the research stages and would"+
		    " be ready for them to ";
		  child.add(s);
		  childItem.add(child);
		  /**
		   * Add Data for clean mumbai drive
		   */
		  child = new ArrayList<String>();
		  s="“The city is my home and I will protect it”. This is the ideology"+
		  " behind our clean-up drive. After the Ganesh Chaturthi festival, "+
		  "the litter is collected at the sea shores of the beaches. We, at "+
		  "MISSION MUMBAI make it our responsibility as citizens of Mumbai to"+
		  " help keep the city clean. This year we plan to go to the Shivaji park"+
		  " beach situated at Dadar to do the same.";
		  child.add(s);
		  childItem.add(child);
		  /**
		   * Add Data for khushiyan
		   */
		  child = new ArrayList<String>();
		  s="In the October scorching heat, the policemen have a tough time"+
		  " working with parched throats. They still do it without complaints."+
		  " As a token of gratitude and heartfelt appreciation, we plan to "+
		  "serve refreshments to the hard working ";
		  child.add(s);
		  childItem.add(child);
		   /**
		    * add data for techie@60
		   */
		  child = new ArrayList<String>();
		  s="With advanced technology in the market, senior citizens of the"+
		  " city have a problem using technology. MISSION MUMBAI aims at "+
		  "visiting an old age home to take technology to them. We will be "+
		  "associating with an old-age home for this project.";
		  child.add(s);
		  childItem.add(child);

	}
}

}