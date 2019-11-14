package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.ServerDateFormatter;

import java.util.ArrayList;

/**
 * this class gets highest prices per day from the main data array
 */
public class PricesService {

    private ArrayList<XYobject> highestPricesArray;
    private ArrayList<XYobject> lowestPricesArray;
    private ServerDateFormatter serverDateFormatter;

    public PricesService(ArrayList<MarketDay> marketDaysArray){
        highestPricesArray = new ArrayList<>();
        lowestPricesArray = new ArrayList<>();
        serverDateFormatter = new ServerDateFormatter();
        extractData(marketDaysArray);
    }

    private void extractData(ArrayList<MarketDay> marketDaysArray){
        long day;
        for(MarketDay marketDay: marketDaysArray){
            // here array gets objects containing date parameter as X and the highest price as Y
            day = marketDay.getTime();
            highestPricesArray.add(new XYobject(serverDateFormatter.formatServerDateToSimpleDate(day), marketDay.getHigh()));
            lowestPricesArray.add(new XYobject(serverDateFormatter.formatServerDateToSimpleDate(day), marketDay.getLow()));
        }
    }



    // GETTERS //
    public ArrayList<XYobject> getHighestPrices(){
        return highestPricesArray;
    }

    public ArrayList<XYobject> getLowestPrices(){
        return lowestPricesArray;
    }

}
