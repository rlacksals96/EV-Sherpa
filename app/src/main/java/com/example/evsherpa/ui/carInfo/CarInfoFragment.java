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

import com.example.evsherpa.R;

public class CarInfoFragment extends Fragment {

    private CarInfoViewModel carInfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        carInfoViewModel =
                new ViewModelProvider(this).get(CarInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_car_info, container, false);
        final TextView textView = root.findViewById(R.id.text_car_info);
        carInfoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}