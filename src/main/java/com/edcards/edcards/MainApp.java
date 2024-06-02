package com.edcards.edcards;

import com.edcards.edcards.ClassControllers.GlobalVAR;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMainAcessPin.class.getResource("/com/edcards/edcards/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dados");
        stage.setScene(scene);
        stage.show();
        GlobalVAR.Dados.setCurrentStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
