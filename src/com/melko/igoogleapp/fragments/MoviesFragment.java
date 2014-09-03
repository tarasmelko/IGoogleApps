package com.melko.igoogleapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.melko.igoogleapp.R;
import com.melko.igoogleapp.adapters.MoviesGridAdapter;
import com.melko.igoogleapp.models.Movie;
import com.melko.igoogleapp.utils.NetworkUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MoviesFragment extends Fragment {

	GridView moviesGrid;
	ArrayList<Movie> mData = new ArrayList<Movie>();
	MoviesGridAdapter adapter;
	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.movies_fragment, container, false);

		moviesGrid = (GridView) mView.findViewById(R.id.fragment_movie_gv);
		adapter = new MoviesGridAdapter(getActivity(), mData);
		moviesGrid.setAdapter(adapter);
		if (NetworkUtil.isNetworkAvaible(getActivity()))
			new LoadDataClass().execute();

		return mView;
	}

	private class LoadDataClass extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			showVideoProgress();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			getData();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

	}

	private void getData() {
		mData.clear();
		if (ParseUser.getCurrentUser() != null) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
			query.findInBackground(new FindCallback<ParseObject>() {
				@SuppressWarnings("unchecked")
				public void done(List<ParseObject> movies, ParseException e) {
					if (e == null) {
						if (movies.size() > 0) {
							new FillDataClass().execute(movies);
						} else {
							stopVideoProgress();
						}
					} else {
						stopVideoProgress();
					}
				}

			});

		} else {
			stopVideoProgress();
		}

	}

	private void fillList(List<ParseObject> movies) {
		for (ParseObject item : movies) {
			Movie movItem = new Movie();
			movItem.setVideo_url(item.getString("video_url"));
			movItem.setName(item.getString("name"));
			movItem.setDescription(item.getString("description"));
			movItem.setIcon_url(item.getString("icon_url"));
			mData.add(movItem);
		}
	}

	private class FillDataClass extends
			AsyncTask<List<ParseObject>, String, String> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(List<ParseObject>... params) {
			fillList(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter.notifyDataSetChanged();
			stopVideoProgress();
			super.onPostExecute(result);
		}

	}

	public void showVideoProgress() {
		mView.findViewById(R.id.main_shadow_iv).setVisibility(View.VISIBLE);
		mView.findViewById(R.id.main_pb).setVisibility(View.VISIBLE);
	}

	public void stopVideoProgress() {
		mView.findViewById(R.id.main_shadow_iv).setVisibility(View.GONE);
		mView.findViewById(R.id.main_pb).setVisibility(View.GONE);
	}

}
