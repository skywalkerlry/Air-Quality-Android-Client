package com.airquality.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.SherlockActivity;
import com.airquality.R;
import com.airquality.cityData.Output;
import com.airquality.data.CityDataSource;
import com.airquality.data.CityItem;
import com.airquality.data.CityList;
import com.airquality.data.CityMapClass;
import com.airquality.data.RankingDataSource;
import com.airquality.data.RankingList;
import com.airquality.domain.AqiDetails;
import com.airquality.tools.JsonTools;
import com.airquality.ui.AQViewPager;
import com.airquality.ui.MainAdapter;
import com.airquality.url.LogInfo;

//import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
//import android.view.Menu;
//import android.view.MenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.airquality.ui.AQViewPager.TransitionEffect;
import com.airquality.utils.HttpUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;

public class MainActivity extends SherlockActivity {

	private AQViewPager mJazzy;
    List<Map<String, String>> dataList;
    RankingDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        dataSource = new RankingDataSource(this);
        try {
            RankingList rl = dataSource.getRankingList();
            dataList = rl.getRankingList();
        } catch (Exception e) {
            dataList = null;
        }
        if (!checkNetworkAvailability())
            Toast.makeText(getApplication(), "网络不给力，请检查网络设置", Toast.LENGTH_LONG).show();
        else {
            if (isFirstSetup()) {
                callWelcomeDialog();
                saveCityMapPrefs();
                displayNewData();
            } else {
                if (dataList != null) {
                    setupJazziness(TransitionEffect.CubeOut);
                    if (needUpdate()) {
                        displayNewData();
                    }

                }
                else
                    displayNewData();
            }
        }
	}

    private boolean needUpdate() {
        SharedPreferences mPreferences = getSharedPreferences("TIMESTAMP", this.MODE_PRIVATE);
        if (mPreferences.contains("day") && mPreferences.contains("hour")) {
            int day = mPreferences.getInt("day", 0);
            int hour = mPreferences.getInt("hour", 0);
            Calendar c = Calendar.getInstance();
            if (day==c.get(Calendar.DATE) && hour==c.get(Calendar.HOUR))
                return false;
            else
                return true;

        }
        return true;
    }

    private boolean checkNetworkAvailability() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo(); // 获取代表联网状态的NetWorkInfo对象
        return (info != null && info.isConnected());
    }

    public void callWelcomeDialog() {
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("欢迎使用")
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessage("感谢PM25.in提供的专业数据" +
                        "\n\n" +
                        "说明：" + "\n\n" +
                        "   按 + 添加关注城市\n")
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#FF04dea1")
                .withIcon(getResources().getDrawable(R.drawable.ic_launcher))
                .withDuration(700)
                .withEffect(Effectstype.Newspager)
                .withButton1Text("OK")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                //.withButton2Text("Cancel")
                .isCancelableOnTouchOutside(true)
                .show();
    }

    private boolean isFirstSetup() {
        boolean first;
        SharedPreferences mPreferences = getSharedPreferences("FIRST", this.MODE_PRIVATE);
        if (mPreferences.contains("firstSetup"))
            first = false;
        else {
            first = true;
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean("firstSetup", true);
            editor.commit();
            createCityList();
            createRankingList();
        }
        return first;
    }

    public void createCityList() {
        CityList cityList = new CityList();
        cityList.setCityList(new ArrayList<CityItem>());
        CityDataSource cityDataSource = new CityDataSource(this);
        cityDataSource.update(cityList);
    }

    public void createRankingList() {
        RankingList rankingList = new RankingList();
        rankingList.setRankingList(new ArrayList<Map<String, String>>());
        dataSource.update(rankingList);
    }

    private void saveCityMapPrefs() {
        SharedPreferences mPreferences = getSharedPreferences("CITYMAP", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Output output = new Output(MainActivity.this);
        Map<String, List<String>>cityMap = output.getReverseCityMap();
        CityMapClass cityMapClass = new CityMapClass();
        cityMapClass.setCityMap(cityMap);
        Gson gson = new Gson();
        String json = gson.toJson(cityMapClass);
        editor.putString("city", json);
        editor.commit();
    }

    public void getConnection() {
        String query = LogInfo.ALL_CITIES_RANKING + "?token=" + LogInfo.PRIVATEAPPKEY;
        String jsonString = HttpUtils.getJsonContent(query);
        if (jsonString != null) {
            dataList = JsonTools.getMapData(jsonString);
   //         Log.i("new dataList", dataList.toString());
            RankingList rl = new RankingList();
            rl.setRankingList(dataList);
            dataSource.update(rl);
            saveCurrentTime();
        }
    }

    private void saveCurrentTime() {
        SharedPreferences mPreferences = getSharedPreferences("TIMESTAMP", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Calendar c = Calendar.getInstance();
        editor.putInt("day", c.get(Calendar.DATE));
        editor.putInt("hour", c.get(Calendar.HOUR));
        editor.commit();
    }

    public void displayNewData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getConnection();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupJazziness(TransitionEffect.CubeOut);
                    }
                });
            }
        }).start();
    }

    @Override
	public boolean onCreateOptionsMenu (Menu menu){
        getSherlock().getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_city) {
            Intent intent = new Intent(this, CityListActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_refresh) {
            displayNewData();
        }

		return true;
	}

    @Override
    protected void onRestart() {
        super.onRestart();
        if (dataList==null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getConnection();
                }
            });
        } else {
            setupJazziness(TransitionEffect.CubeOut);
            if (needUpdate()) {
                displayNewData();
            }
        }
    }

    private void setupJazziness(TransitionEffect effect) {
		mJazzy = (AQViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter(MainActivity.this, mJazzy, dataList));
		mJazzy.setPageMargin(30);
	}

}
