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
    private JSONArray array;

    public ChartController(){
        historicalDataService = new HistoricalDataService();
        responseArrayConverter = new ResponseArrayConverter();
    }

    @RequestMapping(value = "/chartdata")
    public ArrayList<MarketDay> getChartData(){

        array = historicalDataService.getHistoricalData();

        return responseArrayConverter.convertJsonArrayToArray(array);
    }
}
