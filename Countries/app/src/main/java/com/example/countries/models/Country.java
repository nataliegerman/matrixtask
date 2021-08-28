package com.example.countries.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;

public class Country implements Serializable {
    //https://restcountries.eu/rest/v2/all?fields=name;nativeName;area;alpha3Code;borders

    //https://restcountries.eu/rest/v2/alpha/ala

    @SerializedName("name")
    private String name;

    @SerializedName("area")
    private String area;

    // "nativeName"
    @SerializedName("nativeName")
    private String nativeName;

    // "borders"
    @SerializedName("borders")
    private String[] borders;

    //"alpha3Code"
    @SerializedName("alpha3Code")
    private String alpha3Code;

    public Country() {
    }

    public Country(String name, String nativeName, String area, String alpha3Code, String[] borders) {
        this.name = validateEnglishName(name);
        this.nativeName = nativeName;
        this.area = validateArea(area);
        this.borders = borders;
        this.alpha3Code = alpha3Code;
    }

    //english name
    public String getEnglishName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getArea() {
        return area;
    }

    //3 letter country code
    public String getAlpha3Code() {
        return alpha3Code;
    }

    //array of country codes
    public String[] getBorders() {
        return borders;
    }

    //some area values are empty or hexa, replace with valid data in order to sort properly
    private String validateArea(String area) {
        //for Antarctica the area has hexa format - "1.4E7"
        if (area == null) {
            return "0.0";
        }
        //for Antarctica and Russia ("1.4E7")
        if (area.toUpperCase().contains("E")) {
            BigDecimal bigDecimalValue = new BigDecimal(area);
            try {
                area = bigDecimalValue.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return area;
    }

    private String validateEnglishName(String name) {
        if(name == null) {
            return ""; //in case the name is null replace with empty string
        }
        //one country returns with non english characters in english name value - "name":"Åland Islands"
        //in order to sort properly by name, replace with english "A"
        if(name.contains("Å")) {
            name = name.replace("Å", "A");
        }
        return name;
    }
}
