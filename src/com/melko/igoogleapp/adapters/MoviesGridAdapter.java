package com.melko.igoogleapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.melko.igoogleapp.MainActivity;
import com.melko.igoogleapp.R;
import com.melko.igoogleapp.fragments.DescriptionFragment;
import com.melko.igoogleapp.models.Movie;
import com.melko.igoogleapp.utils.LoadImage;

public class MoviesGridAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<Movie> mData;

	private LayoutInflater mInflater;
	private static final String NO_INFORMATION = "No information";
	private LoadImage mLoader;

	public MoviesGridAdapter(Context context, ArrayList<Movie> in) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mData = in;
		mLoader = new LoadImage(mContext);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		TextView name;
		ImageView image;
		TextView descpription;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_movie, parent, false);

			holder.name = (TextView) convertView
					.findViewById(R.id.item_movie_name_tv);
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_movie_image_iv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		final Movie item = (Movie) mData.get(position);

		mLoader.loadImageRoundedCache(item.getIcon_url(), holder.image, 10);
		holder.name.setText(item.getName() != null ? item.getName()
				: NO_INFORMATION);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) mContext).addFragment(DescriptionFragment
						.instance(item.getIcon_url(), item.getVideo_url(),
								item.getDescription(), item.getName()));

			}
		});

		return convertView;
	}
}