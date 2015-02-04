package com.airquality.cityData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCity {

	private static Map<String, String> cpMap = new HashMap<String, String>();

	public static Map<String, String> getCityMap(List<Map<String, Object>> provinceList) {
		for (Map<String, Object> map : provinceList) {
			String provinceName = map.get("provinceName").toString();
			if (map.containsKey("cityItems")) {
				String cityJson = map.get("cityItems").toString();
				List<Map<String, String>> result = Parse.parseCity(cityJson);
				buildMap(result, provinceName);
			} else {
				buildMap(null, provinceName);
			}
		}
		
		return cpMap;

	}

	private static void buildMap(List<Map<String, String>> cityList,
			String provinceName) {

		if (cityList == null) {
			String cityName = provinceName;
			if (cityName.contains("市")){
				cityName = cityName.replace("市", "");
			}
			cpMap.put(cityName, provinceName);
		} else {
			for (int i = 0; i < cityList.size(); i++) {
				Map<String, String> cityMap = cityList.get(i);
				String cityName = cityMap.get("cityName");
				if (cityName.contains("市")){
					cityName = cityName.replace("市", "");
				}
				cpMap.put(cityName, provinceName);
			}
		}

	}

}
