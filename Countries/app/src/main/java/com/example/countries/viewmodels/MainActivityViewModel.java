package com.example.countries.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.example.countries.models.Country;

import java.util.List;

public class MainActivityViewModel {
    private MutableLiveData<List<Country>> mCountriesLiveData;

    public void init(){

    }

    public MutableLiveData<List<Country>> getCountriesLiveData() {
        return mCountriesLiveData;
    }
}
