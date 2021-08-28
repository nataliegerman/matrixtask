package com.example.countries.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countries.MainActivity;
import com.example.countries.models.Country;
import com.example.countries.repositories.CountryRepository;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    private MutableLiveData<List<Country>> mCountries;
    private MutableLiveData<Map<String, Country>> countriesByCodeLiveData = new MutableLiveData<>();
    private Map<String, Country> countriesByCodeMap;

    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(); //true when updating the list/while sorting
    private MutableLiveData<Boolean> isAscending = new MutableLiveData<>(); //isAscending = true by default

    private CountryRepository mRepository;

    public void init() {
        if (mCountries != null && mCountries.getValue().size() > 0) {
            return; //already retrieved the data
        }
        mRepository = CountryRepository.getInstance();
        mCountries = mRepository.getCountries();
        countriesByCodeLiveData = mRepository.getCountriesByCode();
        isAscending.setValue(true); //default is true
        if (countriesByCodeLiveData != null)
            countriesByCodeMap = countriesByCodeLiveData.getValue();
    }

    public LiveData<List<Country>> getCountriesLiveData() {
        return mCountries;
    }

    public MutableLiveData<Boolean> getIsAscending() {
        return isAscending;
    }

    public void sortCountries(MainActivity.SortType sortType) {
        //sorting countries has started, set isUpdating to true in order to show the loading spinner
        isUpdating.postValue(true);

        List<Country> sortedCountries = mCountries.getValue();
        //sort by name or area
        switch (sortType) {
            case AREA:
                //sort by area
                Collections.sort(sortedCountries, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Country c1 = (Country) o1;
                        Country c2 = (Country) o2;
                        Float c1_area = 0.f;
                        Float c2_area = 0.f;
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        //null check since some countries have no area in this API response
                        String c1_area_str = c1.getArea();
                        String c2_area_str = c2.getArea();
                        if(c1_area_str != null) {
                            c1_area = Float.parseFloat(c1_area_str);
                        }
                       if(c2_area_str != null) {
                           c2_area = Float.parseFloat(c2_area_str);
                       }
                        return c1_area.compareTo(c2_area);
                    }
                });
                checkSortIsAscending(sortedCountries);
                break;
            case NAME:
                //sort by name
                Collections.sort(sortedCountries, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Country c1 = (Country) o1;
                        Country c2 = (Country) o2;
                        return c1.getEnglishName().compareToIgnoreCase(c2.getEnglishName());
                    }
                });

                //check if reversed
                checkSortIsAscending(sortedCountries);
                break;
        }

        //update data
        mCountries.postValue(sortedCountries);

        //update is complete, set isUpdating to false in order to hide the loading spinner
        isUpdating.postValue(false);
    }

    private void checkSortIsAscending( List<Country> sortedCountries) {
        //check if reversed
        if (!isAscending.getValue()) {
            Collections.reverse(sortedCountries);
            isAscending.postValue(true);
        } else {
            isAscending.postValue(false);
        }
        isUpdating.postValue(false);
    }

    //get one country object by its code
    public Country getCountryByCode(String code) {
        Country country = new Country();
        if (countriesByCodeLiveData == null)
            countriesByCodeLiveData = new MutableLiveData<>();
        Map<String, Country> currentMap = countriesByCodeLiveData.getValue();

        if (currentMap != null && currentMap.containsKey(code))
            return currentMap.get(code);
        return country;
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    //all countries names by country code
    public MutableLiveData<Map<String, Country>> getCountriesByCodeLiveData() {
        return countriesByCodeLiveData;
    }
}
