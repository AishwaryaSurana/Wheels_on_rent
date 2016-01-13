package com.example.dailyexpenseapp;

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

import com.google.gson.Gson;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseEntryActvity  extends Activity
{
	TextView textUser,textDate;
	EditText etItem,etAmount;
	ImageView imageBill,imageCamera,imageGallery;
	Button btSubmit,btCalender;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_entry);
		textUser=(TextView)findViewById(R.id.textView1);
		
		//read user info from shared preference and show on textview
		SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
	final	int user_id=sp.getInt("user_id", 0);
		String name=sp.getString("name", "");
		
		textUser.setText(name+":"+user_id);
		
		//initialize remaining ui
		textDate=(TextView)findViewById(R.id.textView4);
		etItem=(EditText)findViewById(R.id.editText1);
		etAmount=(EditText)findViewById(R.id.editText2);
		imageBill=(ImageView)findViewById(R.id.imageView1);
		imageCamera=(ImageView)findViewById(R.id.imageView2);
		imageGallery =(ImageView)findViewById(R.id.imageView2);
		
		btCalender=(Button)findViewById(R.id.button1);
		btSubmit=(Button)findViewById(R.id.button2);
		
		//show calender dialog on button click
		btCalender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			final	Dialog dlg=new Dialog(ExpenseEntryActvity.this);
				dlg.setContentView(R.layout.calender_dialog);
				dlg.setTitle("select date");
				
		final DatePicker dp=(DatePicker)dlg.findViewById(R.id.datePicker1);
		Button btDone=(Button)dlg.findViewById(R.id.button1);
		btDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String date=dp.getYear()+"-"
				+(dp.getMonth()+1)+"-"
				+dp.getDayOfMonth();
				
				textDate.setText(date);
				dlg.dismiss();
			}
		});//eof done listener
				
		dlg.show();
		
			}
		});//eof calender button listener
		
		//submit expense data
		btSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String item=etItem.getText().toString();
				String str=etAmount.getText().toString();
				String date=textDate.getText().toString();
				String bill="NA";
				Float amount=Float.parseFloat(str);
				//Expense e=new Expense(expense_id, item, amount, date, bill, user_id)
				Expense e=new Expense(0, item, amount,
						date, bill, user_id);
				
				//convert expense objet to JSOS
				Gson g=new Gson();
				String json=g.toJson(e);
				
//send json to server using async task
String url=WebHelper.baseURL+"ExpenseEntryServlet";
  ExpenseEntryTask task=new ExpenseEntryTask();
  task.execute(url,json);
				
			}
		});
		
	}//eof oncreate
	class ExpenseEntryTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
		
			HttpPost postReq=new HttpPost(params[0]);
			
			//prepare entry to store email/password
			BasicNameValuePair pair1=
					new BasicNameValuePair("expensedata", params[1]);			
			
			ArrayList<BasicNameValuePair> listPairs=
					new ArrayList<BasicNameValuePair>();
			
			listPairs.add(pair1);
		
			
			String result="";
			try{
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
				
				
			}catch(Exception ex) {}
			
			return result;
		}// eof doInBack
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.equals("1"))
			{
				Toast.makeText(ExpenseEntryActvity.this,
					"success..", 5).show();
				finish();
			}
			else
			{
				Toast.makeText(ExpenseEntryActvity.this,
					"failed. try again !!", 5).show();
			}
				
		}
		
	}//eof task
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.history, menu);
		return super.onCreateOptionsMenu(menu);
	}
@Override
public boolean onOptionsItemSelected(MenuItem item) 
{
    if(item.getItemId()==R.id.menuHistory)
    {
    	Intent in=new Intent(ExpenseEntryActvity.this,
    			ExpenseHistoryActvity.class);
    	startActivity(in);
    }
	return super.onOptionsItemSelected(item);
}
	
}
