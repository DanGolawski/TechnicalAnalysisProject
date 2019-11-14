package com.technicalanalysis.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FifoQueue {

    ArrayList<Integer> keys;
    ArrayList<Float> values;
    int size;

    public FifoQueue(int size){
        this.size = size;
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void addElement(int x, float y){
        if(keys.size() < size+1){
            keys.add(x);
            values.add(y);
        }
        else{
            keys.remove(0);
            values.remove(0);
            keys.add(x);
            values.add(y);
        }
    }

    // GETTERS //
    public ArrayList<Integer> getKeys(){
        return keys;
    }
    public ArrayList<Float> getValues(){
        return values;
    }
}
