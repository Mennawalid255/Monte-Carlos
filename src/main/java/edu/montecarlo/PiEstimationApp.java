package edu.montecarlo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PiEstimationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/montecarlo/gui/main.fxml"));
        Scene scene = new Scene(loader.load(), 1040, 850);
        primaryStage.setTitle("Monte Carlo π Estimation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
