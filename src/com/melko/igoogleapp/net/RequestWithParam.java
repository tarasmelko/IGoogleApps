package com.melko.igoogleapp.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class RequestWithParam extends JsonObjectRequest {
	@Override
	protected Map<String, String> getPostParams() throws AuthFailureError {
		
		return super.getPostParams();
	}

	private static final String TAG = "RequestWithParam";
	private Map<String, String> mParams;

	public RequestWithParam(JSONObject requestBody, Map<String, String> params, int method, String url, Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);


		mParams = new HashMap<String, String>();
		mParams = params;
	}

	@Override
	public Map<String, String> getParams() {
		
		return mParams;
	}

}
