package com.example.countries.repositories;

import com.example.countries.models.CountryResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesApi {

    String BASE_URL = "https://restcountries.eu/rest/v2/"; //?fields=name;nativeName;area;alpha3Code;borders
    @GET("all")
    Call<List<CountryResponseData>> getCountriesData();
}
