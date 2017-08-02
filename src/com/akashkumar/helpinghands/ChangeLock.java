package com.akashkumar.helpinghands;

import android.support.v7.app.ActionBarActivity;

import com.akashkumar.helpinghands.utils.Lock9View;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ChangeLock extends ActionBarActivity {

	private FrameLayout enterPatternContainer, confirmPatternContainer;
	private Database database;
	private Lock9View lockViewFirstTry, lockViewConfirm;

	private static String MY_PREFS_NAME = "PatternLock";
	private static String PATTERN_KEY;

	SharedPreferences prefs;
	Editor editor;

	TextView tvMsg;
	private String message;

	@Override
	protected void onCreate(Bundle getVars) {
		super.onCreate(getVars);
		String previousActivity = getIntent().getStringExtra("FROM_ACTIVITY");
		database = new Database(this);
		database.createTables();
		
		
		if (previousActivity.equals("SignUp")) {
			checkDatabase();
			this.finish();
		} 
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_change);
		setTitle("Change Lock Pattern");
		prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

		tvMsg = (TextView) findViewById(R.id.tvMsg);

		tvMsg.setText(getResources().getString(R.string.draw_pattern_msg));

		enterPatternContainer = (FrameLayout) findViewById(R.id.enterPattern);
		confirmPatternContainer = (FrameLayout) findViewById(R.id.confirmPattern);

		lockViewFirstTry = (Lock9View) findViewById(R.id.lock_viewFirstTry);
		lockViewConfirm = (Lock9View) findViewById(R.id.lock_viewConfirm);

		// we can get a call back string when ever user interacts with the
		// pattern lock view
		lockViewFirstTry.setCallBack(new Lock9View.CallBack() {

			public void onFinish(String password) {
				PATTERN_KEY = password;
				enterPatternContainer.setVisibility(View.GONE);
				tvMsg.setText(getResources().getString(R.string.redraw_confirm_pattern_msg));
				confirmPatternContainer.setVisibility(View.VISIBLE);
			}
		});

		lockViewConfirm.setCallBack(new Lock9View.CallBack() {

			public void onFinish(String password) {
				if (password.equals(PATTERN_KEY)) {
					Toast.makeText(getApplicationContext(), "Pattern created successfully!", Toast.LENGTH_SHORT).show();
					editor.putString("Pattern", password);
					editor.commit();
					Intent intent = new Intent(ChangeLock.this, LockMainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "You have drawn the wrong Pattern", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	private void checkDatabase() {

		if (database.checkdetails(this) == true) {
			Intent l = new Intent(this, LockMainActivity.class);
			startActivity(l);
			this.finish();
		} else {
			Toast.makeText(this, "Make a pattern", 3000).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_change, menu);
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.contact:
			new AlertDialog.Builder(this).setTitle("Android-Lock9View")
					.setMessage("Email : anfersyed@gmail.com\n" + "Blog  : http://android-delight.blogspot.in/\n")
					.setPositiveButton("OK", null).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}*/
}
