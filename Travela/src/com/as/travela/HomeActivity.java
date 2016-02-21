package com.as.travela;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity
{

	Button breg,brent;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home);
		Toolbar   t= new Toolbar(HomeActivity.this);
		t.setNavigationIcon(R.drawable.ic_launcher);
		
		Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar1);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_launcher);
		toolbar.setTitle("TRAVELA");
		toolbar.setTitleTextColor(Color.WHITE);
		
		breg=(Button)findViewById(R.id.button1);
		brent=(Button)findViewById(R.id.button2);
		final Intent in= new Intent(this,VehicleList.class);
		
		brent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(in);
			}
		});
		
		breg.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent loginIntent=new Intent(HomeActivity.this,LoginActivity.class);
				startActivity(loginIntent);
			}
		});
	}
	boolean doubleclick=false;
	@Override
	public void onBackPressed() {
		if(doubleclick)
		{
			super.onBackPressed();
			return;
		}
		
		this.doubleclick=true;
		Toast.makeText(getApplicationContext(),"press again to exit",5).show();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				doubleclick=false;
			}
		},2000);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.string.action_logout){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
