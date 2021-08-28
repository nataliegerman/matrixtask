package com.example.countries.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.OnCountryItemClickListener;
import com.example.countries.R;
import com.example.countries.models.Country;

import java.util.List;

public class CountriesRecyclerAdapter extends RecyclerView.Adapter<CountriesRecyclerAdapter.CountryViewHolder> {

    private List<Country> allCountries;

    private final OnCountryItemClickListener listener;

    //constructor with list of countries
    public CountriesRecyclerAdapter(List<Country> countries, OnCountryItemClickListener listener) {
        this.listener = listener;
        this.allCountries = countries;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_country_listitem, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountriesRecyclerAdapter.CountryViewHolder holder, int position) {
        holder.countryEnglishName.setText(allCountries.get(position).getEnglishName());
        holder.countryNativeName.setText(allCountries.get(position).getNativeName());
        holder.countryArea.setText(allCountries.get(position).getArea());
    }

    @Override
    public int getItemCount() {
        if (allCountries != null)
            return allCountries.size();
        return 0;
    }

    //main screen countries view holder
    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView countryNativeName;
        TextView countryEnglishName;
        TextView countryArea;
        LinearLayout parentLayout;

        public CountryViewHolder(View itemView) {
            super(itemView);
            countryNativeName = itemView.findViewById(R.id.country_native_name);
            countryEnglishName = itemView.findViewById(R.id.country_english_name);
            countryArea = itemView.findViewById(R.id.country_area);
            parentLayout = itemView.findViewById(R.id.country_parent_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            if (listener != null && allCountries != null && position < allCountries.size()) {
                listener.onItemClick(allCountries.get(position));
            }
        }
    }
}
