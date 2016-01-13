package p1;

import com.aish.onrent.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class AboutVehicleActivity extends TabActivity
{ /** Called when the activity is first created. */
	TextView textUser,textContact;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_vehicle);
        SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
    	final int owner_id=sp.getInt("owner_id", 0);
    	String name=sp.getString("name", "");
    	textUser.setText("Welcome"+name);
    		
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
     }

	

}
