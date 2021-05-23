package com.example.evsherpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.evsherpa.ui.profile.PopUpCarRegistration;
import com.example.evsherpa.ui.profile.ProfileFragment;

import java.util.Arrays;
import java.util.List;

//TODO: activity -> fragment로 변경. 잦은 profile <-> car registration 시 에러 발생..
// fragment 제작 완료 .. 추후 삭제하기.
public class CarRegistrationActivity extends AppCompatActivity {
    private Spinner car_maker;
    private Spinner car_model;

    ProfileFragment profileFragment;

    ArrayAdapter<CharSequence> arr_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_car_registration);

        profileFragment=new ProfileFragment();

        car_maker=(Spinner)findViewById(R.id.spinner_car_company_name);
        car_maker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(car_maker.getSelectedItem().equals("현대")){
                   arr_model=ArrayAdapter.createFromResource(CarRegistrationActivity.this,R.array.hyundai_model, android.R.layout.simple_spinner_dropdown_item);
               }else if(car_maker.getSelectedItem().equals("기아")){
                   arr_model=ArrayAdapter.createFromResource(CarRegistrationActivity.this,R.array.kia_model, android.R.layout.simple_spinner_dropdown_item);

               }else if(car_maker.getSelectedItem().equals("르노삼성")){
                   arr_model=ArrayAdapter.createFromResource(
                           CarRegistrationActivity.this,
                           R.array.samsung_model,
                           android.R.layout.simple_spinner_dropdown_item);

               }else if(car_maker.getSelectedItem().equals("한국GM")){
                   arr_model=ArrayAdapter.createFromResource(
                           CarRegistrationActivity.this,
                           R.array.gm_model,
                           android.R.layout.simple_spinner_dropdown_item);
               }else if(car_maker.getSelectedItem().equals("BMW")){
                   arr_model=ArrayAdapter.createFromResource(
                           CarRegistrationActivity.this,
                           R.array.bmw_model,
                           android.R.layout.simple_spinner_dropdown_item);
               }else if(car_maker.getSelectedItem().equals("테슬라")){
                   arr_model=ArrayAdapter.createFromResource(
                           CarRegistrationActivity.this,
                           R.array.tesla_model,
                           android.R.layout.simple_spinner_dropdown_item);
               }
                arr_model.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                car_model.setAdapter(arr_model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        car_model=(Spinner)findViewById(R.id.spinner_car_model);





//        Button btn_car_registration_submit=findViewById(R.id.btn_car_registration_submit);
//        btn_car_registration_submit.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(CarRegistrationActivity.this,PreferenceRegistrationActivity.class);
////                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.layout.fragment_car_registration,profileFragment).commit();
//            }
//        });
    }
}