package com.as.travela;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by Aishwarya on 23-Dec-15.
 */
@SuppressWarnings("deprecation")
public class RegistrationActivity extends ActionBarActivity 
{
    EditText ed1, ed2, ed3, ed4,ed5,ed6;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        
        Toolbar   t= new Toolbar(RegistrationActivity.this);
		t.setNavigationIcon(R.drawable.ic_launcher);
		
		Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar1);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_launcher);
		toolbar.setTitle("Wheels on rent");
		toolbar.setTitleTextColor(Color.WHITE);
        
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText) findViewById(R.id.editText5);
        ed6 = (EditText) findViewById(R.id.editText6);
        ed1.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{ if (actionId == EditorInfo.IME_ACTION_DONE) {
	             b.performClick();
	             return true;
	         }
	      	return false;
			}
		 });

		ed2.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{ if (actionId == EditorInfo.IME_ACTION_DONE) {
	             b.performClick();
	             return true;
	         }
	      	return false;
			}
		 });

		ed3.setOnEditorActionListener(new EditText.OnEditorActionListener() 
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{ 
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
	             b.performClick();
	             return true;
				}
	      	return false;
			}
		 });

		ed4.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_DONE) {
	             b.performClick();
	             return true;
	         }
	      	return false;
			}
		 });

		ed5.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{ 
			if (actionId == EditorInfo.IME_ACTION_DONE) 
			{
	             b.performClick();
	             return true;
	        }
	      	return false;
			}
		 });
		ed6.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{ if (actionId == EditorInfo.IME_ACTION_DONE) {
	             b.performClick();
	             return true;
	         }
	      	return false;
			}
		 });

        b = (Button) findViewById(R.id.button1);
        
        b.setOnClickListener(new View.OnClickListener()
       	{
            @Override
            public void onClick(View v)
            {
            
            	String name, surname, contact, email, password,confirmpassword;
            	boolean flag=false;
            	name = ed1.getText().toString();
                surname = ed2.getText().toString();
                contact = ed3.getText().toString();
                if(contact.length()<10)
                {
                	Toast.makeText(RegistrationActivity.this,"Enter valid number", Toast.LENGTH_SHORT).show();
                	contact="";
                }
                email = ed4.getText().toString();
                password=ed5.getText().toString();
                confirmpassword=ed6.getText().toString();
                Log.e("name",name+surname);
                if(!(name.equals("")||surname.equals("")||contact.equals("")||password.equals("")||confirmpassword.equals("")))
                	{
                	flag=true;
                	}
                int c=0;
                if(flag==true)
                {
                	if(email.equals(""))
                  	{	
                		c=1;
                	}
                else if(!email.equals(""))
                { 
                	c=2;
                }
                switch (c)
                {
				case 1:
					email=null;
	                break;
				case 2:
					if(!email.contains("@")&&!email.contains("."))
					{
						Toast.makeText(RegistrationActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
						email=null;
					}
					
					break;

				default:
					break;
				}
               }
                if(flag==true)
                {
                	if(password.equals(confirmpassword))
                	{
	                Owner o= new Owner(name, surname, contact, email,password,0);
	                Gson g=new Gson();
	                String json=g.toJson(o);
	                String u = WebHelper.baseUrl + "/RegistrationServlet";/*name of servlet needed to be launched*/
	                URL url = null;
	                try {
						url=new URL(u);
						Log.e("Url b4",url+"");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                RegistrationTask task = new RegistrationTask();
	                task.execute(url+"",json);
	            	//Toast.makeText(RegistrationActivity.this, "Registered "
	            		//	+ "password",Toast.LENGTH_LONG).show();
	            	
                	}
                	else
                	{
                	Toast.makeText(RegistrationActivity.this, "password doesn't match confirm "
                			+ "password",Toast.LENGTH_LONG).show();
                	}
                }
                else
                {
                	Toast.makeText(RegistrationActivity.this, "Enter required fields", Toast.LENGTH_SHORT).show();
                    	
                }

                
              }
        });

       }
    

    @Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	 	
	}

    
	class RegistrationTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
		
			HttpPost postReq=new HttpPost(params[0]);

			Log.e("Url in do in",params[0]+"");
	
			//prepare entry to store email/password
			BasicNameValuePair pair1=
					new BasicNameValuePair("ownerdata", params[1]);			
			
			ArrayList<BasicNameValuePair> listPairs=new ArrayList<BasicNameValuePair>();
			
			listPairs.add(pair1);
			String result="";
			try
			{
				UrlEncodedFormEntity entity=
						new UrlEncodedFormEntity(listPairs);
				postReq.setEntity(entity);				
				
				//send request to server
				
				HttpClient client=new DefaultHttpClient();
				HttpResponse resp=client.execute(postReq);
				InputStream is=resp.getEntity().getContent();
				InputStreamReader reader=new InputStreamReader(is);
				BufferedReader br=new BufferedReader(reader);
				
				while(true)
				{
						
					String s=br.readLine();
					if(s==null) 
						break;
					result=result+s;
					
				}
				br.close();
				Log.e("result",result+"");
				
			}
			catch(Exception ex)
			{
				Log.e("Error reg",ex+"");
				
			}
			
			return result;
		}// eof doInBack
	
		@Override
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);
			
			if(result.equals("1"))
			{
				Toast.makeText(RegistrationActivity.this,
					"Registration successfull,Now login", Toast.LENGTH_LONG).show();
				Intent in=new Intent(RegistrationActivity.this,
						LoginActivity.class);
				startActivity(in);
				RegistrationActivity.this.finish();
		
			}
			else
			{
				Toast.makeText(RegistrationActivity.this,
					"Registration failed. try again !!", Toast.LENGTH_LONG).show();
			}
				
		}
	}

}
   