package com.melko.igoogleapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.Session;
import com.melko.igoogleapp.R;
import com.melko.igoogleapp.adapters.MoviesGridAdapter;
import com.melko.igoogleapp.models.Movie;
import com.melko.igoogleapp.net.Parser;
import com.melko.igoogleapp.net.WebRequest;
import com.melko.igoogleapp.utils.NetworkUtil;
import com.melko.igoogleapp.utils.Preference;

public class MoviesFragment extends Fragment {

	GridView moviesGrid;
	List<Movie> mData;
	MoviesGridAdapter adapter;
	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.movies_fragment, container, false);

		moviesGrid = (GridView) mView.findViewById(R.id.fragment_movie_gv);
		mData = new ArrayList<Movie>();

		if (NetworkUtil.isNetworkAvaible(getActivity()))
			getDataAgain();
		return mView;
	}

	public void showVideoProgress() {
		mView.findViewById(R.id.main_shadow_iv).setVisibility(View.VISIBLE);
		mView.findViewById(R.id.main_pb).setVisibility(View.VISIBLE);
	}

	public void stopVideoProgress() {
		mView.findViewById(R.id.main_shadow_iv).setVisibility(View.GONE);
		mView.findViewById(R.id.main_pb).setVisibility(View.GONE);
	}

	private void getDataAgain() {
		showVideoProgress();
		if (Preference.getUserPassword().equals("")) {
			WebRequest request = new WebRequest(getActivity());
			request.login(Preference.getUserName(),
					Preference.getUserLastName(), Preference.getUserGender(),
					Preference.getUserPictue(), Preference.getUserEmail(),
					new com.android.volley.Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							stopVideoProgress();

							if (response != null) {
								Preference.saveUserFilms(response);
								try {
									mData = Parser.feeds(response);
								} catch (Exception e) {
									Toast.makeText(getActivity(),
											"Server error", Toast.LENGTH_SHORT)
											.show();
								}
								adapter = new MoviesGridAdapter(getActivity(),
										mData);
								moviesGrid.setAdapter(adapter);
							}
						}
					}, new com.android.volley.Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							stopVideoProgress();
							Log.e("RESPONSE", "error" + error.toString());
						}
					});
		} else {
			WebRequest request = new WebRequest(getActivity());
			request.loginWithEmail(Preference.getUserEmail(),
					Preference.getUserPassword(),
					new com.android.volley.Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							stopVideoProgress();

							if (response != null) {
								Preference.saveUserFilms(response);
								try {
									mData = Parser.feeds(response);
								} catch (Exception e) {
									Toast.makeText(getActivity(),
											"Server error", Toast.LENGTH_SHORT)
											.show();
								}
								adapter = new MoviesGridAdapter(getActivity(),
										mData);
								moviesGrid.setAdapter(adapter);
							}
						}
					}, new com.android.volley.Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							stopVideoProgress();
						}
					});
		}
	}
}
