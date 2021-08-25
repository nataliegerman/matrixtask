package com.example.countries.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countries.models.Country;
import com.example.countries.repositories.CountryRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Country>> mCountries;


    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(); //true when updating the list (while sorting)
    private CountryRepository mRepository;

    public void init(){
        if(mCountries != null && mCountries.getValue().size() > 0){
            return; //already retrieved the data
        }
        mRepository = CountryRepository.getInstance();
        mCountries = mRepository.getCountries();
    }

    public LiveData<List<Country>> getCountriesLiveData() {
        return mCountries;
    }

    public void updateCountries(List<Country> sortedCountries) {
        //sorting countries has started, set isUpdating to true in order to show the loading spinner
        isUpdating.setValue(true);

        //update data
        mCountries.postValue(sortedCountries);
        //update is complete, set isUpdating to false in order to hide the loading spinner
        isUpdating.postValue(false);
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }
}
