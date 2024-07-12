package com.edcards.edcards.tests;

import com.edcards.edcards.MainExe;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Image;
import java.awt.Toolkit;

public class AddHorario extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainExe.class.getResource("/com/edcards/edcards/AddHorario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        GlobalVAR.Dados.setCurrentStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
