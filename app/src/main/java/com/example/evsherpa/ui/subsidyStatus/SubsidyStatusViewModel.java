package com.example.evsherpa.ui.subsidyStatus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubsidyStatusViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SubsidyStatusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is subsidy status fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}