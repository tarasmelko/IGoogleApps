package com.melko.igoogleapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.facebook.Session;
import com.melko.igoogleapp.LoginActivity;
import com.melko.igoogleapp.MainActivity;
import com.melko.igoogleapp.R;
import com.melko.igoogleapp.net.WebRequest;
import com.melko.igoogleapp.utils.LoadImage;
import com.melko.igoogleapp.utils.NetworkUtil;
import com.melko.igoogleapp.utils.Preference;

public class AccountFragment extends Fragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.account_fragment, container, false);
		if (NetworkUtil.isNetworkAvaible(getActivity()))
			new LoadDataClass().execute();

		mView.findViewById(R.id.log_out_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (NetworkUtil.isNetworkAvaible(getActivity())) {
							clearAccountData();
							Intent loginActi = new Intent(getActivity(),
									LoginActivity.class);
							((MainActivity) getActivity())
									.startActivity(loginActi);
							((MainActivity) getActivity()).finish();
						}
					}
				});

		mView.findViewById(R.id.request_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (NetworkUtil.isNetworkAvaible(getActivity())) {
							String message = ((EditText) mView
									.findViewById(R.id.request_movies_et))
									.getText().toString();
							if (!TextUtils.isEmpty(message)) {
								requestMovie(message);
							} else {
								Toast.makeText(getActivity(),
										"Please type your movie name",
										Toast.LENGTH_LONG).show();
							}

						}
					}
				});
		return mView;
	}

	private void requestMovie(String message) {
		WebRequest request = new WebRequest(getActivity());
		request.requestMovie(message, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Log.e("RESP", arg0 + "");
				((EditText) mView.findViewById(R.id.request_movies_et))
						.setText("");
				Toast.makeText(getActivity(), "Your request has been sent",
						Toast.LENGTH_LONG).show();
				Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_LONG)
						.show();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

	public void clearAccountData() {
		Preference.saveUserId("");
		Preference.saveUserPassword("");
		Preference.saveUserName("");
		Preference.saveUserLastName("");
		Preference.saveUserGender("");
		Preference.saveUserPicture("");
		callFacebookLogout(getActivity());
	}

	@Override
	public void onResume() {
		((MainActivity) getActivity()).setBackButton();
		super.onResume();
	}

	@Override
	public void onStop() {
		((MainActivity) getActivity()).setMenu();
		super.onResume();
	}

	private class LoadDataClass extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			LoadImage loader = new LoadImage(getActivity());
			loader.loadImageRoundedCache(Preference.getUserPictue(),
					((ImageView) mView.findViewById(R.id.usericon)), 20);
			((TextView) mView.findViewById(R.id.usermane)).setText(Preference
					.getUserName() + " " + Preference.getUserLastName() + "");
			((TextView) mView.findViewById(R.id.gendertype)).setText(Preference
					.getUserGender() + "");
			((TextView) mView.findViewById(R.id.email)).setText(Preference
					.getUserEmail());
			super.onPostExecute(result);
		}

	}

	public static void callFacebookLogout(Context context) {
		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				// clear your preferences if saved
			}
		} else {

			session = new Session(context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
			// clear your preferences if saved
		}

	}
}
