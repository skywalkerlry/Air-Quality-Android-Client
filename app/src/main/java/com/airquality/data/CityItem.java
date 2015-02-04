package com.airquality.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityItem {
	
	private String cityName;
	private List<Map<String, Double>> aqiHour;
	private List<Map<String, Double>> aqiDay;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<Map<String, Double>> getAqiHour() {
		return aqiHour;
	}
	public void setAqiHour(List<Map<String, Double>> aqiHour) {
		this.aqiHour = aqiHour;
	}
	public List<Map<String, Double>> getAqiDay() {
		return aqiDay;
	}
	public void setAqiDay(List<Map<String, Double>> aqiDay) {
		this.aqiDay = aqiDay;
	}
	
	public void updateHourData(String time, double aqi) {
		Map<String, Double> dataMap = new HashMap<String, Double>();
		dataMap.put(time, aqi);
		this.aqiHour.add(dataMap);
	}
	
	public void updateDayData(String time, double aqi) {
		Map<String, Double> dataMap = new HashMap<String, Double>();
		dataMap.put(time, aqi);
		this.aqiDay.add(dataMap);
	}
	
	public void removeHourData() {
		this.aqiHour.remove(0);
	}
	
	public void removeDayData() {
		this.aqiDay.remove(0);
	}

}
