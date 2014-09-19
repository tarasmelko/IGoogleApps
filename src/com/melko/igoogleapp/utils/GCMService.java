package com.melko.igoogleapp.utils;

import java.util.Iterator;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
		dumpIntent(intent);
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
						.setContentIntent(pIntent).setAutoCancel(true).setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "push.mp3")).build();
				notificationManager.notify(0, n);
			}
		});

	}
	
	public static void dumpIntent(Intent i){

	    Bundle bundle = i.getExtras();
	    if (bundle != null) {
	        Set<String> keys = bundle.keySet();
	        Iterator<String> it = keys.iterator();
	        Log.e("Dump","Dumping Intent start");
	        while (it.hasNext()) {
	            String key = it.next();
	            Log.e("Dump","[" + key + "=" + bundle.get(key)+"]");
	        }
	        Log.e("Dump","Dumping Intent end");
	    }
	}
}
