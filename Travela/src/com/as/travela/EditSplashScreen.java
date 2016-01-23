package com.as.travela;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class EditSplashScreen extends Activity {

	Thread splashTread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_scrren);
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 2000) {
						sleep(100);
						waited += 100;
					}
					Intent intent = new Intent(EditSplashScreen.this,
							EditVehiclePhotoActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
					EditSplashScreen.this.finish();
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					EditSplashScreen.this.finish();
				}

			}
		};
		splashTread.start();
	}

}
