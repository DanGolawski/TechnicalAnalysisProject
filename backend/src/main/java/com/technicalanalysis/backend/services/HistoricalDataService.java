package com.technicalanalysis.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HistoricalDataService {


    private ArrayList marketDayList;
    private ObjectMapper objectMapper;
    private HttpEntity httpEntity;
    private HttpResponse httpResponse;
    private JSONObject result;
    private String stringObject;
    private HttpClient httpClient;
    private HttpGet httpGet;

    public HistoricalDataService(){
        marketDayList = new ArrayList<>();
        objectMapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet("https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=USD&limit=1000&aggregate=1&e=CCCAGG&api_key=36c4b7b0b3c5ef112e707419205a12e8d1a7f85fb5d87b6b23df0413986bfdfe");

    }

    public JSONArray getHistoricalData() {


        try {
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // Read the contents of an entity and return it as a String.
            stringObject = EntityUtils.toString(httpEntity);
            result = new JSONObject(stringObject);
            return result.getJSONObject("Data").getJSONArray("Data");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
