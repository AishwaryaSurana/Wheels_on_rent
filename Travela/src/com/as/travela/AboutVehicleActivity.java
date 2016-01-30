package com.as.travela;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AboutVehicleActivity extends TabActivity
{ /** Called when the activity is first created. */
	TextView textUser,textContact;
	
	// Session Manager Class
    SessionManager session;

	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //image button logout
        ImageButton bImgLogout=(ImageButton)findViewById(R.id.imageButton1);
        
        // Session class instance
        session = new SessionManager(getApplicationContext());
        
        
        
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        
        
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        
        setContentView(R.layout.about_vehicle);
        textUser=(TextView)findViewById(R.id.textView1);
        SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
    	String name=sp.getString("name", "");
    	int ownerId=sp.getInt("owner_id",0);
    	textUser.setText("Welcome "+name);
    	TabHost tabHost = getTabHost();
        
        // Tab for add
        TabSpec addspec = tabHost.newTabSpec("Add Vehicle");
        addspec.setIndicator("Add Vehicle", getResources().getDrawable(R.drawable.icon_add_tab));
        Intent addIntent = new Intent(this, AddVehicleActivity.class);
        addspec.setContent(addIntent);
        
        // Tab for view
        TabSpec viewspec = tabHost.newTabSpec("View Vehicle");
        // setting Title and Icon for the Tab
        viewspec.setIndicator("View Vehicle", getResources().getDrawable(R.drawable.icon_view_tab));
        Intent viewIntent = new Intent(this, ViewVehicleActivity.class);
        viewspec.setContent(viewIntent);
        
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(addspec); // Adding add vehicle tab
        tabHost.addTab(viewspec); // Adding view vehicle tab
        
        
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) 
        { 
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        } 
        
        //create listner for logout
        bImgLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SessionManager session= new SessionManager(getApplicationContext());
				session.logoutUser();
			}
		});
        
     }

	

}
