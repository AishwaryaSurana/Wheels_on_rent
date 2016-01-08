package com.example.wheelsonrent;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Spinner;

public class MyActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		  
        
        ActionBar actionBar = getSupportActionBar(); 
         
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); // you wanted this, right?    
         
        int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
         
        // If you're using sherlock, in older versions of android you are not supposed to get a reference to android.R.id.action_bar_title, So here's little hack for that.
        if (titleId == 0) {
            titleId = com.actionbarsherlock.R.id.abs__action_bar_title;
        }
         
        View titleView = findViewById(titleId);
 
        // attach listener to this spinnerView for handling spinner selection change
        Spinner spinnerView = (Spinner) getLayoutInflater().inflate(R.layout.spinner_layout, null); 
         
        //source of ViewGroupUtils class is given at the end of this post.
        ViewGroupUtils.replaceView(titleView, spinnerView);
         
    }
}

	
