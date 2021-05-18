package com.example.evsherpa.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.evsherpa.CarRegistrationActivity;
import com.example.evsherpa.LoginRequest;
import com.example.evsherpa.MainActivity;
import com.example.evsherpa.PreferenceRegistrationActivity;
import com.example.evsherpa.R;
import com.example.evsherpa.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        final TextView usernameCheckText=findViewById(R.id.txt_email_check);
        final TextView passwordCheckText=findViewById(R.id.txt_password_check);

        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button btnSignUpNormal=findViewById(R.id.btn_sign_up_normal);
        final Button btnSignUpGoogle=findViewById(R.id.btn_sign_up_google);
        final Button btnSignUpKakao=findViewById(R.id.btn_sign_up_kakao);

        final Boolean[] is_username_input={false};
        final Boolean[] is_password_input = {false};

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!passwordEditText.getText().toString().isEmpty()){
                    passwordCheckText.setVisibility(View.INVISIBLE);
                    is_password_input[0] =true;
                }else{
                    passwordCheckText.setVisibility(View.VISIBLE);
                    is_password_input[0] =false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(is_password_input[0] && is_username_input[0]);
//                Log.d("test","password ");
            }
        });
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!usernameEditText.getText().toString().contains("@")){
                    usernameCheckText.setVisibility(View.VISIBLE);
                    is_username_input[0]=true;
                }else{
                    usernameCheckText.setVisibility(View.INVISIBLE);
                    is_username_input[0]=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(is_password_input[0] && is_username_input[0]);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext에 입력된 값 가져오기
                String email=usernameEditText.getText().toString();
                String password=passwordEditText.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("before_try_login",response);

                        try {

//                            JSONObject jsonObject=new JSONObject(response);
                            //boolean success=jsonObject.getBoolean("success");
                            // 원래는 위의 방식으로 진행하는게 맞으나 서버와 연동이 안되서 자체적으로 success처리!
                            JSONObject jsonObject=new JSONObject("{\"success\":true}"); // 추후제거
                            boolean success=true; //추후제거

                            // TODO: 로그인 성공시 sharedPreferences 이용해서 입력했던 정보 자동완성 되도록 만들기.
                            if(success){
                                Log.d("test","test");
//                                String email=jsonObject.getString("email");
//                                String password=jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(),"로그인 성공.."+email+" and "+password,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                                intent.putExtra("email",email);
//                                intent.putExtra("password",password);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 volley를 이용해서 요청을 함.
                LoginRequest loginRequest=new LoginRequest(email,password,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                //temporary... 구현후 삭제..
//                Toast.makeText(LoginActivity.this,"login clicked",Toast.LENGTH_SHORT).show();

            }
        });

        btnSignUpNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"google login",Toast.LENGTH_SHORT).show();

                //need to be changed after implementation
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnSignUpKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"kakao login",Toast.LENGTH_SHORT).show();

                //TODO: need to be changed after implementation
                //TODO: 현재 차량 선택 탭으로 넘어가게 설저오디어 있음.
                Intent intent=new Intent(LoginActivity.this, CarRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }



    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}