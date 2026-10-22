package org.example.ejemploconcurrencia1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(MainApplication.class.getResource("ventas-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 760, 600);

        stage.setTitle("Procesamiento Concurrente de Ventas");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}