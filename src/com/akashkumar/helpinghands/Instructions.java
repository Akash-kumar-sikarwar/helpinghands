package com.akashkumar.helpinghands;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Instructions extends BaseActivity {
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.instructions, frameLayout);
		setTitle("Help");
		
		
		  textView = (TextView) findViewById(R.id.tv);
	        String htmlText = "<p> On app's first launch enter the credentials and create a pattern lock.  </p> <p> When you"
	        		+ "want to send the message press the START button.<p>When in active state you can deactive the app by pressing"
	        		+ " the STOP button.</p>You then enter your 4 digit pin code and press OK to deactivate it.You only have 15 seconds to do so.  </p><p>After sending a notification arises from where you can reply if the earlier message was fake or error</p> <p>"
	        		+ "Swipe right side to see Other options.You can add and delete contacts and email contacts.<p>You can delete a contact by pressing on the list and chooose to delete.</p> </p><p>When you click word activation "
	        		+ "you can set the word you want to activate the app.</p><p>You can also activate a decbel system where it will go off when it reaches a "
	        		+ "certain noise level.</p><p>Click change password to change your old password to a new one.You can also change old pattern lock too.</p>";
	        textView.setText(Html.fromHtml(htmlText));
	}
	
}
