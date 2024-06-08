package com.edcards.edcards.NotDoneApps;

import com.edcards.edcards.ApplicationMainAcessPin;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UpdateUserCard extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMainAcessPin.class.getResource("/com/edcards/edcards/NotDone/UpdateUserCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        GlobalVAR.Dados.setCurrentStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
