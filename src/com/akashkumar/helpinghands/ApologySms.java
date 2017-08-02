package com.akashkumar.helpinghands;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class ApologySms extends Service {
	private Intent sms;
	private String message;
	public static final int NOTIFICATION_ID = 234;

	@Override
	public void onStart(Intent intent, int startid) {

		super.onStart(intent, startid);
		sendSMS(getString(R.string.apology_message));

	}

	private void sendSMS(String message) {
		Database db = new Database(this);

		Cursor cursor = db.getNumbers();
		db.onStop();
		Log.e("message", message);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String phoneNumber = cursor.getString(cursor.getColumnIndex("number"));
				Log.e("number", phoneNumber);
				SmsManager sms = SmsManager.getDefault();
				sms.sendTextMessage(phoneNumber, null, message, null, null);
				Toast.makeText(getApplicationContext(), "Information sent", 5000).show();
				createNotification();

			}

		}
	}

	private void createNotification() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, CameraView.class);

		TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
		taskStackBuilder.addParentStack(CameraView.class);
		taskStackBuilder.addNextIntent(intent);

		PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification = new Notification.Builder(this).setSmallIcon(R.drawable.banner)
				.setContentTitle(getString(R.string.app_name)).setContentText("Apology message has been sent").setAutoCancel(true)
				.setPriority(Notification.PRIORITY_MAX).setDefaults(Notification.DEFAULT_VIBRATE)
				.setContentIntent(pendingIntent).build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);

	}

	public void onDestroy() {
		super.onDestroy();
		try {
			this.stopService(sms);
		} catch (Exception e) {
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
