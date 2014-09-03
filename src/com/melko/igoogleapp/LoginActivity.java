package com.melko.igoogleapp;

import java.util.Arrays;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.model.GraphUser;
import com.melko.igoogleapp.utils.NetworkUtil;
import com.melko.igoogleapp.utils.Preference;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseUser;

public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		Typeface cool = Typeface.createFromAsset(getAssets(), "fonts/cool.ttf");

		((TextView) findViewById(R.id.login_tv)).setTypeface(cool);

		((ImageView) findViewById(R.id.facebook_iv))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (NetworkUtil.isNetworkAvaible(LoginActivity.this))
							connect();
					}
				});

	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private void connect() {
		ParseFacebookUtils.logIn(
				Arrays.asList("email", Permissions.User.ABOUT_ME), this,
				new LogInCallback() {
					@Override
					public void done(ParseUser user, ParseException err) {
						if (user == null) {
							Log.d("FACEBOOK",
									"Uh oh. The user cancelled the Facebook login. Error : "
											+ err);
						} else if (user.isNew()) {
							makeMeRequest();
						} else {
							makeMeRequest();
						}
					}
				});

	}

	private void makeMeRequest() {
		com.facebook.Request request = com.facebook.Request.newMeRequest(
				ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user,
							com.facebook.Response response) {
						if (user != null) {

							ParseUser.getCurrentUser().put("firstname",
									user.getFirstName() + "");
							ParseUser.getCurrentUser().put("lastname",
									user.getLastName() + "");

							String picUrl = "https://graph.facebook.com/"
									+ user.getId() + "/picture?type=large";
							ParseUser.getCurrentUser().put("photo_url", picUrl);
							ParseUser.getCurrentUser().setEmail(
									user.asMap().get("email") != null ? user
											.asMap().get("email").toString()
											: "");

							if (user.asMap().get("gender").toString()
									.equals("male")) {
								ParseUser.getCurrentUser()
										.put("gender", "male");
							} else {
								ParseUser.getCurrentUser().put("gender",
										"female");
							}
							ParseUser.getCurrentUser().saveInBackground();
							saveData(user.getId().toString());
						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == com.facebook.FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == com.facebook.FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d("Error",
										"Error parsing returned user data.");
							} else {
								Log.d("Error", "Some other error: "
										+ response.getError().getErrorMessage());
							}
						}
					}

				});
		request.executeAsync();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
		if (ParseUser.getCurrentUser() != null) {
			((TextView) findViewById(R.id.login_tv)).setText("Wait...");
			((ImageView) findViewById(R.id.facebook_iv))
					.setImageResource(R.drawable.wait);
		}
	}

	private void saveData(String userId) {
		Preference.saveUserId(userId);
		Intent loginActi = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(loginActi);
		finish();
	}

}
