package com.melko.igoogleapp.fragments;

import java.util.ArrayList;
import java.util.List;

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
	ArrayList<Movie> mData;
	MoviesGridAdapter adapter;
	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.movies_fragment, container, false);

		moviesGrid = (GridView) mView.findViewById(R.id.fragment_movie_gv);
		mData = new ArrayList<Movie>();
		adapter = new MoviesGridAdapter(getActivity(), mData);
		moviesGrid.setAdapter(adapter);
		if (NetworkUtil.isNetworkAvaible(getActivity()))
			getData();
		
		return mView;
	}

	private void getData() {
		showVideoProgress();
		if (ParseUser.getCurrentUser() != null) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> movies, ParseException e) {
					if (e == null) {
						if (movies.size() > 0) {
							fillList(movies);
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
		adapter.notifyDataSetChanged();
		stopVideoProgress();
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
