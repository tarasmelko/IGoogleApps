package com.melko.igoogleapp;

import android.app.Application;
import android.content.Context;

import com.melko.igoogleapp.utils.Preference;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class IGoogleAppApplication extends Application {
	private static IGoogleAppApplication mApp;

	@Override
	public void onCreate() {
		mApp = this;
		Preference.getSharedPreferences();
		initImageLoader(getApplicationContext());
		Parse.initialize(this, "JP9GO2D8VFX4AWdA2YBoVfyv0C8G5dFfbMOtoiTa",
				"kzCSeedv2IX4fqvMWM6dwxZZ06AwJkHLPYlMJwbu");
		ParseFacebookUtils.initialize("274862416039469");
		super.onCreate();

	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	public static IGoogleAppApplication instance() {
		return mApp;
	}

}
