package com.melko.igoogleapp.utils;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.melko.igoogleapp.R;
import com.melko.igoogleapp.SplashActivity;

/**
 * Created by taras.melko on 9/15/14.
 */
public class GCMService extends IntentService {

	String mes;
	String title;
	private Handler handler;

	public GCMService() {
		super("GCMService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();

		// GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		title = extras.getString("title");
		mes = extras.getString("message");
		showToast();
		GCMReceiver.completeWakefulIntent(intent);
	}

	public void showToast() {
		handler.post(new Runnable() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			public void run() {
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				Intent intent = new Intent(getApplicationContext(),
						SplashActivity.class);
				PendingIntent pIntent = PendingIntent.getActivity(
						getApplicationContext(), 0, intent, 0);

				Notification n = new Notification.Builder(
						getApplicationContext()).setContentTitle(title)
						.setContentText(mes)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentIntent(pIntent).setAutoCancel(true).build();
				notificationManager.notify(0, n);
			}
		});

	}
}
