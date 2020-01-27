package services;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class GaugeService {

    public static void showGauge() throws Exception {
        DataService dataService = new DataService();
        JSONObject jsonObject = dataService.getObject("rating/periods?period=5&period=10&period=20&period=30&period=50&period=100&period=200&hullperiod=9");


        double indicatorCounter = 0;
        double buyCounter = 0;

        JSONArray arr = jsonObject.getJSONArray("movingAverages");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String verdict = obj.getString("verdict");
            if (verdict.equals("Buy")) buyCounter += 1;
            indicatorCounter += 1;
        }

        Stage primaryStage = new Stage();
        Gauge gauge = new Gauge();

        gauge.setSkin(new ModernSkin(gauge));  //ModernSkin : you guys can change the skin
        gauge.setTitle(jsonObject.getString("shouldBuyVerdict"));  //title
        gauge.setUnit("BTC/USD");  //unit
        gauge.setUnitColor(Color.WHITE);
        gauge.setDecimals(0);
        gauge.setValue((buyCounter / indicatorCounter) * 100); //deafult position of needle on gauage

        gauge.setAnimated(true);
        gauge.setAnimationDuration(3000);

        gauge.setValueColor(Color.WHITE);
        gauge.setTitleColor(Color.WHITE);
        gauge.setSubTitleColor(Color.WHITE);
        gauge.setBarColor(Color.rgb(0, 214, 215));
        gauge.setNeedleColor(Color.RED);
        gauge.setThresholdColor(Color.RED);  //color will become red if it crosses threshold value
//        gauge.setThreshold(85);
        gauge.setThresholdVisible(true);
        gauge.setTickLabelColor(Color.rgb(151, 151, 151));
        gauge.setTickMarkColor(Color.WHITE);
        gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);

        StackPane root = new StackPane();
        root.getChildren().addAll(gauge);
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Market Rating");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
