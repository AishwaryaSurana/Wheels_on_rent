package p1;

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
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.aish.onrent.R;

public class LoginActivity extends Activity
{
	EditText etContact,etPasword;
	Button btnLogin,btnRegister;
	
	 // Session Manager Class
    SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// Session Manager
        session = new SessionManager(getApplicationContext());    
		
		setContentView(R.layout.login);
		etContact=(EditText)findViewById(R.id.editText1);
		etPasword=(EditText)findViewById(R.id.editText2);
		btnLogin=(Button)findViewById(R.id.button1);
		btnRegister=(Button)findViewById(R.id.button2);
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(LoginActivity.this,
						RegistrationActivity.class);
				startActivity(in);
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				String contact_no=etContact.getText().toString();
				String password=etPasword.getText().toString();				
				
				if(contact_no.length()>0&&password.length()>0)
				{
				String url=WebHelper.baseUrl+"/LoginServlet";
				//send num password to servlet using asynctask
				
				LoginTask task=new LoginTask();
				task.execute(url,contact_no,password);
				}
				else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
					// Alert Dialog Manager
					  final AlertDialog alert = new AlertDialog.Builder(LoginActivity.this).create();
					   alert.setTitle("Login failed");
					   alert.setMessage("Please enter correct Username/Password");
					   alert.setButton("OK",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							alert.cancel();
						}
					});
					   alert.show();
				}

			}
		});
		
	}//eof on create
	
	
	class LoginTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
		
			HttpPost postReq=new HttpPost(params[0]);
			BasicNameValuePair pair1=
					new BasicNameValuePair("contact_no", params[1]);			
			BasicNameValuePair pair2=
					new BasicNameValuePair("password", params[2]);
			ArrayList<BasicNameValuePair> listPairs=
					new ArrayList<BasicNameValuePair>();
			Log.e("contact",params[1]+"");
			Log.e("password",params[2]+"");
			listPairs.add(pair1);
			listPairs.add(pair2);
			
			String result="";
			try
			{
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(listPairs);
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
				
				
			}catch(Exception ex) 
			{
				Log.e("Exception",ex+"");
				
			}
			
			return result;
		}// eof doInBack
		@Override
		protected void onPostExecute(String result) 
		{
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
				try
				{
					JSONObject obj=new JSONObject(result);
					int owner_id=obj.getInt("owner_id");
					String name=obj.getString("name");
					String surname=obj.getString("surname");
					String email=obj.getString("email");
					String contact_no=obj.getString("contact_no");
					
					//creating session for this user
					  session.createLoginSession(name,contact_no,email,surname,owner_id);
					  
					  
					Toast.makeText(LoginActivity.this, "Log in Successful", 5).show();
					Intent in=new Intent(LoginActivity.this,
							AboutVehicleActivity.class);
					startActivity(in);
				
				}
				catch(Exception ex)
				{
					Log.e("Error:",ex+"");
					
				}
				
			}
			
			
		}
		
	}

}
