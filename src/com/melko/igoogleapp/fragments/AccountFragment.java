package com.melko.igoogleapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.melko.igoogleapp.R;
import com.melko.igoogleapp.utils.LoadImage;
import com.melko.igoogleapp.utils.Preference;

public class AccountFragment extends Fragment {

	private String userID = null;
	private String first_name = null;
	private String last_name = null;
	private String photo_url = null;
	private String email = null;
	private UiLifecycleHelper uiHelper;

	private LoginButton authButton;
	private ImageView image;
	private TextView username;
	private TextView lastname;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.account_fragment, container,
				false);

		authButton = (LoginButton) view.findViewById(R.id.authButton);
		image = (ImageView) view.findViewById(R.id.user_icon);
		username = (TextView) view.findViewById(R.id.user_name);
		lastname = (TextView) view.findViewById(R.id.user_last_name);
		authButton.setFragment(this);

		if (!Preference.getUserId().equals(""))
			fill();
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
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

	private void makeMeRequest() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			Request request = Request.newMeRequest(session,
					new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							if (user != null) {

								userID = user.getId() + "";
								first_name = user.getFirstName() + "";
								last_name = user.getLastName() + "";

								photo_url = "https://graph.facebook.com/"
										+ user.getId() + "/picture?type=large";

								// email = user.asMap().get("email") != null ?
								// user
								// .asMap().get("email").toString()
								// : "";
								Preference.saveUserId(userID);
								Preference.saveUserName(first_name);
								Preference.saveUserLastName(last_name);
								Preference.saveUserPhotoUrl(photo_url);
								fill();
							} else if (response.getError() != null) {
								Log.d("Error",
										"Error parsing returned user data.");
							}
						}

					});
			request.executeAsync();
		} else {
			Log.e("Session is null", "session is null");
		}

	}

	private void fill() {
		Log.e("load", "laod data");
		authButton.setVisibility(View.GONE);
		username.setVisibility(View.VISIBLE);
		lastname.setVisibility(View.VISIBLE);
		image.setVisibility(View.VISIBLE);
		LoadImage mLoader = new LoadImage(getActivity());
		mLoader.loadImageRoundedCache(Preference.getUserPhotoUrl(), image, 150);
		username.setText(Preference.getUserName());
		lastname.setText(Preference.getUserLstName());
	}
}
