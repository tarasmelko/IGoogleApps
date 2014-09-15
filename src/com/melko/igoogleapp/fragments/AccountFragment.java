package com.melko.igoogleapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melko.igoogleapp.MainActivity;
import com.melko.igoogleapp.R;
import com.melko.igoogleapp.utils.LoadImage;
import com.melko.igoogleapp.utils.NetworkUtil;

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

		// if (NetworkUtil.isNetworkAvaible(getActivity()))
		// new LoadDataClass().execute();

		return mView;
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
			// ((MainActivity) getActivity()).showVideoProgress();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			LoadImage loader = new LoadImage(getActivity());
			loader.loadImageAlphaCache(photo_url,
					((ImageView) mView.findViewById(R.id.usericon)));
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
