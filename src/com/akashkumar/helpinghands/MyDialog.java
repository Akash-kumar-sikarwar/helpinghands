package com.akashkumar.helpinghands;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MyDialog extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		AlertDialog.Builder alert1 = new AlertDialog.Builder(this);

        TextView view = new TextView(this);
		view.setText(
				"If sending alert was accidental than click OK to send apology message to the receivers.Press Cancel to continue");
		view.setTextSize(18);
		alert1.setTitle(Html.fromHtml("<font color='#CC0000'>Send Apology Message</font>"));
		alert1.setView(view);
		alert1.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				Intent sms1 = new Intent(MyDialog.this, ApologySms.class);
				MyDialog.this.startService(sms1);
				return;
			}

		});
		alert1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alert1.create();
		alert1.show();
    }
}