package com.airquality.data;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;

public class CityDataSource {
	
	private static final String PREFCITY = "CITIES";
	private static final String KEY = "key";
	private SharedPreferences cityPrefs;
	
	public CityDataSource(Context context) {
		cityPrefs = context
				.getSharedPreferences(PREFCITY, Context.MODE_PRIVATE);
	}
	
	public CityList getCityList() {
		Gson gson = new Gson();
		String json = cityPrefs.getString(KEY,"");
		CityList cityList = gson.fromJson(json, CityList.class);
		return cityList;
	}
	
	public boolean update(CityList cityList) {
		SharedPreferences.Editor editor = cityPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(cityList);
		editor.putString(KEY, json);
		editor.commit();
		return true;
	}
}
