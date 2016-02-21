package com.as.travela;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class VehiclePhotoActivity extends ActionBarActivity
{
		ImageView imgView;
		ImageButton camera,gallery;
		private Button upload,cancel;
		String imgName="";
		ProgressBar pbar;
		private Bitmap bitmap;
		private static final int PICK_IMAGE = 1;
		private static final int PICK_Camera_IMAGE = 2;
		Uri imageUri, selectedImageUri;
		private ProgressDialog dialog;
		
		
		protected void onCreate(Bundle savedInstanceState) 
		{		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.photo1);
			Toolbar t= new Toolbar(VehiclePhotoActivity.this);
			t.setNavigationIcon(R.drawable.ic_launcher);
			Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar1);
			setSupportActionBar(toolbar);
			toolbar.setNavigationIcon(R.drawable.ic_launcher);
			toolbar.setTitle("Travella");
			toolbar.setTitleTextColor(Color.WHITE);
			
			
			imgView=(ImageView)findViewById(R.id.imageView1);
			camera=(ImageButton)findViewById(R.id.imageButton1);
			
			gallery=(ImageButton)findViewById(R.id.imageButton2);
			
			upload=(Button)findViewById(R.id.button1);			
			cancel=(Button)findViewById(R.id.button2);
			pbar=(ProgressBar)findViewById(R.id.progressBar1);
			pbar.setVisibility(View.INVISIBLE);
			
			//check if profile image already exist
			ApplicationInfo appInfo=getApplicationInfo();
			String appPackageDir=appInfo.dataDir+"/userdir";
			
			SharedPreferences s=getSharedPreferences("settings",MODE_PRIVATE);
			
			int owner_id=s.getInt("owner_id", 0);
			final int vehicle_id=s.getInt("latestVehicle", 0);
			Log.e("Owner id",owner_id+"");
			Log.e("Vehicle id",vehicle_id+"");
			String o=owner_id+"";
			String v=vehicle_id+"";
			imgName=o+v+".jpg";
			
			final File f=new File(appPackageDir,imgName);
			
			upload.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					if (bitmap == null)
					{
						Log.e("Bitmap is null","null");
						Toast.makeText(getApplicationContext(),
								"Please select image", Toast.LENGTH_SHORT).show();
					}
					
					else
					{
						dialog = ProgressDialog.show(VehiclePhotoActivity.this, "Uploading",
								"Please wait...", true);
						
					String filePath=f.getAbsolutePath();
					FileUploadTask task=new FileUploadTask();
					String vehicle_id1=String.valueOf(vehicle_id);
					Log.e("v id" ,vehicle_id1);
					task.execute(filePath,vehicle_id1);
					pbar.setVisibility(View.VISIBLE);
				
					}
				}
			});
			cancel.setOnClickListener(new OnClickListener() 
			{
				public void onClick(View v)
				{
					Intent in=new Intent(VehiclePhotoActivity.this,
							AboutVehicleActivity.class);
					startActivity(in);
					VehiclePhotoActivity.this.finish();
				}
			});


			camera.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v) 
				{
					//define the file-name to save photo taken by Camera activity
			    	String fileName = "new-photo-name.jpg";
			    	
			    	//create parameters for Intent with filename
			    	ContentValues values = new ContentValues();
			    	values.put(MediaStore.Images.Media.TITLE, fileName);
			    	values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
			    	//imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
			    	imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			    	
			    	//create new Intent
			    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    	intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			    	intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			    	startActivityForResult(intent, PICK_Camera_IMAGE);
			    }
			});

			gallery.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
		        	try 
		        	{
						Intent gintent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						gintent.setType("image/*");
						gintent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(gintent, "Select Picture"),
						PICK_IMAGE);
		        	} 
		        	catch (Exception e)
		        	{
						Toast.makeText(getApplicationContext(),
						e.getMessage(),
						Toast.LENGTH_LONG).show();
						Log.e(e.getClass().getName(), e.getMessage(), e);
					}
			
				}
			});
			


			
		}//eof oncreate
		
		class FileUploadTask extends AsyncTask<String, Void, String>
		{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String filePath=params[0];
				final String veh=params[1];
				String str="";
				try{
					URL u=new URL(WebHelper.baseUrl+"/UploadServlet");
					MultipartUtility mpu=new MultipartUtility(u);
					mpu.addFilePart("file", new File(filePath));
					//mpu.addFormField("user_id", "12345");
					mpu.addFormField("vehicle_id",veh);
					byte []result=mpu.finish();
					 str=new String(result);
				}
				catch(Exception ex)
				{
    					str=ex.toString();
				}
				
				return str;
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.e("result", result);
				pbar.setVisibility(View.INVISIBLE);
				Toast.makeText(VehiclePhotoActivity.this, "Image Added", Toast.LENGTH_LONG).show();
				Intent in=new Intent(VehiclePhotoActivity.this,AboutVehicleActivity.class);
				startActivity(in);
				VehiclePhotoActivity.this.finish();
				
			
			}
		}
		protected void onActivityResult(int requestCode, int resultCode, Intent data)
		{
			String filePath = null;
			switch (requestCode) 
			{
					case PICK_IMAGE:
						if (resultCode == Activity.RESULT_OK) 
						{
							selectedImageUri = data.getData();
						}
						break;
					case PICK_Camera_IMAGE:
						 if (resultCode == RESULT_OK)
						 {
			 		        //use imageUri here to access the image
			 		    	selectedImageUri = imageUri;

							/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
							selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
					    }
						else if (resultCode == RESULT_CANCELED) 
						{
			 		        Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
			 		    } 
						 else 
						{
			 		    	Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
			 		    }
						 break;
				}
			
			
				if(selectedImageUri != null)
				{
					try 
						{
							// OI FILE Manager
							String filemanagerstring = selectedImageUri.getPath();
							Log.e("image selected",filemanagerstring);
							// MEDIA GALLERY
							String selectedImagePath = getPath(selectedImageUri);

							Log.e("if uri!=0",""+selectedImagePath);
							if (selectedImagePath != null)
							{
								filePath = selectedImagePath;
							}
							else if (filemanagerstring != null)
							{
								filePath = filemanagerstring;
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Unknown path",
										Toast.LENGTH_LONG).show();
								Log.e("Bitmap", "Unknown path");
							}
				
							if (filePath != null)
							{
								decodeFile(filePath);
							} 
							else
							{
								bitmap = null;
							}
						}
						catch (Exception e)
						{
							Toast.makeText(getApplicationContext(), "Internal error",
									Toast.LENGTH_LONG).show();
							Log.e(e.getClass().getName(), e.getMessage(), e);
						}
				}
		}

		public String getPath(Uri uri)
		{
			String[] projection = {MediaStore.Images.Media.DATA};
			for(int i=0;i<projection.length;i++)
			{
				Log.e("Projection",projection[i]+"");
			}
			Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
			if (cursor != null) {
				// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
				// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				return cursor.getString(column_index);
			} 
			else
				return null;
		}

		public void decodeFile(String filePath)
		{
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 1024;
			Log.e("Decoding",filePath+"");
			
			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) 
			{
				if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			bitmap = BitmapFactory.decodeFile(filePath, o2);
			imgView.setImageAlpha(255);
			imgView.setImageBitmap(bitmap);
			
			//save image data in  your app folder
			ApplicationInfo appInfo=getApplicationInfo();
			String appPackageDir=appInfo.dataDir;
			
			//create folder in app dir
			String folderPath=appPackageDir+"/userdir";
			File userdir=new File(folderPath);
			
			//create folder in external memory card
			//get external memory card path
			//String sdcardPath=Environment.getExternalStorageDirectory().getAbsolutePath();
			//File userdir=new File(sdcardPath+"/userdir");
			
			if(!userdir.exists() ) //no dir exist on this path
				userdir.mkdir();
			
			
			File f=new File(userdir, imgName);
			try{
				FileOutputStream fout=
					new FileOutputStream(f);
				//bmp.compress(format, quality, stream);
				boolean result=bitmap.compress(Bitmap.CompressFormat.PNG,
						100, fout);
				if(result==true)
				 Toast.makeText(VehiclePhotoActivity.this,
						 "profile updated", 6).show();
				
				fout.close();
				
			}catch(Exception ex)
			{
				Log.e("error;", ex.toString());
			}
			
			


		}




		

}
