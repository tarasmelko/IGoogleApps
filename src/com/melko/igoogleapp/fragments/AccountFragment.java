package com.melko.igoogleapp.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Session;
import com.melko.igoogleapp.LoginActivity;
import com.melko.igoogleapp.MainActivity;
import com.melko.igoogleapp.R;
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
		return mView;
	}

	public void clearAccountData() {
		Preference.saveUserId("");
		Session session = Session.getActiveSession();
		if (session != null)
			Session.getActiveSession().closeAndClearTokenInformation();
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
}
