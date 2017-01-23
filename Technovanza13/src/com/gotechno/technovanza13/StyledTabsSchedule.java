package com.gotechno.technovanza13;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TabPageIndicator;

public class StyledTabsSchedule extends SherlockFragmentActivity{
	
	private static final String[] CONTENT = new String[] { "28 Dec", "29 Dec", "30 Dec"  };
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentPagerAdapter adapter = new EventsAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }


	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		// TODO Auto-generated method stub
       	switch(item.getItemId()){
       	case android.R.id.home:
       		NavUtils.navigateUpFromSameTask(this);
       		return true;
       	}
   		return super.onOptionsItemSelected(item);
   	}

    
    class EventsAdapter extends FragmentPagerAdapter {
        public EventsAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) { 
            return TabFragmentSchedule.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }


}

