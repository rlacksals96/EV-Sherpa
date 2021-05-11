package com.example.evsherpa.ui.carOutlet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evsherpa.R;

import java.util.ArrayList;

public class CarOutletActivity extends AppCompatActivity {

    private ArrayList<CarOutletData> arrayList;
    private CarOutletAdapter carOutletAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_outlet);




    }
}