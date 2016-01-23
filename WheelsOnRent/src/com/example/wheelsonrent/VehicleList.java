package com.example.wheelsonrent;

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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
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

import com.google.gson.Gson;

public class VehicleList extends Activity{
	Spinner spinner_state_name,spinner_city_name;
	ArrayList<State> state_name_list= new ArrayList<State>();
	ArrayAdapter<State> adapter;
	ArrayList<City> city_name_list= new ArrayList<City>();
	ArrayAdapter<City> city_adapter;
	
	TextView text1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_list);
		
		spinner_state_name=(Spinner)findViewById(R.id.spinner1);
		spinner_city_name=(Spinner)findViewById(R.id.spinner2);
		text1 =(TextView)findViewById(R.id.textView1);
		String url_state = WebHelper.baseUrl+"/State_Servlet";
		
		//insert data into spinner
				adapter = new ArrayAdapter<State>(VehicleList.this,
						android.R.layout.simple_spinner_item,state_name_list);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_state_name.setAdapter(adapter);
				
				spinner_state_name.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						State s =state_name_list.get(position);
						String state1=s.getState_name();
						
						
						Log.e("state selected",state1);
						
						text1.setText(state1);			
						City_Task city_task= new City_Task();
						String city_url=WebHelper.baseUrl+"City_Servlet";
						city_task.execute(city_url,state1);
					
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
				State_Task task = new State_Task();
				task.execute(url_state);
		
		
	}
	
	
	//create asynctask to send request to servlet for state list
	class State_Task extends AsyncTask<String, Void, String>
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
					//Log.e("value of s",s);
					if(s==null)
					{
						//Log.e("s is null",s+"");
						break;
						
					}
					result=result+s;
					Log.e("REsult is",result+"");
				}
				br.close();
			}
			catch(Exception e)
			{
				Log.e("exception is",e+"");
				Log.e("error in State_task:",e.toString());
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			super.onPostExecute(result);
			state_name_list.clear();
			
			try
			{
				JSONArray array= new JSONArray(result);
				for(int i=0;i<array.length();i++)
				{
					JSONObject obj  = array.getJSONObject(i);
					String strjson=obj.toString();
					Gson g = new Gson();
					State s= g.fromJson(strjson, State.class);
					state_name_list.add(s);
				}
				adapter.notifyDataSetChanged();
			}
			catch(Exception e)
			{
				Log.e("error",e.getMessage());
			}
			
		}
		
	}
	
	//create asynk task to get city list corresponding to state
	class City_Task extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			
			String result="";
			HttpPost postReq=new HttpPost(params[0]);
			BasicNameValuePair state= new BasicNameValuePair("state", params[1]);
			ArrayList<BasicNameValuePair> listPairs= new ArrayList<BasicNameValuePair>();
			listPairs.add(state);
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
			city_name_list.clear();
			try
			{
				JSONArray array= new JSONArray(result);
				for(int i=0;i<array.length();i++)
				{
					JSONObject obj  = array.getJSONObject(i);
					String strjson=obj.toString();
					Gson g = new Gson();
					City c= g.fromJson(strjson, City.class);
					city_name_list.add(c);
				}
				city_adapter = new ArrayAdapter<City>(VehicleList.this,
						android.R.layout.simple_spinner_item,city_name_list);
				spinner_city_name.setAdapter(city_adapter);
				
			}
			catch(Exception e)
			{
				Log.e("error3",e.getMessage());
			}
			
		}
	}
	
	
}
