package com.example.countries;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CountryBordersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_borders);
        recyclerView = findViewById(R.id.country_borders_recycle_view);
        titleText = findViewById(R.id.country_borders_title);

        Bundle b = getIntent().getExtras();
        String selectedCountry = ""; // or other values
        if(b != null) {
            selectedCountry = b.getString(getResources().getString(R.string.EXTRA_COUNTRY_NAME));
            titleText.setText(selectedCountry + "'s borders" );
        }
    }
}
