package com.example.dailyexpenseapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	EditText etEmail,etPasword;
	Button btnLogin,btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		etEmail=(EditText)findViewById(R.id.editText1);
		etPasword=(EditText)findViewById(R.id.editText2);
		btnLogin=(Button)findViewById(R.id.button1);
		btnRegister=(Button)findViewById(R.id.button2);
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(LoginActivity.this,
						RegitrationActivity.class);
				startActivity(in);
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email=etEmail.getText().toString();
				String password=etPasword.getText().toString();				
				
				String url=WebHelper.baseURL+"LoginServlet";
				//send email password to servlet using asynctask
				
				LoginTask task=new LoginTask();
				task.execute(url,email,password);
				
			}
		});
		
	}//eof on create
	
	
	class LoginTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
		
			HttpPost postReq=new HttpPost(params[0]);
			
			//prepare entry to store email/password
			BasicNameValuePair pair1=
					new BasicNameValuePair("email", params[1]);			
			BasicNameValuePair pair2=
					new BasicNameValuePair("password", params[2]);
			ArrayList<BasicNameValuePair> listPairs=
					new ArrayList<BasicNameValuePair>();
			
			listPairs.add(pair1);
			listPairs.add(pair2);
			
			String result="";
			try{
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
				
				
			}catch(Exception ex) {}
			
			return result;
		}// eof doInBack
		@Override
		protected void onPostExecute(String result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("result",result);
			//if fail or error
			if(result.startsWith("fail") ||
					result.startsWith("error") )
			{
				Toast.makeText(LoginActivity.this, result, 5).show();
			}
			else
			{
				//parse json object from result
				try{
					JSONObject obj=new JSONObject(result);
					int user_id=obj.getInt("user_id");
					int user_type=obj.getInt("user_type");
					String name=obj.getString("name");
					String email=obj.getString("email");
					
					//SharedPreference
					SharedPreferences sp=
						getSharedPreferences("settings",MODE_PRIVATE);
					
					//open editor to edit content in settings
					SharedPreferences.Editor editor=sp.edit();
					//editor.putInt(key, value)
					editor.putInt("user_id", user_id);
					editor.putInt("user_type", user_type);
					editor.putString("name",name);
					editor.putString("email",email);
					editor.commit();
							
					//launch next screen as per user type
					
					if(user_type==1 || user_type==2)
					{
					   Intent in=new Intent(LoginActivity.this,
							   ExpenseEntryActvity.class);
					   startActivity(in);
					}
					if(user_type==3)
					{
					   Intent in=new Intent(LoginActivity.this,
							   ManagerActivity.class);
					   startActivity(in);
					}
					
					
				}catch(Exception EX)
				{
					
				}
				
			}
			
		}
		
		
		
		
		
	}
	
	
	
	

	 
}
