package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.XYobject;
import com.technicalanalysis.backend.services.FifoQueue;

import java.util.ArrayList;

public class ResistanceLevelsService {

    private int neighboursNo = 40; // number of neighbours, of considered point

    public void getLevels(ArrayList<XYobject> prices, ArrayList<XYobject> squares){
        FifoQueue<Float, Float> fifo = new FifoQueue<>(neighboursNo);
        ArrayList<Float> extremums = new ArrayList<>();
        for(int i=0; i<squares.size(); i++){
            fifo.addElement(prices.get(i).getY(), squares.get(i).getY());
            if(i > neighboursNo){
                if(checkIncrease(fifo) && checkDecrease(fifo)){
                    extremums.add(findTheGreatestValue(fifo));
                }
            }
        }

    }

    /**
     * this method checks first part of potential peak - whether values increase to the middle element
     * @param fifo - 41-length queue of prices and least squares of prices
     * @return boolean value whether increase appears or not
     */
    private boolean checkIncrease(FifoQueue<Float, Float> fifo){
        boolean answer = true;
        // here I check three left neighbours of the middle element of the queue
        // if previous element is smaller than next one - values increase
        for(int i=neighboursNo/2-1;i<=neighboursNo/2+1;i++){
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
    private boolean checkDecrease(FifoQueue<Float, Float> fifo){
        boolean answer = true;
        // here I check three right neighbours of the middle element of the queue
        // if previous element is greater than next one - values decrease
        for(int i=neighboursNo/2+1; i<neighboursNo/2+4; i++){
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
}
