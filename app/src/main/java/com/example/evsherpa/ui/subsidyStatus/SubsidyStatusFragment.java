package com.example.evsherpa.ui.subsidyStatus;

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

public class SubsidyStatusFragment extends Fragment {

    private SubsidyStatusViewModel subsidyStatusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subsidyStatusViewModel =
                new ViewModelProvider(this).get(SubsidyStatusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subsidy_status, container, false);
        final TextView textView = root.findViewById(R.id.text_subsidy_status);
        subsidyStatusViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}