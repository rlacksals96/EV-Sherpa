package com.example.evsherpa.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.evsherpa.R;

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


    public AlertDialog.Builder createNicknamePopUp(View view, View popUpPage){
        EditText editNickname=popUpPage.findViewById(R.id.editTextNickname);

        TextView nickname=view.findViewById(R.id.txt_nickname);

        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(popUpPage);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
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

                    //TODO 서버에 profile.json 파일 저장하는 기능 구현해야한다.

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