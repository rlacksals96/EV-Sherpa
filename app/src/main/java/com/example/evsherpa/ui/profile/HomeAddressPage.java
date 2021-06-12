package com.example.evsherpa.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.evsherpa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeAddressPage extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 100;

    private TextView txt_address;

    private String str_address;
    private String str_email;
    private String str_type;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_page);

        txt_address=findViewById(R.id.txt_address_result);

        Button btn_search = (Button) findViewById(R.id.btn_search_address);

        if (btn_search != null) {
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(HomeAddressPage.this, HomeAddressRegistration.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }
        Button btn_confirm=findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });
    }

    public void updateAddress(){
        String json=loadJSON();
        try{
            JSONObject jsonObject=new JSONObject(json);


            jsonObject.put("homeAddr", str_address);
            str_address=jsonObject.getString("homeAddr");
            str_email=jsonObject.getString("email");


            //변경사항 파일에 저장하기
            FileOutputStream fos = openFileOutput("profile.json", Context.MODE_PRIVATE);
            String tmp = jsonObject.toString();
            byte[] result = tmp.getBytes();
            fos.write(result);

            Response.Listener<String> responseListener=new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        boolean success=jsonObject.getBoolean("success");
//                        boolean success=true;
                        if(!success){
                            Toast.makeText(HomeAddressPage.this,"서버에 변경사항을 저장하지 못했습니다",Toast.LENGTH_SHORT).show();
                            try{
                                JSONObject profile=new JSONObject(loadJSON());
                                profile.put("homeAddr","");



                                //변경사항 파일에 저장하기
                                FileOutputStream fos = openFileOutput("profile.json", Context.MODE_PRIVATE);
                                String tmp = profile.toString();
                                byte[] result = tmp.getBytes();
                                fos.write(result);
                            } catch (JSONException | IOException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(HomeAddressPage.this, "주소 업데이트 완료", Toast.LENGTH_SHORT).show();
                        }

                    }catch (JSONException je){
                        je.printStackTrace();
                    }

                }
            };
//            Log.e("address page",in.getStringExtra("type"));
//            Log.e("address page",str_email);
//            Log.e("address page",str_address);
//            UpdateHomeAddressRequest request = new UpdateHomeAddressRequest(str_email, str_address,responseListener);
//            RequestQueue queue = Volley.newRequestQueue(AddressPage.this);
//            queue.add(request);
//            finish();
            UpdateHomeAddressRequest request = new UpdateHomeAddressRequest(str_email, str_address,responseListener);
            RequestQueue queue = Volley.newRequestQueue(HomeAddressPage.this);
            queue.add(request);
            finish();



        } catch (JSONException | IOException je){
            je.printStackTrace();
        }


    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        txt_address.setText(data);
                        str_address=data;

                    }
                }
                break;
        }
    }
    public String loadJSON() {
        String json = null;
        FileInputStream fis;
        StringBuilder sb;
        try {
            fis = openFileInput("profile.json");
            InputStreamReader isr = new InputStreamReader(fis);

            BufferedReader br = new BufferedReader(isr);
            sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}