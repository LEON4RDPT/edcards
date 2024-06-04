package com.edcards.edcards.Programa.Controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class FeedBackController {
    private static Stage currentStage;

    public static void feedbackErro(String message) {
        currentStage = GlobalVAR.Dados.getCurrentStage();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(currentStage);
        alert.showAndWait();
    }


    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        FeedBackController.currentStage = currentStage;
    }
}
