package com.airquality.activity;

import com.airquality.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airquality.data.CityDataSource;
import com.airquality.data.CityItem;
import com.airquality.data.CityList;
import com.airquality.data.CityMapClass;
import com.airquality.sortList.CharacterParser;
import com.airquality.sortList.ClearEditText;
import com.airquality.sortList.PinyinComparator;
import com.airquality.sortList.SideBar;
import com.airquality.sortList.SideBar.OnTouchingLetterChangedListener;
import com.airquality.sortList.SortAdapter;
import com.airquality.sortList.SortModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CityListActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar = null;
	private TextView dialog = null;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

    private Boolean cityIndex;
	private PinyinComparator pinyinComparator;

    private String[] itemList;
    private  Map<String, List<String>> cityMap;

    private CityDataSource dataSource;

    private int cityPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dataSource = new CityDataSource(this);
        cityIndex = false;
        itemList = getResources().getStringArray(R.array.date);
        loadCityMap();
        initViews();
        refreshDisplay();
    }

    public void loadCityMap() {
        SharedPreferences mPreference = getSharedPreferences("CITYMAP", MODE_PRIVATE);
        String json = mPreference.getString("city","");
        Gson gson = new Gson();
        CityMapClass cityMapClass = gson.fromJson(json, CityMapClass.class);
        cityMap = cityMapClass.getCityMap();
    }

	private void initViews() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
    	sideBar.setTextView(dialog);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
//        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

	}

    public void refreshDisplay() {
        cityPosition = -1;
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        });
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //		Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                if (!cityIndex) {
                    String provinceName = ((SortModel)adapter.getItem(position)).getName();
                    cityIndex = true;
                    List<String> cityList = cityMap.get(provinceName);
                    itemList = cityList.toArray(new String[cityList.size()]);
                    refreshDisplay();
                } else {
                    int selectedPos = 0;
                    String cityName = ((SortModel)adapter.getItem(position)).getName();
                    List<CityItem> cityItemList = dataSource.getCityList().getCityList();
                    boolean selected = false;
                    for (int i=0; i<cityItemList.size(); i++) {
                        CityItem cityInMem = cityItemList.get(i);
                        if (cityInMem.getCityName().equals(cityName)) {
                            selected = true;
                            selectedPos = i;
                            break;
                        }
                    }
                    if (!selected) {
                        CityItem cityItem = new CityItem();
                        cityItem.setCityName(cityName);
                        cityItemList.add(0, cityItem);
                        Toast.makeText(getApplication(), "已选择"+cityName, Toast.LENGTH_SHORT).show();
                    } else {
                        cityPosition = position;
                        cityItemList.remove(selectedPos);
                        Toast.makeText(getApplication(), "取消选择"+cityName, Toast.LENGTH_SHORT).show();
                    }
                    CityList cl = new CityList();
                    cl.setCityList(cityItemList);
                    dataSource.update(cl);

                    refreshDisplay();
                }

            }
        });
        SourceDateList = filledData(itemList);
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList, cityIndex, cityPosition);
        sortListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (cityIndex) {
            cityIndex = false;
            itemList = getResources().getStringArray(R.array.date);
            refreshDisplay();
        } else {
 //           Intent intent = new Intent(this, MainActivity.class);
 //           startActivity(intent);
            super.onBackPressed();
        }

    }

    private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}
