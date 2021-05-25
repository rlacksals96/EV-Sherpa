package com.example.evsherpa.ui.profile;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateWorkplaceAddrRequest extends StringRequest {
    final static private String ipAddress="localhost";
    final static private String portNum="8080";
    final static private String route="/account/update/age";
    final static private String URL="http://"+ipAddress+":"+portNum+route;

    private final Map<String,String> map;

    public UpdateWorkplaceAddrRequest(String email, String workplaceAddr, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("homeAddr",workplaceAddr);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
