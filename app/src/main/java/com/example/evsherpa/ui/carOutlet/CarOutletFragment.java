package com.example.evsherpa.ui.carOutlet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evsherpa.R;
import com.example.evsherpa.ui.carInfo.CenterZoomLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarOutletFragment extends Fragment {

    private ArrayList<CarOutletData> arrayList;
    private CarOutletAdapter carOutletAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_outlet, container, false);
        ArrayList<CarOutletData> outletList=getJsonData();

        recyclerView=(RecyclerView)view.findViewById(R.id.outlet_rv);

//        linearLayoutManager=new LinearLayoutManager(getActivity());
        CenterZoomLayoutManager centerZoomLayoutManager=new CenterZoomLayoutManager(getActivity());
        recyclerView.setLayoutManager(centerZoomLayoutManager);

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
    public List<Integer> getOutletImages(){
        List<Integer> images= Arrays.asList(
                R.drawable.outlet_ac_single_5_pin,
                R.drawable.outlet_ac_three_7_pin,
                R.drawable.outlet_dc_10_pin,
                R.drawable.outlet_dc_7_pin,
                R.drawable.outlet_tesla_pin
        );

        return images;
    }
    public ArrayList<CarOutletData> getJsonData(){

        ArrayList<CarOutletData> outletList=new ArrayList<CarOutletData>();
        List<Integer> images=getOutletImages();
        try{
            JSONObject obj=new JSONObject(loadJSONFromAsset());
            JSONArray arr=obj.getJSONArray("outlets");



            for(int i=0;i<arr.length();i++){
                JSONObject outlet=arr.getJSONObject(i);
                String connector_name=outlet.getString("connector_name");
                String connector_image=outlet.getString("connector_image");


                String charge_current=outlet.getString("charge_current");
                String charge_voltage=outlet.getString("charge_voltage");
                String charge_power=outlet.getString("charge_power");
                String charge_level=outlet.getString("charge_level");
                String available_car=outlet.getString("available_car");


                outletList.add(new CarOutletData(
                        images.get(i),
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
        return outletList;
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