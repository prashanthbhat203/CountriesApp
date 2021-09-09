package com.example.countries.api;

import com.example.countries.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryAPI {

    @GET("/v2/continent/asia")
    Call<List<Country>> getCountryList();
}
