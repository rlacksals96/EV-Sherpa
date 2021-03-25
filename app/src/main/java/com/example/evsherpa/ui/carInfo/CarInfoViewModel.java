package com.example.evsherpa.ui.carInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarInfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CarInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is car info fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}