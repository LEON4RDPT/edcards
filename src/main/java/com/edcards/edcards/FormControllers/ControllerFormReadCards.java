package com.edcards.edcards.FormControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerFormReadCards {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}