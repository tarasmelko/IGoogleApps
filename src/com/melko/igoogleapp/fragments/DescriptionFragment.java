package com.melko.igoogleapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.melko.igoogleapp.MainActivity;
import com.melko.igoogleapp.R;
import com.melko.igoogleapp.VideoStreamActivity;
import com.melko.igoogleapp.utils.Constants;
import com.melko.igoogleapp.utils.LoadImage;

public class DescriptionFragment extends Fragment implements OnClickListener {

	public static DescriptionFragment instance(String icon, String media,
			String description, String name) {
		DescriptionFragment frag = new DescriptionFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.ICON, icon);
		bundle.putString(Constants.MEDIA, media);
		bundle.putString(Constants.NAME, name);
		bundle.putString(Constants.DESCRIPTION, description);
		frag.setArguments(bundle);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.description_fragment, container,
				false);

		((TextView) mView.findViewById(R.id.movie_name)).setText(getArguments()
				.getString(Constants.NAME));
		((TextView) mView.findViewById(R.id.description_text))
				.setText(getArguments().getString(Constants.DESCRIPTION));

		LoadImage loader = new LoadImage(getActivity());
		loader.loadImageAlphaCache(getArguments().getString(Constants.ICON),
				((ImageView) mView.findViewById(R.id.movie_icon_iv)));

		mView.findViewById(R.id.watch_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent movie = new Intent(getActivity(),
								VideoStreamActivity.class);
						movie.putExtra(Constants.MEDIA, getArguments()
								.getString(Constants.MEDIA));
						movie.putExtra(Constants.NAME, getArguments()
								.getString(Constants.NAME));
						movie.putExtra(Constants.ICON, getArguments()
								.getString(Constants.ICON));
						((MainActivity) getActivity()).startActivity(movie);

					}
				});

		mView.setOnClickListener(this);

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
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
