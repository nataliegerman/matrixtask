package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.countries.adapters.CountriesRecyclerAdapter;
import com.example.countries.models.Country;
import com.example.countries.viewmodels.MainActivityViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public enum SortType {
        NAME,
        AREA
    }

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ProgressBar spinner;
    private Button sortByNameBtn;
    private Button sortByAreaBtn;

    private CountriesRecyclerAdapter mAdapter;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        spinner = findViewById(R.id.loading_spinner);
        sortByNameBtn = findViewById(R.id.sort_by_name_btn);
        sortByAreaBtn = findViewById(R.id.sort_by_area_btn);

        Log.d(TAG, "onCreate: started.");

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //init the view model
        mainActivityViewModel.init();
        //observe changes done to view model (countries live data objects)
        mainActivityViewModel.getCountriesLiveData().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                initRecyclerView();
                mAdapter.notifyDataSetChanged();
                spinner.setVisibility(View.INVISIBLE);
                Log.d(TAG, " mAdapter.notifyDataSetChanged()");
            }
        });

        //observe countries list changes in order to show or hide the spinner
        mainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    //show spinner
                    spinner.setVisibility(View.VISIBLE);
                } else {
                    //hide spinner
                    spinner.setVisibility(View.INVISIBLE);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        mainActivityViewModel.getIsAscending().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //todo change button
            }
        });

        mainActivityViewModel.getCountriesByCodeLiveData().observe(this, new Observer<Map<String, Country>>() {
            @Override
            public void onChanged(Map<String, Country> stringCountryMap) {
                Log.d(TAG, "countries by code updated");
            }
        });

        sortByNameBtn.setOnClickListener(view -> {
            //sort countries by name and update livedata
            mainActivityViewModel.sortCountries(SortType.NAME);
        });

        sortByAreaBtn.setOnClickListener(view -> {
            //sort countries by area and update livedata
            mainActivityViewModel.sortCountries(SortType.AREA);
        });
    }


    private void initRecyclerView() {
        mAdapter = new CountriesRecyclerAdapter(mainActivityViewModel.getCountriesLiveData().getValue(), new OnCountryItemClickListener() {
            @Override
            public void onItemClick(Country country) {
                openBordersActivity(country);
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openBordersActivity(Country selectedCountry) {
        //get country's bordering countries by codes here
       List<Country> list = getOneCountrysBordersList(selectedCountry);
        //show selected country's bordering countries
        showBorders(selectedCountry, list);
    }

    //country object has the list of borders that contains only country codes.
    // In order to get the list of each code's country, use this function
    private List<Country> getListOfCountryBorders(String[] bordersCodes) {
        List<Country> listOfBorderCountries = new ArrayList<>();
        for (String code : bordersCodes) {
            Country tempCountry = mainActivityViewModel.getCountryByCode(code);
            listOfBorderCountries.add(tempCountry);
        }
        return listOfBorderCountries;
    }

    private void showBorders(Country selectedCountry, List<Country> list) {

        Intent intent = new Intent(MainActivity.this, CountryBordersActivity.class);
        intent.putExtra(getResources().getString(R.string.EXTRA_COUNTRY_NAME), selectedCountry.getEnglishName());
        intent.putExtra(getResources().getString(R.string.EXTRA_BORDERING_COUNTRIES_LIST), (Serializable) list);

        MainActivity.this.startActivity(intent);
    }

    private List<Country> getOneCountrysBordersList(Country selectedCountry) {
        List<Country> list = getListOfCountryBorders(selectedCountry.getBorders());
        return list;
    }
}