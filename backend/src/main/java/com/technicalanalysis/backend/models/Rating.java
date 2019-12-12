package com.technicalanalysis.backend.models;

import java.util.ArrayList;
import java.util.List;

public class Rating {

    private List<Integer> periods;
    private int hullPeriod;
    private List<RatingItem> movingAverages;
    private float closePrice;
    private String shouldBuyVerdict;

    public Rating(List<Integer> periods, int hullperiod, float closePrice) {
        this.periods = periods;
        this.hullPeriod = hullperiod;
        this.closePrice = closePrice;
        movingAverages = new ArrayList<>();
    }

    public void makeVerdict(){
        int buyRecomendationCounter = 0;
        int sellRecomendationCounter = 0;
        for(RatingItem ratingItem : movingAverages) {
            if(ratingItem.getVerdict().equals("Buy")){
                buyRecomendationCounter += 1;
            }
            else{
                sellRecomendationCounter += 1;
            }
        }
        if(buyRecomendationCounter > sellRecomendationCounter) {
            this.shouldBuyVerdict = "Buy";
        }
        else{
            this.shouldBuyVerdict = "Sell";
        }
    }

    // SETTERS & ADDERS //

    public void setPeriods(List<Integer> periods) {
        this.periods = periods;
    }

    public void setHullPeriod(int hullPeriod) {
        this.hullPeriod = hullPeriod;
    }

    public void addMovingAverage(RatingItem movingAverage) {
        this.movingAverages.add(movingAverage);
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public void setShouldBuyVerdict(String shouldBuyVerdict) {
        this.shouldBuyVerdict = shouldBuyVerdict;
    }



    // GETTERS //

    public List<Integer> getPeriods() {
        return periods;
    }

    public int getHullPeriod(){
        return hullPeriod;
    }

    public List<RatingItem> getMovingAverages() {
        return movingAverages;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public String getShouldBuyVerdict() {
        return shouldBuyVerdict;
    }
}
