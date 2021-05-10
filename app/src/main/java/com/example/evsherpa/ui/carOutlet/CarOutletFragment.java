package com.example.evsherpa.ui.carOutlet;

import android.content.res.AssetManager;
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
import java.util.HashMap;

public class CarOutletFragment extends Fragment {

    private ArrayList<CarOutletData> arrayList;
    private CarOutletAdapter carOutletAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_outlet, container, false);
        ArrayList<CarOutletData> outletList=null;
        try{
            JSONObject obj=new JSONObject(loadJSONFromAsset());
            JSONArray arr=obj.getJSONArray("outlets");
            outletList=new ArrayList<CarOutletData>();


            for(int i=0;i<arr.length();i++){
                JSONObject outlet=arr.getJSONObject(i);
                String connector_name=outlet.getString("connector_name");
                String connector_image=outlet.getString("connector_image");
                //TODO: null 나오는 경우 기본 이미지 나오게 처리해야함.

                String charge_current=outlet.getString("charge_current");
                String charge_voltage=outlet.getString("charge_voltage");
                String charge_power=outlet.getString("charge_power");
                String charge_level=outlet.getString("charge_level");
                String available_car=outlet.getString("available_car");

                //TODO: 첫번째 파라미터가 충전기 이미지인데 현재 기본 이미지 받으려고 int로 설정한 상황임. 추후 String으로 변경해도 문제 없게 믄들어야 함.

                outletList.add(new CarOutletData(
                        R.mipmap.ic_launcher,
                        connector_name,
                        charge_current,
                        charge_voltage,
                        charge_power,
                        charge_level,
                        available_car)
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView=(RecyclerView)view.findViewById(R.id.outlet_rv);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

//        arrayList=new ArrayList<>();
        carOutletAdapter=new CarOutletAdapter(outletList);
        recyclerView.setAdapter(carOutletAdapter);
//        carOutletAdapter.notifyDataSetChanged();



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public String loadJSONFromAsset(){
        String json=null;
        try{
            InputStream is=getActivity().getAssets().open("charger_info.json");
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
}