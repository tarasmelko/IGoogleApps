package com.melko.igoogleapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.melko.igoogleapp.IGoogleAppApplication;

public class Preference {

	// private static final String TAG = "Preference";
	private static final String PREF = "igoogle";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "name";
	private static final String USER_PIC = "pic";
	private static final String USER_EMAIL = "email";
	private static final String USER_GENDER = "gender";
	private static final String USER_LASTNAME = "lastname";
	private static final String REG_ID = "reg_id";
	private static final String PASS = "pass";
	private static SharedPreferences sharedPreferences = null;

	public static SharedPreferences getSharedPreferences() {
		if (sharedPreferences == null) {
			sharedPreferences = IGoogleAppApplication.instance()
					.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	public synchronized static void saveUserPassword(String userId) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(PASS, userId);
		editor.commit();
	}

	public synchronized static String getUserPassword() {
		return getSharedPreferences().getString(PASS, "");
	}

	// id
	public synchronized static void saveUserId(String userId) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_ID, userId);
		editor.commit();
	}

	public synchronized static String getUserId() {
		return getSharedPreferences().getString(USER_ID, "");
	}

	//
	// reg id
	public synchronized static void saveUserRegistrationId(String userId) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(REG_ID, userId);
		editor.commit();
	}

	public synchronized static String getRegistrationId() {
		return getSharedPreferences().getString(REG_ID, "");
	}

	//

	public synchronized static void saveUserEmail(String email) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_EMAIL, email);
		editor.commit();
	}

	public synchronized static String getUserEmail() {
		return getSharedPreferences().getString(USER_EMAIL, "");
	}

	public synchronized static void saveUserName(String name) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_NAME, name);
		editor.commit();
	}

	public synchronized static String getUserLastName() {
		return getSharedPreferences().getString(USER_LASTNAME, "");
	}

	public synchronized static void saveUserLastName(String name) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_LASTNAME, name);
		editor.commit();
	}

	public synchronized static String getUserName() {
		return getSharedPreferences().getString(USER_NAME, "");
	}

	public synchronized static void saveUserPicture(String icon) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_PIC, icon);
		editor.commit();
	}

	public synchronized static String getUserPictue() {
		return getSharedPreferences().getString(USER_PIC, "");
	}

	public synchronized static void saveUserGender(String gender) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(USER_GENDER, gender);
		editor.commit();
	}

	public synchronized static String getUserGender() {
		return getSharedPreferences().getString(USER_GENDER, "");
	}

	public synchronized static void saveUserFilms(String films) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString("FILMS", films);
		editor.commit();
	}

	public synchronized static String getUserFilms() {
		return getSharedPreferences().getString("FILMS", "");
	}

}
