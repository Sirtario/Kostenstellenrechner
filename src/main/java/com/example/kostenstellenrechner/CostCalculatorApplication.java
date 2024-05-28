package com.example.kostenstellenrechner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CostCalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CostCalculatorApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        JSONFileHandler fileHandler = new JSONFileHandler();
        List<CurrentData> data = fileHandler.deseriseCurrentData("/Users/phillipeckstein/Code/Kostenstellenrechner/src/main/resources/com/example/kostenstellenrechner/currentdata.json");
    }

    public static void main(String[] args) {
        launch();
    }
}