package com.melko.igoogleapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.melko.igoogleapp.IGoogleAppApplication;

public class Preference {

	private static final String TAG = "Preference";
	private static final String PREF = "miniswap";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "name";
	private static final String USER_LAST_NAME = "last_name";
	private static final String USER_EMAIL = "email";
	private static final String USER_PHOTO_URL = "photo_url";
	private static final String USER_FACEBOOK_ID = "facebook_id";
	private static final String USER_GCM_ID = "gcm_id";

	private static final String SEARCH_DISTANCE = "search_dis";

	private static final String APP_VERSION = "app_version";

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

	public synchronized static void saveUserName(String name) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_NAME, name);
		editor.commit();
	}

	public synchronized static String getUserName() {
		return getSharedPreferences().getString(USER_NAME, "");
	}

	public synchronized static void saveUserLastName(String lastname) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_LAST_NAME, lastname);
		editor.commit();
	}

	public synchronized static String getUserLstName() {
		return getSharedPreferences().getString(USER_LAST_NAME, "");
	}

	public synchronized static void saveUserEmail(String email) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_EMAIL, email);
		editor.commit();
	}

	public synchronized static String getUserEmail() {
		return getSharedPreferences().getString(USER_EMAIL, "");
	}

	public synchronized static void saveUserPhotoUrl(String url) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_PHOTO_URL, url);
		editor.commit();
	}

	public synchronized static String getUserPhotoUrl() {
		return getSharedPreferences().getString(USER_PHOTO_URL, "");
	}

	public synchronized static void saveUserFacebookId(String id) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_FACEBOOK_ID, id);
		editor.commit();
	}

	public synchronized static String getUserFacebookId() {
		return getSharedPreferences().getString(USER_FACEBOOK_ID, "");
	}

	public synchronized static void saveUserGCMId(String id) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_GCM_ID, id);
		editor.commit();
	}

	public synchronized static String getUserGcmId() {
		return getSharedPreferences().getString(USER_GCM_ID, "");
	}

	public synchronized static void saveSearchDistance(String distance) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(SEARCH_DISTANCE, distance);
		editor.commit();
	}

	public synchronized static String getSearchDistance() {
		return getSharedPreferences().getString(SEARCH_DISTANCE, "500");
	}

}
