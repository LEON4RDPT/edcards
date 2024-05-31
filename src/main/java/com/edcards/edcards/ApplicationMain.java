package com.edcards.edcards;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        LoadFont();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Pos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("POS Compras");
        stage.setMinHeight(720);
        stage.setMinWidth(1024);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void LoadFont() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Nirmala.ttf"), 12);

    }
}