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
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateSimpleMovingAverage(fifo)));
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
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateWeightedMovingAverage(fifo)));
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
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), calculateExponentialMovingAverage(fifo)));
            }
            else {
                calculatedAverages.add(new XYobject(closePrices.get(i).getX(), 0));
            }
        }
        return calculatedAverages;
    }

    public ArrayList<XYobject> getArrayOfPricesCalculatedByHullMovingAverage(ArrayList<XYobject> closePrices, int period) {

        ArrayList<XYobject> calculatedMMAs = getMMAarray(closePrices, period);
        return getHMAarray(closePrices, calculatedMMAs, period);

    }

    private ArrayList<XYobject> getMMAarray(ArrayList<XYobject> closePrices, int period) {
        ArrayList<XYobject> calculatedMMAs = new ArrayList<>();
        FifoQueue<Integer, Float> fifoMMA = new FifoQueue<>(period);
        for(int i = 0; i < closePrices.size(); i++) {
            fifoMMA.addElement(i, closePrices.get(i).getY());
            if(i >= period) {
                calculatedMMAs.add(new XYobject(closePrices.get(i).getX(), calculateModifiedMovingAverage(fifoMMA)));
            }
            else {
                calculatedMMAs.add(new XYobject(closePrices.get(i).getX(), 0));
            }
        }
        return calculatedMMAs;
    }

    private ArrayList<XYobject> getHMAarray(ArrayList<XYobject> closePrices, ArrayList<XYobject> calculatedMMAs, int period) {
        int hullPeriod = (int) Math.sqrt(period);
        FifoQueue<Integer, Float> fifoHMA = new FifoQueue<>(hullPeriod);
        ArrayList<XYobject> calculatedHMAs = new ArrayList<>();
        for(int j = 0; j < closePrices.size(); j++) {
            fifoHMA.addElement(j, calculatedMMAs.get(j).getY());
            if(j >= period + hullPeriod) {
                calculatedHMAs.add(new XYobject(closePrices.get(j).getX(), calculateWeightedMovingAverage(fifoHMA)));
            }
            else {
                calculatedHMAs.add(new XYobject(closePrices.get(j).getX(), 0));
            }
        }
        return calculatedHMAs;
    }


    private float calculateSimpleMovingAverage(FifoQueue<Integer, Float> fifo) {
        float sum = 0;
        for(float value : fifo.getValues()) {
            sum += value;
        }
        return sum / fifo.getSize();
    }

    public float calculateSimpleMovingAverage(float[] prices) {
        float sum = 0;
        for(float price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    private float calculateWeightedMovingAverage(FifoQueue<Integer, Float> fifo) {
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

    private float calculateExponentialMovingAverage(FifoQueue<Integer, Float> fifo) {
        float initialEMA = calculateSimpleMovingAverage(fifo);
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

    public float calculateExponentialMovingAverage(float[] prices) {
        float initialEMA = calculateSimpleMovingAverage(prices);
        boolean initialCalculation = true;
        float smoothingConstant = 2 / (float)(prices.length + 1);
        float calculatedEMA = 0;
        for(float value : prices){
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

    private float calculateModifiedMovingAverage(FifoQueue<Integer, Float> fifo) {
        return 2 * calculateWeightedMovingAverage(fifo.getLastElements(fifo.getSize() / 2)) - calculateWeightedMovingAverage(fifo);
    }


}
