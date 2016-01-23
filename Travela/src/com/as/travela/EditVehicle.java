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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditVehicle extends Activity
{
	EditText ed1,ed2,ed3,ed4,ed5;
	Spinner wheelerSpinner,seaterSpinner,typeSpinner,citySpinner,stateSpinner;
	CheckBox ch1,ch2,ch3;
	Button b1;
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
	

	int getPos(String str)
	{
		SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
    	int Position=sp.getInt(str,0);
    	Log.e("Position",Position+"");
    	return Position;
	}

	boolean getSelection(String str)
	{
		SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
    	boolean selection=sp.getBoolean(str, true);
    	//.e("Selection",selection+"");
    	return selection;
    	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_vehicle);
		
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
		
		Intent in=getIntent();
		Vehicle v=(Vehicle)in.getSerializableExtra("vehicle");
		
		final int vehicleId=v.getVehicleId();
		String name=v.getName();
		String regNo=v.getReg_no();
		String location=v.getLocation();
		double rent_per_km=v.getRent_per_km();
		double rent_daily=v.getRent_daily();
		String imageName=v.getImageName();
		
		SharedPreferences spp=getSharedPreferences("settings",MODE_PRIVATE);
		SharedPreferences.Editor editor=spp.edit();
		editor.putInt("edit_vehicle_id", vehicleId);
		editor.putString("editimage", imageName);
		editor.commit();
		
		
		ed1.setText(name);
		ed2.setText(regNo);
		ed5.setText(location);
		ed3.setText(rent_per_km+"");
		ed4.setText(rent_daily+"");
		
		ch1.setChecked(getSelection("with"));
		ch2.setChecked(getSelection("withOut"));

		b1=(Button)findViewById(R.id.button1);
		//b1.setEnabled(false);
		String seats[]=new String[]{"1","2","3","4","5","6","7","8","9","10"};
		
		for(int i=0;i<seats.length;i++)
		{
			alSeater.add(seats[i]);
		}
		
		seaterAdapter=new ArrayAdapter<String>(EditVehicle.this, android.R.layout.
						simple_spinner_item,alSeater);
		seaterSpinner.setAdapter(seaterAdapter);
		seaterSpinner.setSelection(getPos("seaterPosition"));
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
		wheelerAdapter=new ArrayAdapter<String>(EditVehicle.this, android.R.layout.simple_spinner_item,alWheeler);
		wheelerSpinner.setAdapter(wheelerAdapter);

		final String type[]=new String[6];
		wheelerSpinner.setSelection(getPos("wheelerPosition"));
		
		wheelerSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
			{
				wheelerType=alWheeler.get(position);
				int wheelerPosition=position;
				SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
				
				SharedPreferences.Editor editor=sp.edit();
				editor.putInt("wheelerPosition", wheelerPosition);
				editor.commit();
				
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
				typeAdapter=new ArrayAdapter<String>(EditVehicle.this, android.R.layout.simple_spinner_item,alType);
				typeSpinner.setAdapter(typeAdapter);
				
				typeSpinner.setSelection(getPos("typePosition"));
							typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id)
					{
						vType=alType.get(position);
						int typePosition=position;
						SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
						
						SharedPreferences.Editor editor=sp.edit();
						editor.putInt("typePosition",typePosition);
						editor.commit();
						
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
				Toast.makeText(EditVehicle.this,"Select wheeler", 5).show();
				
			}
		});
		ch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked)
					ch3.setText("Not Available");	
				else if(isChecked)
					ch3.setText("Available");	
				
				}
		});
		b1.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				String regNo,vehicleName,rent1,rent2,availability,wheeler1,seater1,location,type,driver = null,state,city;
				int wheeler,seater;
				Double rent_per_km, rent_daily;
				boolean flag=false;
				vehicleName=ed1.getText().toString();
				regNo=ed2.getText().toString();
				location=ed5.getText().toString();
				rent1=ed3.getText().toString();
				rent_per_km=Double.valueOf(rent1);
				rent2=ed4.getText().toString();
				rent_daily=Double.valueOf(rent2);
				wheeler1=(String)wheelerSpinner.getSelectedItem();
				wheeler=Integer.valueOf(wheeler1);
				seater1=(String)seaterSpinner.getSelectedItem();
				seater=Integer.valueOf(seater1);
				type=(String)typeSpinner.getSelectedItem();
				availability="";
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
				if(ch3.isChecked())
				{
				availability="Available";	
				}
				if(!ch3.isChecked())
				{
				availability="Not Available";	
				}
				state=(String)stateSpinner.getSelectedItem();
				City c1=new City();
				
				c1=(City)citySpinner.getSelectedItem();
				
				city=c1.toString();
				SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
		    	String contact=sp.getString("contact_no", "");
		    	int id=sp.getInt("owner_id", 0);
		    	if((regNo!=null&&vehicleName!=null&&driver!=null)&&!(regNo.equals("")&&vehicleName.equals("")&&
	                	driver.equals("")))
	                	{
	                		flag=true;
	                	}
	             if(flag==true)   
	             	{
	            
				    	Vehicle vehicle= new Vehicle(wheeler, seater, id,vehicleId, type, state, city, vehicleName
				    			, regNo, driver,location,availability, rent_daily, rent_per_km);
				    	Gson g=new Gson();
		                String json=g.toJson(vehicle);
		                String url = WebHelper.baseUrl + "/VehicleUpdationServlet";/*name of servlet needed to be launched*/
		                VehicleRegistrationTask task = new VehicleRegistrationTask();
		                task.execute(url,json);
		            	
	             	}
	             else
	             {
	            	 Toast.makeText(EditVehicle.this,"Enter all required fields", 5).show();
		             
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

		
		adapter = new ArrayAdapter<String>(EditVehicle.this,
				android.R.layout.simple_spinner_item,state_name_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		stateSpinner.setAdapter(adapter);
		stateSpinner.setSelection(getPos("statePosition"));

		stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id)
			{
				String s =state_name_list.get(position);
				int statePosition=position;
				if(statePosition==0)
				{
					Toast.makeText(EditVehicle.this,"Select State",5).show();
				}
				else
				{
				SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
				
				SharedPreferences.Editor editor=sp.edit();
				editor.putInt("statePosition", statePosition);
				editor.commit();
				
				//String state1=s.getState_name();
				Log.e("state selected",s);
				
			//	text1.setText(state1);			
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
		Log.e("Result",result+"");
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
			city_adapter = new ArrayAdapter<City>(EditVehicle.this,
					android.R.layout.simple_spinner_item,city_name_list);
			citySpinner.setAdapter(city_adapter);
			citySpinner.setSelection(getPos("cityPosition"));

			citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					int cityPosition=position;
					if(cityPosition==0)
					{
						Toast.makeText(EditVehicle.this, "Please select city",5).show();
					}
					else
					{
					SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
					SharedPreferences.Editor editor=sp.edit();
					editor.putInt("cityPosition", cityPosition);
					editor.commit();
					}
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
				BasicNameValuePair pair1=
				new BasicNameValuePair("vehicledata", params[1]);			
		//.e("paramater",params[1]+"");	
		
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
		int res=Integer.valueOf(result);
		if(res>0)
		{
			Toast.makeText(EditVehicle.this,
				"Vehicle Updated..", Toast.LENGTH_LONG).show();
			Intent in=new Intent(EditVehicle.this,
					EditSplashScreen.class);
			startActivity(in);
	
		}
		else
		{
			Toast.makeText(EditVehicle.this,
				"Vehicle Updation failed. try again !!", Toast.LENGTH_LONG).show();
		}
			
	}

}
}