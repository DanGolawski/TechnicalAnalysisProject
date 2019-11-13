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
    @RequestMapping(value = "/leastSquareHigh")
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
        System.out.println("LEAST SQUARES : " + data);
        return calculateLeastSquares(calculateLeastSquares(data));
    }


    /**
     * metoda wylicza linie metoda najmniejszych kwadratow
     * @param objectArray
     * @return
     */
    private ArrayList<XYobject> calculateLeastSquares(ArrayList<XYobject> objectArray){
        LeastSquaresCalculator leastSquaresCalculator = new LeastSquaresCalculator();
        ArrayList<XYobject> array = new ArrayList<>();
        int k = 30;
        for(int i=0; i<k; i++){
            array.add(objectArray.get(i));
        }
        for(int i=k; i<objectArray.size()-k; i++){
            ArrayList<Integer> x = new ArrayList<>();
            ArrayList<Float> y = new ArrayList<>();
            for(int j=0; j<k*2+1; j++){
                x.add(i-k+j);
                y.add(objectArray.get(i-k+j).getY());
            }
            array.add(new XYobject("Data XX", leastSquaresCalculator.calculate2(x, y)));
        }

        return array;
    }

    /**
     * metoda wyznacza punkty przez metode najmniejszych kwadratow aby zlagodzic outliery
     * @return
     */
    @RequestMapping(value = "/leastSquareLow")
    public ArrayList<XYobject> getHoles(){
        ArrayList<Float> points = new ArrayList<>();
        ArrayList<XYobject> data = new ArrayList<>();
        int k = 8; // nearest neighbours number
        k = k / 2;
        for(int i=0; i<k*1.5;i++){data.add(new XYobject("Data XX", (float) 0.0));}
        LeastSquaresCalculator leastSquaresCalculator = new LeastSquaresCalculator();
        for(MarketDay md : array){
            points.add(md.getLow());
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
        System.out.println("LEAST SQUARES : " + data);
        return calculateLeastSquares(calculateLeastSquares(data));
    }


    /**
     * ta metoda wyznacza poziomy wsparcia i oporu
     */
    @RequestMapping(value = "/resistance")
    public ArrayList<Float> getResistanceLevels(){
        // lista na podstawie wykresu najmniejszych kwadratow
        ArrayList<XYobject> squares = getPeaks();
        // lista punktow poziomu oporu
        ArrayList<Float> peaks = new ArrayList<>();
        // lista najwyzszych cen per dzien
        ArrayList<XYobject> prices = getHighestPrices();
        // lista szczytow na wykresie cen
        ArrayList<Float> peaksPrices = new ArrayList<>();
        // filtruje punkty ktore sa pikami wykresu najmniejszych kwadratow

        for(int i=3; i<squares.size()-3; i++){
            // POZIOMY OPORU
            if(squares.get(i-3).getY() < squares.get(i-2).getY() && squares.get(i-2).getY() < squares.get(i-1).getY() && squares.get(i-1).getY() < squares.get(i).getY() && squares.get(i).getY() > squares.get(i+1).getY() && squares.get(i+1).getY() > squares.get(i+2).getY() && squares.get(i+2).getY() > squares.get(i+3).getY()){
//                peaks.add(squares.get(i-1).getY());
                float x = 0;
                if(i >= 40) {
                    for (int j = -40; j < 40; j++) {
                        if (prices.get(i + j).getY() > x) {
                            x = prices.get(i + j).getY();
                        }
                    }
                }
                System.out.println(squares.get(i-3).getY() + "   " + squares.get(i-2).getY() + "   " + squares.get(i-1).getY() + "   --- " + squares.get(i).getY() + " ---   " + squares.get(i+1).getY() + "   " + squares.get(i+2).getY() + "   " + squares.get(i+3).getY() + "____________________" + x);
                peaks.add(x);
            }
//            if(squares.get(i-3).getY() < squares.get(i-2).getY() && squares.get(i-2).getY() < squares.get(i-1).getY() && squares.get(i-1).getY() < squares.get(i).getY() && squares.get(i).getY() > squares.get(i+1).getY() && squares.get(i+1).getY() > squares.get(i+2).getY() && squares.get(i+2).getY() > squares.get(i+3).getY()){
////                peaks.add(squares.get(i-1).getY());
//                System.out.println(squares.get(i-3).getY() + "   " + squares.get(i-2).getY() + "   " + squares.get(i-1).getY() + "   --- " + squares.get(i).getY() + " ---   " + squares.get(i+1).getY() + "   " + squares.get(i+2).getY() + "   " + squares.get(i+3).getY());
//
//                peaks.add(squares.get(i).getY());
//            }
        }


        return peaks;
    }


    @RequestMapping(value = "/support")
    public ArrayList<Float> getSupportLevels(){
        // lista na podstawie wykresu najmniejszych kwadratow
        ArrayList<XYobject> squares = getHoles();
        // lista punktow poziomu wsparcia
        ArrayList<Float> peaks = new ArrayList<>();
        // lista najnizszych cen per dzien
        ArrayList<XYobject> prices = getLowestPrices();
        // lista szczytow na wykresie cen
        ArrayList<Float> peaksPrices = new ArrayList<>();
        // filtruje punkty ktore sa pikami wykresu najmniejszych kwadratow

        for(int i=3; i<squares.size()-3; i++){
//            // POZIOMY WSPARCIA
            if(squares.get(i-3).getY() > squares.get(i-2).getY() && squares.get(i-2).getY() > squares.get(i-1).getY() && squares.get(i-1).getY() > squares.get(i).getY() && squares.get(i).getY() < squares.get(i+1).getY() && squares.get(i+1).getY() < squares.get(i+2).getY() && squares.get(i+2).getY() < squares.get(i+3).getY()){
//                peaks.add(squares.get(i-1).getY());
                float x = prices.get(i).getY();
                if(i >= 10){
                    for(int j=-10; j<10; j++){
                        if(prices.get(i+j).getY() < x){
                            x = prices.get(i+j).getY();
                        }
                    }
                    System.out.println(squares.get(i-3).getY() + "   " + squares.get(i-2).getY() + "   " + squares.get(i-1).getY() + "   --- " + squares.get(i).getY() + " ---   " + squares.get(i+1).getY() + "   " + squares.get(i+2).getY() + "   " + squares.get(i+3).getY() + "____________________" + x);
                    peaks.add(x);
                }

            }
//            if(squares.get(i-3).getY() > squares.get(i-2).getY() && squares.get(i-2).getY() > squares.get(i-1).getY() && squares.get(i-1).getY() > squares.get(i).getY() && squares.get(i).getY() < squares.get(i+1).getY() && squares.get(i+1).getY() < squares.get(i+2).getY() && squares.get(i+2).getY() < squares.get(i+3).getY()){
////                peaks.add(squares.get(i-1).getY());
//                System.out.println(squares.get(i-3).getY() + "   " + squares.get(i-2).getY() + "   " + squares.get(i-1).getY() + "   --- " + squares.get(i).getY() + " ---   " + squares.get(i+1).getY() + "   " + squares.get(i+2).getY() + "   " + squares.get(i+3).getY());
//
//                peaks.add(squares.get(i).getY());
//            }
        }


        return peaks;
    }


//    public void getSupportResistanceLevels(){
//        ArrayList<Float> resistanceLevels = getResistanceLevels();
//        ArrayList<Float> supportLevels = getSupportLevels();
//
//        for()
//    }


}
