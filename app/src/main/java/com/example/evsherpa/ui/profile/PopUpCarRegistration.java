package com.example.evsherpa.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.evsherpa.CarRegistrationActivity;
import com.example.evsherpa.R;

//TODO: 추후 삭제해도 되는지 점검해보기.. popup으로 집어 넣은이상 쓸모 없어 보임.
public class PopUpCarRegistration extends DialogFragment implements View.OnClickListener {

    private Spinner car_maker;
    private Spinner car_model;
    ProfileFragment profileFragment;
    ArrayAdapter<CharSequence> arr_model;
    public static PopUpCarRegistration getInstance(){
        PopUpCarRegistration p=new PopUpCarRegistration();
        return p;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_registration, container, false);
        initElement(view);
//        car_maker=(Spinner)view.findViewById(R.id.spinner_car_company_name);
//        car_maker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(car_maker.getSelectedItem().equals("현대")){
//                    arr_model=ArrayAdapter.createFromResource(getContext(),R.array.hyundai_model, android.R.layout.simple_spinner_dropdown_item);
//                }else if(car_maker.getSelectedItem().equals("기아")){
//                    arr_model=ArrayAdapter.createFromResource(getContext(),R.array.kia_model, android.R.layout.simple_spinner_dropdown_item);
//
//                }else if(car_maker.getSelectedItem().equals("르노삼성")){
//                    arr_model=ArrayAdapter.createFromResource(
//                            getContext(),
//                            R.array.samsung_model,
//                            android.R.layout.simple_spinner_dropdown_item);
//
//                }else if(car_maker.getSelectedItem().equals("한국GM")){
//                    arr_model=ArrayAdapter.createFromResource(
//                            getContext(),
//                            R.array.gm_model,
//                            android.R.layout.simple_spinner_dropdown_item);
//                }else if(car_maker.getSelectedItem().equals("BMW")){
//                    arr_model=ArrayAdapter.createFromResource(
//                            getContext(),
//                            R.array.bmw_model,
//                            android.R.layout.simple_spinner_dropdown_item);
//                }else if(car_maker.getSelectedItem().equals("테슬라")){
//                    arr_model=ArrayAdapter.createFromResource(
//                            getContext(),
//                            R.array.tesla_model,
//                            android.R.layout.simple_spinner_dropdown_item);
//                }
//                arr_model.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//                car_model.setAdapter(arr_model);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//


        setCancelable(false);

        return view;
    }

    public void initElement(View view){
        car_maker=view.findViewById(R.id.spinner_car_company_name);
        car_model=view.findViewById(R.id.spinner_car_model);
    }
    @Override
    public void onClick(View v) {

    }
}