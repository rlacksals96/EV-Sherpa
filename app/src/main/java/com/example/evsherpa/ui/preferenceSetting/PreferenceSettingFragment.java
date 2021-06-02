package com.example.evsherpa.ui.preferenceSetting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.evsherpa.MainActivity;
import com.example.evsherpa.R;
import com.example.evsherpa.ui.profile.UpdateAgeRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PreferenceSettingFragment extends Fragment {
    private Button btn_distance,btn_fast_charge,btn_num_of_charger,btn_is_parking_lot,btn_price,btn_company;
    private Button btn_preference_reset,btn_preference_confirm;

    private Spinner spinner_company;
    private Button btn_select_company;

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

        txt_preference[selected_cnt].setText(str);
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



                        //TODO: 업체명 관련 spinner 추가
                        spinner_company.setVisibility(View.VISIBLE);
                        spinner_company.setSelection(0);
                        btn_select_company.setVisibility(View.VISIBLE);
                        btn_select_company.setEnabled(true);
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
                    txt_preference[i].setText("");
                }
                selected_cnt=0;

                spinner_company.setVisibility(View.INVISIBLE);
                spinner_company.setSelection(0);
                btn_select_company.setVisibility(View.INVISIBLE);

            }
        });
        btn_preference_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"confirm clicked",Toast.LENGTH_SHORT).show();
                //TODO: 서버 연결되면 전송할수 있게 처리할것..
                savePreference();
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn_select_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner_company.getSelectedItemPosition()==0) {
                    Toast.makeText(getContext(), "업체를 선택하지 않았습니다", Toast.LENGTH_SHORT).show();
                    return;

                }
                selected_cnt++;
//                setRankText("선호업체");
                company_clicked=true;


                String company=spinner_company.getSelectedItem().toString();
                StringBuilder sb=new StringBuilder();
                sb.append("선호업체")
                        .append("(")
                        .append(company)
                        .append(")");
                setRankText(sb.toString());
                btn_select_company.setEnabled(false);

            }
        });
    }
    void savePreference(){
        String str_email="";
        String result=loadJSON();
        String preferences=txt_preference[1].getText().toString()+","
                +txt_preference[2].getText().toString()+","
                +txt_preference[3].getText().toString();

        try{
            JSONObject profile=new JSONObject(result);
            str_email=profile.getString("email");
            profile.put("preferences",preferences);
            saveJSON(profile);
        }catch (JSONException je){
            je.printStackTrace();

        }
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);

                    //TODO: 서버와 연결시 주석 변경하기
//                                    boolean success=jsonObject.getBoolean("success");
                    boolean success=true;
                    if(!success){
                        Toast.makeText(getContext(),"서버에 변경사항을 저장하지 못했습니다",Toast.LENGTH_SHORT).show();
                        try{
                            JSONObject profile=new JSONObject(loadJSON());

                            //변경사항 파일에 저장하기
                            FileOutputStream fos = getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
                            String tmp = profile.toString();
                            byte[] result = tmp.getBytes();
                            fos.write(result);
                        } catch (JSONException | IOException e){
                            e.printStackTrace();
                        }
                    }
                }catch (JSONException je){
                    je.printStackTrace();
                }
            }
        };
        UpdatePreferenceRequest preferenceRequest=new UpdatePreferenceRequest(str_email,preferences,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(preferenceRequest);


    }
    public void saveJSON(JSONObject profile) {
        //변경사항 파일에 저장하기
        try{
            FileOutputStream fos = getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
            String tmp = profile.toString();
            byte[] result = tmp.getBytes();
            fos.write(result);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
    public String loadJSON() {
        String json = null;
        FileInputStream fis;
        StringBuilder sb;
        try {
            // TODO: fragment에서openFileInput 자체가 nullpointexectoin 뜬다.. hotfix 필요!!!!
            fis = getActivity().openFileInput("profile.json");
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

        spinner_company=view.findViewById(R.id.spinner_company);
        btn_select_company=view.findViewById(R.id.btn_select_company);
    }
}