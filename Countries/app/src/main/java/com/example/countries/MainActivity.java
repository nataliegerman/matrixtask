package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.countries.adapters.RecyclerAdapter;
import com.example.countries.models.Country;
import com.example.countries.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private RecyclerView recyclerView;

    private RecyclerAdapter mAdapter;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

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

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerView.");

        mAdapter = new RecyclerAdapter(this, mainActivityViewModel.getCountriesLiveData().getValue());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}