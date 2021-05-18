package com.example.evsherpa.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.evsherpa.R;

public class PopUpUpdateNickname extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG="dialog_event";
    private EditText editText_nickname;

    public PopUpUpdateNickname() {
    }

    public static PopUpUpdateNickname getInstance(){
        PopUpUpdateNickname p=new PopUpUpdateNickname();
        return p;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pop_up_update_nickname,container);
        initElement(view);



        setCancelable(false);
        return view;
    }

    public void initElement(View view){
        editText_nickname=view.findViewById(R.id.editTextNickname);


    }

    @Override
    public void onClick(View v) {

    }
}