package com.edcards.edcards.FormControllers.Utils;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ResizeUtil {
    public static void resizeAndCenterText(TextField textField, AnchorPane pane) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            textField.setLayoutX((width - textField.getPrefWidth()) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            textField.setLayoutY((height - textField.getPrefHeight()) / 2);
        });

    }
    public static void resizeAndCenterButton(Button button, AnchorPane pane) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            button.setLayoutX((width - button.getPrefWidth()) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            button.setLayoutY((height - button.getPrefHeight()) / 2);
        });

    }

    public static void resizeAndCenterMiddleButtons(Button button1, Button button2, AnchorPane pane) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double buttonWidthSum = button1.getPrefWidth() + button2.getPrefWidth();

            // Calculate the horizontal space between the buttons
            double spaceBetweenButtons = width - buttonWidthSum;

            // Calculate the X position of the first button
            double firstButtonX = spaceBetweenButtons / 3;

            // Set the layout X for the first button
            button1.setLayoutX(firstButtonX);

            // Calculate the X position of the second button
            double secondButtonX = firstButtonX + button1.getPrefWidth() + spaceBetweenButtons / 3;

            // Set the layout X for the second button
            button2.setLayoutX(secondButtonX);
        });

        // Add a listener for height changes if necessary
    }
    public static void resizeAndPositionButton(Button button, AnchorPane pane, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            button.setLayoutX((width - button.getPrefWidth()) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double buttonHeight = button.getPrefHeight();
            double positionY = height * relativePosition - buttonHeight / 2;
            button.setLayoutY(positionY);
        });
    }

    public static void resizeAndPositionTextArea(TextArea textArea, AnchorPane pane, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            textArea.setLayoutX((width - textArea.getPrefWidth()) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double buttonHeight = textArea.getPrefHeight();
            double positionY = height * relativePosition - buttonHeight / 2;
            textArea.setLayoutY(positionY);
        });
    }

    public static void resizeAndPositionChoiceBox(ChoiceBox choiceBox, AnchorPane pane, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            choiceBox.setLayoutX((width - choiceBox.getPrefWidth()) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double buttonHeight = choiceBox.getPrefHeight();
            double positionY = height * relativePosition - buttonHeight / 2;
            choiceBox.setLayoutY(positionY);
        });
    }


}