package com.technicalanalysis.backend.controllers;

import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.HistoricalDataService;
import com.technicalanalysis.backend.services.ResponseArrayConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ChartController {

    private HistoricalDataService historicalDataService;
    private ResponseArrayConverter responseArrayConverter;
    private ArrayList<MarketDay> array;

    public ChartController(){
        historicalDataService = new HistoricalDataService();
        responseArrayConverter = new ResponseArrayConverter();
    }

    @RequestMapping(value = "/chartdata")
    public ArrayList<MarketDay> getChartData(){

        return responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());


    }

    @RequestMapping(value = "/highestPrices")
    public ArrayList<XYobject> getHighestPrices(){
        ArrayList<MarketDay> arrayy = new ArrayList<>();
        ArrayList<XYobject> highestPrices = new ArrayList<>();
        arrayy = responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());
        for(MarketDay marketDay: arrayy){
            // TODO zrobic konwertowanie czasu na date
            highestPrices.add(new XYobject("Data X", marketDay.getHigh()));
        }
        System.out.println(highestPrices);
        return highestPrices;
    }
}
