package com.example.evsherpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CarRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_registration);

        Button btn_car_registration_submit=findViewById(R.id.btn_car_registration_submit);
        btn_car_registration_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CarRegistrationActivity.this,PreferenceRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}