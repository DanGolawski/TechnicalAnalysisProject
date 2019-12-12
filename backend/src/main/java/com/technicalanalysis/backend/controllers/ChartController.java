package com.technicalanalysis.backend.controllers;

import com.technicalanalysis.backend.chartServices.*;
import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.Rating;
import com.technicalanalysis.backend.models.XYobject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ChartController {

    // TODO sprawdzic, czy nie da sie zastapic list wywolaniem price service

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
    private ArrayList<XYobject> leastSquaresOfHighestPrices;
    // the list of least squares of lowest prices
    private ArrayList<XYobject> leastSquaresOfLowestPrices;
    // the service that handles method to find support and resistance levels
    private SupportResistanceLevelsService supportResistanceLevelsService;
    // the set consist of resistance levels (without duplicates)
    private Set<Float> resistanceLevels;
    // the set consist of support levels (without duplicates)
    private Set<Float> supportLevels;
    // the array of close prices
    private ArrayList<XYobject> closePrices;
    // The service that calculates moving average
    private MovingAverageService movingAverageService;

    /**
     * object initialization, getting and extracting data
     */
    public ChartController(){
        marketDataService = new MarketDataService();
        marketDaysArray = marketDataService.getHistoricalData();
        pricesService = new PricesService(marketDaysArray);
        leastSquareService = new LeastSquareService();
        supportResistanceLevelsService = new SupportResistanceLevelsService();
        movingAverageService = new MovingAverageService();
        getAllTheData();
    }

    /**
     * this method starts calculating several data to display in frontend
     */
    private void getAllTheData(){
        highestPrices = pricesService.getHighestPrices();
        lowestPrices = pricesService.getLowestPrices();
        leastSquaresOfHighestPrices = leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(highestPrices,30),90),30);
        leastSquaresOfLowestPrices = leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(leastSquareService.getLeastSquares(lowestPrices,30),90),30);
        resistanceLevels = supportResistanceLevelsService.getResistanceLevels(highestPrices, leastSquaresOfHighestPrices);
        supportLevels = supportResistanceLevelsService.getSupportLevels(lowestPrices, leastSquaresOfLowestPrices);
        closePrices = pricesService.getClosePrices();
    }

    /**
     * This method returns the highest prices per the day
     * @return array of objects X-date, Y-price
     */
    @GetMapping(value = "/highestPrices")
    private ArrayList<XYobject> getHighestPrices(){
        return highestPrices;
    }

    /**
     * This method returns the lowest prices per the day
     * @return array of objects X-date, Y-price
     */
    @GetMapping(value = "/lowestPrices")
    private ArrayList<XYobject> getLowestPrices(){
        return lowestPrices;
    }


    /**
     * this method calculates least squares of the highest prices values (3 times to eliminate undesirable peaks)
     * @return the array of squared values
     */
    @GetMapping(value = "/leastSquareHigh")
    private ArrayList<XYobject> getSquaredHighestPrices(){
        return leastSquaresOfHighestPrices;
    }

    /**
     * this method calculates least squares of the lowest prices values (3 times to eliminate undesirable holes)
     * @return the array of squared values
     */
    @GetMapping(value = "/leastSquareLow")
    private ArrayList<XYobject> getSquaredLowestPrices(){
        return leastSquaresOfLowestPrices;
    }


    /**
     * this method extract peaks that appoint resistance levels
     * @return set of unique resistance levels
     */
    @GetMapping(value = "/resistance")
    private Set<Float> getResistanceLevels() {
        return resistanceLevels;
    }

    /**
     * this method extract holes that appoint support levels
     * @return set of unique support levels
     */
    @GetMapping(value = "/support")
    private Set<Float> getSupportLevels() {
        return supportLevels;
    }

    @RequestMapping(value = "/SimpleMovingAverage/{period}")
    private ArrayList<XYobject> getArrayOfPricesCalculatedBySimpleMovingAverage(@PathVariable("period") int period) {
        return movingAverageService.getArrayOfPricesCalculatedBySimpleMovingAverage(closePrices, period);
    }

    @RequestMapping(value = "/WeightedMovingAverage/{period}")
    private ArrayList<XYobject> getArrayOfPricesCalculatedByWeightedMovingAverage(@PathVariable("period") int period) {
        return movingAverageService.getArrayOfPricesCalculatedByWeightedMovingAverage(closePrices, period);
    }

    @RequestMapping(value = "/ExponentialMovingAverage/{period}")
    private ArrayList<XYobject> getArrayOfPricesCalculatedByExponentialMovingAverage(@PathVariable("period") int period) {
        return movingAverageService.getArrayOfPricesCalculatedByExponentialMovingAverage(closePrices, period);
    }

    @RequestMapping(value = "/HullMovingAverage/{period}")
    private ArrayList<XYobject> getArrayOfPricesCalculatedByHullMovingAverage(@PathVariable("period") int period){
        return movingAverageService.getArrayOfPricesCalculatedByHullMovingAverage(closePrices, period);
    }


    // TODO zaimplementować algorytm ratingowy
    // TODO napisać testy

    @RequestMapping(value = "/rating/periods")
    @ResponseBody
    public Rating makeRatingBasedOnPredictors(@RequestParam List<Integer> period, @RequestParam int hullperiod) {
        //return "IDs are " + id;
//        period.forEach(x -> {
//            System.out.println("---------------------------" + x);
//        });
//        System.out.println("================" + hullperiod);
        RatingService ratingService = new RatingService();
        return ratingService.rate(period, hullperiod, closePrices);
    }
}
