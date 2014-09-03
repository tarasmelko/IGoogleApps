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
		Parse.initialize(this, "CLNtgAwrmiCbbHOVfrvLskirkuNSly4XEYj8cFx8",
				"3fWg5qllrPCsiT6g5DOWUHb2XZ8YUm84CxsDNhHZ");
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
