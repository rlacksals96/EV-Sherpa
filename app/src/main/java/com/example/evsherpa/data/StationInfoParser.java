package com.example.evsherpa.data;

import android.content.Context;
import android.util.Log;

import com.example.evsherpa.data.model.ChargerInfo;
import com.example.evsherpa.data.model.StationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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

        //Log.d("ev-sherpa", json);

        return json;
    }

    public HashMap<String, StationInfo> Parse(String jsonStr) {

        HashMap<String, StationInfo> stationInfoHashMap = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("item");

            stationInfoHashMap = new HashMap<String, StationInfo>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                //Log.d("ev-sherpa", object.toString());

                String statId = object.getString("statId");

                // 이미 있는 경우 충전기 정보만 추가
                if(stationInfoHashMap.containsKey(statId)) {
                    StationInfo stationInfo = stationInfoHashMap.get(statId);
                    stationInfo.addChargerInfo(new ChargerInfo(Integer.parseInt(object.getString("chgerId")), Integer.parseInt(object.getString("chgerType"))));
                }

                // 없는 경우 새롭게 충전소 정보 추가
                else {
                    StationInfo stationInfo = new StationInfo();
                    stationInfo.setStatNm(object.getString("statNm"));
                    stationInfo.setStatId(statId);
                    stationInfo.addChargerInfo(new ChargerInfo(Integer.parseInt(object.getString("chgerId")), Integer.parseInt(object.getString("chgerType"))));
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

                    stationInfoHashMap.put(statId, stationInfo);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stationInfoHashMap;
    }
}
