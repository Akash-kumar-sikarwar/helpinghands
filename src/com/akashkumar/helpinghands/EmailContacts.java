package com.akashkumar.helpinghands;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EmailContacts extends ListActivity {
	private String[] values;
	private String itemPressed;
	private ListView listView;
	private Context context;
	private FloatingActionButton fabButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Email Contacts List");
		this.context = this;
		Database db = new Database(this);
		Cursor cursor = db.getEmailContacts();
		int i = 0;
		if (cursor.getCount() > 0) {
			this.values = new String[cursor.getCount() + 1];
			while (cursor.moveToNext()) {
				values[i] = cursor.getString(cursor.getColumnIndex("name"));
				i++;
			}

			values[i++] = getString(R.string.add_new_email_contact);

		} else {
			Toast.makeText(this, "empty", 500).show();
			String val = getResources().getString(R.string.field_is_empty);
			this.values = new String[] { val };

		}
		EmailArrayAdaptor adapter = new EmailArrayAdaptor(this, values);
		setListAdapter(adapter);

		fabButton = new FloatingActionButton.Builder(this).withDrawable(getResources().getDrawable(R.drawable.circle1))
				.withGravity(Gravity.BOTTOM | Gravity.RIGHT).withMargins(0, 0, 16, 16).create();
		fabButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent subMenuView = new Intent(EmailContacts.this, SubMenuViews.class);
				subMenuView.putExtra("buttonPressed", "Email Contacts");
				EmailContacts.this.finish();
				startActivity(subMenuView);

			}
		});
		ListView lv = getListView();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				onListItemClick(v, pos, id);
			}
		});

		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
				return onLongListItemClick(v, pos, id);
			}
		});
	}

	protected void onListItemClick(View v, int pos, long id) {
		
	}

	protected boolean onLongListItemClick(View v, int position, long id) {
		itemPressed = (String) getListAdapter().getItem(position);

		AlertDialog.Builder alert = new AlertDialog.Builder(EmailContacts.this);

		TextView view = new TextView(EmailContacts.this);
		view.setText("Do you want to delete " + itemPressed + " from Email Contacts List?");
		view.setTextSize(25);
		alert.setTitle(Html.fromHtml("<font color='#CC0000'>Delete Contact</font>"));
		alert.setView(view);
		alert.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Database db = new Database(context);
				db.deleteEmailContact(itemPressed);
				Intent i = new Intent(context, EmailContacts.class);
				EmailContacts.this.finish();
				startActivity(i);
				return;
			}

		});
		alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alert.create();
		alert.show();

		return true;
	}

		/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent settings = new Intent(context, BaseActivity.class);
			EmailContacts.this.finish();
			startActivity(settings);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// Handle "up" button behavior here.
			Intent i1 = new Intent();
			i1.setClass(context, CameraView.class);
			i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i1);
			return true;
		} else {
			// handle other items here
			return false;
		}
		// return true if you handled the button click, otherwise return false.

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		Intent i1 = new Intent();
		i1.setClass(context, CameraView.class);
		i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i1);
	}
}
