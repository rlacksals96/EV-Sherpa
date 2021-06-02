package com.example.evsherpa.data;

import android.content.Context;
import android.util.Log;

import com.example.evsherpa.data.model.StationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class StationInfoParser {
    private Context context;

    public StationInfoParser(Context context) {
        this.context = context;
    }

    public String getJsonString() {
        String json = "";

        try {
            InputStream is = context.getAssets().open("sample.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Log.d("ev-sherpa", json);

        return json;
    }

    public StationInfo[] Parse(String jsonStr) {

        StationInfo[] stationInfos = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("item");

            stationInfos = new StationInfo[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                Log.d("ev-sherpa", object.toString());

                StationInfo stationInfo = new StationInfo();
                stationInfo.setStatNm(object.getString("statNm"));
                stationInfo.setStatId(object.getString("statId"));
                stationInfo.setChgerId(Integer.parseInt(object.getString("chgerId")));
                stationInfo.setChgerType(Integer.parseInt(object.getString("chgerType")));
                stationInfo.setAddr(object.getString("addr"));
                stationInfo.setLat(Float.parseFloat(object.getString("lat")));
                stationInfo.setLng(Float.parseFloat(object.getString("lng")));
                stationInfo.setUseTime(object.getString("useTime"));
                stationInfo.setUseTime(object.getString("useTime"));
                stationInfo.setBusiId(object.getString("busiId"));
                stationInfo.setBusiNm(object.getString("busiNm"));
                stationInfo.setBusiCall(object.getString("busiCall"));
                stationInfo.setZcode(Integer.parseInt(object.getString("zcode")));
                stationInfo.setParkingFree(object.getString("parkingFree"));

                stationInfos[i] = stationInfo;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stationInfos;
    }
}
