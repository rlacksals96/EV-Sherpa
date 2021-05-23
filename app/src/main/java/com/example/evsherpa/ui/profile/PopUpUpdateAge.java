package com.example.evsherpa.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evsherpa.R;

public class PopUpUpdateAge extends DialogFragment implements View.OnClickListener {

    public PopUpUpdateAge() {
    }
    public static PopUpUpdateAge getInstance(){
        PopUpUpdateAge p=new PopUpUpdateAge();
        return p;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pop_up_update_nickname,container);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}