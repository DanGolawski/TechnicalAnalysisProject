package com.technicalanalysis.backend.chartServices;


import com.technicalanalysis.backend.models.MarketDay;
import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;
import com.technicalanalysis.backend.services.LeastSquaresCalculator;

import java.util.ArrayList;

public class LeastSquareService {

    private LeastSquaresCalculator leastSquaresCalculator;

    public LeastSquareService(){
        leastSquaresCalculator = new LeastSquaresCalculator();
    }

    public ArrayList<XYobject> getLeastSquares(ArrayList<XYobject> chartPoints, int neighbourNumber){
        int k = neighbourNumber / 2;
        FifoQueue<Integer, Float> fifo = new FifoQueue<>(neighbourNumber);
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

    /**
     * metoda wylicza linie metoda najmniejszych kwadratow
     * @param objectArray
     * @return
     */
//    public ArrayList<XYobject> calculateLeastSquares(ArrayList<XYobject> objectArray){
//        LeastSquaresCalculator leastSquaresCalculator = new LeastSquaresCalculator();
//        ArrayList<XYobject> array = new ArrayList<>();
//        int k = 30;
//        for(int i=0; i<k; i++){
//            array.add(objectArray.get(i));
//        }
//        for(int i=k; i<objectArray.size()-k; i++){
//            ArrayList<Integer> x = new ArrayList<>();
//            ArrayList<Float> y = new ArrayList<>();
//            for(int j=0; j<k*2+1; j++){
//                x.add(i-k+j);
//                y.add(objectArray.get(i-k+j).getY());
//            }
//            array.add(new XYobject("Data XX", leastSquaresCalculator.calculate(x, y)));
//        }
//
//        return array;
//    }
}
