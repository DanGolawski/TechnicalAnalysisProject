package com.technicalanalysis.backend.services;

import com.google.gson.Gson;
import com.technicalanalysis.backend.models.MarketDay;
import org.json.JSONArray;

import java.util.ArrayList;

public class ResponseArrayConverter {

    public ArrayList<MarketDay> convertJsonArrayToArray(JSONArray array){
        Gson gson=new Gson();
        ArrayList<MarketDay> marketDays = new ArrayList<>();
        for(int i=0; i<array.length(); i++){
            marketDays.add(gson.fromJson(String.valueOf(array.getJSONObject(i)),MarketDay.class));
        }
        return marketDays;
    }
}
