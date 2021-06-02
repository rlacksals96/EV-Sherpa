package com.example.evsherpa.ui.signUp;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpRequest extends StringRequest {
    // 서버 url 설정 (php 파일 연동)
    final static private String ipAddress="172.20.10.6";
    final static private String portNum="8080";
    final static private String route="/account/signup";
        final static private String URL="http://"+ipAddress+":"+portNum+route;
//    final static private String URL="http://a4382432.dothome.co.kr/Register.php";
    private final Map<String,String> map;

    public SignUpRequest(String email, String nickname, String password, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("nickname",nickname);
        map.put("password",password);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
