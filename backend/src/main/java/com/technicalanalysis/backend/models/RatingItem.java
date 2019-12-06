package com.technicalanalysis.backend.models;


public class RatingItem {

    private String itemName;
    private int period;
    private float averageValue;
    private boolean shouldBuy;

    public RatingItem(String itemName, int period, float averageValue, boolean shouldBuy){
        this.period = period;
        this.averageValue = averageValue;
        this.itemName = itemName + "(" + period + ")";
        this.shouldBuy = shouldBuy;
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

    public boolean isShouldBuy() {
        return shouldBuy;
    }
}
