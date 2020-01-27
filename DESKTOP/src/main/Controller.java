package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import org.json.JSONArray;
import services.DataService;
import services.GaugeService;
import services.MyGraph;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private KeyCode[] keyCodes = {KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7,
                        KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, KeyCode.F5, KeyCode.F6, KeyCode.F7};
    private String[] periods = {"5", "10", "20", "30", "50", "100", "200"};

    private boolean anyGrapgShown = false;

    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    @FXML
    private Button lineGraphButton;

    @FXML
    private Button areaGraphButton;

    @FXML
    private Button highestPricesButton;

    @FXML
    private Button lowestPricesButton;

    @FXML
    private Button suppResistButton;

    @FXML
    private Button leastSquaresHighButton;

    @FXML
    private Button leastSquaresLowButton;

    @FXML
    private Button ratingButton;

    @FXML
    private Button clearButton;

    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;
    private DataService dataService;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        mathsGraph = new MyGraph(lineGraph, 1000);
        areaMathsGraph = new MyGraph(areaGraph, 1000);
        dataService = new DataService();
        lineGraph.setCreateSymbols(false);
        areaGraph.setCreateSymbols(false);
    }

    @FXML
    private void handleLineGraphButtonAction(final ActionEvent event) {
        lineGraph.setVisible(true);
        areaGraph.setVisible(false);
    }

    @FXML
    private void handleAreaGraphButtonAction(final ActionEvent event) {
        areaGraph.setVisible(true);
        lineGraph.setVisible(false);
    }

    @FXML
    private void handleHighestPricesData(final ActionEvent event) throws Exception {
        anyGrapgShown = true;
        plotLine("highestPrices");
        handleKeyEvents();
    }

    @FXML
    private void handleLowestPricesData(final ActionEvent event) throws Exception {
        anyGrapgShown = true;
        plotLine("lowestPrices");
        handleKeyEvents();
    }

    @FXML
    private void handleSupportResistanceLevels(final ActionEvent event) throws Exception {
        if (!anyGrapgShown) return;
        plotLevels("resistance");
        plotLevels("support");
    }

    @FXML
    private void handleLeastSquaresHigh(final ActionEvent event) throws Exception {
        if (!anyGrapgShown) return;
        plotLine("leastSquareHigh");
    }

    @FXML
    private void handleLeastSquaresLow(final ActionEvent event) throws Exception {
        if (!anyGrapgShown) return;
        plotLine("leastSquareLow");
    }

    @FXML
    private void handleRatingGauge(final ActionEvent event) throws Exception {
        GaugeService.showGauge();
    }

    @FXML
    private void handleClearButtonAction(final ActionEvent event) {
        clear();
    }

    private void handleKeyEvents() {
        Main.scene.setOnKeyPressed(x -> {
            try {
                handleMovingAverages(x.getCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleMovingAverages(KeyCode code) throws Exception {
        if (!anyGrapgShown) return;
        if (code.equals(KeyCode.H))
            plotLine("HullMovingAverage/9");
        else if (Arrays.asList(keyCodes).contains(code)) {
            List<KeyCode> list = Arrays.asList(keyCodes);
            List<String> list2 = Arrays.asList(periods);
            int idx = list.indexOf(code);
            plotLine(idx < 7 ? "SimpleMovingAverage/"+list2.get(idx) : "ExponentialMovingAverage/"+list2.get(idx%7));
        }

    }


    private void plotLine(String endpoint) throws Exception {
        JSONArray array = dataService.getData(endpoint);
        if (lineGraph.isVisible()) {
            mathsGraph.plotLine(array);
        } else {
            areaMathsGraph.plotLine(array);
        }
    }

    private void plotLevels(String endpoint) throws Exception {
        JSONArray array = dataService.getData(endpoint);
        if (lineGraph.isVisible()) {
            mathsGraph.plotLevels(array);
        } else {
            areaMathsGraph.plotLevels(array);
        }
    }

    private void clear() {
        anyGrapgShown = false;
        if (lineGraph.isVisible()) {
            mathsGraph.clear();
        } else {
            areaMathsGraph.clear();
        }
    }
}

