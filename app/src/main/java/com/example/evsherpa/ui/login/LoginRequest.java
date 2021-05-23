package com.example.evsherpa.ui.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    // 서버 url 설정 (php 파일 연동)
    final static private String ipAddress="localhost";
    final static private String portNum="8080";
    final static private String route="/signin";
//    final static private String URL="http://"+ipAddress+":"+portNum+route
    final static private String URL="http://a4382432.dothome.co.kr/Login.php";
    private final Map<String,String> map;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("password",password);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
