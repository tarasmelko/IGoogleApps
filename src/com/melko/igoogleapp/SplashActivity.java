package com.melko.igoogleapp;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.melko.igoogleapp.utils.Preference;

public class SplashActivity extends BaseActivity {
	private Timer mSplashTime;
	String PROJECT_NUMBER = "1059048236175";
	GoogleCloudMessaging gcm;
	String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_activity);
		getRegId();
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.melko.igoogleapp", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

		mSplashTime = new Timer();

		TimerTask launchMainActivity = new TimerTask() {

			@Override
			public void run() {
				if (!Preference.getUserId().equals("")) {
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(SplashActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};

		mSplashTime.schedule(launchMainActivity, 3000);
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	public void getRegId() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(getApplicationContext());
					}
					if (gcm.register(PROJECT_NUMBER) != null)
						regid = gcm.register(PROJECT_NUMBER);
					msg = "Device registered, registration ID=" + regid;
					Log.e("GCM", msg);

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();

				}
				return msg;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

			}

			@Override
			protected void onPostExecute(String msg) {
				Preference.saveUserRegistrationId(regid);
			}
		}.execute(null, null, null);
	}
}
