package com.airquality.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by lry on 2014/11/28.
 */
public class RankingDataSource {

    private static final String PREFRANKING = "RANKING";
    private static final String KEY = "key";
    private SharedPreferences rankingPrefs;

    public RankingDataSource(Context context) {
        rankingPrefs = context
                .getSharedPreferences(PREFRANKING, Context.MODE_PRIVATE);
    }

    public RankingList getRankingList() {
        Gson gson = new Gson();
        String json = rankingPrefs.getString(KEY,"");
        RankingList rankingList = gson.fromJson(json, RankingList.class);
        return rankingList;
    }

    public boolean update(RankingList rankingList) {
        SharedPreferences.Editor editor = rankingPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(rankingList);
        editor.putString(KEY, json);
        editor.commit();
        return true;
    }
}
