package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;
import java.lang.Math;

import java.util.ArrayList;

public class MovingAverageService {

    public ArrayList<XYobject> getArrayOfPricesCalculatedBySimpleMovingAverage(ArrayList<XYobject> closePrices, int period) {
        ArrayList<XYobject> calculatedAverages = new ArrayList<>();
        FifoQueue<Integer, Float> fifo = new FifoQueue<>(period);
        for(int i = 0; i < closePrices.size(); i++) {
            fifo.addElement(i, closePrices.get(i).getY());
            if(i >= period) {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateSimpleAverage(fifo)));
            }
            else {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), 0));
            }
        }
        return calculatedAverages;
    }

    public ArrayList<XYobject> getArrayOfPricesCalculatedByWeightedMovingAverage(ArrayList<XYobject> closePrices, int period) {
        ArrayList<XYobject> calculatedAverages = new ArrayList<>();
        FifoQueue<Integer, Float> fifo = new FifoQueue<>(period);
        for(int i = 0; i < closePrices.size(); i++) {
            fifo.addElement(i, closePrices.get(i).getY());
            if(i >= period) {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateWeightedAverage(fifo)));
            }
            else {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), 0));
            }
        }
        return calculatedAverages;
    }

    public ArrayList<XYobject> getArrayOfPricesCalculatedByExponentialMovingAverage(ArrayList<XYobject> closePrices, int period) {
        ArrayList<XYobject> calculatedAverages = new ArrayList<>();
        FifoQueue<Integer, Float> fifo = new FifoQueue<>(period);
        for(int i = 0; i < closePrices.size(); i++) {
            fifo.addElement(i, closePrices.get(i).getY());
            if(i >= period) {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateExponentialAverage(fifo)));
            }
            else {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), 0));
            }
        }
        return calculatedAverages;
    }

    public ArrayList<XYobject> getArrayOfPricesCalculatedByHullMovingAverage(ArrayList<XYobject> closePrices, int period) {
        ArrayList<XYobject> calculatedAverages = new ArrayList<>();
        FifoQueue<Integer, Float> fifo = new FifoQueue<>(period);
        for(int i = 0; i < closePrices.size(); i++){
            fifo.addElement(i, closePrices.get(i).getY());
            if(i >= period){
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateHullMovingAverage(fifo)));
            }
            else{
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), 0));
            }
        }
        return calculatedAverages;
    }

    private float calculateSimpleAverage(FifoQueue<Integer, Float> fifo) {
        float sum = 0;
        for(float value : fifo.getValues()) {
            sum += value;
        }
        return sum / fifo.getSize();
    }

    private float calculateWeightedAverage(FifoQueue<Integer, Float> fifo) {
        float sum = 0;
        float weight = 1;
        float weightSum = 0;
        for(float value : fifo.getValues()) {
            weightSum += weight;
            sum += weight * value;
            weight += 1;
        }
        return sum / weightSum;
    }

    private float calculateExponentialAverage(FifoQueue<Integer, Float> fifo) {
        float initialEMA = calculateSimpleAverage(fifo);
        boolean initialCalculation = true;
        float smoothingConstant = 2 / (float)(fifo.getSize() + 1);
        float calculatedEMA = 0;
        for(float value : fifo.getValues()){
            if(!initialCalculation){
                calculatedEMA = (value - calculatedEMA) * smoothingConstant + calculatedEMA;
            }
            else{
                initialCalculation = false;
                calculatedEMA = (value - initialEMA) * smoothingConstant + initialEMA;
            }
        }
        return calculatedEMA;
    }

    private float calculateHullMovingAverage(FifoQueue<Integer, Float> fifo) {
        // calculation the WMA of the period
        float WMAofThePeriod = calculateWeightedAverage(fifo);
        // calculation the WMA of half of the period
        int halfPeriod = fifo.getSize() / 2;
        float WMAofHalfOfThePeriod = calculateWeightedAverage(fifo.getLastElements(halfPeriod));
        // https://www.incrediblecharts.com/indicators/hull-moving-average.php
        // multiplying the second WMA by 2 and subtraction the first WMA
    }


}
