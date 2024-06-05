package com.edcards.edcards.Programa.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class FeedBackController {
    private static Stage currentStage = GlobalVAR.Dados.getCurrentStage();


    public static void feedbackErro(String message) {
        currentStage = GlobalVAR.Dados.getCurrentStage();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(currentStage);
        alert.showAndWait();

    }

    public static boolean feedbackYesNo(String message) {
        currentStage = GlobalVAR.Dados.getCurrentStage();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(currentStage);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    public static boolean feedbackYesNo(String message,String title) {
        currentStage = GlobalVAR.Dados.getCurrentStage();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(currentStage);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }


    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        FeedBackController.currentStage = currentStage;
    }
}
