package com.example.evsherpa.ui.carInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evsherpa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarInfoFragment extends Fragment {

    private ArrayList<CarInfoData> arrayList;
    private CarInfoAdapter carInfoAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_info, container, false);


        ArrayList<CarInfoData> carInfoList=getJsonData();
        recyclerView=(RecyclerView)view.findViewById(R.id.car_info_rv);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        carInfoAdapter=new CarInfoAdapter(carInfoList);
        recyclerView.setAdapter(carInfoAdapter);

        return view;
    }
    public List<Integer> getCarImages(){
        List<Integer> cars= Arrays.asList(

                R.drawable.car_soul,
                R.drawable.car_soul,
                R.drawable.car_ionic,
                R.drawable.car_ionic,
                R.drawable.car_ionic_5,
                R.drawable.car_ionic_5,
                R.drawable.car_kona,
                R.drawable.car_kona,
                R.drawable.car_niro,
                R.drawable.car_niro,
                R.drawable.car_zoe_itens,
                R.drawable.car_zoe_itens,
                R.drawable.car_bolt_ev_lt,
                R.drawable.car_bolt_ev_lt,
                R.drawable.car_i3_120ah,
                R.drawable.car_i3_120ah,
                R.drawable.car_model_3,
                R.drawable.car_model_3,
                R.drawable.car_model_3,
                R.drawable.car_model_y,
                R.drawable.car_model_y
        );
        return cars;
    }
    public String loadJSONFromAsset(){
        String json=null;
        try{
            InputStream is=getActivity().getAssets().open("car_info.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<CarInfoData> getJsonData(){
        ArrayList<CarInfoData> carinfoList=null;
        List<Integer> images=getCarImages();
        try{
            JSONObject obj=new JSONObject(loadJSONFromAsset());
            JSONArray arr=obj.getJSONArray("cars");
            carinfoList=new ArrayList<CarInfoData>();


            for(int i=0;i<arr.length();i++){
                JSONObject car=arr.getJSONObject(i);
                String car_name=car.getString("차량명");
                String car_maker=car.getString("제조사");
                String car_capacity=car.getString("승차인원");
                String car_max_speed=car.getString("최고속도출력");
                String car_max_distance=car.getString("1회충전주행거리");
                String car_battery=car.getString("배터리");
                String car_subsidy=car.getString("국고보조금");
                String car_maker_phone_num=car.getString("제조사번호");

                String car_profile=car.getString("차량이미지");

                carinfoList.add(new CarInfoData(
                        images.get(i),
                        car_name,
                        car_maker,
                        car_capacity,
                        car_max_speed,
                        car_max_distance,
                        car_battery,
                        car_subsidy,
                        car_maker_phone_num
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return carinfoList;
    }
}