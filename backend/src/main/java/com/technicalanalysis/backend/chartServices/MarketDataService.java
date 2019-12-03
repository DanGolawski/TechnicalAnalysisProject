package com.technicalanalysis.backend.chartServices;

import com.google.gson.Gson;
import com.technicalanalysis.backend.models.MarketDay;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * this class is used to get all the data of 1000 days past
 */
public class MarketDataService {

    private HttpEntity httpEntity;
    private HttpResponse httpResponse;
    private String stringObject;
    private HttpClient httpClient;
    private HttpGet httpGet;
    private ArrayList<MarketDay> marketDayArray;
    private Gson gson;
    private ArrayList<MarketDay> marketDays;


    public MarketDataService(){
        httpClient = HttpClientBuilder.create().build();
        httpGet = new HttpGet("https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=USD&limit=1000&aggregate=1&e=CCCAGG&api_key=36c4b7b0b3c5ef112e707419205a12e8d1a7f85fb5d87b6b23df0413986bfdfe");
        gson = new Gson();
        marketDays = new ArrayList<>();
        downloadHistoricalData();
    }

    /**
     * this method returns list of objects containing all the data of particular day
     * @return array of 1000 elements containing day info
     */
    public void downloadHistoricalData() {
        try {
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // Read the contents of an entity and return it as a String.
            stringObject = EntityUtils.toString(httpEntity);
            marketDayArray = convertJsonArrayToArray(new JSONObject(stringObject).getJSONObject("Data").getJSONArray("Data"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MarketDay> convertJsonArrayToArray(JSONArray array){
        marketDays.clear();
        for(int i=0; i<array.length(); i++){
            marketDays.add(gson.fromJson(String.valueOf(array.getJSONObject(i)),MarketDay.class));
        }
        return marketDays;
    }

    // GETTER //
    public ArrayList<MarketDay> getHistoricalData(){
        return marketDayArray;
    }
}
