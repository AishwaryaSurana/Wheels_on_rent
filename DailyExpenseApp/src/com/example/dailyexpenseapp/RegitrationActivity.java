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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.gson.Gson;

public class RegitrationActivity  extends Activity
{
	EditText etName,etMobile,etEmail,etPass,etConfirmPass,etManagerEmail;
	RadioGroup groupUserType;
	Button btnSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		etName=(EditText)findViewById(R.id.editText1);
		etEmail=(EditText)findViewById(R.id.editText2);
		etMobile=(EditText)findViewById(R.id.editText3);
		etPass=(EditText)findViewById(R.id.editText6);
		etConfirmPass=(EditText)findViewById(R.id.editText5);
		etManagerEmail=(EditText)findViewById(R.id.editText4);
		groupUserType=(RadioGroup)findViewById(R.id.radioGroup1);
		btnSubmit=(Button)findViewById(R.id.button1);
		
		// show/hide manager email box on radio selection
		groupUserType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				if(checkedId==R.id.radio1)//member
					etManagerEmail.setVisibility(View.VISIBLE);
				else
					etManagerEmail.setVisibility(View.GONE);
				
			}
		});
		
		
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String name=etName.getText().toString();
				String email=etEmail.getText().toString();
				String mobile=etMobile.getText().toString();
				String pass=etPass.getText().toString();
				String cpass=etConfirmPass.getText().toString();
				
				String manager_email="";
				int radioId=groupUserType.getCheckedRadioButtonId();				
				int user_type=1;
				if(radioId==R.id.radio0)
				{
					user_type=1;//indivisual
				}
				if(radioId==R.id.radio1)
				{
					user_type=2;//member
					manager_email=etManagerEmail.getText().toString();
				}
				if(radioId==R.id.radio2)
				{
					user_type=3;//supervisor/manager
				}
				
				//collect all above info and send to server;
				//User u=new User(user_id, user_type, name, email, mobile, password, manager_email)
				User u=new User(0, user_type, name, email, mobile, 
						pass, manager_email);
				
				Gson g=new Gson();
                String json=g.toJson(u);
                
                ///send user json to server using async task
                if(pass.equals(cpass))
                {
					String url=WebHelper.baseURL+"RegistrationServlet";
					RegistrationTask task=new RegistrationTask();
					task.execute(url,json);
					Log.e("user data", json);
                }
			}
		});
				
	}//eof oncreate
	
	class RegistrationTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
		
			HttpPost postReq=new HttpPost(params[0]);
			
			//prepare entry to store email/password
			BasicNameValuePair pair1=
					new BasicNameValuePair("userdata", params[1]);			
			
			ArrayList<BasicNameValuePair> listPairs=
					new ArrayList<BasicNameValuePair>();
			
			listPairs.add(pair1);
		
			
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
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.equals("1"))
			{
				Toast.makeText(RegitrationActivity.this,
					"registered..", 5).show();
				finish();
			}
			else
			{
				Toast.makeText(RegitrationActivity.this,
					"registration failed. try again !!", 5).show();
			}
				
		}
		
	}

}
