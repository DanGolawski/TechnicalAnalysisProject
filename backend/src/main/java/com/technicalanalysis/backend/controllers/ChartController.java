package com.technicalanalysis.backend.controllers;

import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.HistoricalDataService;
import com.technicalanalysis.backend.services.LeastSquaresCalculator;
import com.technicalanalysis.backend.services.ResponseArrayConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

@RestController
public class ChartController {

    private HistoricalDataService historicalDataService;
    private ResponseArrayConverter responseArrayConverter;
    private ArrayList<MarketDay> array;

    public ChartController(){
        historicalDataService = new HistoricalDataService();
        responseArrayConverter = new ResponseArrayConverter();
        array = responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());
    }

    @RequestMapping(value = "/chartdata")
    public ArrayList<MarketDay> getChartData(){

        return responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());


    }

    /**
     * Ta metoda zwraca najwyzsze ceny per dzien
     * @return
     */
    @RequestMapping(value = "/highestPrices")
    public ArrayList<XYobject> getHighestPrices(){
        ArrayList<XYobject> highestPrices = new ArrayList<>();
//        arrayy = responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());
        for(MarketDay marketDay: array){
            // TODO zrobic konwertowanie czasu na date
            highestPrices.add(new XYobject("Data X", marketDay.getHigh()));
        }
        System.out.println(highestPrices);
        return highestPrices;
    }

    /**
     * ta metoda zwraca najmniejsze ceny per dzien
     * @return
     */
    @RequestMapping(value = "/lowestPrices")
    public ArrayList<XYobject> getLowestPrices(){
        ArrayList<XYobject> lowestPrices = new ArrayList<>();
//        arrayy = responseArrayConverter.convertJsonArrayToArray(historicalDataService.getHistoricalData());
        for(MarketDay marketDay: array){
            // TODO zrobic konwertowanie czasu na date
            lowestPrices.add(new XYobject("Data X", marketDay.getLow()));
        }
        System.out.println(lowestPrices);
        return lowestPrices;
    }

    /**
     * metoda wyznacza punkty przez metode najmniejszych kwadratow aby zlagodzic outliery
     * @return
     */
    @RequestMapping(value = "/leastSquares")
    public ArrayList<XYobject> getPeaks(){
        ArrayList<Float> points = new ArrayList<>();
        ArrayList<XYobject> data = new ArrayList<>();
        int k = 8; // nearest neighbours number
        k = k / 2;
        for(int i=0; i<k*1.5;i++){data.add(new XYobject("Data XX", (float) 0.0));}
        LeastSquaresCalculator leastSquaresCalculator = new LeastSquaresCalculator();
        for(MarketDay md : array){
            points.add(md.getHigh());
        }
        for(int i=k; i<points.size()-k; i++){
//            data.add(new XYobject("Data XX", leastSquaresCalculator.calculate(i-1, points.get(i-1), i, points.get(i), i+1, points.get(i+1))));
            ArrayList<Integer> x = new ArrayList<>();
            ArrayList<Float> y = new ArrayList<>();
            for(int j=0; j<k*2+1; j++){
                x.add(i-k+j);
                y.add(points.get(i-k+j));
            }
            data.add(new XYobject("Data XX", leastSquaresCalculator.calculate2(x, y)));
        }
//        for(int i=0; i<k;i++){data.add(new XYobject("Data XX", (float) 0.0));}
        return data;
    }
}
