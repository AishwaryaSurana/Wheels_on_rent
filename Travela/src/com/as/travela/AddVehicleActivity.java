package com.as.travela;


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

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddVehicleActivity extends Activity
{
	EditText ed1,ed2,ed3,ed4,ed5;
	Spinner wheelerSpinner,seaterSpinner,typeSpinner,citySpinner,stateSpinner;
	CheckBox ch1,ch2,ch3;
	Button b1,b2;
	String vType="",wheelerType="",seater="";
	ArrayList<String>alWheeler=new ArrayList<String>();
	ArrayList<String>alSeater=new ArrayList<String>();
	ArrayList<String>alType=new ArrayList<String>();
	ArrayAdapter<String>wheelerAdapter;
	ArrayAdapter<String>typeAdapter;
	ArrayAdapter<String>seaterAdapter;
	ArrayList<String> state_name_list= new ArrayList<String>();
	ArrayAdapter<String> adapter;
	ArrayList<City> city_name_list= new ArrayList<City>();
	ArrayAdapter<City> city_adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_vehicle);
		
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		ed3=(EditText)findViewById(R.id.editText3);
		ed4=(EditText)findViewById(R.id.editText4);
		ed5=(EditText)findViewById(R.id.editText5);
		
		wheelerSpinner=(Spinner)findViewById(R.id.spinner1);
		typeSpinner=(Spinner)findViewById(R.id.spinner2);
		seaterSpinner=(Spinner)findViewById(R.id.spinner3);

		stateSpinner=(Spinner)findViewById(R.id.spinner4);
		citySpinner=(Spinner)findViewById(R.id.spinner5);
		
		ch1=(CheckBox)findViewById(R.id.checkBox1);
		ch2=(CheckBox)findViewById(R.id.checkBox2);
		ch3=(CheckBox)findViewById(R.id.checkBox3);
		
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		
		String seats[]=new String[]{"1","2","3","4","5","6","7","8","9","10"};
		
		for(int i=0;i<seats.length;i++)
		{
			alSeater.add(seats[i]);
		}
		seaterAdapter=new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.
						simple_spinner_item,alSeater);
		seaterSpinner.setAdapter(seaterAdapter);
		seaterSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
			{
				seater=alSeater.get(position);
			}

			@Override
	
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
		String wheeler[]=new String[]{"4","2"};
		for(int i=0;i<wheeler.length;i++)
		{
			alWheeler.add(wheeler[i]);
		}
		wheelerAdapter=new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_spinner_item,alWheeler);
		wheelerSpinner.setAdapter(wheelerAdapter);

		final String type[]=new String[6];

		wheelerSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
			{
				wheelerType=alWheeler.get(position);
				
				if(position==0)
				{
					type[0]="SUV";
					type[1]="Sedan";
					type[2]="Station Wagon";
					type[3]="Van";
					type[4]="Convertible";
					type[5]="Others";
				}
				else if (position==1)
				{
					type[0]="Cruiser";
					type[1]="Sport";
					type[2]="Touring";
					type[3]="Standard";
					type[4]="Dirt bike";
					type[5]="Other";
				}
				if(alType.isEmpty())	
				{
					for(int i=0;i<type.length;i++)
					{
						alType.add(type[i]);
					}
				}
				else
				{
					alType.clear();

					for(int i=0;i<type.length;i++)
					{
						alType.add(type[i]);
					}
					
				}
				typeAdapter=new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_spinner_item,alType);
				typeSpinner.setAdapter(typeAdapter);
				typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id)
					{
						vType=alType.get(position);
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent)
					{
						
					}
				});


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) 
			{
				//flag=0;
				Toast.makeText(AddVehicleActivity.this,"Select wheeler", Toast.LENGTH_LONG).show();
				
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Intent in=new Intent(AddVehicleActivity.this,
						Terms.class);
				
				startActivity(in);
			
			}
		});
		b1.setEnabled(false);
		b1.setAlpha(0.5f);
		ch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				b1.setEnabled(isChecked);
				b1.setAlpha(1.0f);
			}
		});
		b1.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				try
				{
				String regNo,vehicleName,rent1,rent2,availability,wheeler1;
				String seater1,location,type,driver="",state,city;
				int wheeler,seater;
				double rent_per_km=0, rent_daily=0;
				boolean flag=false;
				vehicleName=ed1.getText().toString();
				Log.e("name v",vehicleName);
				regNo=ed2.getText().toString();
				location=ed5.getText().toString();
				rent1=ed3.getText().toString();
				rent2=ed4.getText().toString();
					if(rent2.equals("")&&(!rent1.equals("")))
					{
						rent_per_km=Double.valueOf(rent1);
					}	
					else if(!rent2.equals("")&&(!rent1.equals("")))
					{
						rent_per_km=Double.valueOf(rent1);
						rent_daily=Double.valueOf(rent2);
					}
				
				wheeler1=(String)wheelerSpinner.getSelectedItem();
				wheeler=Integer.valueOf(wheeler1);
				seater1=(String)seaterSpinner.getSelectedItem();
				seater=Integer.valueOf(seater1);
				type=(String)typeSpinner.getSelectedItem();
				availability="Available";
				if(ch1.isChecked()&&ch2.isChecked())
				{
					driver="with and without";
					
				}
				else if(ch1.isChecked())
				{
					driver="only with";
					
				}
				else if(ch2.isChecked())
				{
					driver="only without";
					
				}
				String st,ct;
				
				st=(String)stateSpinner.getSelectedItem();
				if(st.equals("Select State"))
				{
					state="";
				}
				else
				{
					state=st;
					
				}
				City c1=new City();
				Log.e("city",c1+"");
				c1=(City)citySpinner.getSelectedItem();
				ct=c1.toString();
				
				if(ct.equals("Select City")||ct.equals(""))
				{
					city="";
				}
				else
				{
					city=ct;
				}
				
				SharedPreferences sp1=getSharedPreferences("settings",MODE_PRIVATE);
		    	int id=sp1.getInt("owner_id", 0);
		    	//regNo,vehicleName,rent1,rent2,availability,wheeler1,seater1,location,type,driver = null,state,city;
				//int wheeler,seater;
				//double rent_per_km=0, rent_daily=0;
		    	if(!(regNo.equals("")||vehicleName.equals("")||driver.equals("")||state.equals("")||city.equals("")||rent_per_km==0.0))
	                	{
	                	flag=true;
	                	}
		    	Log.e("flag",flag+"");
	             if(flag==true)   
	             	{
	            	 Vehicle vehicle= new Vehicle(wheeler, seater, id,0, type, state, city, vehicleName
	            			 , regNo, driver,location,availability, rent_daily, rent_per_km);
	 		    	 Gson g=new Gson();
	                 String json=g.toJson(vehicle);
	                 String url = WebHelper.baseUrl + "/VehicleRegistrationServlet";/*name of servlet needed to be launched*/
	                 VehicleRegistrationTask task = new VehicleRegistrationTask();
	                 task.execute(url,json);
	 				
	 			
	             	}
	             else
	             {
	            	 Toast.makeText(AddVehicleActivity.this,"Enter all required fields", 5).show();
	             }
			}
			catch(NumberFormatException nmb)
				{
					Log.e("Error in num",nmb+"");
					Toast.makeText(AddVehicleActivity.this,"Enter Valid Rent", Toast.LENGTH_SHORT).show();
				}
				catch(NullPointerException nl)
				{
				Log.e("Error is there",nl+"");
				Toast.makeText(AddVehicleActivity.this, "Enter Required fields", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		String state[]=new String[]{"Select State","Andaman and Nicobar","Andhra Pradesh",	"Arunachal Pradesh",
				"Assam","Bihar","Chandigarh","Chhattisgarh","Dadra and Nagar Haveli",
				"Daman and Diu","Delhi","Goa","Gujarat","Haryana","Himachal Pradesh",
				"Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Lakshadweep",
				"Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram",
				"Nagaland","Orissa","Puducherry","Punjab","Rajasthan","Sikkim",
				"Tamil Nadu","Tripura","Uttarakhand","Uttar Pradesh","West Bengal"};
		
		for(int i=0;i<state.length;i++)
		{
			state_name_list.add(state[i]);
		}

		
		adapter = new ArrayAdapter<String>(AddVehicleActivity.this,
				android.R.layout.simple_spinner_item,state_name_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stateSpinner.setAdapter(adapter);
		
		stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id)
			{
				String s =state_name_list.get(position);
				int statePosition=position;
				if(statePosition==0)
				{
				
				}
				else
				{
					Log.e("state selected",s);
					City_Task city_task= new City_Task();
					String city_url=WebHelper.baseUrl+"/City_Servlet";
					city_task.execute(city_url,s);
				}
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});		
		}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		
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
				if(s==null)
				{
					break;
					
				}
				result=result+s;
				Log.e("Result",result+"");
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
		City c1 = new City();
		c1.setCity_name("Select City");
		city_name_list.add(c1);
		
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
			city_adapter = new ArrayAdapter<City>(AddVehicleActivity.this,
					android.R.layout.simple_spinner_item,city_name_list);
			citySpinner.setAdapter(city_adapter);
			citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
			});
			
		}
		catch(Exception e)
		{
			Log.e("error3",e.getMessage());
		}
		
	}
}


