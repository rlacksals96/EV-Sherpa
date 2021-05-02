package com.example.evsherpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.evsherpa.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button sign_up_submit=findViewById(R.id.btn_sign_up_submit);



        EditText editText_email=findViewById(R.id.editTextEmail);
        EditText editText_password=findViewById(R.id.editTextPassword);
        EditText editText_password_re=findViewById(R.id.editTextPasswordRe);
        EditText editText_nickname=findViewById(R.id.editTextNickname);
        TextView txt_email_check=findViewById(R.id.txt_email_check);
        TextView txt_password_check=findViewById(R.id.txt_password_check);
        Boolean check_email=false;
        Boolean check_password=false;

        sign_up_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(SignUpActivity.this,CarRegistrationActivity.class);
//                startActivity(intent);

                //edittext에 입력된 값 가져오기
                String email=editText_email.getText().toString();
                String password=editText_password.getText().toString();
                String nickname=editText_nickname.getText().toString();

//                Toast.makeText(getApplicationContext(),email+password+nickname,Toast.LENGTH_SHORT).show();
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("before_try",response);

                        try {
//                            Log.d("after_try",response);

                            JSONObject jsonObject=new JSONObject(response);
//                            boolean success=jsonObject.getBoolean("success");
                            boolean success=true; //서버와 연결시 위에 있는 코드로 변경.
//                            Log.d("find success",success+"");
//                            Toast.makeText(getApplicationContext(),"access onResponse",Toast.LENGTH_SHORT).show();

                            if(success){
                                Toast.makeText(SignUpActivity.this,"회원가입 성공.."+email+" ,"+password+","+nickname,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);

                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUpActivity.this,"회원가입 실패",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 volley를 이용해서 요청을 함.
                SignUpRequest signUpRequest=new SignUpRequest(email,nickname,password,responseListener);
                RequestQueue queue= Volley.newRequestQueue(SignUpActivity.this);
                queue.add(signUpRequest);
                //temporary... 구현후 삭제..
//                Toast.makeText(LoginActivity.this,"login clicked",Toast.LENGTH_SHORT).show();
            }
        });

        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!editText_email.getText().toString().contains("@")){
                    txt_email_check.setVisibility(View.VISIBLE);
                }else{
                    txt_email_check.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText_password_re.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!editText_password_re.getText().toString().equals(editText_password.getText().toString())){
                    txt_password_check.setVisibility(View.VISIBLE);

                }else{
                    txt_password_check.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}