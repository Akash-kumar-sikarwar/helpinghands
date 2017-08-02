package com.akashkumar.helpinghands;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class LoadPage extends Activity {
	private final int DISPLAY_LENGHT = 2000;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start_up);

		new Handler().postDelayed(new Runnable() {

			public void run() {
				startUpActivity();
			}

		}, DISPLAY_LENGHT);

	}

	public void startUpActivity() {
		Intent mainIntent = new Intent(getBaseContext(), SignUp.class);

		startActivity(mainIntent);

		this.finish();
	}

}
