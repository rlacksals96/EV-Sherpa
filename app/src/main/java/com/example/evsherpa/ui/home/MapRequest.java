package com.example.evsherpa.ui.home;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MapRequest extends StringRequest {
    private final Map<String,String> map;

    public MapRequest(int method, String URL, Map<String,String> map, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, URL, listener, errorListener);
        this.map = map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
