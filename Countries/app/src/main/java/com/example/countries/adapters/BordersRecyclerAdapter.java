package com.example.countries.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.countries.R;
import com.example.countries.models.Country;
import java.util.List;

public class BordersRecyclerAdapter extends RecyclerView.Adapter<BordersRecyclerAdapter.BorderingCountryViewHolder>  {

    private final List<Country> borderingCountries;

    public BordersRecyclerAdapter(List<Country> countries) {
        this.borderingCountries = countries;
    }

    @Override
    public BorderingCountryViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_border_listitem, parent, false);
        BorderingCountryViewHolder viewHolder = new BorderingCountryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BordersRecyclerAdapter.BorderingCountryViewHolder holder, int position) {
        holder.countryEnglishName.setText(borderingCountries.get(position).getEnglishName());
        holder.countryNativeName.setText(borderingCountries.get(position).getNativeName());
    }

    @Override
    public int getItemCount() {
        if(borderingCountries != null)
            return borderingCountries.size();
        return 0;
    }

    public class BorderingCountryViewHolder extends RecyclerView.ViewHolder{
        TextView countryNativeName;
        TextView countryEnglishName;

        public BorderingCountryViewHolder(View itemView) {
            super(itemView);
            countryNativeName = itemView.findViewById(R.id.border_native_name);
            countryEnglishName = itemView.findViewById(R.id.border_english_name);
        }
    }
}
