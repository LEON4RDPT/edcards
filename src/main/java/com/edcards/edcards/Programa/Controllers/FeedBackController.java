package com.edcards.edcards.Programa.Controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class FeedBackController {
    private static Stage currentStage = GlobalVAR.Dados.getCurrentStage();

    private Image imageQuestion = new Image(getClass().getResourceAsStream("/com/edcards/edcards/images/icons/question.png"));


    private void setPic(Image img, Alert alert) {
        ImageView imageView = new ImageView(img);

        alert.setGraphic(imageView);
        //todo
    }

    public static void feedbackErroShowOtherForm(String message,String fxml) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(GlobalVAR.Dados.getCurrentStage());
        alert.setOnCloseRequest(event -> {
            try {
                setStage(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        alert.showAndWait();
    }

    public static void feedbackErroCloseApp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(GlobalVAR.Dados.getCurrentStage());
        alert.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(1);
        });
        alert.showAndWait();
    }

    public static void feedbackErro(String message) {
        currentStage = GlobalVAR.Dados.getCurrentStage();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(currentStage);
        alert.showAndWait();

    }
    public static void feedbackConf(String message) {
        currentStage = GlobalVAR.Dados.getCurrentStage();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
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

    public static boolean feedbackYesNo(String message, String title) {
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
