package com.as.travela;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class VehicleList extends TabActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_list);
		
		final TabHost tabHost = getTabHost();
	
		 
		
        // Tab for two wheeler
        TabSpec twospec = tabHost.newTabSpec("Two Wheeler");
        twospec.setIndicator("Two Wheeler", getResources().getDrawable(R.drawable
        				.two_wheeler_icon_tab));
        final Intent twoIntent = new Intent(VehicleList.this, TWActivity.class);
       
        twospec.setContent(twoIntent);
       
        // Tab for four wheeler
        TabSpec fourspec = tabHost.newTabSpec("Four Wheeler");
        // setting Title and Icon for the Tab
        fourspec.setIndicator("Four Wheeler", getResources().getDrawable(R.drawable
        			.four_wheel_icon_tab));
      final Intent fourIntent = new Intent(VehicleList.this, FWActivity.class);
    
        fourspec.setContent(fourIntent);
        
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(twospec); // Adding add vehicle tab
        tabHost.addTab(fourspec); // Adding view vehicle tab
  
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) 
        { 
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        } 

        
       	
     
       	
      int current_tab=tabHost.getCurrentTab();
      
        if(current_tab==0)
        {
        	//Toast.makeText(VehicleList.this,"two wheeler", 5).show();
        	
        	
        	
        }
       
       tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				int current_tab=tabHost.getCurrentTab();
      
		        if(current_tab==0)
		        {
		        	//Toast.makeText(VehicleList.this,"two wheeler", 5).show();
		        	
		            
		        }
		        if(current_tab==1)
		        {
		        	//Toast.makeText(VehicleList.this,"four wheeler", 5).show();
		        	
		        }
			}
		});
        
		
		
		
		
		
		

      
		
	}
	
	
	
	
}
