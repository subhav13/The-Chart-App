package com.mainsm.apmc.retrofit;

import com.mainsm.apmc.model.APMCResponse;
import com.mainsm.apmc.model.citiesModel.CitiesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

@GET("data/States?relationsDepth=3")
    Call<List<APMCResponse>> getData();

@GET("data/Cities?relationsDepth=2")
    Call<List<CitiesResponse>> getCitiesData();

}
