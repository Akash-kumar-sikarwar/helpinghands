package com.akashkumar.helpinghands;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

public class SMS extends Service {
	private String location;
	public static final int NOTIFICATION_ID = 230;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startid) {
		super.onStart(intent, startid);
		Bundle getvars = intent.getExtras();
		if (getvars != null) {
			location = getvars.getString("location");
		}
		char[] array = location.toCharArray();
		location = location.replaceAll(" ", "");
		String loc = "http://maps.google.com/?q=" + location;
		Log.e("message", location);
		sendSMS(getString(R.string.this_person_needs_your_help_they_are_at_) + loc);
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

				createNotification();
			}

		}
	}

	private void createNotification() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ApologySms.class);
		Intent actionIntent = new Intent(this, ApologySms.class);

		TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
		taskStackBuilder.addParentStack(CameraView.class);
		taskStackBuilder.addNextIntent(intent);

		PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent actionPendingIntent = PendingIntent.getService(this, 0, actionIntent,
				0);

		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
				.setContentTitle(getString(R.string.app_name)).setContentText("Alert message has been sent.")
				.setSmallIcon(R.drawable.banner).setContentIntent(pendingIntent).setDefaults(Notification.DEFAULT_SOUND)
				.setVibrate(new long[] { 100, 2000, 500, 2000 }).setLights(Color.GREEN, 400, 400)

				.addAction(R.drawable.reply, "Reply", actionPendingIntent);

		Notification notification = nBuilder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);

	}
}
