package com.example.countries.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CountryViewHolder>  {
    private static final String TAG = "RecyclerAdapter";

    private Context context;
    private ArrayList<String> nativeNames = new ArrayList<>();
    private ArrayList<String> englishNames = new ArrayList<>();
    private ArrayList<String> areas = new ArrayList<>();

    public RecyclerAdapter(Context context, ArrayList<String> nativeNames, ArrayList<String> englishNames, ArrayList<String> areas) {
        this.nativeNames = nativeNames;
        this.englishNames = englishNames;
        this.areas = areas;
        this.context = context;
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
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + englishNames.get(position));
                Toast.makeText(context, englishNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return englishNames.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{

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
        }
    }
}
