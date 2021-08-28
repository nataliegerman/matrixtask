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
                    ArrayList<Country> countries = new ArrayList<>();
                    countriesNamesByCodeMap = new HashMap<>();

                    for (int i = 0; i < countriesList.size(); i++) {
                        String name = countriesList.get(i).getEnglishName();
                        String nativeName = countriesList.get(i).getNativeName();
                        String area = countriesList.get(i).getArea();
                        String countryCode = countriesList.get(i).getAlpha3Code();
                        String[] borders = countriesList.get(i).getBorders();
                        Country newCountry = new Country(name, nativeName, area, countryCode, borders);
                        countries.add(newCountry);
                        countriesNamesByCodeMap.put(countryCode, newCountry);
                        Log.d(TAG, "setCountries: new country added, " + nativeName + " " + name + " " + area);
                    }
                    countriesDataSet = countries;
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


    public Map<String, Country> getCountriesNamesByCodeMap() {
        return countriesNamesByCodeMap;
    }
}
