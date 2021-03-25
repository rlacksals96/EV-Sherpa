package com.example.evsherpa.ui.subsidyInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubsidyInfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SubsidyInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is subsidy info fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}