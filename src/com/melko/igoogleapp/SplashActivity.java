package com.melko.igoogleapp;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
	private Timer mSplashTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_activity);

		Typeface cool = Typeface.createFromAsset(getAssets(), "fonts/cool.ttf");
		((TextView) findViewById(R.id.igoogle)).setTypeface(cool);

		mSplashTime = new Timer();

		TimerTask launchMainActivity = new TimerTask() {

			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP
						| Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				overridePendingTransition(R.anim.push_up_in,
						R.anim.push_down_out);
				startActivity(intent);
			}
		};

		mSplashTime.schedule(launchMainActivity, 3000);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
