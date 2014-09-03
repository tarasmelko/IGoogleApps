package com.melko.igoogleapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by taras.melko on 8/18/14.
 */
public class NetworkUtil {

	public static boolean isNetworkAvaible(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
			ConnectionDialog dialog = new ConnectionDialog();
			dialog.show(((com.melko.igoogleapp.BaseActivity) context)
					.getSupportFragmentManager(), "INTERNET");
		}
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static boolean isNetworkAvaibleNoDialog(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
