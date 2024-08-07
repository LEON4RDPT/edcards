package com.edcards.edcards.FormControllers.Utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ResizeUtil {

    public static void resizeGridPane(GridPane gridPane, AnchorPane anchorPane) {
        gridPane.prefWidthProperty().bind(anchorPane.widthProperty());
        gridPane.prefHeightProperty().bind(anchorPane.heightProperty());
        gridPane.maxWidthProperty().bind(anchorPane.widthProperty());
        gridPane.maxHeightProperty().bind(anchorPane.heightProperty());


        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);
    }

    public static void resizeSplitPane(SplitPane splitPane, AnchorPane anchorPane) {
        splitPane.prefWidthProperty().bind(anchorPane.widthProperty());
        splitPane.prefHeightProperty().bind(anchorPane.heightProperty());
        splitPane.maxWidthProperty().bind(anchorPane.widthProperty());
        splitPane.maxHeightProperty().bind(anchorPane.heightProperty());

        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
    }

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
    public static void resizeAndPositionMiddleButtons(Button button1, Button button2, AnchorPane pane, double relativePosition) {
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

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double buttonHeight = button1.getPrefHeight();

            // Calculate the Y position based on the relative position
            double positionY = height * relativePosition - (buttonHeight / 2);

            // Set the layout Y for both buttons
            button1.setLayoutY(positionY);
            button2.setLayoutY(positionY);
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

    public static void resizeAndPosition(Node node, AnchorPane pane, double widthRatio, double heightRatio) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double paneWidth = newVal.doubleValue();
            double nodeWidth = paneWidth * widthRatio;
            node.setScaleX(nodeWidth / node.getBoundsInParent().getWidth());
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double paneHeight = newVal.doubleValue();
            double nodeHeight = paneHeight * heightRatio;
            node.setScaleY(nodeHeight / node.getBoundsInParent().getHeight());
        });

        // Initial call to set size based on initial dimensions
        double paneWidth = pane.getWidth();
        double nodeWidth = paneWidth * widthRatio;
        node.setScaleX(nodeWidth / node.getBoundsInParent().getWidth());

        double paneHeight = pane.getHeight();
        double nodeHeight = paneHeight * heightRatio;
        node.setScaleY(nodeHeight / node.getBoundsInParent().getHeight());
    }

    public static void resizeAndPosition(Node node, AnchorPane pane, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double labelWidth = node.getBoundsInParent().getWidth();
            node.setLayoutX((width - labelWidth) / 2); // Center horizontally
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double labelHeight = node.getBoundsInParent().getHeight();
            double positionY = height * relativePosition - labelHeight / 2; // Center vertically
            node.setLayoutY(positionY);
        });
    }

    public static void resizeAndPositionTwoNodes(Node nodeLeft, Node nodeRight, AnchorPane pane, double relativePosition) {
        ChangeListener<Number> widthListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
                double width = newVal.doubleValue();

                // Position nodeLeft on the left
                nodeLeft.setLayoutX(0); // Align left

                // Position nodeRight on the right
                double nodeRightWidth = nodeRight.getBoundsInParent().getWidth();
                nodeRight.setLayoutX(width - nodeRightWidth); // Align right
            }
        };

        ChangeListener<Number> heightListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
                double height = newVal.doubleValue();

                // Center vertically with relativePosition
                double nodeHeight = height * relativePosition;

                // Set both nodes to the same height
                nodeLeft.resize(nodeLeft.getBoundsInParent().getWidth(), nodeHeight);
                nodeRight.resize(nodeRight.getBoundsInParent().getWidth(), nodeHeight);

                // Center nodeLeft vertically
                double positionYLeft = (height - nodeHeight) / 2;
                nodeLeft.setLayoutY(positionYLeft);

                // Center nodeRight vertically
                double positionYRight = (height - nodeHeight) / 2;
                nodeRight.setLayoutY(positionYRight);
            }
        };

        // Add listeners to the properties of the pane
        pane.widthProperty().addListener(widthListener);
        pane.heightProperty().addListener(heightListener);
    }


    public static void resizeAndPositionLabel(Label label, AnchorPane pane, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double labelWidth = label.getWidth();
            label.setLayoutX((width - labelWidth) / 2); // Center horizontally
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double labelHeight = label.getHeight();
            double positionY = height * relativePosition - labelHeight / 2; // Center vertically
            label.setLayoutY(positionY);
        });
    }

    public static void pinImageToCenter(ImageView imageView, AnchorPane pane, double widthRatio, double heightRatio) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double paneWidth = newVal.doubleValue();
            double imageWidth = paneWidth * widthRatio;
            double imageX = (paneWidth - imageWidth) / 2;
            imageView.setFitWidth(imageWidth);
            imageView.setLayoutX(imageX);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double paneHeight = newVal.doubleValue();
            double imageHeight = paneHeight * heightRatio;
            double imageY = (paneHeight - imageHeight) / 2;
            imageView.setFitHeight(imageHeight);
            imageView.setLayoutY(imageY);
        });
    }

    public static void pinImageToCenter1(ImageView imageView, AnchorPane pane, double widthRatio, double heightRatio) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double paneWidth = newVal.doubleValue();
            double imageWidth = paneWidth * widthRatio;
            imageView.setFitWidth(imageWidth);
            AnchorPane.setLeftAnchor(imageView, (paneWidth - imageWidth) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double paneHeight = newVal.doubleValue();
            double imageHeight = paneHeight * heightRatio;
            imageView.setFitHeight(imageHeight);
            AnchorPane.setTopAnchor(imageView, (paneHeight - imageHeight) / 2);
        });

        // Initial call to set position and size based on initial dimensions
        double paneWidth = pane.getWidth();
        double imageWidth = paneWidth * widthRatio;
        imageView.setFitWidth(imageWidth);
        AnchorPane.setLeftAnchor(imageView, (paneWidth - imageWidth) / 2);

        double paneHeight = pane.getHeight();
        double imageHeight = paneHeight * heightRatio;
        imageView.setFitHeight(imageHeight);
        AnchorPane.setTopAnchor(imageView, (paneHeight - imageHeight) / 2);
    }

    public static void pinImageAndLabelToCenter(ImageView imageView, Label label, AnchorPane pane, double widthRatio, double heightRatio) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double paneWidth = newVal.doubleValue();
            double imageWidth = paneWidth * widthRatio;
            imageView.setFitWidth(imageWidth);
            AnchorPane.setLeftAnchor(imageView, (paneWidth - imageWidth) / 2);

            double labelWidth = label.getWidth();
            label.setLayoutX((paneWidth - labelWidth) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double paneHeight = newVal.doubleValue();
            double imageHeight = paneHeight * heightRatio;
            imageView.setFitHeight(imageHeight);
            AnchorPane.setTopAnchor(imageView, (paneHeight - imageHeight) / 2);

            double labelHeight = label.getHeight();
            double labelY = (paneHeight - imageHeight) / 2 - labelHeight - 10; // adjust the Y position
            label.setLayoutY(labelY);
        });

        // Initial call to set position and size based on initial dimensions
        double paneWidth = pane.getWidth();
        double imageWidth = paneWidth * widthRatio;
        imageView.setFitWidth(imageWidth);
        AnchorPane.setLeftAnchor(imageView, (paneWidth - imageWidth) / 2);

        double paneHeight = pane.getHeight();
        double imageHeight = paneHeight * heightRatio;
        imageView.setFitHeight(imageHeight);
        AnchorPane.setTopAnchor(imageView, (paneHeight - imageHeight) / 2);

        double labelWidth = label.getWidth();
        label.setLayoutX((paneWidth - labelWidth) / 2);

        double labelHeight = label.getHeight();
        double labelY = (paneHeight - imageHeight) / 2 - labelHeight - 10; // adjust the Y position
        label.setLayoutY(labelY);
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

    public static void resizeAndPositionTextbox(TextField textField, AnchorPane pane, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            textField.setLayoutX((width - textField.getPrefWidth()) / 2);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();
            double buttonHeight = textField.getPrefHeight();
            double positionY = height * relativePosition - buttonHeight / 2;
            textField.setLayoutY(positionY);
        });
    }

    public static void resizeAndPositionTextAreaStickWithPane(TextArea textArea, AnchorPane pane, double widthRatio, double heightRatio, double relativePosition) {
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double paneWidth = newVal.doubleValue();
            double textAreaWidth = paneWidth * widthRatio;
            textArea.setPrefWidth(textAreaWidth);
            double textAreaX = (paneWidth - textAreaWidth) / 2;
            textArea.setLayoutX(textAreaX);
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            double paneHeight = newVal.doubleValue();
            double textAreaHeight = paneHeight * heightRatio;
            textArea.setPrefHeight(textAreaHeight);
            double textAreaY = paneHeight * relativePosition - textAreaHeight / 2;
            textArea.setLayoutY(textAreaY);
        });

        // Initial call to set position and size based on initial dimensions
        double paneWidth = pane.getWidth();
        double textAreaWidth = paneWidth * widthRatio;
        textArea.setPrefWidth(textAreaWidth);
        textArea.setLayoutX((paneWidth - textAreaWidth) / 2);

        double paneHeight = pane.getHeight();
        double textAreaHeight = paneHeight * heightRatio;
        textArea.setPrefHeight(textAreaHeight);
        textArea.setLayoutY((paneHeight * relativePosition) - (textAreaHeight / 2));
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