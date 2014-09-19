package com.melko.igoogleapp.net;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.melko.igoogleapp.utils.Preference;

public class WebRequest {

	public static final String ACTION_SIGNIN = "http://igoogleapps.com/signup_api.php?key=dt3dBjv1pVz2LTI6Arf1zTnw";

	private RequestQueue mQueue;

	public WebRequest(Activity activity) {
		mQueue = Volley.newRequestQueue(activity);

	}

	public void send(RequestWithParam request) {
		mQueue.add(request);
	}

	public void sendWithArray(RequestWithArray request) {
		mQueue.add(request);
	}

	public void login(String firstname, String lastname, String gender,
			String iconURL, String email, Response.Listener<String> listener,
			Response.ErrorListener error) {

		Map<String, String> mParams = new HashMap<String, String>();
		mParams.put("name", firstname);
		mParams.put("lastname", lastname);
		mParams.put("gender", gender);
		mParams.put("picture", iconURL);
		mParams.put("email", email);
		mParams.put("registration_id", Preference.getRegistrationId());
		StringPostRequest reqeust = new StringPostRequest(Request.Method.POST,
				ACTION_SIGNIN, listener, error, mParams);
		mQueue.add(reqeust);
	}

}
