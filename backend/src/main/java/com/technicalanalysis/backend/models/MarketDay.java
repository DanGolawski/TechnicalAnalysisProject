package com.technicalanalysis.backend.models;

public class MarketDay {

    private float high;
    private float low;
    private String convertionSymbol;
    private float volumeto;
    private float volumefrom;
    private long time;
    private String convertionType;
    private float close;
    private float open;


//    SETTERS   //
    public void setHigh(float high) {
        this.high = high;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setConvertionSymbol(String convertionSymbol) {
        this.convertionSymbol = convertionSymbol;
    }

    public void setVolumeto(float volumeto) {
        this.volumeto = volumeto;
    }

    public void setVolumefrom(float volumefrom) {
        this.volumefrom = volumefrom;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setConvertionType(String convertionType) {
        this.convertionType = convertionType;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setOpen(float open) {
        this.open = open;
    }



//    GETTERS   //
    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public String getConvertionSymbol() {
        return convertionSymbol;
    }

    public float getVolumeto() {
        return volumeto;
    }

    public float getVolumefrom() {
        return volumefrom;
    }

    public long getTime() {
        return time;
    }

    public String getConvertionType() {
        return convertionType;
    }

    public float getClose() {
        return close;
    }

    public float getOpen() {
        return open;
    }
}
