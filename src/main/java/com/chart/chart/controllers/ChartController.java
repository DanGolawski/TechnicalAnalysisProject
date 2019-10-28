package com.chart.chart.controllers;

import com.chart.chart.services.HistoricalDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {

    HistoricalDataService historicalDataService;

    public ChartController(){
        historicalDataService = new HistoricalDataService();
    }

    @RequestMapping(value = "/chartdata")
    public String getChartData(){

        String data = historicalDataService.getHistoricalData();
        return null;
    }
}
