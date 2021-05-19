package com.example.evsherpa.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.evsherpa.CarRegistrationActivity;
import com.example.evsherpa.R;
import com.example.evsherpa.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProfileFragment extends Fragment {


    private ImageView img_profile;
    private TextView txt_email;
    private TextView txt_profile;
    private TextView txt_nickname;
    private TextView txt_carname;
    private Button btn_change_nickname;
    private Button btn_change_car;

    private Button btn_cancel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initElements(view);

        return view;
    }

    public void initElements(View view){
        //connect elements
        img_profile=view.findViewById(R.id.img_view_profile);
        txt_nickname=view.findViewById(R.id.txt_nickname);
        txt_email=view.findViewById(R.id.txt_email);
        txt_carname=view.findViewById(R.id.txt_car_name);



        //init elements
        img_profile.setImageResource(R.mipmap.ic_launcher);
        try{
            JSONObject json=new JSONObject(loadJSON());
            txt_nickname.setText(json.getString("nickname"));
            txt_email.setText(json.getString("email"));
            txt_carname.setText(json.getString("carName"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        btn_change_nickname=view.findViewById(R.id.btn_change_nickname);
        btn_change_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View nicknamePopup=getLayoutInflater().inflate(R.layout.fragment_pop_up_update_nickname,null);
                final AlertDialog.Builder builder= createNicknamePopUp(view,nicknamePopup);
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }
        });

        btn_change_car=view.findViewById(R.id.btn_change_car);
        btn_change_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View carRegistrationPopup=getLayoutInflater().inflate(R.layout.fragment_car_registration,null);
                final AlertDialog.Builder builder=createCarRegistrationPopUp(view,carRegistrationPopup);
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }
    public String loadJSON(){
        String json=null;
        FileInputStream fis;
        StringBuilder sb;
        try{
//            InputStream is=
            fis=getActivity().openFileInput("profile.json");
            InputStreamReader  isr=new InputStreamReader(fis);

            BufferedReader br=new BufferedReader(isr);
            sb=new StringBuilder();
            String text;

            while((text=br.readLine())!=null){
                sb.append(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }


    public AlertDialog.Builder createCarRegistrationPopUp(View view,View popUpPage){

        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(popUpPage);



        Spinner car_model=popUpPage.findViewById(R.id.spinner_car_model);
        Spinner car_battery=popUpPage.findViewById(R.id.spinner_car_battery_type);
        //제조사에 따른 차량 모델 스피너 변경 기능 구현완료.
        Spinner car_maker=popUpPage.findViewById(R.id.spinner_car_company_name);
        car_maker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(car_maker.getSelectedItem().equals("현대")){
//                    arr_model =ArrayAdapter.createFromResource(getActivity(),R.array.hyundai_model, android.R.layout.simple_spinner_dropdown_item);
                    car_model.setAdapter(ArrayAdapter.createFromResource(getActivity(),
                            R.array.hyundai_model,android.R.layout.simple_spinner_dropdown_item));
                }else if(car_maker.getSelectedItem().equals("기아")){
                    car_model.setAdapter(ArrayAdapter.createFromResource(getActivity(),
                            R.array.kia_model, android.R.layout.simple_spinner_dropdown_item));
                }else if(car_maker.getSelectedItem().equals("르노삼성")){
                    car_model.setAdapter(ArrayAdapter.createFromResource(getActivity(),
                            R.array.samsung_model, android.R.layout.simple_spinner_dropdown_item));
                }else if(car_maker.getSelectedItem().equals("한국GM")){
                    car_model.setAdapter(ArrayAdapter.createFromResource(getActivity(),
                            R.array.gm_model, android.R.layout.simple_spinner_dropdown_item));
                }else if(car_maker.getSelectedItem().equals("BMW")){
                    car_model.setAdapter(ArrayAdapter.createFromResource(getActivity(),
                            R.array.bmw_model, android.R.layout.simple_spinner_dropdown_item));
                }else if(car_maker.getSelectedItem().equals("테슬라")){
                    car_model.setAdapter(ArrayAdapter.createFromResource(getActivity(),
                            R.array.tesla_model, android.R.layout.simple_spinner_dropdown_item));
                }




//                arr_model.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//                car_model.setAdapter(arr_model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle("차량 선택")
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strCarModel=car_model.getSelectedItem().toString();
                String strCarMaker=car_maker.getSelectedItem().toString();
                String strCarBattery=car_battery.getSelectedItem().toString();
                //TODO: ok 버튼 누른 후의 기능 추가하기기
                Toast.makeText(getContext(),strCarMaker+" "+strCarModel+" "+strCarBattery,Toast.LENGTH_SHORT).show();
            }
       }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        }).setCancelable(false);
        return builder;
    }

    public AlertDialog.Builder createNicknamePopUp(View view, View popUpPage){
        EditText editNickname=popUpPage.findViewById(R.id.editTextNickname);

        TextView nickname=view.findViewById(R.id.txt_nickname);

        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(popUpPage);
        builder.setTitle("별명 변경")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try{

                    //json내용 꺼내오고, nickname textview 변경하기
                    JSONObject profile=new JSONObject(loadJSON());
                    profile.put("nickname",editNickname.getText().toString());

                    //입력값이 없으면 그냥 취소된 걸로 인식.
                    if(!editNickname.getText().toString().equals(""))
                        nickname.setText(profile.getString("nickname"));

                    //변경사항 파일에 저장하기
                    FileOutputStream fos=getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
                    String tmp=profile.toString();
                    byte[] result=tmp.getBytes();
                    fos.write(result);

                    //TODO:(local저장은 구현완료 했고) 서버에 profile.json 파일 저장하는 기능 구현해야한다.

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        })
        .setCancelable(false);

        return builder;
    }


}