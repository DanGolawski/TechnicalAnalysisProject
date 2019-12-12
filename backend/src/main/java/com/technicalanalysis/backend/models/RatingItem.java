package com.technicalanalysis.backend.models;


public class RatingItem {

    private String itemName;
    private int period;
    private float averageValue;
    private String verdict;

    public RatingItem(String itemName, int period, float averageValue, boolean verdict){
        this.period = period;
        this.averageValue = averageValue;
        this.itemName = itemName + "(" + period + ")";
        this.verdict = verdict ? "Buy" : "Sell";
    }



    // GETTERS //

    public String getItemName() {
        return itemName;
    }

    public int getPeriod() {
        return period;
    }

    public float getAverageValue() {
        return averageValue;
    }

    public String getVerdict() {
        return verdict;
    }
}
