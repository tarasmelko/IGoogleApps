package com.melko.igoogleapp.net;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class WebRequest {

	public static final String ACTION_SIGNIN = "http://igoogleapps.com/signup_api.php?key=dt3dBjv1pVz2LTI6Arf1zTnw";

	private RequestQueue mQueue;
	private Activity mActivity;

	public WebRequest(Activity activity) {
		mActivity = activity;
		mQueue = Volley.newRequestQueue(activity);

	}

	public void send(RequestWithParam request) {
		mQueue.add(request);
	}

	public void sendWithArray(RequestWithArray request) {
		mQueue.add(request);
	}

}
