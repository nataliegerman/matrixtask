package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.countries.adapters.RecyclerAdapter;
import com.example.countries.models.Country;
import com.example.countries.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public enum SortType  {
        NAME,
        AREA
    }

    private static final String TAG = "MainActivity";


    private RecyclerView recyclerView;
    private ProgressBar spinner;
    private Button sortByNameBtn;

    private RecyclerAdapter mAdapter;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        spinner = findViewById(R.id.loading_spinner);
        sortByNameBtn = findViewById(R.id.sort_by_name_btn);

        Log.d(TAG, "onCreate: started.");

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //init the view model
        mainActivityViewModel.init();
        //observe changes done to view model (countries live data objects)
        mainActivityViewModel.getCountriesLiveData().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, " mAdapter.notifyDataSetChanged()");
            }
        });

        //observe countries list changes in order to show or hide the spinner
        mainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    //show spinner
                    spinner.setVisibility(View.VISIBLE);
                    Log.d(TAG, " spinner.setVisibility(View.VISIBLE)");
                }
                else {
                    //hide spinner
                    spinner.setVisibility(View.INVISIBLE);
                    recyclerView.smoothScrollToPosition(mainActivityViewModel.getCountriesLiveData().getValue().size() - 1); //just for test. TODO - remove
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        sortByNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.updateCountries(sortCountries(SortType.NAME));
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new RecyclerAdapter(this, mainActivityViewModel.getCountriesLiveData().getValue());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /////
    public List<Country> sortCountries(SortType sortType) {
        //copy the existing countries list
        List<Country> sortedCountries = mainActivityViewModel.getCountriesLiveData().getValue();
        //TODO - sort by name or area
        switch (sortType) {
            case AREA:
                //TODO sort by area
                break;
            case NAME:
                //TODO sort by name
                break;
        }
        return sortedCountries;
    }
}