package com.gotechno.technovanza13;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unchecked")
public class NewAdapter extends BaseExpandableListAdapter {

 public ArrayList<String> groupItem, tempChild;
 public ArrayList<Object> Childtem = new ArrayList<Object>();
 public int x,y;
 public LayoutInflater minflater;
 public Activity activity;
 SharedPreferences s;
 String Contents[][] = { { "Real Steel: Robowars", "VJ Robotics Challenge(VRC)", "Wall-E","Monster Arena 3.0"},
			{ "Fast & Furious","Climb-e-Rope",},
			{ "Bridge the Gap"," Drive Thru"},
			{ "TPP","HSW", "X-CON","RCMO","Junior XCON","Contraption "},
			{ "Unicus", "Hire", "Wallstreet", "Consultant", "Freakonomics", "Biz-Quiz", "S.C.A.M", "Webpreneur"},
			{ "C-way", "Java Guru", "Technohunt", "Ultimate Coder", "Mission SQL", "AI Wars","Code Swap" },
			{ "Algocrack", "Code in X", "Cryptext", "Myst", "VSM", "Forex"},
			{ "Tricky Tronics","LAN gaming","Text-o-Mania", "Click N' Capture", "Google Junkie", "Techno Quiz","Tech-Charades",""},
			{ "Laser CS", "Aqua Soccer", "Techno Drift","Master Of Puppets"} };
Context c=activity;
 public NewAdapter(ArrayList<String> grList, ArrayList<Object> childItem,int x,int y) {
  groupItem = grList;
  this.Childtem = childItem;
  this.x=x;
  this.y=y;
 }

 public void setInflater(LayoutInflater mInflater, Activity act) {
  this.minflater = mInflater;
  activity = act;
 }

 @Override
 public Object getChild(int groupPosition, int childPosition) {
  return null;
 }

 @Override
 public long getChildId(int groupPosition, int childPosition) {
  return 0;
 }

@Override
 public View getChildView(int groupPosition, final int childPosition,
   boolean isLastChild, View convertView, ViewGroup parent) {
  tempChild = (ArrayList<String>) Childtem.get(groupPosition);
  TextView text = null;
  if (convertView == null) {
   convertView = minflater.inflate(R.layout.childrow, null);
  }
  c=activity;
  text = (TextView) convertView.findViewById(R.id.textView1);
  text.setText(tempChild.get(childPosition));
  final int t=groupPosition;
  
  
  convertView.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
	   
	   
   }
  });
  
  return convertView;
 }
 

 @Override
 public int getChildrenCount(int groupPosition) {
  return ((ArrayList<String>) Childtem.get(groupPosition)).size();
 }

 @Override
 public Object getGroup(int groupPosition) {
  return null;
 }

 @Override
 public int getGroupCount() {
  return groupItem.size();
 }

 @Override
 public void onGroupCollapsed(int groupPosition) {
  super.onGroupCollapsed(groupPosition);
 }

 @Override
 public void onGroupExpanded(int groupPosition) {
  super.onGroupExpanded(groupPosition);
 }

 @Override
 public long getGroupId(int groupPosition) {
  return 0;
 }

 @Override
 public View getGroupView(int groupPosition, boolean isExpanded,
   View convertView, ViewGroup parent) {
  if (convertView == null) {
   convertView = minflater.inflate(R.layout.grouprow, null);
  }
  ((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
  ((CheckedTextView) convertView).setChecked(isExpanded);
  return convertView;
 }

 @Override
 public boolean hasStableIds() {
  return false;
 }

 @Override
 public boolean isChildSelectable(int groupPosition, int childPosition) {
  return false;
 }

}
