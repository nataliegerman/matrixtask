package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.countries.adapters.RecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<String> nativeNames = new ArrayList<>();
    private ArrayList<String> englishNames = new ArrayList<>();
    private ArrayList<String> areas = new ArrayList<>();

    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started.");
        initCountriesData(); //JUST for test! TODO remove it
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerView.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerAdapter(this, nativeNames, englishNames, areas);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    
    //TODO remove it! temp function for country names just for test
    private void initCountriesData(){
        int countriesAmount = 196;
        for (int i = 0; i < countriesAmount; i++) {
            nativeNames.add(" עברית שיהי" + i);
            englishNames.add("english country name " + i);
            areas.add("13245." + i);
        }
    }
}