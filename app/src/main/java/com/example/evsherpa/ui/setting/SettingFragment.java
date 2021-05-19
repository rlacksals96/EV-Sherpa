package com.example.evsherpa.ui.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.evsherpa.MainActivity;
import com.example.evsherpa.PreferenceRegistrationActivity;
import com.example.evsherpa.R;

public class SettingFragment extends Fragment {
    private Button btn_distance,btn_fast_charge,btn_num_of_charger,btn_is_parking_lot,btn_price,btn_company;
    private Button btn_preference_reset,btn_preference_confirm;
    private boolean distance_clicked,fast_charge_clicked,num_of_charger_clicked,is_parking_lot_clicked,
            price_clicked,company_clicked;
    private TextView[]txt_preference;
    private int selected_cnt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preference_setting, container, false);

        initElements(view);
        setClickListener();
        return view;
    }

    public void setRankText(String str){

        if(selected_cnt>3) {
            Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();
            return;
        }
        txt_preference[selected_cnt].setText(new StringBuilder()
                .append(selected_cnt)
                .append(". ")
                .append(str).toString());
    }
    public void setClickListener(){
        btn_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(distance_clicked){
                    Toast.makeText(getContext(),"이미 선택했습니다",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(selected_cnt>3){
                        Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();

                    }else {
                        btn_distance.setBackgroundColor(Color.GREEN);
                        selected_cnt++;
                        setRankText("거리순");
                        distance_clicked=true;

                    }

                }
            }
        });
        btn_fast_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fast_charge_clicked){
                    Toast.makeText(getContext(),"이미 선택했습니다",Toast.LENGTH_SHORT).show();

                }
                else{
                    if(selected_cnt>3){
                        Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();

                    }else{
                        btn_fast_charge.setBackgroundColor(Color.GREEN);
                        selected_cnt++;
                        setRankText("고속 충전");
                        fast_charge_clicked=true;

                    }

                }
            }
        });
        btn_num_of_charger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num_of_charger_clicked){
                    Toast.makeText(getContext(),"이미 선택했습니다",Toast.LENGTH_SHORT).show();


                }
                else{
                    if(selected_cnt>3){
                        Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();

                    }else{
                        btn_num_of_charger.setBackgroundColor(Color.GREEN);
                        selected_cnt++;
                        setRankText("충전기 댓수");
                        num_of_charger_clicked=true;

                    }

                }
            }
        });
        btn_is_parking_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_parking_lot_clicked){
                    Toast.makeText(getContext(),"이미 선택했습니다",Toast.LENGTH_SHORT).show();


                }else{
                    if(selected_cnt>3){
                        Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();

                    }else{
                        btn_is_parking_lot.setBackgroundColor(Color.GREEN);
                        selected_cnt++;
                        setRankText("주차장 여부");
                        is_parking_lot_clicked=true;

                    }

                }
            }
        });
        btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price_clicked){
                    Toast.makeText(getContext(),"이미 선택했습니다",Toast.LENGTH_SHORT).show();


                }else{
                    if(selected_cnt>3){
                        Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();

                    }else {
                        btn_price.setBackgroundColor(Color.GREEN);
                        selected_cnt++;
                        setRankText("가격순");
                        price_clicked=true;

                    }
                }
            }
        });
        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(company_clicked){
                    Toast.makeText(getContext(),"이미 선택했습니다",Toast.LENGTH_SHORT).show();


                }else {
                    if(selected_cnt>3){
                        Toast.makeText(getContext(),"우선순위는 최대 3개까지 등록 가능합니다",Toast.LENGTH_SHORT).show();

                    }else {
                        btn_company.setBackgroundColor(Color.GREEN);
                        selected_cnt++;
                        setRankText("선호업체");
                        company_clicked=true;

                        //TODO: 업체명 관련 spinner 추가

                    }
                }
            }

        });
        btn_preference_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance_clicked=false;
                fast_charge_clicked=false;
                num_of_charger_clicked=false;
                is_parking_lot_clicked=false;
                price_clicked=false;
                company_clicked=false;

                btn_distance.setBackgroundColor(Color.GRAY);
                btn_fast_charge.setBackgroundColor(Color.GRAY);
                btn_num_of_charger.setBackgroundColor(Color.GRAY);
                btn_is_parking_lot.setBackgroundColor(Color.GRAY);
                btn_price.setBackgroundColor(Color.GRAY);
                btn_company.setBackgroundColor(Color.GRAY);

                for(int i=1;i<=3;i++){
                    txt_preference[i].setText(i+". ");
                }
                selected_cnt=0;
            }
        });
        btn_preference_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"confirm clicked",Toast.LENGTH_SHORT).show();
                //TODO: 서버 연결되면 전송할수 있게 처리할것..

                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initElements(View view){
        btn_distance=view.findViewById(R.id.btn_distance);
        btn_fast_charge=view.findViewById(R.id.btn_fast_charge);
        btn_num_of_charger=view.findViewById(R.id.btn_num_of_charger);
        btn_is_parking_lot=view.findViewById(R.id.btn_is_parking_lot);
        btn_price=view.findViewById(R.id.btn_price);
        btn_company=view.findViewById(R.id.btn_company);

        distance_clicked=false;
        fast_charge_clicked=false;
        num_of_charger_clicked=false;
        is_parking_lot_clicked=false;
        price_clicked=false;
        company_clicked=false;

        btn_preference_reset=view.findViewById(R.id.btn_preference_reset);
        btn_preference_confirm=view.findViewById(R.id.btn_preference_confirm);

        selected_cnt=0;

        txt_preference=new TextView[4];
        txt_preference[1]=view.findViewById(R.id.txt_preference_first);
        txt_preference[2]=view.findViewById(R.id.txt_preference_second);
        txt_preference[3]=view.findViewById(R.id.txt_preference_third);
    }
}