package com.example.evsherpa.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.evsherpa.MainActivity;
import com.example.evsherpa.OnBackPressedListener;
import com.example.evsherpa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class HomeFragment extends Fragment {

    Toast toast;
    MainActivity activity;
    private long backKeyPressedTime=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //TODO: 서버와 연결하면 getProfileFromServer() 호출하여 되는지 확인.
        activity=(MainActivity)getActivity();
        toast=Toast.makeText(activity, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);

        return root;
    }



}