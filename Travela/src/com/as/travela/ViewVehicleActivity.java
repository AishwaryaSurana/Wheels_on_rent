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
import org.json.JSONArray;
import org.json.JSONObject;


import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class ViewVehicleActivity extends Activity
{
	int owner_id;
	ListView listview;
	ArrayList<Vehicle> list_vehicle=new ArrayList<Vehicle>();
	VehicleAdapter adapter;
	//static String imageName="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_vehicle_new);
		listview=(ListView)findViewById(R.id.listView1);
		SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
		owner_id=sp.getInt("owner_id", 0);
		String url=WebHelper.baseUrl+"/Vehicle_Servlet";
		Log.e("url and owner",url+" "+owner_id);
		
		ViewTask task=new ViewTask();
		task.execute(url,owner_id+"");
		
    	
	}//create adapter as inner class
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
				
				final Vehicle v = list_vehicle.get(position);
				
				//load the view/UI for item
				LayoutInflater inf = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
				View itemView=inf.inflate(R.layout.showvehicle,null);
				ImageView img;
				
				//fill item with vehicle v
				img=(ImageView)itemView.findViewById(R.id.imageView1);
				TextView name=(TextView)itemView.findViewById(R.id.textView4);
				TextView driver=(TextView)itemView.findViewById(R.id.textView5);
				TextView available=(TextView)itemView.findViewById(R.id.textView6);
				Button edit=(Button)itemView.findViewById(R.id.button1);
				Button delete=(Button)itemView.findViewById(R.id.button2);
				
				String imageName=v.getImageName();
				driver.setText(v.getDriver());
				String n= Character.toUpperCase((v.getName()).charAt(0)) + (v.getName()).substring(1);
				Log.e("name=",n);
				name.setText(n);
				
				available.setText(v.getAvailability());
				
				final int vehicle_id=v.getVehicleId();
				
				SharedPreferences sp1=getSharedPreferences("settings",MODE_PRIVATE);
				SharedPreferences.Editor editor=sp1.edit();
				editor.putInt("vehicle_id", vehicle_id);
				editor.commit();
				
				String url=WebHelper.phpUrl+"/uploads/"+imageName;
		        ImageTask task=new ImageTask();
		    	task.execute(url,owner_id+"",vehicle_id+"",img);
		   
				final Intent in=new Intent(ViewVehicleActivity.this,EditVehicle.class);
				in.putExtra("vehicle", v);
				
				edit.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v)
					{

						startActivity(in);
						
						
					}
				});
				delete.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v) 
					{
						String url=WebHelper.baseUrl+"/DeleteVehicleServlet";
						DeleteTask task=new DeleteTask();
						task.execute(url,vehicle_id+"");
						
						
					}
				});
				return itemView;
			}
			
			
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
				img=(ImageView)params[3];
				
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

		class DeleteTask extends AsyncTask<String, Void, String>
		{

			@Override
			protected String doInBackground(String... params) 
			{
				HttpPost postReq=new HttpPost(params[0]);
				BasicNameValuePair pair1=new BasicNameValuePair("vehicle_id", params[1]);			
				ArrayList<BasicNameValuePair> listPairs=new ArrayList<BasicNameValuePair>();
				Log.e("id",params[1]+"");
				listPairs.add(pair1);
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
					
					
				}
				catch(Exception ex) 
				{
					Log.e("Exception",ex+"");
					
				}
				
				return result;
			}
			// eof doInBack
			@Override
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);
				
				Log.e("result",result);
				//if fail or error
				if(result.equals("1"))
				{
					Toast.makeText(ViewVehicleActivity.this,
						"Deleted Successfully ", Toast.LENGTH_LONG).show();
					Intent in=new Intent(ViewVehicleActivity.this,
							ViewVehicleActivity.class);
					startActivity(in);
			
				}
				else
				{
					Toast.makeText(ViewVehicleActivity.this,
						"Deletion failed. try again !!", Toast.LENGTH_LONG).show();
				}
			
			}
		}

		class ViewTask extends AsyncTask<String, Void, String>
		{
			private ProgressDialog dialog = 
					   new ProgressDialog(ViewVehicleActivity.this);

			 protected void onPreExecute()
			 {
				   dialog.setMessage("Getting your data... Please wait...");
				   dialog.show();
			 }

		
			@Override
			protected String doInBackground(String... params)
			{
			
				HttpPost postReq=new HttpPost(params[0]);
				BasicNameValuePair pair1=
						new BasicNameValuePair("owner_id", params[1]);			
				ArrayList<BasicNameValuePair> listPairs=
						new ArrayList<BasicNameValuePair>();
				Log.e("id",params[1]+"");
				listPairs.add(pair1);
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
					
					
				}
				catch(Exception ex) 
				{
					Log.e("Exception",ex+"");
					
				}
				
				return result;
			}
			// eof doInBack
			@Override
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);
				dialog.dismiss();
				
				Log.e("result",result);
				//if fail or error
				if(result.startsWith("fail") ||
						result.startsWith("error") )
				{
					Toast.makeText(ViewVehicleActivity.this, result, 5).show();
				}
				else
				{
					//parse json object from result
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
						Intent in=new Intent(ViewVehicleActivity.this,EditVehicle.class);
						adapter = new  VehicleAdapter();
						listview.setAdapter(adapter);
						
						
					}
					catch(Exception e)
					{
						Log.e("error3",e.getMessage());
					}
				}
				
			}
					
	

			protected void onCancelled()
			{
				   dialog.dismiss();
				   Toast toast = Toast.makeText(ViewVehicleActivity.this, 
				     "Error connecting to Server", Toast.LENGTH_LONG);
				   toast.setGravity(Gravity.TOP, 25, 400);
				   toast.show();

			}
		}
	
	}
	
