package com.melko.igoogleapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.melko.igoogleapp.utils.Preference;

public class SplashActivity extends BaseActivity {
	private Timer mSplashTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_activity);

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
}
