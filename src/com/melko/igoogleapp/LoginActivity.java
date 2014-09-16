package com.melko.igoogleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.melko.igoogleapp.net.WebRequest;
import com.melko.igoogleapp.utils.Preference;

public class LoginActivity extends BaseActivity {
	private String userID = null;
	private String first_name = null;
	private String last_name = null;
	private String photo_url = null;
	private String email = null;
	private String gender = null;
	private UiLifecycleHelper uiHelper;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private void makeMeRequest() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			com.facebook.Request request = com.facebook.Request.newMeRequest(
					session, new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								com.facebook.Response response) {
							if (user != null) {
								userID = user.getId() + "";
								first_name = user.getFirstName() + "";
								last_name = user.getLastName() + "";

								photo_url = "https://graph.facebook.com/"
										+ user.getId() + "/picture?type=large";

								email = user.asMap().get("email") != null ? user
										.asMap().get("email").toString()
										: "";
								gender = user.asMap().get("gender").toString();
								Log.d("Face data", "name : " + first_name);

								makeLoginRequest();

							} else if (response.getError() != null) {
								if ((response.getError().getCategory() == com.facebook.FacebookRequestError.Category.AUTHENTICATION_RETRY)
										|| (response.getError().getCategory() == com.facebook.FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
									Log.d("Error",
											"Error parsing returned user data.");
								} else {
									Log.d("Error", "Some other error: "
											+ response.getError()
													.getErrorMessage());
								}
							}
						}

					});
			request.executeAsync();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		makeMeRequest();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {

	}

	private void makeLoginRequest() {
		WebRequest request = new WebRequest(this);
		request.login(first_name, last_name, gender, photo_url, email,
				new com.android.volley.Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.e("RESPONSE", "resp" + response.toString());
						if (response != null) {
							saveData(response.toString());
						}
					}
				}, new com.android.volley.Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("RESPONSE", "error" + error.toString());
						if (Session.getActiveSession().isOpened())
							Session.getActiveSession()
									.closeAndClearTokenInformation();
					}
				});
	}

	private void saveData(String films) {
		Preference.saveUserFilms(films);
		Preference.saveUserId(userID);
		Preference.saveUserEmail(email);
		Preference.saveUserGender(gender);
		Preference.saveUserName(first_name);
		Preference.saveUserLastName(last_name);
		Preference.saveUserPicture(photo_url);
		Intent loginActi = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(loginActi);
		finish();
	}

}
