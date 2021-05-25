package com.example.evsherpa.ui.profile;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateNicknameRequest extends StringRequest {

    final static private String ipAddress="localhost";
    final static private String portNum="8080";
    final static private String route="/account/update/nickname";
    final static private String URL="http://"+ipAddress+":"+portNum+route;
    private final Map<String,String> map;

    public UpdateNicknameRequest(String email,String nickname, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("nickname",nickname);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }
}