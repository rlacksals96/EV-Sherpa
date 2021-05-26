package com.example.evsherpa.ui.subsidyInfo;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.evsherpa.R;
import com.github.chrisbanes.photoview.PhotoView;

public class SubsidyInfoFragment extends Fragment {

    private Button btn_electric_sedan;
    private Button btn_electric_cargo;
    private Button btn_electric_van;
    private Button btn_electric_bike;
    private Button btn_hydro_sedan;
    private Button btn_hydro_van;
    private Button btn_construction_vehicle;

    private ImageView img_electric_sedan; //xml에서는 PhotoView라는 오픈소스 dom을 쓰긴 했는데 ImageView 확장판이라 그런지 안바꿔도 문제 없음.
    private ImageView img_electric_cargo;
    private ImageView img_electric_van;
    private ImageView img_electric_bike;
    private ImageView img_hydro_sedan;
    private ImageView img_hydro_van;
    private ImageView img_construction_vehicle;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //layout...이전 레이아웃은 R.layout.legacy_fragment_subsidy_status에 임시 저장.

        //legacy
        /*
        //layout에서 테이블 가져오기
        initTableLayout(view);

        //TODO:일반 java 파일에서 jsoup 파싱 제대로 맞춰 온담에 적용하자..
        SubsidyStatus subsidyStatus= new SubsidyStatus();
        //html page가져오기
        subsidyStatus.connect();

        //html 파싱해서 arraylist에 저장
        subsidyStatus.updateSubsidyStatus();

        //tableview에 arraylist 내용 추가
        updateTable(subsidyStatus);

*/
        View view = inflater.inflate(R.layout.fragment_subsidy_info, container, false);

        initElements(view);
        setOnClickBtn();



        return view;
    }




    public void setOnClickBtn(){
        btn_electric_sedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_electric_sedan.getVisibility()==View.VISIBLE){//VISIBLE
                    img_electric_sedan.setVisibility(View.GONE);
                }
                else{//GONE
                    img_electric_sedan.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_electric_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_electric_cargo.getVisibility()==View.VISIBLE){//VISIBLE
                    img_electric_cargo.setVisibility(View.GONE);
                }
                else{//GONE
                    img_electric_cargo.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_electric_van.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_electric_van.getVisibility()==View.VISIBLE){//VISIBLE
                    img_electric_van.setVisibility(View.GONE);
                }
                else{//GONE
                    img_electric_van.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_electric_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_electric_bike.getVisibility()==View.VISIBLE){//VISIBLE
                    img_electric_bike.setVisibility(View.GONE);
                }
                else{//GONE
                    img_electric_bike.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_hydro_sedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_hydro_sedan.getVisibility()==View.VISIBLE){//VISIBLE
                    img_hydro_sedan.setVisibility(View.GONE);
                }
                else{//GONE
                    img_hydro_sedan.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_hydro_van.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_hydro_van.getVisibility()==View.VISIBLE){//VISIBLE
                    img_hydro_van.setVisibility(View.GONE);
                }
                else{//GONE
                    img_hydro_van.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_construction_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_construction_vehicle.getVisibility()==View.VISIBLE){//VISIBLE
                    img_construction_vehicle.setVisibility(View.GONE);
                }
                else{//GONE
                    img_construction_vehicle.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void initElements(View view){
        btn_electric_sedan=view.findViewById(R.id.btn_electric_sedan);
        btn_electric_cargo=view.findViewById(R.id.btn_electric_cargo);
        btn_electric_van=view.findViewById(R.id.btn_electric_van);
        btn_electric_bike=view.findViewById(R.id.btn_electric_bike);
        btn_hydro_sedan=view.findViewById(R.id.btn_hydro_sedan);
        btn_hydro_van=view.findViewById(R.id.btn_hydro_van);
        btn_construction_vehicle=view.findViewById(R.id.btn_electric_construction_vehicle);


        img_electric_sedan=view.findViewById(R.id.img_view_electric_sedan);
        img_electric_sedan.setImageResource(R.drawable.subsidy_status_electric_sedan);
        img_electric_cargo=view.findViewById(R.id.img_view_electric_cargo);
        img_electric_cargo.setImageResource(R.drawable.subsidy_status_electric_cargo);
        img_electric_van=view.findViewById(R.id.img_view_electric_van);
        img_electric_van.setImageResource(R.drawable.subsidy_status_electric_van);
        img_electric_bike=view.findViewById(R.id.img_view_electric_bike);
        img_electric_bike.setImageResource(R.drawable.subsidy_status_electric_bike);
        img_hydro_sedan=view.findViewById(R.id.img_view_hydro_sedan);
        img_hydro_sedan.setImageResource(R.drawable.subsidy_status_hydro_sedan);
        img_hydro_van=view.findViewById(R.id.img_view_hydro_van);
        img_hydro_van.setImageResource(R.drawable.subsidy_status_hydro_van);
        img_construction_vehicle=view.findViewById(R.id.img_view_electric_construction_vehicle);
        img_construction_vehicle.setImageResource(R.drawable.subsidy_status_hydro_construction);
    }


}