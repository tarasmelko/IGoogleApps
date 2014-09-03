package com.melko.igoogleapp;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.melko.igoogleapp.utils.Preference;

public class SplashActivity extends BaseActivity {
	private Timer mSplashTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_activity);

		Typeface cool = Typeface.createFromAsset(getAssets(), "fonts/cool.ttf");
		((TextView) findViewById(R.id.igoogle)).setTypeface(cool);

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
