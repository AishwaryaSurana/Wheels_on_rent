package p1;


import java.util.ArrayList;

import com.aish.onrent.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AddVehicleActivity extends Activity
{
	EditText ed1,ed2,ed3,ed4;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_vehicle);
		
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		ed3=(EditText)findViewById(R.id.editText3);
		ed4=(EditText)findViewById(R.id.editText4);
		
		wheelerSpinner=(Spinner)findViewById(R.id.spinner1);
		typeSpinner=(Spinner)findViewById(R.id.spinner2);
		seaterSpinner=(Spinner)findViewById(R.id.spinner3);
		citySpinner=(Spinner)findViewById(R.id.spinner4);
		stateSpinner=(Spinner)findViewById(R.id.spinner5);
		
		ch1=(CheckBox)findViewById(R.id.checkBox1);
		ch2=(CheckBox)findViewById(R.id.checkBox2);
		ch3=(CheckBox)findViewById(R.id.checkBox3);
		
		b1=(Button)findViewById(R.id.button1);

		String seats[]=new String[]{"1","2","3","4","5","6","7","8","9","10"};

		for(int i=0;i<seats.length;i++)
		{
			alSeater.add(seats[i]);
		}
		seaterAdapter=new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_list_item_2,alSeater);
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
		wheelerAdapter=new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_list_item_1,alWheeler);
		wheelerSpinner.setAdapter(wheelerAdapter);
		wheelerSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
			{
				wheelerType=alWheeler.get(position);
				
				String type[]=new String[4];
				if(position==1)
				{
					type[0]="SUV";
					type[1]="Sedan";
					type[2]="others";
					
				}
				if(position==2)
				{
					type[0]="Mophet";
					type[1]="Sports";
					type[2]="others";
					
				}
				for(int i=0;i<type.length;i++)
				{
					alType.add(type[i]);
				}
				typeAdapter=new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_list_item_2,alType);
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
					public void onNothingSelected(AdapterView<?> parent) {
						
					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
		});
		
		b1.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				String regNo,vehicleName,rent1,rent2,wheeler,seater,type,driver;
				vehicleName=ed1.getText().toString();
				regNo=ed2.getText().toString();
				rent1=ed3.getText().toString();
				rent2=ed4.getText().toString();
				wheeler=(String)wheelerSpinner.getSelectedItem();
				seater=(String)seaterSpinner.getSelectedItem();
				type=(String)typeSpinner.getSelectedItem();
				if(ch1.isChecked())
				{
					driver="with";
					
				}
				if(ch2.isChecked())
				{
					driver="without";
					
				}
				if(ch1.isChecked()&&ch2.isChecked())
				{
					driver="Both";
					
				}
				
				
				
			}
		});
	}

}