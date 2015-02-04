package com.airquality.cityData;

import java.util.List;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

public class Parse {
	public static Map<String, List<Map<String, Object>>> parseJson(
			String jsonString) {
		Map<String, List<Map<String, Object>>> result = null;
		try {
			Gson gson = new Gson();
			result = gson.fromJson(jsonString,
					new TypeToken<Map<String, List<Map<String, Object>>>>() {
					}.getType());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public static List<Map<String, String>> parseCity(String jsonString) {
		List<Map<String, String>> result = null;
		try {
			Gson gson = new Gson();
			result = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, String>>>() {
					}.getType());
			return result;

		} catch (Exception e) {

			return null;
		}
	}

}
