package com.melko.igoogleapp.net;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
		//
		// JSONObject requestBody = new JSONObject();
		// try {
		// requestBody.putOpt("name", firstname);
		// requestBody.putOpt("lastname", lastname);
		// requestBody.putOpt("gender", gender);
		// requestBody.putOpt("picture", iconURL);
		// requestBody.putOpt("email", email);
		//
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }

		// RequestWithArray createUser = new RequestWithArray(
		// requestBody.toString(), null, Request.Method.POST,
		// ACTION_SIGNIN, listener, error);
		Map<String, String> mParams = new HashMap<String, String>();
		mParams.put("name", firstname);
		mParams.put("lastname", lastname);
		mParams.put("gender", gender);
		mParams.put("picture", iconURL);
		mParams.put("email", email);
		StringPostRequest reqeust = new StringPostRequest(Request.Method.POST,
				ACTION_SIGNIN, listener, error, mParams);
		mQueue.add(reqeust);
	}

}
