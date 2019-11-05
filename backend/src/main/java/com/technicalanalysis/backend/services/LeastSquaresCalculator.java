package com.technicalanalysis.backend.services;
import java.lang.Math;
import java.util.ArrayList;

public class LeastSquaresCalculator {

    public float calculate(int x1, float y1, int x2, float y2, int x3, float y3){
        float meanY = (y1 + y2 + y3) / (float) 3.0;
        float numerator = (x1 - x2)*(y1-meanY) + (x3 - x2)*(y3 - meanY);
        float denominator = (float) (Math.pow((x1-x2), 2) + Math.pow((x3-x2), 2));
        float a = numerator / denominator;
        float b = meanY - a * x2;
        return a*x2 + b;
    }

    public float calculate2(ArrayList<Integer> x, ArrayList<Float> y){
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
