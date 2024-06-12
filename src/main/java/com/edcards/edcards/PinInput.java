package com.edcards.edcards;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class PinInput extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        FXMLLoader fxmlLoader = new FXMLLoader(MainExe.class.getResource("PIN.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinHeight(640);
        stage.setMinWidth(835);
        stage.setScene(scene);
        stage.show();
    }
}
