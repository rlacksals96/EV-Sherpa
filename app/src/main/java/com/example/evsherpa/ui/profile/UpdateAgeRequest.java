package com.example.evsherpa.ui.profile;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateAgeRequest extends StringRequest {
    final static private String ipAddress="172.20.10.6";
    final static private String portNum="8080";
    final static private String route="/account/update/age";
    final static private String URL="http://"+ipAddress+":"+portNum+route;

    private final Map<String,String> map;

    public UpdateAgeRequest(String email, String age, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("age",age);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
