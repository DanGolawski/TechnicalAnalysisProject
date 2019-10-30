package com.technicalanalysis.backend.controllers;

import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.services.HistoricalDataService;
import com.technicalanalysis.backend.services.ResponseArrayConverter;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ChartController {

    private HistoricalDataService historicalDataService;
    private ResponseArrayConverter responseArrayConverter;
    private ArrayList<MarketDay> array;
    private ArrayList<Float> highestPrices;

    public ChartController(){
        historicalDataService = new HistoricalDataService();
        responseArrayConverter = new ResponseArrayConverter();
        highestPrices = new ArrayList<>();
    }

    @RequestMapping(value = "/chartdata")
    public ArrayList<Float> getChartData(){

        array = responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());
        highestPrices.clear();
        for(MarketDay marketDay: array){
            highestPrices.add(marketDay.getHigh());
        }
        System.out.println(highestPrices);
        return highestPrices;
    }
}
