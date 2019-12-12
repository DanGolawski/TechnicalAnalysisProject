package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.Rating;
import com.technicalanalysis.backend.models.RatingItem;
import com.technicalanalysis.backend.models.XYobject;

import java.util.ArrayList;
import java.util.List;

public class RatingService {

    private MovingAverageService movingAverageService;

    public RatingService(){
        movingAverageService = new MovingAverageService();
    }

    public Rating rate(List<Integer> periods, int hullperiod, ArrayList<XYobject> closePrices){
        // periods, hullPeriod, closePrice
        float todaysClosePrice = closePrices.get(closePrices.size()-1).getY();
        Rating rating = new Rating(periods, hullperiod, todaysClosePrice);
        // movingAverages
        periods.forEach(period -> {
            float[] lastPrices = getLastClosePrices(closePrices, period);
            float simpleMovingAverage = movingAverageService.calculateSimpleMovingAverage(lastPrices);
            rating.addMovingAverage(new RatingItem("SMA", period, simpleMovingAverage, todaysClosePrice > simpleMovingAverage));
            float exponentialMovingAverage = movingAverageService.calculateExponentialMovingAverage(lastPrices);
            rating.addMovingAverage(new RatingItem("EMA", period, exponentialMovingAverage, todaysClosePrice > exponentialMovingAverage));
        });
        float hullMovingAverage = movingAverageService.getArrayOfPricesCalculatedByHullMovingAverage(closePrices, hullperiod).get(closePrices.size()-1).getY();
        // hullMovingAverage
        rating.addMovingAverage(new RatingItem("HMA", hullperiod, hullMovingAverage, todaysClosePrice > hullMovingAverage));
        rating.makeVerdict();
        return rating;
    }

    private float[] getLastClosePrices(ArrayList<XYobject> closePrices, int period) {
        int arraySize = closePrices.size();
        float[] lastPrices = new float[period];
        for(int i = 0; i < period; i++) {
            lastPrices[i] = closePrices.get(arraySize - period + i).getY();
        }
        return lastPrices;
    }
}
