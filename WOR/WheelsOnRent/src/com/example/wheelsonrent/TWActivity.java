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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheelsonrent.FWActivity.VehicleTask;
import com.google.gson.Gson;

public class TWActivity extends Activity
{	
	EditText edit_loc;
	Button b_search;
	Spinner spinner_state_name,spinner_city_name;
	ArrayList<State> state_name_list= new ArrayList<State>();
	ArrayAdapter<State> state_adapter;
	ArrayList<City> city_name_list= new ArrayList<City>();
	ArrayAdapter<City> city_adapter;
	String type="", state,City,location;
	
	ListView listview;
	ArrayList<Vehicle> list_vehicle=new ArrayList<Vehicle>();
	VehicleAdapter adapter;
	
	
	final String wheeler="2";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.two_wheeler);
		
		spinner_state_name=(Spinner)findViewById(R.id.spinner1);
		spinner_city_name=(Spinner)findViewById(R.id.spinner2);
		edit_loc=(EditText)findViewById(R.id.editText1);
		b_search=(Button)findViewById(R.id.button1); 
		
		listview=(ListView)findViewById(R.id.listView1);
		
		
		
		String url_state = WebHelper.baseUrl+"State_Servlet";
		//insert select city into spinner
		city_adapter = new ArrayAdapter<City>(TWActivity.this,
				android.R.layout.simple_spinner_item,city_name_list);
		
		City c = new City();
		c.setCity_name("select city");
		city_name_list.add(c);
		spinner_city_name.setAdapter(city_adapter);
		
		//insert data into spinner
				state_adapter = new ArrayAdapter<State>(TWActivity.this,
						android.R.layout.simple_spinner_item,state_name_list);
				state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				State s =new State();
				s.setState_name("select state");
				state_name_list.add(s);
				spinner_state_name.setAdapter(state_adapter);
				
				
				spinner_state_name.setOnItemSelectedListener(new OnItemSelectedListener() {
				
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						State s =state_name_list.get(position);
						 state=s.getState_name();
						
						
						Log.e("state selected",state);
						
								
						City_Task city_task= new City_Task();
						String city_url=WebHelper.baseUrl+"City_Servlet";
						city_task.execute(city_url,state);
					
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});;
				
				//add listner to city spinner
				spinner_city_name.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						City c =city_name_list.get(position);
						 City=c.getCity_name();
						
						
						Log.e("city selected",City);
					
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});;
				
				
				
		
				//create custom dialog for two wheeler
		        final Dialog dlg_tw=new android.app.Dialog(TWActivity.this);
		    	dlg_tw.setTitle("select two wheeler type");
		    	dlg_tw.setContentView(R.layout.tw_dialog);
		    	final RadioGroup rggrp=(RadioGroup)dlg_tw.findViewById(R.id.radio);
		       final	RadioButton rb_cruiser=(RadioButton)dlg_tw.findViewById(R.id.radioButton1);
		       	final RadioButton rb_sport=(RadioButton)dlg_tw.findViewById(R.id.radioButton2);
		       	final RadioButton rb_touring=(RadioButton)dlg_tw.findViewById(R.id.radioButton3);
		       	final RadioButton rb_standard=(RadioButton)dlg_tw.findViewById(R.id.radioButton4);
		       	final RadioButton rb_dirtbike=(RadioButton)dlg_tw.findViewById(R.id.radioButton5);
		       	final RadioButton rb_other=(RadioButton)dlg_tw.findViewById(R.id.radioButton6);
		       	final Button btn1=(Button)dlg_tw.findViewById(R.id.button1);
		       	
		       	
		       	rggrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
					
						if(rb_cruiser.isChecked())
							type="Cruiser";
						if(rb_sport.isChecked())
							type="Sport";
						if(rb_touring.isChecked())
							type="Touring";
						if(rb_standard.isChecked())
							type="Standard";
						if(rb_dirtbike.isChecked())
							type="Dirt Bike";
						if(rb_other.isChecked())
							type="Other";
					Log.e("radio button selected is",type);
					}
				});
		       	
		       	btn1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlg_tw.cancel();
						if(!state.equals("select state")&&!City.equals("select city")&&!type.equals(""))
						{
							String tw_url=WebHelper.baseUrl+"Vehicle_Servlet";
							
							VehicleTask task = new VehicleTask();
							Log.e("state",state);
							Log.e("city",City);
							Log.e("type",type);
							Log.e("location",location);
							Log.e("wheeler",wheeler);
							task.execute(tw_url,state,City,type,location,wheeler);
						}
						else
						{
							if(state.equals("select state"))
							{
								Toast.makeText(TWActivity.this,"please select any state" , 5).show();
							}
							else if(City.equals("select city"))
							{
								Toast.makeText(TWActivity.this,"please select any city" , 5).show();
							}
							else if(type.equals(""))
							{
								Log.e("type:",type);
								Toast.makeText(TWActivity.this,"please select any two wheeler type" , 5).show();
							}
						}
					}
						
				});
		    	//end of tw custom dialog
		       	
		      //add listner on search button
				b_search.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						location=edit_loc.getText().toString();
						dlg_tw.show();
							
				       
					}
				});
				
		State_Task state_task = new State_Task();
		state_task.execute(url_state);
						
		
			
	}
	
	//create adapter as inner class
		class VehicleAdapter extends BaseAdapter
		{

			@Override
			public int getCount() {
				
				return list_vehicle.size();
			}

			@Override
			public Object getItem(int position) {
				
				return list_vehicle.get(position);
			}

			@Override
			public long getItemId(int position) {
				
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				final Vehicle v1 = list_vehicle.get(position);
				
				//load the view/UI for item
				LayoutInflater inf = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
				View itemView=inf.inflate(R.layout.t_nd_f_list_item,null);
				
				//fill item with vehicle v
				ImageView image=(ImageView)itemView.findViewById(R.id.imageView1);
				TextView model_name=(TextView)itemView.findViewById(R.id.textView4);
				TextView seater=(TextView)itemView.findViewById(R.id.textView5);
				TextView available=(TextView)itemView.findViewById(R.id.textView6);
				Button button1=(Button)itemView.findViewById(R.id.button1);
				
				
				model_name.setText(v1.getName());
				seater.setText(String.valueOf(v1.getRent_per_km()));
				available.setText(v1.getAvailability());
				if(v1.getAvailability().equals("no"))
				{
					button1.setEnabled(false);
				}
				if(v1.getAvailability().equals("yes"))
				{
					button1.setEnabled(true);
				}
				button1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent in=new Intent(TWActivity.this,
								VehicleDetailActivity.class);
						
						in.putExtra("vehicle",v1);
						startActivity(in);
					}
				});
				
				
				return itemView;
			}
			
		}
		
		//create vehicle task to send url to vehicle_servlet
		class VehicleTask extends AsyncTask<String , Void, String>
		{
			private ProgressDialog dialog = 
					   new ProgressDialog(TWActivity.this);

			 protected void onPreExecute() {
				   dialog.setMessage("Getting your data... Please wait...");
				   dialog.show();
				  }

			
			String result="";
			@Override
			protected String doInBackground(String... params) {
				
				
				HttpPost postReq=new HttpPost(params[0]);
				BasicNameValuePair state= new BasicNameValuePair("state", params[1]);
				BasicNameValuePair city= new BasicNameValuePair("city", params[2]);
				BasicNameValuePair type= new BasicNameValuePair("type", params[3]);
				BasicNameValuePair location= new BasicNameValuePair("location", params[4]);
				BasicNameValuePair wheeler= new BasicNameValuePair("wheeler", params[5]);
				ArrayList<BasicNameValuePair> listPairs= new ArrayList<BasicNameValuePair>();
				listPairs.add(state);
				listPairs.add(city);
				listPairs.add(type);
				listPairs.add(location);
				listPairs.add(wheeler);
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
				dialog.dismiss();
				
				if(result.equals("[]"))
				{
					Log.e("result is null",result);
					Toast toast = Toast.makeText(TWActivity.this, 
						     "No Two wheeler is present at this location", Toast.LENGTH_LONG);
						   toast.setGravity(Gravity.TOP, 25, 400);
						   toast.show();
				}
				list_vehicle.clear();
				try
				{
					
					JSONArray array= new JSONArray(result);
					for(int i=0;i<array.length();i++)
					{
						JSONObject obj  = array.getJSONObject(i);
						String strjson=obj.toString();
						Gson g = new Gson();
						Vehicle v= g.fromJson(strjson, Vehicle.class);
						list_vehicle.add(v);
					}
					adapter = new  VehicleAdapter();
					listview.setAdapter(adapter);
					
				}
				catch(Exception e)
				{
					Log.e("error4",e.getMessage());
				}
				
			}
			
			protected void onCancelled() {
				   dialog.dismiss();
				   Toast toast = Toast.makeText(TWActivity.this, 
				     "Error connecting to Server", Toast.LENGTH_LONG);
				   toast.setGravity(Gravity.TOP, 25, 400);
				   toast.show();

				  }

			
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
					state_adapter.notifyDataSetChanged();
				}
				catch(Exception e)
				{
					Log.e("error",e.getMessage());
				}
				
			}
			protected void onCancelled() {
				   
				   Toast toast = Toast.makeText(TWActivity.this, 
				     "Error connecting to Server", Toast.LENGTH_LONG);
				   toast.setGravity(Gravity.TOP, 25, 400);
				   toast.show();

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
				//city_name_list.clear();
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
					city_adapter.notifyDataSetChanged();
					
				}
				catch(Exception e)
				{
					Log.e("error3",e.getMessage());
				}
				
			}
			
			protected void onCancelled() {
			
				   Toast toast = Toast.makeText(TWActivity.this, 
				     "Error connecting to Server", Toast.LENGTH_LONG);
				   toast.setGravity(Gravity.TOP, 25, 400);
				   toast.show();

				  }
		}

}