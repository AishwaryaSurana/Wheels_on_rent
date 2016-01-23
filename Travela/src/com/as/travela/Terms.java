package com.as.travela;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Terms extends Activity
{
	Button b;
	TextView t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		String s="Terms and Conditions";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terms_and_conditions);
		b=(Button)findViewById(R.id.button1);
		t1=(TextView)findViewById(R.id.textView1);
		t1.setText(s);
		b.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent in=new Intent(Terms.this,
						AddVehicleActivity.class);
				startActivity(in);
		
			}
		});
	}

}
