package com.melko.igoogleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.facebook.Session;
import com.melko.igoogleapp.utils.Preference;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public void replaceFragment(Fragment frag) {
		Fragment newFragment = frag;
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(R.anim.enter, R.anim.exit,
				R.anim.pop_enter, R.anim.pop_exit);
		transaction.replace(R.id.container, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void addFragment(Fragment frag) {
		Fragment newFragment = frag;
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(R.anim.enter, R.anim.exit,
				R.anim.pop_enter, R.anim.pop_exit);
		transaction.add(R.id.container, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
