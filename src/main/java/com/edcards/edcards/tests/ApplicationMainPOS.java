package com.edcards.edcards.tests;

import com.edcards.edcards.FormControllers.Pos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMainPOS extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMainPOS.class.getResource("/com/edcards/edcards/Pos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("POS Compras");
        stage.setMinHeight(720);
        stage.setMinWidth(1024);
        stage.setScene(scene);
        Pos posController = fxmlLoader.getController();
        stage.setOnCloseRequest(e -> posController.shutdown());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}