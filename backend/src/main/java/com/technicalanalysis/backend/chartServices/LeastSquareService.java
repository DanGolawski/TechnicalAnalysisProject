package com.technicalanalysis.backend.chartServices;


import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;
import com.technicalanalysis.backend.services.LeastSquaresCalculator;

import java.util.ArrayList;

public class LeastSquareService {

    private LeastSquaresCalculator leastSquaresCalculator;

    public LeastSquareService(){
        leastSquaresCalculator = new LeastSquaresCalculator();
    }

    /**
     * metoda wylicza linie metoda najmniejszych kwadratow
     * @param chartPoints
     * @param neighbourNumber
     * @return
     */
    public ArrayList<XYobject> getLeastSquares(ArrayList<XYobject> chartPoints, int neighbourNumber){
        int k = neighbourNumber / 2;
        FifoQueue<Integer, Float> fifo = new FifoQueue<>(neighbourNumber + 1);
        ArrayList<XYobject> squaredPoints = new ArrayList<>();
        for(int i=0; i<chartPoints.size(); i++){
            fifo.addElement(i, chartPoints.get(i).getY());
            if(i<k){
                squaredPoints.add(new XYobject(chartPoints.get(i).getX(), chartPoints.get(0).getY()));
            }
            else if(i > neighbourNumber){
                squaredPoints.add(new XYobject(chartPoints.get(i).getX(), leastSquaresCalculator.calculate(fifo.getKeys(), fifo.getValues())));
            }
        }
        return squaredPoints;
    }



}
