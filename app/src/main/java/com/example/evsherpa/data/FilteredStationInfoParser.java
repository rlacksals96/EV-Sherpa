package com.example.evsherpa.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

public class FilteredStationInfoParser {
    private Context context;

    public FilteredStationInfoParser(Context context) {
        this.context = context;
    }

    public ArrayList<String> Parse(String jsonStr) {
        ArrayList<String> list = new ArrayList<String>();

        try {
            jsonStr = jsonStr.replace("\\","");
            jsonStr = jsonStr.replace("]\"","]");
            jsonStr = jsonStr.replace("\"[","[");

            Log.i("ev-sherpa", jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }
}
