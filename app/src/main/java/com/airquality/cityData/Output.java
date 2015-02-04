package com.airquality.cityData;

import android.content.Context;
import android.util.Log;

import com.airquality.tools.JsonTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Output {

    private Context context;

    public Output(Context context) {
        this.context = context;
    }

    public Map<String, String> getCityMap() {
        String file = ReadIn.read(context, "cities.json");
        Map<String, List<Map<String, Object>>> result = Parse.parseJson(file);
        List<Map<String, Object>> provinceList = result.get("provinceItems");

        return GetCity.getCityMap(provinceList);
    }

    public List<String> getDetectedCityList() {
        String file = ReadIn.read(context, "detected_cities.txt");
        Map<String,List<String>> result = JsonTools.getListData(file);
        return result.get("cities");
    }

    public Map<String, List<String>> getReverseCityMap() {
        Map<String, List<String>> reverseCityMap = new HashMap<String, List<String>>();
        Map<String, String> cityMap = getCityMap();
        List<String> detectedCities = getDetectedCityList();
        for (String city:detectedCities) {
            if (cityMap.containsKey(city)) {
                if (reverseCityMap.containsKey(cityMap.get(city))) {
                    reverseCityMap.get(cityMap.get(city)).add(city);
                } else {
                    List<String> cityArray = new ArrayList<String>();
                    cityArray.add(city);
                    reverseCityMap.put(cityMap.get(city), cityArray);
                }
            }
        }
        return reverseCityMap;
    }

}
