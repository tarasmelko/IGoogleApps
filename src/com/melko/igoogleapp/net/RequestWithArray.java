package com.melko.igoogleapp.net;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

public class RequestWithArray extends JsonRequest<JSONArray> {

	private static final String TAG = "RequestWithHeader";

	public RequestWithArray(String body, Map<String, String> params,
			int method, String url, Response.Listener<JSONArray> listener,
			Response.ErrorListener errorListener) {
		super(method, url, body, listener, errorListener);
		Log.i(TAG, "constructor call : " + method);
	}

	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONArray(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

}
