package com.aish.aishwarya.rental;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Aishwarya on 24-Dec-15.
 */
public class VehicleRegistration extends Activity

{
    String type,model;
    Double rent;
    int seater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle);

    }
}
