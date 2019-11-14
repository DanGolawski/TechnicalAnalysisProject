package com.technicalanalysis.backend.chartServices;

import com.technicalanalysis.backend.models.XYobject;

import java.util.ArrayList;

public class LeastSquareService {

    private ArrayList<Float> points;
    private ArrayList<XYobject> data;
    private int k = 8; // nearest neighbours number

    public LeastSquareService(){
        points = new ArrayList<>();
        data = new ArrayList<>();
    }

    public ArrayList<XYobject> getPeaks(){
        int neighboursNumber = k / 2;
        return null;
    }
}
