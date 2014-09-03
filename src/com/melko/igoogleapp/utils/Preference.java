package com.melko.igoogleapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.melko.igoogleapp.IGoogleAppApplication;

public class Preference {

	// private static final String TAG = "Preference";
	private static final String PREF = "igoogle";
	private static final String USER_ID = "user_id";

	private static SharedPreferences sharedPreferences = null;

	public static SharedPreferences getSharedPreferences() {
		if (sharedPreferences == null) {
			sharedPreferences = IGoogleAppApplication.instance()
					.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	public synchronized static void saveUserId(String userId) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_ID, userId);
		editor.commit();
	}

	public synchronized static String getUserId() {
		return getSharedPreferences().getString(USER_ID, "");
	}

}
