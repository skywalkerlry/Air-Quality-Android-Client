package com.airquality.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.airquality.domain.AqiDetails;

public class DataListToObj {
	
	public static List<AqiDetails> getDataList(List<Map<String, Object>> dataList) {
		List<AqiDetails> list = new ArrayList<AqiDetails>();
		for (Map<String, Object> map : dataList){
			AqiDetails dataItem = new AqiDetails();
			if (map.get("aqi") != null)
				dataItem.setAqi(Double.parseDouble(map.get("aqi").toString()));
			if (map.get("area") != null)
				dataItem.setArea(map.get("area").toString());
			if (map.get("co") != null)
				dataItem.setCo(Double.parseDouble(map.get("co").toString()));
			if (map.get("co_24h") != null)
				dataItem.setCo_24h(Double.parseDouble(map.get("co_24h").toString()));
			if (map.get("no2") != null)
				dataItem.setNo2(Double.parseDouble(map.get("no2").toString()));
			if (map.get("no2_24h") != null)
				dataItem.setNo2_24h(Double.parseDouble(map.get("no2_24h").toString()));
			if (map.get("o3") != null)
				dataItem.setO3(Double.parseDouble(map.get("o3").toString()));
			if (map.get("o3_24h") != null)
				dataItem.setO3_24h(Double.parseDouble(map.get("o3_24h").toString()));
			if (map.get("o3_8h") != null)
				dataItem.setO3_8h(Double.parseDouble(map.get("o3_8h").toString()));
			if (map.get("o3_8h_24h") != null)
				dataItem.setO3_8h_24h(Double.parseDouble(map.get("o3_8h_24h").toString()));
			if (map.get("pm10") != null)
				dataItem.setPm10(Double.parseDouble(map.get("pm10").toString()));
			if (map.get("pm10_24h") != null)
				dataItem.setPm10_24h(Double.parseDouble(map.get("pm10_24h").toString()));
			if (map.get("pm2_5") != null)
				dataItem.setPm2_5(Double.parseDouble(map.get("pm2_5").toString()));
			if (map.get("pm2_5_24h") != null)
				dataItem.setPm2_5_24h(Double.parseDouble(map.get("pm2_5_24h").toString()));
			if (map.get("position_name") != null)
				dataItem.setPosition_name(map.get("position_name").toString());
			if (map.get("primary_pollutant") != null)
				dataItem.setPrimary_pollutant(map.get("primary_pollutant").toString());
			if (map.get("quality") != null)
				dataItem.setQuality(map.get("quality").toString());
			if (map.get("so2") != null)
				dataItem.setSo2(Double.parseDouble(map.get("so2").toString()));
			if (map.get("so2_24h") != null)
				dataItem.setSo2_24h(Double.parseDouble(map.get("so2_24h").toString()));
			if (map.get("station_code") != null)
				dataItem.setStation_code(map.get("station_code").toString());
			if (map.get("time_point") != null)
				dataItem.setTime_point(map.get("time_point").toString());
			
			list.add(dataItem);
		}
		return list;
	}
}
