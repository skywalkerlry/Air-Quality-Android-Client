package com.airquality.domain;

public class AqiDetails {
	private double aqi;
	private String area;
	private double co;
	private double co_24h;
	private double no2;
	private double no2_24h;
	private double o3;
	private double o3_24h;
	private double o3_8h;
	private double o3_8h_24h;
	private double pm10;
	private double pm10_24h;
	private double pm2_5;
	private double pm2_5_24h;
	private String position_name;
	private String primary_pollutant;
	private String quality;
	private String level;
	private double so2;
	private double so2_24h;
	private String station_code;
	private String time_point;

	public AqiDetails() {
		setAqi(-1);
		setArea("");
		setCo(-1);
		setCo_24h(-1);
		setLevel("");
		setNo2(-1);
		setNo2_24h(-1);
		setO3(-1);
		setO3_24h(-1);
		setO3_8h(-1);
		setO3_8h_24h(o3_8h_24h);
		setPm10(-1);
		setPm10_24h(-1);
		setPm2_5(-1);
		setPm2_5_24h(-1);
		setPosition_name("");
		setPrimary_pollutant("");
		setQuality("");
		setSo2(-1);
		setSo2_24h(-1);
		setStation_code("");
		setTime_point("");
	}

	public double getAqi() {
		return aqi;
	}

	public void setAqi(double aqi) {
		this.aqi = aqi;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public double getCo() {
		return co;
	}

	public void setCo(double co) {
		this.co = co;
	}

	public double getCo_24h() {
		return co_24h;
	}

	public void setCo_24h(double co_24h) {
		this.co_24h = co_24h;
	}

	public double getNo2() {
		return no2;
	}

	public void setNo2(double no2) {
		this.no2 = no2;
	}

	public double getNo2_24h() {
		return no2_24h;
	}

	public void setNo2_24h(double no2_24h) {
		this.no2_24h = no2_24h;
	}

	public double getO3() {
		return o3;
	}

	public void setO3(double o3) {
		this.o3 = o3;
	}

	public double getO3_24h() {
		return o3_24h;
	}

	public void setO3_24h(double o3_24h) {
		this.o3_24h = o3_24h;
	}

	public double getO3_8h() {
		return o3_8h;
	}

	public void setO3_8h(double o3_8h) {
		this.o3_8h = o3_8h;
	}

	public double getO3_8h_24h() {
		return o3_8h_24h;
	}

	public void setO3_8h_24h(double o3_8h_24h) {
		this.o3_8h_24h = o3_8h_24h;
	}

	public double getPm10() {
		return pm10;
	}

	public void setPm10(double pm10) {
		this.pm10 = pm10;
	}

	public double getPm10_24h() {
		return pm10_24h;
	}

	public void setPm10_24h(double pm10_24h) {
		this.pm10_24h = pm10_24h;
	}

	public double getPm2_5() {
		return pm2_5;
	}

	public void setPm2_5(double pm2_5) {
		this.pm2_5 = pm2_5;
	}

	public double getPm2_5_24h() {
		return pm2_5_24h;
	}

	public void setPm2_5_24h(double pm2_5_24h) {
		this.pm2_5_24h = pm2_5_24h;
	}

	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}

	public String getPrimary_pollutant() {
		return primary_pollutant;
	}

	public void setPrimary_pollutant(String primary_pollutant) {
		this.primary_pollutant = primary_pollutant;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getSo2() {
		return so2;
	}

	public void setSo2(double so2) {
		this.so2 = so2;
	}

	public double getSo2_24h() {
		return so2_24h;
	}

	public void setSo2_24h(double so2_24h) {
		this.so2_24h = so2_24h;
	}

	public String getStation_code() {
		return station_code;
	}

	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}

	public String getTime_point() {
		return time_point;
	}

	public void setTime_point(String time_point) {
		this.time_point = time_point;
	}
}
