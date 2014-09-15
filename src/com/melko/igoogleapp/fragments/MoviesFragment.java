package com.melko.igoogleapp.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.melko.igoogleapp.R;
import com.melko.igoogleapp.adapters.MoviesGridAdapter;
import com.melko.igoogleapp.models.Movie;
import com.melko.igoogleapp.utils.Preference;

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
		// if (NetworkUtil.isNetworkAvaible(getActivity()))
		// getData();

		Log.e("FILMS", Preference.getUserFilms());
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

}
