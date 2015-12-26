package com.aish.aishwarya.rental;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Aishwarya on 23-Dec-15.
 */
public class Registration extends Activity {
    EditText ed1, ed2, ed3, ed4;
    Button b;
    Spinner spinner1;
    Connection connect;
    PreparedStatement stmt;
    ResultSet rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        b = (Button) findViewById(R.id.button1);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        connect = CONN(WebHelper.userName, WebHelper.password,WebHelper.dbname,WebHelper.baseUrl);
        String query = "SELECT Distinct`State/Union Territory` FROM `india_states_cities`";
        try
        {
            connect = CONN(WebHelper.userName, WebHelper.password,WebHelper.dbname,WebHelper.baseUrl);

            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rs.next())
            {
                String id = rs.getString("State/Union Territory");
                data.add(id);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinner1.setAdapter(NoCoreAdapter);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                String name = spinner1.getSelectedItem().toString();
                Toast.makeText(Registration.this, name, Toast.LENGTH_SHORT)
                        .show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, surname, contact, email;
                name = ed1.getText().toString();
                surname = ed2.getText().toString();
                contact = ed3.getText().toString();
                email = ed4.getText().toString();
                //Owner o=new Owner(name,surname,contact,email);

                String url = WebHelper.baseUrl + "RegistrationServlet";/*name of servlet needed to be launched*/
               // RegisterTask task = new RegisterTask();
               // task.execute(url);


            }
        });

    }


        //recieve final value return from



    private Connection CONN(String _user, String _pass, String _DB,
                            String _server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
                    + "databaseName=" + _DB + ";user=" + _user + ";password="
                    + _pass + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
