package com.melko.igoogleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.melko.igoogleapp.utils.Constants;
import com.melko.igoogleapp.utils.LoadImage;

public class DescriptionActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description_fragment);

		((TextView) findViewById(R.id.movie_name)).setText(getIntent()
				.getExtras().getString(Constants.NAME));
		((TextView) findViewById(R.id.description_text)).setText(getIntent()
				.getExtras().getString(Constants.DESCRIPTION));

		LoadImage loader = new LoadImage(this);
		loader.loadImageAlphaCache(
				getIntent().getExtras().getString(Constants.ICON),
				((ImageView) findViewById(R.id.movie_icon)));

		findViewById(R.id.watch_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent movie = new Intent(DescriptionActivity.this,
						VideoStreamActivity.class);
				movie.putExtra(Constants.MEDIA, getIntent().getExtras()
						.getString(Constants.MEDIA));
				movie.putExtra(Constants.NAME, getIntent().getExtras()
						.getString(Constants.NAME));
				movie.putExtra(Constants.ICON, getIntent().getExtras()
						.getString(Constants.ICON));
				startActivity(movie);

			}
		});
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
