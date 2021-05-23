package com.example.evsherpa;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//TODO: nav_header 관련 함수가 전혀 없음. 개인정보 가져와서 내용 변경해주는거 추가하기.
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView car_name;
    private TextView nickname;
    private ImageView img_profile;


    private NavigationView navigationView;

    public NavigationView getNavView(){
        return navigationView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_setting,R.id.nav_subsidy_info,R.id.nav_car_info,R.id.nav_car_outlet,R.id.nav_subsidy_status)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mkProfile();

        //navigation에 있는 프로필 내용 초기화
        View headerView=navigationView.getHeaderView(0);
        refreshNavHeader(headerView);



    }
    public void refreshNavHeader(View headerView){
        car_name=(TextView) headerView.findViewById(R.id.txt_header_car_model);
        nickname=(TextView) headerView.findViewById(R.id.txt_nickname);
        img_profile=(ImageView) headerView.findViewById(R.id.img_profile);

        String result=loadJSON();
        try{
            JSONObject json=new JSONObject(result);
            car_name.setText(json.getString("carName"));
            nickname.setText(json.getString("nickname"));
            updateCarImage(json.getString("carName"));
        }catch (JSONException je){
            je.printStackTrace();
        }


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
    public String loadJSON(){
        String json=null;
        FileInputStream fis;
        StringBuilder sb;
        try{
            fis=openFileInput("profile.json");
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


    public void mkProfile(){
        FileOutputStream fos=null;
        String FILE_NAME= "profile.json";
        String FILE_PATH="/data/data/com.example.evsherpa/files/profile.json";

        File f=new File(FILE_PATH);


        if(!f.exists()){
            try{

                InputStream is=this.getAssets().open("default_profile.json");
                int size=is.available();
                byte[] buffer=new byte[size];
                is.read(buffer);
                is.close();
                String result=new String(buffer,"UTF-8");



                fos=openFileOutput(FILE_NAME,MODE_PRIVATE);
                fos.write(result.getBytes());
                Log.i("file","create profile.json complete");
            } catch(IOException fe){
                fe.printStackTrace();
            } finally {
                if(fos!=null){
                    try{
                        fos.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}