class VehicleRegistrationTask extends AsyncTask<String, Void, String>
{
	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(String... params) {
	
		HttpPost postReq=new HttpPost(params[0]);
		
		//prepare entry to store email/password
		BasicNameValuePair pair1=
				new BasicNameValuePair("vehicledata", params[1]);			
		
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
			Log.e("Error",ex+"");
			
		}
		
		return result;
	}// eof doInBack

	@Override
	protected void onPostExecute(String result) 
	{
		super.onPostExecute(result);
		if(!result.startsWith("error"))
		{
		int v_id=Integer.parseInt(result);
		SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putInt("latestVehicle", v_id);
		editor.commit();
		
		if(v_id > 0)
		{
			Toast.makeText(AddVehicleActivity.this,
				"Vehicle Registered..", Toast.LENGTH_LONG).show();
			Intent in=new Intent(AddVehicleActivity.this,
					SplashScreen.class);
			
			startActivity(in);
			AddVehicleActivity.this.finish();
	
		}
		else
		{
			Toast.makeText(AddVehicleActivity.this,
				"Vehicle Registration failed. try again !!", Toast.LENGTH_LONG).show();
		}
		}
		else
		{
			Toast.makeText(AddVehicleActivity.this,
					"Vehicle Registration failed. try again !!", Toast.LENGTH_LONG).show();
			
			
		}
			
	}

}
}