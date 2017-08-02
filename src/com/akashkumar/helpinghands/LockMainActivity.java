package com.akashkumar.helpinghands;

import com.akashkumar.helpinghands.utils.Lock9View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LockMainActivity extends ActionBarActivity {

    private Lock9View lock9View;

    private static String MY_PREFS_NAME = "PatternLock";
    private static String PATTERN_KEY;
    
    SharedPreferences prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setTitle("Helping Hands");
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        
        lock9View.setCallBack(new Lock9View.CallBack() {

            public void onFinish(String password) {
            	PATTERN_KEY = prefs.getString("Pattern", "invalid");
            	
            	if(PATTERN_KEY.equals("invalid")){
            		Toast.makeText(LockMainActivity.this, "Options --> Create new Pattern", Toast.LENGTH_LONG).show();	
            	}else{
            		if(password.equals(PATTERN_KEY)){
                		Intent intent = new Intent(LockMainActivity.this, CameraView.class);
                		startActivity(intent);
                		finish();
                		Toast.makeText(LockMainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                	}else{
                		Toast.makeText(LockMainActivity.this, "Pattern incorrect!", Toast.LENGTH_SHORT).show();
                	}
            	}  
            }
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact:
                new AlertDialog.Builder(this)
                        .setTitle("Android-Lock9View")
                        .setMessage(
                                "Name : akash kumar" +
                                "Email  : akash.sky.heaven@gmail.com\n")
                        .setPositiveButton("OK", null)
                        .show();
                return true;
                
            case R.id.create_new_pattern:
            	Intent intent = new Intent(LockMainActivity.this, ChangeLock.class);
            	startActivity(intent);
            	finish();
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
*/    }


