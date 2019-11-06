package services;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class LineChartFactory {
    public LineChart<NumberAxis, NumberAxis> createLineChart(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Days");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Prices");

        return new LineChart(xAxis, yAxis);
    }
}
