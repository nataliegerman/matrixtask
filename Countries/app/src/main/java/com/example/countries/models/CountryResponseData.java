package com.example.countries.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryResponseData {
    //https://restcountries.eu/rest/v2/all?fields=name;nativeName;area;alpha3Code;borders

    //https://restcountries.eu/rest/v2/alpha/ala

    @SerializedName("name")
    private String englishName;

    @SerializedName("area")
    private String area;

    // "nativeName"
    @SerializedName("nativeName")
    private String nativeName;

    // "borders"
    @SerializedName("borders")
    private ArrayList<String> borders;

    //"alpha3Code"
    @SerializedName("alpha3Code")
    private String alpha3Code;


    public CountryResponseData(String name, String nativeName, String area,  ArrayList<String> borders, String alpha3Code) {
        this.englishName = name;
        this.nativeName = nativeName;
        this.area = area;
        this.borders = borders;
        this.alpha3Code = alpha3Code;
    }

    public String getName() {
        return englishName;
    }

    public String getArea() {
        return area;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }
}
