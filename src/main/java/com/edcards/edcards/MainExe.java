package com.edcards.edcards;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainExe extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainExe.class.getResource("ReadCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Aplicação");
        stage.setScene(scene);
        GlobalVAR.Dados.setCurrentStage(stage);
        GlobalVAR.Dados.getCurrentStage().setOnCloseRequest(event -> {
            event.consume(); // Consume the close event to prevent the stage from closing
            GlobalVAR.Dados.confirmExit();
        });
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}