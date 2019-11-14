package com.technicalanalysis.backend.chartServices;


import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;
import com.technicalanalysis.backend.services.LeastSquaresCalculator;

import java.util.ArrayList;

public class LeastSquareService {

    private int neighbourNumber = 30;
    private LeastSquaresCalculator leastSquaresCalculator;

    public LeastSquareService(){
        leastSquaresCalculator = new LeastSquaresCalculator();
    }

    public ArrayList<XYobject> getLeastSquares(ArrayList<XYobject> chartPoints){
        int k = neighbourNumber / 2;
        FifoQueue fifo = new FifoQueue(k);
        ArrayList<XYobject> squaredPoints = new ArrayList<>();
        for(int i=0; i<chartPoints.size()-k; i++){
            fifo.addElement(i, chartPoints.get(i).getY());
            if(i<k){
                // TODO pomyslec o poczatkowych elementach listy
                squaredPoints.add(new XYobject(chartPoints.get(i).getX(), (float)0.0));
            }
            else{
                squaredPoints.add(new XYobject(chartPoints.get(i).getX(), leastSquaresCalculator.calculate(fifo.getKeys(), fifo.getValues())));
            }
        }
        return squaredPoints;
    }
}
