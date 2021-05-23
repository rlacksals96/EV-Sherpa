package com.example.evsherpa.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.evsherpa.MainActivity;
import com.example.evsherpa.R;
import com.google.android.material.navigation.NavigationView;

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
    private TextView txt_home_addr;
    private TextView txt_work_addr;
    private TextView txt_age;

    private Button btn_change_age;
    private Button btn_change_nickname;
    private Button btn_change_car;
    private Button btn_change_home_addr;
    private Button btn_change_work_addr;


    private Button btn_cancel;
    NavigationView navigationView;
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
        navigationView=view.findViewById(R.id.nav_view);
        txt_home_addr=view.findViewById(R.id.txt_home_addr);
        txt_work_addr=view.findViewById(R.id.txt_work_addr);
        txt_age=view.findViewById(R.id.txt_age);

        //str to check whether info is typed
        String str_age="";
        String str_home_addr="";
        String str_work_addr="";


        //init elements
        img_profile.setImageResource(R.mipmap.ic_launcher);
        try{
            //profile.json
            JSONObject json=new JSONObject(loadJSON());
            txt_nickname.setText(json.getString("nickname"));
            txt_email.setText(json.getString("email"));
            txt_carname.setText(json.getString("carName"));
            txt_age.setText(json.getString("age"));
            txt_home_addr.setText(json.getString("homeAddr"));
            txt_work_addr.setText(json.getString("workplaceAddr"));

            if(!json.getString("age").equals(""))
                str_age=json.getString("age");
            if(!json.getString("homeAddr").equals(""))
                str_home_addr=json.getString("homeAddr");
            if(!json.getString("workplaceAddr").equals(""))
                str_work_addr=json.getString("workplaceAddr");


            updateCarImage(json.getString("carName"));


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

        btn_change_age=view.findViewById(R.id.btn_change_age);
        btn_change_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View agePopup=getLayoutInflater().inflate(R.layout.fragment_pop_up_update_age,null);
                final AlertDialog.Builder builder=createAgeRegistrationPopUp(view,agePopup);
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        if(!str_age.equals(""))
            btn_change_age.setText("변경");


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

        btn_change_home_addr=view.findViewById(R.id.btn_change_home_addr);
        btn_change_home_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View addressRegistrationPopup=getLayoutInflater().inflate(R.layout.activity_address_registration,null);
                final AlertDialog.Builder builder=createAddrRegistrationPopUp(addressRegistrationPopup,"home");
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        if(!str_home_addr.equals(""))
            btn_change_home_addr.setText("변경");

        btn_change_work_addr=view.findViewById(R.id.btn_change_work_addr);
        btn_change_work_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View addressRegistrationPopup=getLayoutInflater().inflate(R.layout.activity_address_registration,null);
                final AlertDialog.Builder builder=createAddrRegistrationPopUp(addressRegistrationPopup,"work");
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        if(!str_work_addr.equals(""))
            btn_change_work_addr.setText("변경");
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


    public AlertDialog.Builder createAgeRegistrationPopUp(View view,View popUpPage){
        EditText editAge=popUpPage.findViewById(R.id.edit_age);
        TextView txt_age=view.findViewById(R.id.txt_age);
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(popUpPage);
        builder.setTitle("나이 추가")
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            JSONObject profile=new JSONObject(loadJSON());
                            profile.put("age",editAge.getText().toString());

                            //입력값이 없으면 그냥 취소된 걸로 인식.
                            if(!editAge.getText().toString().equals("")) {
                                 txt_age.setText(editAge.getText().toString());

                                //변경사항 파일에 저장하기
                                FileOutputStream fos = getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
                                String tmp = profile.toString();
                                byte[] result = tmp.getBytes();
                                fos.write(result);
                            }else{
                                Toast.makeText(getContext(),"입력된 값이 없습니다",Toast.LENGTH_SHORT).show();
                            }

                        }catch(JSONException | IOException e){
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
    }
    public AlertDialog.Builder createAddrRegistrationPopUp(View popUpPage,String type){
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(popUpPage);

        builder
        .setTitle("주소 입력")
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equals("home")){
                    //TODO: 집주소 받아오면 json에 저장하기
                }else{
                    //TODO: 직장주소 받아오면 json에 저장하기
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
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

                try{

                    //json내용 꺼내오고, nickname textview 변경하기
                    JSONObject profile=new JSONObject(loadJSON());
                    profile.put("carName",strCarModel);
                    //변경사항 파일에 저장하기
                    FileOutputStream fos=getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
                    String tmp=profile.toString();
                    byte[] result=tmp.getBytes();
                    fos.write(result);

                    //TODO:(local저장은 구현완료 했고) 서버에 profile.json 파일 저장하는 기능 구현해야한다.

                    txt_carname.setText(profile.get("carName").toString());

                    updateCarImage(strCarModel);


                    //TODO: 여기서 프로필 변경시 navigation 부분도 변경 되야 한다.


                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                //TODO: 작동은 잘 되나 이런식으로 호출해서 사용해도 되는지 모르겠음..
                View headerView=((MainActivity)getActivity()).getNavView();
                ((MainActivity)getActivity()).refreshNavHeader(headerView);


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
    public void updateCarImage(String name){
        switch (name){
            case "아이오닉":
                img_profile.setImageResource(R.drawable.car_ionic);
                break;
            case "아이오닉5":
                img_profile.setImageResource(R.drawable.car_ionic_5);
                break;
            case "쏘울":
                img_profile.setImageResource(R.drawable.car_soul);
                break;
            case "코나":
                img_profile.setImageResource(R.drawable.car_kona);
                break;
            case "니로EV":
                img_profile.setImageResource(R.drawable.car_niro);
                break;
            case "ZOE ITENS":
                img_profile.setImageResource(R.drawable.car_zoe_itens);
                break;
            case "ZOE INTENS":
                img_profile.setImageResource(R.drawable.car_zoe_itens);
                break;
            case "BOLT EV LT":
                img_profile.setImageResource(R.drawable.car_bolt_ev_lt);
                break;
            case "BOLT EV Primier":
                img_profile.setImageResource(R.drawable.car_bolt_ev_lt);
                break;
            case "i3 120Ah":
                img_profile.setImageResource(R.drawable.car_i3_120ah);
                break;
            case "i3 120Ah Sol+":
                img_profile.setImageResource(R.drawable.car_i3_120ah);
                break;
            case "Model 3":
                img_profile.setImageResource(R.drawable.car_model_3);
                break;
            case "Model Y":
                img_profile.setImageResource(R.drawable.car_model_y);
                break;

        }
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
                    if(!editNickname.getText().toString().equals("")) {
                        nickname.setText(profile.getString("nickname"));
                        Toast.makeText(getContext(),"입력된 값이 없습니다",Toast.LENGTH_SHORT).show();
                    }

                    //변경사항 파일에 저장하기
                    FileOutputStream fos=getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
                    String tmp=profile.toString();
                    byte[] result=tmp.getBytes();
                    fos.write(result);

                    //TODO:(local저장은 구현완료 했고) 서버에 profile.json 파일 저장하는 기능 구현해야한다.

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                //TODO: 작동은 잘 되나 이런식으로 호출해서 사용해도 되는지 모르겠음..
                View headerView=((MainActivity)getActivity()).getNavView();
                ((MainActivity)getActivity()).refreshNavHeader(headerView);
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