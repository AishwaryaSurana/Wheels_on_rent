package com.example.wheelsonrent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class VehicleList extends Activity{
	Spinner scity_name;
	ArrayList<String> city_name_list= new ArrayList<String>();
	ArrayAdapter adapter;
	Button bcar,bauto,bmotorcycle,bscooter;
	TextView text1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_list);
		bcar=(Button)findViewById(R.id.bcar);
		bauto=(Button)findViewById(R.id.bauto);
		bmotorcycle=(Button)findViewById(R.id.bmotor_cycle);
		bscooter=(Button)findViewById(R.id.bscooter);
		scity_name=(Spinner)findViewById(R.id.spinner1);
		text1 =(TextView)findViewById(R.id.textView1);
		String url = WebHelper.baseUrl+"State_City_Servlet";
		
		State_City_Task task = new State_City_Task();
		task.execute(url);
		//insert data into spinner
		
		//get data from spinner on selection
		scity_name.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String selected_city=city_name_list.get(position);
				text1.setText(selected_city);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(VehicleList.this);
			    builder.setTitle("No selection");
			    builder.setMessage("Please select any city");
			    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
						
					}
				});
			    
			    builder.setIcon(android.R.drawable.ic_dialog_alert);
			    builder.show();
			}
		});
	}
	
	
	//create asynctask to send request to servlet
	class State_City_Task extends AsyncTask<String, Void, String>
	{
		String result="";
		@Override
		protected String doInBackground(String... params) {
			
			HttpPost postReq=new HttpPost(params[0]);
			
			try
			{
				//send request to server
				HttpClient client= new DefaultHttpClient();
				HttpResponse resp= client.execute(postReq);
				InputStream in=resp.getEntity().getContent();
				InputStreamReader reader= new InputStreamReader(in);
				BufferedReader br = new BufferedReader(reader);
			
				
				while(true)
				{
					String s=br.readLine();
					Log.e("value of s",s);
					if(s==null)
					{
						break;
						
					}
					result=result+s;
				}
				br.close();
			}
			catch(Exception e)
			{
				Log.e("error",e.getMessage());
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try
			{
				JSONObject obj = new JSONObject(result);
				String state_name=obj.getString("state_name");
				String city_name=obj.getString("city_name");
				Log.e("state name list:",state_name);
				Log.e("city name list:",city_name);
			}
			catch(Exception e)
			{
				Log.e("error",e.getMessage());
			}
			
		}
		
	}
}
