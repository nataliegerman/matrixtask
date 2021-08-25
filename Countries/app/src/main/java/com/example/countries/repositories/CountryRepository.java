package com.example.countries.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.countries.models.Country;
import com.example.countries.models.CountryResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Singleton pattern*/

public class CountryRepository {

    private static final String TAG = "CountryRepository";
    private static CountryRepository instance;
    private ArrayList<Country> dataSet = new ArrayList<>();
    MutableLiveData<List<Country>> data = new MutableLiveData<>();

    public static CountryRepository getInstance() {
        if(instance == null){
            instance = new CountryRepository();
        }
        return instance;
    }

    //access data here
    public MutableLiveData<List<Country>> getCountries(){
        //retrieve the data from web
        setCountries();

//        MutableLiveData<List<Country>> data = new MutableLiveData<>();
        //set the retrieved data
        data.setValue(dataSet);
        Log.d(TAG, " data.setValue(dataSet)");
        return data;
    }

    private void setCountries(){
        //just for test - hardcoded data
//        int countriesAmount = 2;
//        for (int i = 0; i < countriesAmount; i++) {
//            dataSet.add(new Country(" עברית שיהי" + i, "english country name " + i, "13245." + i));
//        }
        //TODO access REST api here
        getCountriesRequest();
    }

    private void getCountriesRequest() {
        Call<List<CountryResponseData>> call = RetrofitClient.getInstance().getMyApi().getCountriesData();
        call.enqueue(new Callback<List<CountryResponseData>>() {
            @Override
            public void onResponse(Call<List<CountryResponseData>> call, Response<List<CountryResponseData>> response) {
                if(response != null && response.body() != null) {
                    List<CountryResponseData> countriesList = response.body();
                    ArrayList<Country> countries = new ArrayList<>();
                    String[] oneCountry = new String[countriesList.size()];

                    for (int i = 0; i < countriesList.size(); i++) {
                        oneCountry[i] = countriesList.get(i).getName();
                        String name = countriesList.get(i).getName();
                        String nativeName = countriesList.get(i).getNativeName();
                        String area = countriesList.get(i).getArea();
                        String countryCode = countriesList.get(i).getAlpha3Code();
                        ArrayList<String> borders = countriesList.get(i).getBorders();
                        countries.add(new Country(nativeName, name, area));
                        Log.d(TAG, "setCountries: new country added, " + nativeName + " " + name + " " + area + " borders: " + borders);
                    }
                    dataSet = countries;
                    //set the retrieved data
                    data.setValue(dataSet);
                }
//                //set the retrieved data
//                data.setValue(dataSet);
            }

            @Override
            public void onFailure(Call<List<CountryResponseData>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure");
            }

        });
    }
}
