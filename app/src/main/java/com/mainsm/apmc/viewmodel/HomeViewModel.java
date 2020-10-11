package com.mainsm.apmc.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mainsm.apmc.R;
import com.mainsm.apmc.constants.Constants;
import com.mainsm.apmc.interfaces.HomeListener;
import com.mainsm.apmc.model.APMCResponse;
import com.mainsm.apmc.model.citiesModel.CitiesResponse;
import com.mainsm.apmc.retrofit.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName() ;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void getCities(HomeListener homeListener){
        RetrofitClient.getInstance().getApi().getCitiesData().enqueue(new Callback<List<CitiesResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<CitiesResponse>> call, @NotNull Response<List<CitiesResponse>> response) {
                if(response.isSuccessful() || null != response.body()){
                    editor.putString(Constants.RESPONSE2, new Gson().toJson(response.body()));
                    editor.apply();
                    Log.e(TAG, "onResponse: " + response.body() );
                    if(null != homeListener)
                        homeListener.setCities(response.body());

                }else {
                    Toast.makeText(getApplication(), R.string.try_again, Toast.LENGTH_LONG).show();
                    try {
                        if(null != response.errorBody())
                            Log.e(TAG, "onResponse FAILED: " + response.errorBody().string() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CitiesResponse>> call, @NotNull Throwable t) {
                Toast.makeText(getApplication(), R.string.server_error, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    public void getStates(HomeListener homeListener){
        RetrofitClient.getInstance().getApi().getData().enqueue(new Callback<List<APMCResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<APMCResponse>> call, @NotNull Response<List<APMCResponse>> response) {
                if(response.isSuccessful() || null != response.body()){
                    editor.putString(Constants.RESPONSE, new Gson().toJson(response.body()));
                    editor.apply();
                    Log.e(TAG, "onResponse: " + response.body() );
                    if(null != homeListener)
                        homeListener.setData(response.body());
                }else {
                    Toast.makeText(getApplication(), R.string.try_again, Toast.LENGTH_LONG).show();
                    try {
                        if(null != response.errorBody())
                            Log.e(TAG, "onResponse FAILED: " + response.errorBody().string() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<APMCResponse>> call, @NotNull Throwable t) {
                Toast.makeText(getApplication(), R.string.server_error, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    public List<APMCResponse> getStateList(){
        Type type = new TypeToken<List<APMCResponse>>() {
        }.getType();
        List<APMCResponse> responseList = new Gson().fromJson(sharedPreferences.getString(Constants.RESPONSE, ""), type);
        if (responseList == null) {
            responseList = new ArrayList<>();
        }
        return responseList;
    }

    public List<CitiesResponse> getCitiesList(){
        Type type = new TypeToken<List<CitiesResponse>>() {
        }.getType();
        List<CitiesResponse> responseList = new Gson().fromJson(sharedPreferences.getString(Constants.RESPONSE2, ""), type);
        if (responseList == null) {
            responseList = new ArrayList<>();
        }
        return responseList;
    }
}
