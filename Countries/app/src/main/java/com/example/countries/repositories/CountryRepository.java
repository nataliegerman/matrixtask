package com.example.countries.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.countries.models.Country;

import java.util.ArrayList;
import java.util.List;

/*Singleton pattern*/

public class CountryRepository {

    private static final String TAG = "CountryRepository";
    private static CountryRepository instance;
    private ArrayList<Country> dataSet = new ArrayList<>();

    public static CountryRepository getInstance() {
        if(instance == null){
            instance = new CountryRepository();
        }
        return instance;
    }

    //access data here
    public MutableLiveData<List<Country>> getCounries(){
        //retrieve the data from web
        setCountries();

        MutableLiveData<List<Country>> data = new MutableLiveData<>();
        //set the retrieved data
        data.setValue(dataSet);
        return data;
    }

    private void setCountries(){
        //TODO access REST api here
        //just for test - hardcoded data
        int countriesAmount = 196;
        for (int i = 0; i < countriesAmount; i++) {
            dataSet.add(new Country(" עברית שיהי" + i, "english country name " + i, "13245." + i));
            Log.d(TAG, "setCountries: new country added, i=" + i);
        }
    }
}
