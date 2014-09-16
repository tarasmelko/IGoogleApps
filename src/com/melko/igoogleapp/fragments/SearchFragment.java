package com.melko.igoogleapp.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.melko.igoogleapp.R;
import com.melko.igoogleapp.adapters.MoviesGridAdapter;
import com.melko.igoogleapp.models.Movie;
import com.melko.igoogleapp.net.Parser;
import com.melko.igoogleapp.utils.Preference;

public class SearchFragment extends Fragment {

	GridView moviesGrid;
	List<Movie> mData = new ArrayList<Movie>();
	List<Movie> mFlteredData = new ArrayList<Movie>();
	MoviesGridAdapter adapter;
	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.search_fragment, container, false);

		moviesGrid = (GridView) mView.findViewById(R.id.fragment_search_gv);
		adapter = new MoviesGridAdapter(getActivity(), mFlteredData);
		moviesGrid.setAdapter(adapter);
		try {
			mData = Parser.feeds(Preference.getUserFilms());
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT)
					.show();
		}
		final EditText searchText = (EditText) mView
				.findViewById(R.id.fragemnt_search_et);
		mView.findViewById(R.id.fragemnt_search_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						fillData(searchText.getText().toString());
					}

				});

		return mView;
	}

	private void fillData(String string) {
		mFlteredData.clear();
		Pattern patern = Pattern.compile(string);
		Matcher m;
		for (Movie item : mData) {
			m = patern.matcher(item.getName());
			if (m.find())
				mFlteredData.add(item);
		}
		Log.e("filter", mFlteredData.size() + " :size");
		if (mFlteredData.size() == 0) {
			Toast.makeText(getActivity(), "Not found", Toast.LENGTH_LONG)
					.show();
		} else {
			adapter.notifyDataSetChanged();
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
