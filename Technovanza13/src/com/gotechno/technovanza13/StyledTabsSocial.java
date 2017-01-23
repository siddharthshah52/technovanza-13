package com.gotechno.technovanza13;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Display;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TabPageIndicator;

public class StyledTabsSocial extends SherlockFragmentActivity {
    private static final String[] CONTENT = new String[] { "Pratigya", "Mission Mumbai"  };
    static int width;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentPagerAdapter adapter = new SocialEventsAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        Display newDisplay = getWindowManager().getDefaultDisplay(); 
  	  	width = newDisplay.getWidth();

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

    
    class SocialEventsAdapter extends FragmentPagerAdapter {
        public SocialEventsAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) { 
            return TabFragmentSocial.newInstance(position, width);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }


}

