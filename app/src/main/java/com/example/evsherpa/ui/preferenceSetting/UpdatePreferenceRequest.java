package com.example.evsherpa.ui.preferenceSetting;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePreferenceRequest extends StringRequest {
    final static private String ipAddress="localhost";
    final static private String portNum="8080";
    final static private String route="/account/update/preference";
    final static private String URL="http://"+ipAddress+":"+portNum+route;

    private final Map<String,String> map;

    public UpdatePreferenceRequest(String email,String preferences,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("preferences",preferences);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
