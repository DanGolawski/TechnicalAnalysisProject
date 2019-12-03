package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * this is the class that finds resistance levels based on prices and least squares
 */
public class SupportResistanceLevelsService {

    private static int neighboursNo = 60; // number of neighbours of considered point

    // TODO pomyslec czy nie da sie zlaczyc dwoch ponizszych metod
    /**
     * this method finds resistance levels
     * @param prices list of highest prices per day
     * @param squares list of least squares of highest prices
     * @return short list of resistance levels
     */
    public Set<Float> getResistanceLevels(ArrayList<XYobject> prices, ArrayList<XYobject> squares){
        FifoQueue<Float, Float> fifo = new FifoQueue<>(neighboursNo+1);
        Set<Float> extremes = new TreeSet<>();
        for(int i=0; i<squares.size(); i++){
            fifo.addElement(prices.get(i).getY(), squares.get(i).getY());
            if(i > neighboursNo){
                if(checkIncrease(fifo, neighboursNo/2-1, neighboursNo/2+1) && checkDecrease(fifo, neighboursNo/2+1, neighboursNo/2+3)){
                    extremes.add(findTheGreatestValue(fifo));
                }
            }
        }
        return extremes;
    }

    /**
     * this method finds support levels
     * @param prices list of highest prices per day
     * @param squares list of least squares of highest prices
     * @return short list of support levels
     */
    public Set<Float> getSupportLevels(ArrayList<XYobject> prices, ArrayList<XYobject> squares){
        FifoQueue<Float, Float> fifo = new FifoQueue<>(neighboursNo+1);
        Set<Float> extremes = new TreeSet<>();
        for(int i=0; i<squares.size(); i++){
            fifo.addElement(prices.get(i).getY(), squares.get(i).getY());
            if(i > neighboursNo){
                if(checkDecrease(fifo, neighboursNo/2-1, neighboursNo/2+1) && checkIncrease(fifo, neighboursNo/2+1, neighboursNo/2+3)){
                    extremes.add(findTheLowestValue(fifo));
                }
            }
        }
        return extremes;
    }


    /**
     * this method checks first part of potential peak - whether values increase to the middle element
     * @param fifo - 41-length queue of prices and least squares of prices
     * @return boolean value whether increase appears or not
     */
    private boolean checkIncrease(FifoQueue<Float, Float> fifo, int loopStart, int loopEnd){
        boolean answer = true;
        // here I check three left neighbours of the middle element of the queue
        // if previous element is smaller than next one - values increase
        for(int i=loopStart+1;i<=loopEnd;i++){
            if(fifo.getValueByIndex(i-1) > fifo.getValueByIndex(i)){
                answer = false;
                break;
            }
        }
        return answer;
    }

    /**
     * this method checks second part of potential peak - whether values decrease from the middle element
     * @param fifo - 41-length queue of prices and least squares of prices
     * @return boolean value whether decrease appears or not
     */
    private boolean checkDecrease(FifoQueue<Float, Float> fifo, int loopStart, int loopEnd){
        boolean answer = true;
        // here I check three right neighbours of the middle element of the queue
        // if previous element is greater than next one - values decrease
        for(int i=loopStart; i<=loopEnd-1; i++){
            if(fifo.getValueByIndex(i) < fifo.getValueByIndex(i+1)){
                answer = false;
                break;
            }
        }
        return answer;
    }

    private float findTheGreatestValue(FifoQueue<Float, Float> fifo){
        float theGreatest = -1;
        for(float x : fifo.getKeys()){
            if(x > theGreatest){
                theGreatest = x;
            }
        }
        return theGreatest;
    }

    private float findTheLowestValue(FifoQueue<Float, Float> fifo){
        float theLowest = fifo.getValueByIndex(0);
        for(float x : fifo.getKeys()){
            if(x < theLowest){
                theLowest = x;
            }
        }
        return theLowest;
    }
}
