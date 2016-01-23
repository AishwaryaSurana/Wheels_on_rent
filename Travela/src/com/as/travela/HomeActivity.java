package com.as.travela;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
		toolbar.setTitle("Wheels on rent");

		
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
				Intent loginIntent=new Intent(HomeActivity.this,AboutVehicleActivity.class);
				startActivity(loginIntent);
			}
		});
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
