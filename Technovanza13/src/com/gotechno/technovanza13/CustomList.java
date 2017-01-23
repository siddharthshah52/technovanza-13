package com.gotechno.technovanza13;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomList extends BaseAdapter {
	Context mContext;
	LayoutInflater inflater;
	List<Event> eventList = null;
	ArrayList<Event> eventArrayList;
	
	public CustomList(Context mContext, List<Event> eventList){
		this.mContext=mContext;
		this.eventList=eventList;
		inflater = LayoutInflater.from(mContext);
		eventArrayList=new ArrayList<Event>();
		eventArrayList.addAll(eventList);
	}
	
	public class ViewHolder{
		TextView time;
		TextView event;
		TextView room;
		View color;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return eventList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return eventList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_row, null);
			holder.color=convertView.findViewById(R.id.type);
			holder.event=(TextView)convertView.findViewById(R.id.event);
			holder.room=(TextView)convertView.findViewById(R.id.room);
			holder.time=(TextView)convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		}
		else holder = (ViewHolder)convertView.getTag();
		
		holder.color.setBackgroundColor(eventList.get(position).color);
		holder.event.setText(eventList.get(position).name);
		holder.room.setText(eventList.get(position).room);
		holder.time.setText(eventList.get(position).time);
		
		return convertView;
	}
	
	public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        eventList.clear();
        if (charText.length() == 0) {
            eventList.addAll(eventArrayList);
        } else {
            for (Event e : eventArrayList) {
                if (e.name.toLowerCase(Locale.getDefault())
                        .startsWith(charText)) {
                    eventList.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }
}