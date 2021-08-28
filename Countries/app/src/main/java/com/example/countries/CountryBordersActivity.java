package com.example.countries;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.adapters.BordersRecyclerAdapter;
import com.example.countries.models.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryBordersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView titleText;
    private BordersRecyclerAdapter mAdapter;

    private List<Country> listOfBorderCountries = new ArrayList<>();
    private String selectedCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_borders);
        recyclerView = findViewById(R.id.country_borders_recycle_view);
        titleText = findViewById(R.id.country_borders_title);

        //get EXTRAs from MainActivity: Country name and its borders list
        Bundle bundle = getIntent().getExtras();
        selectedCountry = "";

        if (bundle != null) {
            selectedCountry = bundle.getString(getResources().getString(R.string.EXTRA_COUNTRY_NAME));
            try {
                listOfBorderCountries = (List<Country>) bundle.getSerializable(getResources().getString(R.string.EXTRA_BORDERING_COUNTRIES_LIST));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }

            if (listOfBorderCountries != null && listOfBorderCountries.size() == 0) {
                titleText.setText(selectedCountry + getResources().getString(R.string.no_borders_txt));
            } else {
                titleText.setText(selectedCountry + getResources().getString(R.string.borders_activity_title));
                initRecyclerView();
            }
        }

        //add back button
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getText(R.string.borders_toolbar_title));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle back arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to MainActivity
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initRecyclerView() {
        mAdapter = new BordersRecyclerAdapter(listOfBorderCountries);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
