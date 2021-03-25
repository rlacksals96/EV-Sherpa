package com.example.evsherpa.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.evsherpa.R;
import com.example.evsherpa.ui.login.LoginViewModel;
import com.example.evsherpa.ui.login.LoginViewModelFactory;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }
}