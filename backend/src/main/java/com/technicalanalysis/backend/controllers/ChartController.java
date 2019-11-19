package com.technicalanalysis.backend.controllers;

import com.technicalanalysis.backend.chartServices.LeastSquareService;
import com.technicalanalysis.backend.chartServices.PricesService;
import com.technicalanalysis.backend.chartServices.ResistanceLevelsService;
import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.chartServices.MarketDataService;
import com.technicalanalysis.backend.services.LeastSquaresCalculator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ChartController {

    private MarketDataService marketDataService;
    // the service that gets highest and lowest prices
    private PricesService pricesService;
    // the service that gets least squares of highest and lowest prices
    private LeastSquareService leastSquareService;
    private ArrayList<MarketDay> marketDaysArray;
    // the list of highest prices per day
    private ArrayList<XYobject> highestPrices;
    // the list of lowest prices per day
    private ArrayList<XYobject> lowestPrices;
    // the list of least squares of highest prices
    private ArrayList<Float> leastSquaresOfHighestPrices;

    /**
     * object initialization, getting and extracting data
     */
    public ChartController(){
        marketDataService = new MarketDataService();
        marketDaysArray = marketDataService.getHistoricalData();
        pricesService = new PricesService(marketDaysArray);
        leastSquareService = new LeastSquareService();
        highestPrices = new ArrayList<>();
        lowestPrices = new ArrayList<>();
        leastSquaresOfHighestPrices = new ArrayList<>();
        getAllTheData();
    }

    private void getAllTheData(){
        highestPrices = pricesService.getHighestPrices();
        lowestPrices = pricesService.getLowestPrices();
        leastSquaresOfHighestPrices =
    }

    /**
     * This method returns the highest prices per the day
     * @return array of objects X-date, Y-price
     */
    @RequestMapping(value = "/highestPrices")
    private ArrayList<XYobject> getHighestPrices(){
        return highestPrices;
    }

    /**
     * This method returns the lowest prices per the day
     * @return array of objects X-date, Y-price
     */
    @RequestMapping(value = "/lowestPrices")
    private ArrayList<XYobject> getLowestPrices(){
        return lowestPrices;
    }


    /**
     * this method calculates least squares of the highest prices values (3 times to eliminate undesirable peaks)
     * @return the array of squared values
     */
    @RequestMapping(value = "/leastSquareHigh")
    private ArrayList<XYobject> getPeaks(){
        return leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(highestPrices,30),60),60);
    }

    /**
     * this method calculates least squares of the lowest prices values (3 times to eliminate undesirable holes)
     * @return the array of squared values
     */
    @RequestMapping(value = "/leastSquareLow")
    public ArrayList<XYobject> getHoles(){
        return leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(lowestPrices,30),60),60);
    }


    @RequestMapping(value = "/resistance")
    public ArrayList<Float> getResistanceLevels() {
        ResistanceLevelsService resistanceLevelsService = new ResistanceLevelsService();
        // TODO niekompatybilne typy float i XYobject
        resistanceLevelsService.getLevels(highestPrices, );
    }





    /**
     * ta metoda wyznacza poziomy wsparcia i oporu
     */
//    @RequestMapping(value = "/resistance")
//    public ArrayList<Float> getResistanceLevels(){
//        ResistanceLevelsService resistanceLevelsService = new ResistanceLevelsService();
//        resistanceLevelsService.getLevels();
//        // lista na podstawie wykresu najmniejszych kwadratow
//        ArrayList<XYobject> squares = getPeaks();
//        // lista punktow poziomu oporu
//        ArrayList<Float> peaks = new ArrayList<>();
//        // lista najwyzszych cen per dzien
//        ArrayList<XYobject> prices = getHighestPrices();
//        // lista szczytow na wykresie cen
//        ArrayList<Float> peaksPrices = new ArrayList<>();
//        // filtruje punkty ktore sa pikami wykresu najmniejszych kwadratow
//
//        for(int i=3; i<squares.size()-3; i++){
//            // POZIOMY OPORU
//            if(squares.get(i-3).getY() < squares.get(i-2).getY() && squares.get(i-2).getY() < squares.get(i-1).getY() && squares.get(i-1).getY() < squares.get(i).getY() && squares.get(i).getY() > squares.get(i+1).getY() && squares.get(i+1).getY() > squares.get(i+2).getY() && squares.get(i+2).getY() > squares.get(i+3).getY()){
////                peaks.add(squares.get(i-1).getY());
//                float x = 0;
//                if(i >= 40) {
//                    for (int j = -40; j < 40; j++) {
//                        if (prices.get(i + j).getY() > x) {
//                            x = prices.get(i + j).getY();
//                        }
//                    }
//                }
//                System.out.println(squares.get(i-3).getY() + "   " + squares.get(i-2).getY() + "   " + squares.get(i-1).getY() + "   --- " + squares.get(i).getY() + " ---   " + squares.get(i+1).getY() + "   " + squares.get(i+2).getY() + "   " + squares.get(i+3).getY() + "____________________" + x);
//                peaks.add(x);
//            }
////
//        }


//        return peaks;
//    }


//    @RequestMapping(value = "/support")
//    public ArrayList<Float> getSupportLevels(){
//        // lista na podstawie wykresu najmniejszych kwadratow
//        ArrayList<XYobject> squares = getHoles();
//        // lista punktow poziomu wsparcia
//        ArrayList<Float> peaks = new ArrayList<>();
//        // lista najnizszych cen per dzien
//        ArrayList<XYobject> prices = getLowestPrices();
//        // lista szczytow na wykresie cen
//        ArrayList<Float> peaksPrices = new ArrayList<>();
//        // filtruje punkty ktore sa pikami wykresu najmniejszych kwadratow
//
//        for(int i=3; i<squares.size()-3; i++){
////            // POZIOMY WSPARCIA
//            if(squares.get(i-3).getY() > squares.get(i-2).getY() && squares.get(i-2).getY() > squares.get(i-1).getY() && squares.get(i-1).getY() > squares.get(i).getY() && squares.get(i).getY() < squares.get(i+1).getY() && squares.get(i+1).getY() < squares.get(i+2).getY() && squares.get(i+2).getY() < squares.get(i+3).getY()){
////                peaks.add(squares.get(i-1).getY());
//                float x = prices.get(i).getY();
//                if(i >= 10){
//                    for(int j=-10; j<10; j++){
//                        if(prices.get(i+j).getY() < x){
//                            x = prices.get(i+j).getY();
//                        }
//                    }
////                    System.out.println(squares.get(i-3).getY() + "   " + squares.get(i-2).getY() + "   " + squares.get(i-1).getY() + "   --- " + squares.get(i).getY() + " ---   " + squares.get(i+1).getY() + "   " + squares.get(i+2).getY() + "   " + squares.get(i+3).getY() + "____________________" + x);
////                    peaks.add(x);
//                }
//
//            }
//
//        }
//
//
//        return peaks;
//    }
//




}
