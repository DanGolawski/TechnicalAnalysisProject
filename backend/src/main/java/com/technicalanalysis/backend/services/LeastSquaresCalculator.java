package com.technicalanalysis.backend.services;
import java.lang.Math;
import java.util.ArrayList;

public class LeastSquaresCalculator {

    public float calculate(ArrayList<Integer> x, ArrayList<Float> y){
        float meanX = 0;
        float meanY = 0;
        for(int i=0; i<x.size(); i++){
            meanX += x.get(i);
            meanY += y.get(i);
        }
        meanX = meanX / x.size();
        meanY = meanY / y.size();

        float numerator = 0;
        float denominator = 0;

        for(int i=0; i<x.size(); i++){
            numerator += (x.get(i) - meanX) * (y.get(i) - meanY);
            denominator += Math.pow((x.get(i) - meanX),2);
        }

        float a = numerator / denominator;
        float b = meanY - a*meanX;
        return a*x.get((x.size()/2)+1) + b;
    }
}
