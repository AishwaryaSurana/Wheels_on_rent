package com.as.travela;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;






import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class VehiclePhotoActivity extends ActionBarActivity
{
	ImageButton camera,gallery;
	private static final int PICK_IMAGE = 1;
	private static final int PICK_Camera_IMAGE = 2;
	private ImageView imgView;
	private Button upload,cancel;
	private Bitmap bitmap;
	private ProgressDialog dialog;
	Uri imageUri, selectedImageUri;
	int owner_id,vehicle_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		Toolbar   t= new Toolbar(VehiclePhotoActivity.this);
		t.setNavigationIcon(R.drawable.ic_launcher);
		
		Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar1);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_launcher);
		toolbar.setTitle("Wheels on rent");
		toolbar.setTitleTextColor(Color.WHITE);
		
		camera=(ImageButton)findViewById(R.id.imageButton1);
		gallery=(ImageButton)findViewById(R.id.imageButton2);
		imgView = (ImageView) findViewById(R.id.imageView1);
		imgView.setImageAlpha(50);
		upload = (Button) findViewById(R.id.button1);
		cancel = (Button) findViewById(R.id.button2);
		upload.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (bitmap == null) {
					Log.e("Bitmap is null","null");
					Toast.makeText(getApplicationContext(),
							"Please select image", Toast.LENGTH_SHORT).show();
				}
				else 
				{
					dialog = ProgressDialog.show(VehiclePhotoActivity.this, "Uploading",
							"Please wait...", true);
					new VehiclePhotoTask().execute();
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v)
			{
				Intent in=new Intent(VehiclePhotoActivity.this,
						ViewVehicleActivity.class);
				startActivity(in);
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
//					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//					startActivityForResult(i, RESULT_LOAD_IMAGE);
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
	class VehiclePhotoTask extends AsyncTask<Void, Void, String> 
		{
			@Override
				protected String doInBackground(Void... unsued) 
				{
						InputStream is;
					    BitmapFactory.Options bfo;
					    Bitmap bitmapOrg;
					    ByteArrayOutputStream bao;
					   
					    bfo = new BitmapFactory.Options();
					    bfo.inSampleSize = 2;
					    //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);
					      
					    bao = new ByteArrayOutputStream();
					    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
						byte [] ba = bao.toByteArray();
						String ba1 = Base64.encodeBytes(ba);
						SharedPreferences s=getSharedPreferences("settings",MODE_PRIVATE);
						
						owner_id=s.getInt("owner_id", 0);
						vehicle_id=s.getInt("latestVehicle", 0);
						Log.e("Owner id",owner_id+"");
						Log.e("Vehicle id",vehicle_id+"");
						
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("image",ba1));
						nameValuePairs.add(new BasicNameValuePair("owner_id", owner_id+""));
						nameValuePairs.add(new BasicNameValuePair("vehicle_id", vehicle_id+""));
						String o=owner_id+"";
						String v=vehicle_id+"";
						String imgName=o+v+".jpg";
						
						nameValuePairs.add(new BasicNameValuePair("cmd",imgName));
						Log.v("image basic name", imgName);
						SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
						
						//open editor to edit content in settings
						SharedPreferences.Editor editor=sp.edit();
						editor.putString("imageName",imgName);
						editor.commit();
						
						try
						{
						        HttpClient httpclient = new DefaultHttpClient();
						        HttpPost httppost = new 
		                
						        //Here you need to put your server file address
						        HttpPost(WebHelper.phpUrl+"/upload_photo.php");
						        Log.e("path=",WebHelper.phpUrl+"/upload_photo.php");
						        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						        HttpResponse response = httpclient.execute(httppost);
						        HttpEntity entity = response.getEntity();
						        is = entity.getContent();
						        
						}
						catch(Exception e)
						{
						   Log.v("log_tag", "Error in http connection "+e.toString());
						}
					return "Success";
					// (null);
				}

				@Override
				protected void onProgressUpdate(Void... unsued) {

				}

				@Override
				protected void onPostExecute(String sResponse) 
				{
					try 
					{
						if (dialog.isShowing())
							dialog.dismiss();
				
						Toast.makeText(VehiclePhotoActivity.this, "Image Added", Toast.LENGTH_LONG).show();
						Intent in=new Intent(VehiclePhotoActivity.this,ViewVehicleActivity.class);
						startActivity(in);
					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(),
								e.getMessage(),Toast.LENGTH_LONG).show();
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

			}

	

}
