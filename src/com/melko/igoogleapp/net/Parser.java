package com.melko.igoogleapp.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Parser {

	public static List<String> feeds(JSONArray response) {

		List<String> feeds = new ArrayList<String>();
		if (response != null) {
			Type type = new TypeToken<List<String>>() {
			}.getType();
			Gson parser = new Gson();
			feeds = parser.fromJson(response.toString(), type);
		}
		return feeds;

	}

}
