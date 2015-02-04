package com.airquality.data;

import java.util.List;

public class CityList {
	
	private List<CityItem> cityList;

	public List<CityItem> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityItem> cityList) {
		this.cityList = cityList;
	}
	
	public void addCity(CityItem city, int index) {
		cityList.add(index, city);
	}
	
	public void removeCity(int index) {
		cityList.remove(index);
	}
	
	public CityItem getCity(int index) {
		return cityList.get(index);
	}
}
