package com.melko.igoogleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

		// LoginButton loginBtn = (LoginButton) findViewById(R.id.loginBotton);
		Button loginEmail = (Button) findViewById(R.id.login_button);
		final EditText email = (EditText) findViewById(R.id.email);
		final EditText password = (EditText) findViewById(R.id.password);

		loginEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(email.getText().toString())
						|| TextUtils.isEmpty(password.getText().toString())) {
					Toast.makeText(LoginActivity.this, "Input your data",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					if (!isValidEmail(email.getText().toString())) {
						Toast.makeText(LoginActivity.this, "Wrong email",
								Toast.LENGTH_LONG).show();
					} else {
						loginWithEmail(email.getText().toString(), password
								.getText().toString());
					}
				}

			}
		});

	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(target)
						.matches();
	}

	private void loginWithEmail(final String email, final String password) {
		WebRequest request = new WebRequest(this);
		showProgress();
		request.loginWithEmail(email, password,
				new com.android.volley.Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						stopProgress();
						Log.e("RESPONSE", "resp" + response.toString());
						if (response != null) {
							saveEmail(response.toString(), email, password);
						}
					}
				}, new com.android.volley.Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("RESPONSE", "error" + error.toString());
						stopProgress();
					}
				});
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

	private void saveEmail(String films, String emails, String passwords) {
		Preference.saveUserFilms(films);
		Preference.saveUserEmail(emails);
		Preference.saveUserPassword(passwords);
		Intent loginActi = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(loginActi);
		finish();
	}

	public void showProgress() {
		findViewById(R.id.login_shadow_iv).setVisibility(View.VISIBLE);
		findViewById(R.id.login_pb).setVisibility(View.VISIBLE);
	}

	public void stopProgress() {
		findViewById(R.id.login_shadow_iv).setVisibility(View.GONE);
		findViewById(R.id.login_pb).setVisibility(View.GONE);
	}
}
