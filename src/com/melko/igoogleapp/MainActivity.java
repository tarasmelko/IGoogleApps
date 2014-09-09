package com.melko.igoogleapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melko.igoogleapp.fragments.AccountFragment;
import com.melko.igoogleapp.fragments.DescriptionFragment;
import com.melko.igoogleapp.fragments.MoviesFragment;
import com.melko.igoogleapp.utils.NetworkUtil;

@SuppressLint("NewApi")
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
		replaceFragment(new MoviesFragment());
	}

	@Override
	public void onBackPressed() {
		Fragment frag = getSupportFragmentManager().findFragmentById(
				R.id.container);
		if (frag instanceof MoviesFragment) {
			finish();
		} else if (frag instanceof DescriptionFragment) {
			super.onBackPressed();
		} else if (frag instanceof AccountFragment) {
			super.onBackPressed();
		}
	}

	public void setBackButton() {
		((ImageView) findViewById(R.id.menu_icon))
				.setImageResource(R.drawable.back);
	}

	public void setMenu() {
		((ImageView) findViewById(R.id.menu_icon))
				.setImageResource(R.drawable.menu);
	}

	private void initActionbar() {

		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.custom_bar, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.panel));

		findViewById(R.id.menu_icon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment fragm = getSupportFragmentManager().findFragmentById(
						R.id.container);
				if (fragm instanceof DescriptionFragment) {
					onBackPressed();
					setMenu();
				} else if (fragm instanceof AccountFragment) {
					onBackPressed();
					setMenu();
				} else {
					if (mDrawerToggle.isDrawerIndicatorEnabled()) {
						if (mDrawerLayout.isDrawerOpen(mNavMenu)) {
							mDrawerLayout.closeDrawer(mNavMenu);
						} else {
							mDrawerLayout.openDrawer(mNavMenu);
						}
					} else {
						onBackPressed();
					}
				}
			}
		});

		findViewById(R.id.settings_ic).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Fragment frags = getSupportFragmentManager()
								.findFragmentById(R.id.container);
						if (mDrawerLayout.isDrawerOpen(mNavMenu)) {
							mDrawerLayout.closeDrawer(mNavMenu);
						}
						if (!(frags instanceof AccountFragment)) {
							replaceFragment(new AccountFragment());
						}
						Log.e("OnClick", "Click");
					}
				});
	}

	private void initSliderElements() {
		TextView movies = (TextView) findViewById(R.id.main_movies_btn);
		movies.setOnClickListener(this);
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
	public void onClick(View v) {
		Fragment frag = getSupportFragmentManager().findFragmentById(
				R.id.container);
		switch (v.getId()) {

		case R.id.main_movies_btn:
			if (!(frag instanceof MoviesFragment)) {
				replaceFragment(new MoviesFragment());
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

}
