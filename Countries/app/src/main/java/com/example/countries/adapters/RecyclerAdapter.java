package com.example.countries.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.countries.OnCountryItemClickListener;
import com.example.countries.R;
import com.example.countries.models.Country;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CountryViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    private Context context;
    private List<Country> allCountries;

    private ArrayList<String> nativeNames;
    private ArrayList<String> englishNames;
    private ArrayList<String> areas;
    private ArrayList<String[]> countryCodesArray;
    private ArrayList<String> code;

    private final OnCountryItemClickListener listener;

    //constructor with list of countries
    public RecyclerAdapter(Activity activity, List<Country> countries, OnCountryItemClickListener listener) {

        this.listener = listener;
        this.allCountries = countries;
        this.nativeNames = new ArrayList<>();
        this.englishNames = new ArrayList<>();
        this.areas = new ArrayList<>();
        this.code = new ArrayList<>();
        this.countryCodesArray = new ArrayList<String[]>();
        this.context = activity;

        if (countries != null && countries.size() > 0) {
            for (Country country : countries) {
                if (country != null) {
                    nativeNames.add(country.getNativeName());
                    englishNames.add(country.getEnglishName());
                    areas.add(country.getArea());
                    countryCodesArray.add(country.getBorders());
                    code.add(country.getArea());
                }
            }
        }
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_country_listitem, parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.CountryViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");

        holder.countryNativeName.setText(nativeNames.get(position));
        holder.countryEnglishName.setText(englishNames.get(position));
        holder.countryArea.setText(areas.get(position));

    }

    @Override
    public int getItemCount() {
        return englishNames.size();
    }

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
