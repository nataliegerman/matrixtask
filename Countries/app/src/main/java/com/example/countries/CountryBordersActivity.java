package com.example.countries;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.adapters.RecyclerAdapter;
import com.example.countries.models.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryBordersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView titleText;
    private RecyclerAdapter mAdapter;

    private List<Country> listOfBorderCountries = new ArrayList<>();
    private String selectedCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_borders);
        recyclerView = findViewById(R.id.country_borders_recycle_view);
        titleText = findViewById(R.id.country_borders_title);

        Bundle bundle = getIntent().getExtras();
        selectedCountry = "";

        if (bundle != null) {
            selectedCountry = bundle.getString(getResources().getString(R.string.EXTRA_COUNTRY_NAME));
            listOfBorderCountries = (List<Country>) bundle.getSerializable(getResources().getString(R.string.EXTRA_BORDERING_COUNTRIES_LIST));

            if(listOfBorderCountries != null && listOfBorderCountries.size() == 0){
                titleText.setText(selectedCountry + getResources().getString(R.string.no_borders_txt));
            } else {
                titleText.setText(selectedCountry + getResources().getString(R.string.borders_activity_title));
            }
        }

        initRecyclerView();

    }


    private void initRecyclerView() {
//        listOfBorderCountries =
        mAdapter = new RecyclerAdapter(this, listOfBorderCountries, country -> {
            /*do nothing here. just reusing the same recycle view as in MainActivity, where itemclick shows borders */
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
