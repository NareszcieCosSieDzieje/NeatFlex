
package GUI;
import NeatFlexClasses.Simulation.Distributor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.LinkedHashMap;
import java.util.Map;



public class GraphPlotter {

    //jakis fxml do tego
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public LineChart ViewsChart;
    public XYChart.Series series;


    @FXML
    public void initialize() throws InterruptedException {

        ViewsChart.setTitle("Wykres oglądalności w czasie");
        //defining a series
        series = new XYChart.Series();
        series.setName("Wyświetlenia");
        //populating the series with data
        ViewsChart.getData().add(series);
    }


    public void populateChart(LinkedHashMap<Integer,Integer> plotMap){
        this.series.getData().clear();
        for (Map.Entry<Integer,Integer> entry : plotMap.entrySet()) {
            this.series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
    }
}




