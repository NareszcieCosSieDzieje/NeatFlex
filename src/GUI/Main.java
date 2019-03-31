package GUI;

import NeatFlexClasses.Simulation.DataGenerator;
import NeatFlexClasses.Simulation.NeatFlexService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Main extends Application {

    private static Stage pStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        DataGenerator.initialize();
        Parent root = FXMLLoader.load(getClass().getResource("mainGUI.fxml"));
        primaryStage.setTitle("Neat-Flex");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
        pStage=primaryStage;
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    public static void showCantGoOn(){
      pStage.close(); // inside my app is breaking, my fingers may be aching, but my smile still stays on
    }

    public static void main(String[] args) {
        launch(args);
    }
}

