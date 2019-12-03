package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;
import java.lang.Math;

import java.util.ArrayList;

public class MovingAverageService {

    public ArrayList<XYobject> getPricesCalculatedBySimpleMovingAverage(ArrayList<XYobject> closePrices, int period) {
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

    public ArrayList<XYobject> getPricesCalculatedByWeightedMovingAverage(ArrayList<XYobject> closePrices, int period) {
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

    public ArrayList<XYobject> getPricesCalculatedByExponentialMovingAverage(ArrayList<XYobject> closePrices, int period) {
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
        float numerator = 0;
        float denominator = 0;
        float coefficient;
        for(int i = 0; i < fifo.getSize(); i++) {
            coefficient = 2 / (float)i+1;
            numerator += Math.pow((1 - coefficient), i) * fifo.getValueByIndex(i);
            denominator += Math.pow((1 - coefficient), i);
        }

        return numerator / denominator;
    }
}
