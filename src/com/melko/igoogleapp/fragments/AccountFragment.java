package com.melko.igoogleapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melko.igoogleapp.R;
import com.melko.igoogleapp.utils.LoadImage;
import com.melko.igoogleapp.utils.NetworkUtil;
import com.parse.ParseUser;

public class AccountFragment extends Fragment {

	private String username;
	private String email;
	private String gender;
	private String photo_url;
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.account_fragment, container, false);

		if (NetworkUtil.isNetworkAvaible(getActivity()))
			new LoadDataClass().execute();

		return mView;
	}

	private void getData() {
		if (ParseUser.getCurrentUser() != null) {
			username = ParseUser.getCurrentUser().get("firstname") != null
					&& ParseUser.getCurrentUser().get("lastname") != null ? ParseUser
					.getCurrentUser().get("firstname").toString()
					+ " "
					+ ParseUser.getCurrentUser().get("lastname").toString()
					: "None";
			gender = ParseUser.getCurrentUser().get("gender") != null ? ParseUser
					.getCurrentUser().get("gender").toString()
					: "None";
			email = ParseUser.getCurrentUser().get("email") != null ? ParseUser
					.getCurrentUser().get("email").toString() : "None";
			photo_url = ParseUser.getCurrentUser().get("photo_url") != null ? ParseUser
					.getCurrentUser().get("photo_url").toString()
					: "";

		} else {
			Log.e("USER", "IS NULL");
		}
	}

	private class LoadDataClass extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// ((MainActivity) getActivity()).showVideoProgress();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			getData();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			LoadImage loader = new LoadImage(getActivity());
			loader.loadImageRoundedCache(photo_url,
					((ImageView) mView.findViewById(R.id.usericon)), 150);
			((TextView) mView.findViewById(R.id.usermane)).setText(username
					+ "");
			((TextView) mView.findViewById(R.id.gendertype)).setText(gender
					+ "");
			((TextView) mView.findViewById(R.id.email)).setText(email);
			// ((MainActivity) getActivity()).stopVideoProgress();
			super.onPostExecute(result);
		}

	}
}
