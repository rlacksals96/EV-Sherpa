package com.example.evsherpa.ui.carOutlet;

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

import java.util.ArrayList;

public class CarOutletFragment extends Fragment {

    private ArrayList<CarOutletData> arrayList;
    private CarOutletAdapter carOutletAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_outlet, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.outlet_rv);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList=new ArrayList<>();
        carOutletAdapter=new CarOutletAdapter(arrayList);
        recyclerView.setAdapter(carOutletAdapter);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }
    private void initDataset(){
        //for testing

    }
}