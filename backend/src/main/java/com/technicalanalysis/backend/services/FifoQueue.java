package com.technicalanalysis.backend.services;

import java.util.ArrayList;

/**
 * This is the generic FIFO queue (key-value) with variable size
 * @param <K> first type - keys
 * @param <V> second type - values
 */
public class FifoQueue<K, V> {

    private ArrayList<K> keys;
    private ArrayList<V> values;
    private int size;

    public FifoQueue(int size){
        this.size = size;
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void addElement(K x, V y){
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
    public K getKeyByIndex(int index){
        return keys.get(index);
    }
    public V getValueByIndex(int index){
        return values.get(index);
    }
    public ArrayList<K> getKeys(){
        return keys;
    }
    public ArrayList<V> getValues(){
        return values;
    }
    public int getSize(){
        return size;
    }
}
