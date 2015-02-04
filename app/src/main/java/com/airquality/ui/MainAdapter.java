package com.airquality.ui;

import java.util.List;
import java.util.Map;

import com.airquality.data.CityDataSource;
import com.airquality.data.CityItem;
import com.airquality.data.CityList;
import com.airquality.dialChart.DialChart03View;
import com.airquality.R;


//import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MainAdapter extends PagerAdapter {
	
	AQViewPager mJazzy;
    Context context;
    List<Map<String,String>> dataList;
    static float UPBOUND = 300;

	public MainAdapter(Context context, AQViewPager mJazzy, List<Map<String,String>> aqiDataList) {
		// TODO Auto-generated constructor stub
		this.mJazzy = mJazzy;
		this.context = context;
		this.dataList = aqiDataList;
	}

    public String getCityOnPage(int position) {
        try {
            CityDataSource dataSource = new CityDataSource(context);
            CityList cl = dataSource.getCityList();
            CityItem ci = cl.getCityList().get(position);
            return ci.getCityName();
        } catch (Exception e) {
            return null;
        }
     }

    public Map<String, String> getCityData(String city) {
        if (dataList!=null) {
            for (Map<String,String> cityItem:dataList) {
                if (cityItem.get("area").equals(city)){
                    return cityItem;
                }
            }
        }
        return null;
    }

    public float getAqiofCity(Map<String,String> cityItem) {
        return Float.parseFloat(cityItem.get("aqi"));
    }

    public int getRankofCity(String city) {
        if (dataList!=null) {
            for (int i=0; i<dataList.size(); i++) {
                Map<String, String> cityItem = dataList.get(i);
                if (cityItem.get("area").equals(city)){
                    return i+1;
                }
            }
        }
        return 0;
    }

    String getLevel(float aqi) {
        if (0<aqi && aqi<=50) {
            return "优";
        } else if (50<aqi && aqi<=100) {
            return "良";
        } else if (100<aqi && aqi<=150) {
            return "轻度污染";
        } else if (150<aqi && aqi<=200) {
            return "中度污染";
        } else if (200<aqi && aqi<=300) {
            return "重度污染";
        } else
            return "此表已爆";
    }

    public int setBackgroundColor(float aqi) {
        if (0<aqi && aqi<=50) {
            return R.color.good;
        } else if (50<aqi && aqi<=100) {
            return R.color.nice;
        } else if (100<aqi && aqi<=150) {
            return R.color.minor;
        } else if (150<aqi && aqi<=200) {
            return R.color.mediate;
        } else if (200<aqi && aqi<=300) {
            return R.color.bad;
        } else
            return R.color.overflow;
    }
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
        float percentage;
		LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.pager_layout, null);
		TextView city = (TextView)view.findViewById(R.id.city_name);
        TextView ranking = (TextView)view.findViewById(R.id.city_ranking);
        if (getCityOnPage(position)!=null) {
            String cityName = getCityOnPage(position);
            city.setText(cityName);
            Map<String,String> cityItem = getCityData(cityName);
            float aqi = getAqiofCity(cityItem);
            int rank = getRankofCity(cityName);
            ranking.setText("全国城市排名："+Integer.toString(rank)+"/190");
            DialChart03View chart = (DialChart03View)view.findViewById(R.id.chart_view);
            if (aqi>300) {
                percentage = 1;
            } else
                percentage = aqi/UPBOUND;
            int color = setBackgroundColor(aqi);
            String cs = Integer.toHexString(context.getResources().getColor(color));
            chart.setCurrentStatus(percentage, getLevel(aqi), cs);
            view.setBackgroundResource(color);
        }

		container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mJazzy.setObjectForPosition(view, position);
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object obj) {
		container.removeView(mJazzy.findViewFromObject(position));
	}
	@Override
	public int getCount() {
        CityDataSource dataSource = new CityDataSource(context);
        CityList cl = dataSource.getCityList();
		return cl.getCityList().size();
	}
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		if (view instanceof OutlineContainer) {
			return ((OutlineContainer) view).getChildAt(0) == obj;
		} else {
			return view == obj;
		}
	}
}