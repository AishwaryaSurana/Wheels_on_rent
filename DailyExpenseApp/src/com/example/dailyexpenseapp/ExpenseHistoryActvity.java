package com.example.dailyexpenseapp;

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

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ExpenseHistoryActvity extends ListActivity 
{
	ListView listView;
	ArrayList<Expense> listExpenses=
			new ArrayList<Expense>();
	ArrayAdapter<Expense> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listView=getListView();
		
		adapter=new ArrayAdapter<Expense>
		(ExpenseHistoryActvity.this,
		 android.R.layout.simple_list_item_1,
		 listExpenses);
		
		listView.setAdapter(adapter);
		
		//download expense from server for 
		//logged in user
		String url=WebHelper.baseURL+"ExpenseListServlet";
		//find user id of logged in user
		SharedPreferences sp=getSharedPreferences
				("settings", MODE_PRIVATE);
		int user_id=sp.getInt("user_id", 0);
		
		//prepare get url with user_id
		url=url+"?user_id="+user_id;
		//send user id to server using AsyncTask
		
		ExpenseListTask task=new ExpenseListTask();
		task.execute(url);
		
	}//eof oncreate
	class ExpenseListTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) 
		{		
	HttpGet getReq=new HttpGet(params[0]);					
	String result="";
	try{
		
		HttpClient client=new DefaultHttpClient();
		HttpResponse resp=client.execute(getReq);
		
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
		try{
			
JSONArray array=new JSONArray(result);
for(int i=0;i<array.length();i++)
{
	JSONObject obj=array.getJSONObject(i);
	String strjson=obj.toString();
	//convert json object string to expense object
   Gson g=new Gson();
   Expense e=g.fromJson(strjson, Expense.class);
   listExpenses.add(e);
}
adapter.notifyDataSetChanged();
			
		}catch(Exception ex)
		{
			Toast.makeText(ExpenseHistoryActvity.this,
					result, 5).show();

		}
		
		
				
		}
		
	}

}
