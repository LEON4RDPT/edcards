package com.edcards.edcards.FormControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import java.io.File;


public class ControllerCompras implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("src/main/resources/com/edcards/edcards/images/logonfc.jpg");
        Image image = new Image(file.toURI().toString());

        ImageView[] slotImages = {
                imageSlot1,
                imageSlot2,
                imageSlot3,
                imageSlot4,
                imageSlot5,
                imageSlot6,
                imageSlot7,
                imageSlot8,
                imageSlot9,
                imageSlot10,
                imageSlot11,
                imageSlot12,
        };

        for (int i = 0; i < slotImages.length; i++) {
            slotImages[i].setImage(image);
        }
    }

    @FXML
    Button buttonSearchImage;
    @FXML
    Button buttonSell;

    @FXML
    ImageView imageSlot1;
    @FXML
    ImageView imageSlot2;
    @FXML
    ImageView imageSlot3;
    @FXML
    ImageView imageSlot4;
    @FXML
    ImageView imageSlot5;
    @FXML
    ImageView imageSlot6;
    @FXML
    ImageView imageSlot7;
    @FXML
    ImageView imageSlot8;
    @FXML
    ImageView imageSlot9;
    @FXML
    ImageView imageSlot10;
    @FXML
    ImageView imageSlot11;
    @FXML
    ImageView imageSlot12;

    @FXML
    private void searchImage() {

    }

    @FXML
    private void buttonClickTest() {
        System.out.println("hahahha");
    }
}
