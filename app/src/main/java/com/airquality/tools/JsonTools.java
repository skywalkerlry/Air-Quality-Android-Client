package com.airquality.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonTools {

	public static List<Map<String, String>> getMapData(String jsonString) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, String>>>() {
					}.getType());

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

    public static Map<String,List<String>> getListData(String jsonString) {
        Map<String,List<String>> mapList = new HashMap<String, List<String>>();
        try {
            Gson gson = new Gson();
            mapList = gson.fromJson(jsonString, new TypeToken<Map<String,List<String>>>() {}.getType());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mapList;
    }

}
