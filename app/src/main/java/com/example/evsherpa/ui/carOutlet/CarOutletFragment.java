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

import com.example.evsherpa.R;

public class CarOutletFragment extends Fragment {

    private CarOutletViewModel carOutletViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        carOutletViewModel =
//                new ViewModelProvider(this).get(CarOutletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_car_outlet, container, false);
//        final TextView textView = root.findViewById(R.id.text_car_outlet);
//        carOutletViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}