package com.as.travela;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
	int owner_id,vehicle_id;
	ListView listview;
	ArrayList<Vehicle> list_vehicle=new ArrayList<Vehicle>();
	VehicleAdapter adapter;
	//static String imageName="";
	TextView emptyText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_vehicle_new);
		listview=(ListView)findViewById(R.id.listView1);
		emptyText=(TextView)findViewById(R.id.textView1);
		listview.setEmptyView(emptyText);
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
				//final int v_id=v.getVehicleId();
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
				name.setText(v.getName());
				
				available.setText(v.getAvailability());
				
				vehicle_id=v.getVehicleId();
				
				SharedPreferences sp1=getSharedPreferences("settings",MODE_PRIVATE);
				SharedPreferences.Editor editor=sp1.edit();
				editor.putInt("vehicle_id", vehicle_id);
				editor.commit();

				String url=WebHelper.baseUrl+"/uploads/"+imageName;
		        ImageTask task=new ImageTask();
		        String oid=owner_id+"";
				String vid=vehicle_id+"";
				String imgName=oid+vid+".jpg";
				
		    	task.execute(url,img,imgName);
		    	final Intent in=new Intent(ViewVehicleActivity.this,EditVehicle.class);
				in.putExtra("vehicle", v);
				
				
				edit.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v)
					{
						
						startActivity(in);
						ViewVehicleActivity.this.finish();
						
					}
				});
				delete.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View view) 
					{
						int v_id=v.getVehicleId();
						String url=WebHelper.baseUrl+"/DeleteVehicleServlet";
						DeleteTask task=new DeleteTask();
						task.execute(url,v_id+"");
						Log.e("deletion id",v_id+"");
						
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
				String imgName = params[2].toString();
				
				HttpGet getReq=new HttpGet(url);
				Log.e("Url is",url);
				HttpClient client=new DefaultHttpClient();
				Bitmap bm=null;
				img=(ImageView)params[1];

				ApplicationInfo appInfo=getApplicationInfo();
				String appPackageDir=appInfo.dataDir+"/userdir";
				File fi=new File(appPackageDir,imgName);
				if(fi.exists())
				{
					bm=BitmapFactory.decodeFile(fi.getAbsolutePath());
					boolean b=fi.exists();
					boolean b1=fi.isFile();
					Log.e("existence ",b+" and isfile "+b1);
					Log.e("file ", fi+"");

				}
				else
				{
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

		
		class DeletePhotoTask extends AsyncTask<String,Void,String>
		{

			@SuppressWarnings("deprecation")
			@Override
			protected String doInBackground(String... params) 
			{
			HttpPost postReq=new HttpPost(params[0]);
			Log.e("url ",params[0]+"");
			int vehicle_id=Integer.valueOf(params[1]);
			int owner_id=Integer.valueOf(params[2]);
			String o=owner_id+"";
			String v=vehicle_id+"";
			String imgName=o+v+".jpg";
			String str="";
			ApplicationInfo appInfo=getApplicationInfo();
			String appPackageDir=appInfo.dataDir+"/userdir";
			File fi=new File(appPackageDir,imgName);
			if(fi.exists())
			{
				boolean b=fi.delete();
				Log.e("file Deleted ", b+"");
			}
			BasicNameValuePair pair1=
					new BasicNameValuePair("imageName",imgName);			
			ArrayList<BasicNameValuePair> listPairs=
					new ArrayList<BasicNameValuePair>();
			listPairs.add(pair1);
			
			String result="";
			try
			{
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(listPairs);
				postReq.setEntity(entity);			
				Log.e("entity is",entity+"");
				
				//send request to server
				
				HttpClient client=new DefaultHttpClient();
				HttpResponse resp=client.execute(postReq);
				Log.e("response:",resp+"");
				
				InputStream is=resp.getEntity().getContent();
				InputStreamReader reader=new InputStreamReader(is);
				BufferedReader br=new BufferedReader(reader);
				Log.e("br ",br+"");
				
				
				while(true)
				{
					
					Log.e("in loop","loop");
					
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
			Log.e("image del name", imgName);		
			return result;
		
			}
			
			@Override
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);
				try 
				{
					Log.e("result of delete", result);
					
					//Toast.makeText(ViewVehicleActivity.this, "Photo Deleted", Toast.LENGTH_LONG).show();
				}
				catch (Exception e)
				{
					Toast.makeText(getApplicationContext(),
							e.getMessage(),Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
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
				vehicle_id=Integer.valueOf(params[1]);
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
			
			@Override
			protected void onProgressUpdate(Void... values) 
			{
			
			}
			// eof doInBack
			@Override
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);
				
				Log.e("result",result);
				int res=Integer.valueOf(result);
			      
				//if fail or error
				if(res>=1)
				{
					String url2=WebHelper.baseUrl+"/DeletionServlet";
					DeletePhotoTask dltTask=new DeletePhotoTask();
					dltTask.execute(url2,vehicle_id+"",owner_id+"");
					
					

					Toast.makeText(ViewVehicleActivity.this,
						"Deleted Successfully ", Toast.LENGTH_LONG).show();
					Intent in=new Intent(ViewVehicleActivity.this,
							AboutVehicleActivity.class);
					
					startActivity(in);
					ViewVehicleActivity.this.finish();
			
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
	
