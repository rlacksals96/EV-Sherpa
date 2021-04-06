package com.example.evsherpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.ZoneId;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button sign_up_submit=findViewById(R.id.btn_sign_up_submit);
        sign_up_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,CarRegistrationActivity.class);
                startActivity(intent);
            }
        });


        EditText editText_email=findViewById(R.id.editTextEmail);
        EditText editText_password=findViewById(R.id.editTextPassword);
        EditText editText_password_re=findViewById(R.id.editTextPasswordRe);

        TextView txt_email_check=findViewById(R.id.txt_email_check);
        TextView txt_password_check=findViewById(R.id.txt_password_check);

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