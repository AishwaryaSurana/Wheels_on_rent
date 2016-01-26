package com.as.travela;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Terms extends Activity
{
	Button b;
	EditText e1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		String s="Terms and Conditions \n Terms of Use of the Application \n "
				+ "This application is published by TRAVELA Wheels on rent, The person responsible for publication is Mr. Husain Ezzy, "
				+ "Chief Excutive Officer of TRAVELA_ Wheels on rent."
			+"Acceptance of the Terms & Conditions \n \n"
			+"Please read these Terms & Conditions carefully before using"
			+"a TRAVELA Application or making reservations. They can be accessed via the hyperlink on the footer of each page on our Application. You are advised to print them out and keep a copy.";

String term="";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terms_and_conditions);
		b=(Button)findViewById(R.id.button1);
		e1=(EditText)findViewById(R.id.editText1);
		e1.setText(s);
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
