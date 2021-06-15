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

public class ChargerStatusInfoParser {
    private Context context;

    public ChargerStatusInfoParser(Context context) {
        this.context = context;
    }

    public String getJsonString() {
        String json = "";

        try {
            InputStream is = context.getAssets().open("sample_status.json");
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

    public void Parse(String jsonStr, HashMap<String, StationInfo> stationInfoHashMap) {

        try {
            /*
            jsonStr = jsonStr.replace("\\","");
            jsonStr = jsonStr.replace("]\"","]");
            jsonStr = jsonStr.replace("\"[","[");

            Log.i("ev-sherpa", jsonStr);
             */

            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("item");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String statId = object.getString("statId");
                int chargerId = Integer.parseInt(object.getString("chgerId"));
                int stat = Integer.parseInt(object.getString("stat"));
                String statUpdDt = object.getString("statUpdDt");

                ChargerInfo chargerInfo = stationInfoHashMap.get(statId).getChargerInfo(chargerId);
                chargerInfo.setStatus(stat);
                chargerInfo.setStatusUpdDt(statUpdDt);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
