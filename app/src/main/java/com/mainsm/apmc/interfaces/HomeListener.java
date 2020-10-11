package com.mainsm.apmc.interfaces;

import com.mainsm.apmc.model.APMCResponse;
import com.mainsm.apmc.model.citiesModel.CitiesResponse;

import java.util.List;

public interface HomeListener {
    void setData(List<APMCResponse> data);
    void setCities(List<CitiesResponse> citiesResponses);
}
