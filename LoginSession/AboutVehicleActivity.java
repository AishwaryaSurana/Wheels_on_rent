package p1;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.aish.onrent.R;

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
        textContact=(TextView)findViewById(R.id.textView2);
        SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
    	//final int owner_id=sp.getInt("owner_id", 0);
    	String name=sp.getString("name", "");
    	String contact=sp.getString("contact_no", "");
    	int id=sp.getInt("owner_id", 0);
    	textUser.setText("Welcome "+name+" id="+id);
    	textContact.setText(contact);
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
