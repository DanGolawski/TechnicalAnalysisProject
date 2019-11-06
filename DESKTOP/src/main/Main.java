package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import services.DataService;
import services.LineChartFactory;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        LineChartFactory lineChartFactory = new LineChartFactory();
        LineChart chart = lineChartFactory.createLineChart();
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("BTC/USD");


        DataService dataService = new DataService();
        JSONArray array = dataService.getData("highestPrices");
        for(int i = 0; i<array.length(); i++){
//            System.out.println(array.getJSONObject(i).get("y"));
            dataSeries1.getData().add(new XYChart.Data(i, array.getJSONObject(i).get("y")));
        }
        chart.getData().add(dataSeries1);


//        Scene scene = new Scene(chart);
//
//        primaryStage.setMaximized(true);
//        primaryStage.setScene(scene);
//
//
//        primaryStage.show();



        chart.setCreateSymbols(false);
        ScrollPane root = new ScrollPane(chart);
        root.setMinSize(4000,600);
        root.fitToHeightProperty().set(true);
        chart.setMinSize(root.getMinWidth(),root.getMinHeight()-20);
        Scene scene  = new Scene(root);


        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
