package com.technicalanalysis.backend.models;

import java.util.List;

public class Rating {

    private List<Integer> periods;
    private int hullPeriod;
    private List<RatingItem> movingAverages;
    private RatingItem hullMovingAverage;
    private float closePrice;
    private boolean shouldBuyVerdict;

    public Rating(List<Integer> periods, int hullperiod, float closePrice) {
        this.periods = periods;
        this.hullPeriod = hullperiod;
        this.closePrice = closePrice;
    }

    // SETTERS & ADDERS //

    public void setPeriods(List<Integer> periods) {
        this.periods = periods;
    }

    public void setHullPeriod(int hullPeriod) {
        this.hullPeriod = hullPeriod;
    }

    public void addMovingAverage(RatingItem movingAverage) {
        movingAverages.add(movingAverage);
    }

    public void setHullMovingAverage(RatingItem hullMovingAverage) {
        this.hullMovingAverage = hullMovingAverage;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public void setShouldBuyVerdict(boolean shouldBuyVerdict) {
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

    public RatingItem getHullMovingAverage() {
        return hullMovingAverage;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public boolean isShouldBuyVerdict() {
        return shouldBuyVerdict;
    }
}
