package com.melko.igoogleapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melko.igoogleapp.fragments.AccountFragment;
import com.melko.igoogleapp.fragments.HomeFragment;
import com.melko.igoogleapp.fragments.MoviesFragment;
import com.melko.igoogleapp.utils.NetworkUtil;

public class MainActivity extends BaseActivity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private LinearLayout mNavMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initActionbar();
		initDrawerSlider();
		initSliderElements();
		replaceFragment(new HomeFragment());

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
	}

	@Override
	public void onBackPressed() {
		Fragment frag = getSupportFragmentManager().findFragmentById(
				R.id.container);
		if (frag instanceof HomeFragment) {
			finish();
		}
	}

	private void initActionbar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(android.R.color.transparent);

		ColorDrawable cd = new ColorDrawable(getResources().getColor(
				R.color.action_bar));
		actionBar.setBackgroundDrawable(cd);
	}

	private void initSliderElements() {
		TextView movies = (TextView) findViewById(R.id.main_movies_btn);
		movies.setOnClickListener(this);
		TextView home = (TextView) findViewById(R.id.home);
		home.setOnClickListener(this);
		TextView account = (TextView) findViewById(R.id.main_account_btn);
		account.setOnClickListener(this);
	}

	private void initDrawerSlider() {
		mNavMenu = (LinearLayout) findViewById(R.id.nav_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.slide_holder);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Fragment frag = getSupportFragmentManager().findFragmentById(
				R.id.container);
		switch (id) {
		case android.R.id.home:
			if (mDrawerToggle.isDrawerIndicatorEnabled()) {
				if (mDrawerLayout.isDrawerOpen(mNavMenu)) {
					mDrawerLayout.closeDrawer(mNavMenu);
				} else {
					mDrawerLayout.openDrawer(mNavMenu);
				}
			} else {
				onBackPressed();
			}
			break;
		case R.id.action_user:
			if (mDrawerLayout.isDrawerOpen(mNavMenu)) {
				mDrawerLayout.closeDrawer(mNavMenu);
			}
			if (!(frag instanceof AccountFragment)) {
				setBarTitle(R.string.account);
				replaceFragment(new AccountFragment());
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Fragment frag = getSupportFragmentManager().findFragmentById(
				R.id.container);
		switch (v.getId()) {

		case R.id.main_movies_btn:
			if (!(frag instanceof MoviesFragment)) {
				setBarTitle(R.string.movies_tv);
				replaceFragment(new MoviesFragment());
			}
			break;
		case R.id.home:
			if (!(frag instanceof HomeFragment)) {
				setBarTitle(R.string.app_name);
				replaceFragment(new HomeFragment());
			}
			break;
		case R.id.main_account_btn:
			if (NetworkUtil.isNetworkAvaible(MainActivity.this)) {
				clearAccountData();
				Intent loginActi = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(loginActi);
				finish();
			}
			break;
		}

		mDrawerLayout.closeDrawer(mNavMenu);
	}

	private void setBarTitle(int id) {
		getActionBar().setTitle(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bar_items, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
