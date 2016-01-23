package com.as.travela;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

public class VehicleDetailActivity extends ActionBarActivity{

	TextView text_model,text_seater,text_availability,text_type,
	text_rent_pd,text_rent_pkm,text_driver,text_name,text_mobile,text_location,text_email,
	text_warning;
	Button b_back;
	ImageView imageView1;
	 Dialog dlg_user;
	 Vehicle v ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_details);
		
		Toolbar   t= new Toolbar(VehicleDetailActivity.this);
		t.setNavigationIcon(R.drawable.ic_launcher);
		
		Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar1);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_launcher);
		toolbar.setTitle("Wheels on rent");
		
		text_model=(TextView)findViewById(R.id.textView2);
		text_seater=(TextView)findViewById(R.id.textView4);
		text_availability=(TextView)findViewById(R.id.textView6);
		text_type=(TextView)findViewById(R.id.textView8);
		text_rent_pd=(TextView)findViewById(R.id.textView10);
		text_rent_pkm=(TextView)findViewById(R.id.textView12);
		text_driver=(TextView)findViewById(R.id.textView13);
		text_name=(TextView)findViewById(R.id.textView15);
		text_mobile=(TextView)findViewById(R.id.textView17);
		text_location=(TextView)findViewById(R.id.textView19);
		text_email=(TextView)findViewById(R.id.textView21);
		text_warning=(TextView)findViewById(R.id.textView22);
		
		b_back=(Button)findViewById(R.id.button1);
		
		imageView1=(ImageView)findViewById(R.id.imageView1);
		
		//create custom dialog for user
        dlg_user=new android.app.Dialog(VehicleDetailActivity.this);
    	dlg_user.setTitle("Please enter your name and contact");
    	dlg_user.setContentView(R.layout.user_dialog);
    	final EditText edit_name=(EditText)dlg_user.findViewById(R.id.editText1);
    	final EditText edit_contact=(EditText)dlg_user.findViewById(R.id.editText2);
       	final Button btn1=(Button)dlg_user.findViewById(R.id.button1);
       	
       	
       
       	
       	btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			String name=edit_name.getText().toString();
			String contact=edit_contact.getText().toString();
					if(!name.equals(null)&&!contact.equals(null))
					{
						String url=WebHelper.baseUrl+"User_Servlet";
				
					UserTask task = new UserTask();
					task.execute(url,name,contact+"");
					}
					else 
					{
						if(name.equals(null))
						{
							Toast.makeText(VehicleDetailActivity.this, 
									"Please enter your name", 5).show();
						}
						else if(contact.equals(null))
						{
							Toast.makeText(VehicleDetailActivity.this, 
									"Please enter your contact no", 5).show();
						}
					}
			}
		});
    	//end of tw custom dialog
		dlg_user.show();
		Intent in = getIntent();
		v =(Vehicle)in.getSerializableExtra("vehicle");
		Log.e("vehicle object",v+"");
		int vehicle_id=v.getVehicleId();
		
		String url=WebHelper.baseUrl+"Owner_Servlet";
		OwnerTask task = new OwnerTask();
		task.execute(url,v.getOwnerId()+"",text_name,text_email,text_mobile);
		
		
		
		text_model.setText(v.getName());
		text_seater.setText(String.valueOf(v.getSeater()));
		text_availability.setText(v.getAvailability());
		text_type.setText(v.getType());
		text_rent_pkm.setText(String.valueOf(v.getRent_per_km()));
		text_rent_pd.setText(String.valueOf(v.getRent_daily()));
		if(v.getDriver().equals("only with"))
		{
			text_driver.setText("available with driver");
		}
		else if(v.getDriver().equals("only without"))
		{
			text_driver.setText("available without driver");
			text_warning.setVisibility(View.VISIBLE);
			text_warning.setText("you have to submitt your original ID proof to Owner.");
		}
		if(v.getDriver().equals("with and without"))
		{
			text_driver.setText("available with driver and without driver");
			text_warning.setVisibility(View.VISIBLE);
			text_warning.setText("you have to submitt your original ID proof to Owner.");
		}
		text_location.setText(v.getLocation());
		
		
		String imageName=v.getImageName();

		String url1=WebHelper.phpUrl+"/uploads/"+imageName;
        ImageTask task1=new ImageTask();
    	task1.execute(url1,vehicle_id+"",imageView1);
   
		
		
	}
	

	class ImageTask extends AsyncTask<Object, Void, Bitmap>
    {
		ImageView img;

		@Override
		protected Bitmap doInBackground(Object... params)
		{
			String url=params[0].toString();
			HttpGet getReq=new HttpGet(url);
			Log.e("Url is",url);
			HttpClient client=new DefaultHttpClient();
			Bitmap bm=null;
			img=(ImageView)params[2];
			
			try
			{
				HttpResponse resp=client.execute(getReq);
				InputStream is=resp.getEntity().getContent();
				bm=BitmapFactory.decodeStream(is);
				
			}
			catch(Exception ex)
			{
				Log.e("Exception 1",ex+"");
			}
			Log.e("bitmap",bm+"");
			return bm;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) 
		{
			super.onPostExecute(result);
			if(result==null)
			{
				img.setImageResource(R.drawable.ic_launcher);
			}
			else
			{
				img.setImageBitmap(result);
			}
		}
	}

	class OwnerTask extends AsyncTask<Object, Void , String> 
	{
		
		String result="";
		TextView text_name,text_email,text_mobile;
		
		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			HttpPost postReq=new HttpPost(params[0].toString());
			BasicNameValuePair owner_id= new BasicNameValuePair("owner_id", params[1].toString());
			ArrayList<BasicNameValuePair> listPairs= new ArrayList<BasicNameValuePair>();
			listPairs.add(owner_id);
			 text_name=(TextView)params[2];
			 text_email=(TextView)params[3];
			 text_mobile=(TextView)params[4];
			try
			{
				UrlEncodedFormEntity entity= new UrlEncodedFormEntity(listPairs);
				postReq.setEntity(entity);
				//send request to server
				HttpClient client= new DefaultHttpClient();
				HttpResponse resp= client.execute(postReq);
				InputStream in=resp.getEntity().getContent();
				InputStreamReader reader= new InputStreamReader(in);
				BufferedReader br = new BufferedReader(reader);
			
				
				while(true)
					
				{
					String s=br.readLine();
					//Log.e("value of s",s);
					if(s==null)
					{
						break;
						
					}
					result=result+s;
				}
				Log.e("result is owner:",result);
				
				br.close();
			
			}
			catch(Exception e)
			{
				Log.e("error2",e.getMessage());
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
			
			
			
			try
			{
				
				
					JSONObject obj  = new JSONObject(result);
					String strjson=obj.toString();
					Gson g = new Gson();
					Owner o= g.fromJson(strjson,Owner.class);
					text_name.setText(o.getName());
					text_mobile.setText(o.getContact_no());
					text_email.setText(o.getEmail());
					
				
				
				
			}
			catch(Exception e)
			{
				Log.e("error4",e.getMessage());
			}
			
		}
		
		protected void onCancelled() {
			  
			   Toast toast = Toast.makeText(VehicleDetailActivity.this, 
			     "Error connecting to Server", Toast.LENGTH_LONG);
			   toast.setGravity(Gravity.TOP, 25, 400);
			   toast.show();

			  }

		
		
		}
	
	class UserTask extends AsyncTask<String, Void , String> 
	{
		

		String result="";
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpPost postReq=new HttpPost(params[0]);
			BasicNameValuePair name= new BasicNameValuePair("name", params[1]);
			BasicNameValuePair contact= new BasicNameValuePair("mobile_no", params[2]);
			ArrayList<BasicNameValuePair> listPairs= new ArrayList<BasicNameValuePair>();
			listPairs.add(name);
			listPairs.add(contact);
			try
			{
				UrlEncodedFormEntity entity= new UrlEncodedFormEntity(listPairs);
				postReq.setEntity(entity);
				//send request to server
				HttpClient client= new DefaultHttpClient();
				HttpResponse resp= client.execute(postReq);
				InputStream in=resp.getEntity().getContent();
				InputStreamReader reader= new InputStreamReader(in);
				BufferedReader br = new BufferedReader(reader);
			
				
				while(true)
					
				{
					String s=br.readLine();
					//Log.e("value of s",s);
					if(s==null)
					{
						break;
						
					}
					result=result+s;
				}
				Log.e("result is:",result);
				
				br.close();
			
			}
			catch(Exception e)
			{
				Log.e("error2",e.getMessage());
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.equals("1"))
			{
				Toast.makeText(VehicleDetailActivity.this, "submitted successfuly", 5).show();
				dlg_user.dismiss();
			}
			else
			{
				Toast.makeText(VehicleDetailActivity.this, "error submit enter your data again", 5).show();
				
				
			}
			
			
		}
		
		protected void onCancelled() {
			   
			   Toast toast = Toast.makeText(VehicleDetailActivity.this, 
			     "Error connecting to Server", Toast.LENGTH_LONG);
			   toast.setGravity(Gravity.TOP, 25, 400);
			   toast.show();

			  }

		
		
		}
	
		
	}

