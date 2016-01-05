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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aish.onrent.R;
import com.google.gson.Gson;

/**
 * Created by Aishwarya on 23-Dec-15.
 */
public class RegistrationActivity extends Activity 
{
    EditText ed1, ed2, ed3, ed4,ed5,ed6;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText) findViewById(R.id.editText5);
        ed6 = (EditText) findViewById(R.id.editText6);
        b = (Button) findViewById(R.id.button1);
        
        b.setOnClickListener(new View.OnClickListener()
       	{
            @Override
            public void onClick(View v)
            {
            	int id=0;
            	String name, surname, contact, email, password,confirmpassword;
                name = ed1.getText().toString();
                surname = ed2.getText().toString();
                contact = ed3.getText().toString();
                email = ed4.getText().toString();
                password=ed5.getText().toString();
                confirmpassword=ed6.getText().toString();
               	
                Owner o=new Owner(name,surname,contact,email,password);
                Gson g=new Gson();
                String json=g.toJson(o);

                if(password.equals(confirmpassword))
                {

                String url = WebHelper.baseUrl + "/RegistrationServlet";/*name of servlet needed to be launched*/
                RegistrationTask task = new RegistrationTask();
                task.execute(url,json);
            	//Toast.makeText(RegistrationActivity.this, "Registered "
            		//	+ "password",Toast.LENGTH_LONG).show();
            	
                }
                else
                {
                	Toast.makeText(RegistrationActivity.this, "password doesn't match confirm "
                			+ "password",Toast.LENGTH_LONG).show();
                }


            }
        });

       }
	class RegistrationTask extends AsyncTask<String, Void, String>
	{
		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
		
			HttpPost postReq=new HttpPost(params[0]);
			//Log.e("paramater",params[0]+"");	
			
			//prepare entry to store email/password
			BasicNameValuePair pair1=
					new BasicNameValuePair("ownerdata", params[1]);			
			//Log.e("paramater",params[1]+"");	
			
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
				Log.e("Response",resp+"");
				InputStream is=resp.getEntity().getContent();
				InputStreamReader reader=new InputStreamReader(is);
				BufferedReader br=new BufferedReader(reader);
				
				while(true)
				{
						
					String s=br.readLine();
					if(s==null) 
						break;
					result=result+s;
					//Log.e("in loop",result+"");	
					
				}
				br.close();
				Log.e("result",result+"");
				
			}
			catch(Exception ex)
			{
				Log.e("Error",ex+"");
				
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
					"Registered..", Toast.LENGTH_LONG).show();
				Intent in=new Intent(RegistrationActivity.this,
						AboutVehicleActivity.class);
				startActivity(in);
		
			}
			else
			{
				Toast.makeText(RegistrationActivity.this,
					"Registration failed. try again !!", Toast.LENGTH_LONG).show();
			}
				
		}
		
	}

}
   