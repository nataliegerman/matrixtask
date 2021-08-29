package com.example.countries.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.countries.models.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Singleton pattern*/

public class CountryRepository {

    private static final String TAG = "CountryRepository";
    private static CountryRepository instance;
    private ArrayList<Country> countriesDataSet = new ArrayList<>();
    private Map<String, Country> countriesNamesByCodeMap;
    MutableLiveData<List<Country>> countriesLiveData = new MutableLiveData<>();
    MutableLiveData<Map<String, Country>> countriesNamesByCodeLiveData = new MutableLiveData<>();


    public static CountryRepository getInstance() {
        if (instance == null) {
            instance = new CountryRepository();
        }
        return instance;
    }

    //access data here
    public MutableLiveData<List<Country>> getCountries() {
        //retrieve the data from web, access REST api
        getCountriesRequest();
        return countriesLiveData;
    }

    //countriesNamesByCodeLiveData
    public MutableLiveData<Map<String, Country>> getCountriesByCode() {
        return countriesNamesByCodeLiveData;
    }

    private void getCountriesRequest() {
        Call<List<Country>> call = RetrofitClient.getInstance().getMyApi().getCountriesData();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response != null && response.body() != null) {
                    List<Country> countriesList = response.body();
                    countriesNamesByCodeMap = new HashMap<>();

                    for (Country country : countriesList) {
                        //creating map of countries by their code
                        // in order to retrieve fast when showing selected country's bordering countries
                        country.validateNameAndArea();
                        countriesNamesByCodeMap.put(country.getAlpha3Code(), country);
                        Log.d(TAG, "setCountries: new country added, " + country.getEnglishName() + " " + country.getNativeName() + " " + country.getArea());
                    }

                    countriesDataSet = (ArrayList<Country>) countriesList;
                    //set the retrieved data
                    countriesLiveData.setValue(countriesDataSet);
                    countriesNamesByCodeLiveData.setValue(countriesNamesByCodeMap);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d(TAG, "request failed");
            }

        });
    }

}
