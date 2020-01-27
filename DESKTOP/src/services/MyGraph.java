package services;

import javafx.scene.chart.XYChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyGraph {

    private XYChart<Double, Double> graph;
    private double range;

    public MyGraph(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }


    public void plotLine(JSONArray jsonArray) throws JSONException {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        insertPointsInSeries(series, jsonArray);
        graph.getData().add(series);
    }

    public void plotLevels(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            final XYChart.Series<Double, Double> series = new XYChart.Series<>();
            insertPointsInSeries(series, jsonArray.getDouble(i));
            graph.getData().add(series);
        }
    }

    private void insertPointsInSeries(XYChart.Series<Double, Double> series, JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            plotPoint(obj, series, i);
        }
    }

    private void insertPointsInSeries(XYChart.Series<Double, Double> series, double value) throws JSONException {
        for (double i = 0; i < range; i+=1)
            series.getData().add(new XYChart.Data<>(i, value));
    }


    private void plotPoint(final JSONObject object, final XYChart.Series<Double, Double> series, double idx) throws JSONException {
        series.getData().add(new XYChart.Data<>(
                idx,
                object.getDouble("y"))
        );
    }


    public void clear() {
        graph.getData().clear();
    }
}