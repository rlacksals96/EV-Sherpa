package com.example.evsherpa.ui.carOutlet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarOutletViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CarOutletViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is car outlet fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}