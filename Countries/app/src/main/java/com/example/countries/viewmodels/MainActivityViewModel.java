package com.example.countries.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countries.models.Country;
import com.example.countries.repositories.CountryRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Country>> mCountries;
    private CountryRepository mRepository;

    public void init(){
        if(mCountries != null){
            return; //already retrieved the data
        }
        mRepository = CountryRepository.getInstance();
        mCountries = mRepository.getCounries();
    }

    public LiveData<List<Country>> getCountriesLiveData() {
        //TODO
        return mCountries;
    }
}
