package com.edcards.edcards.FormControllers;

import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class POSAdmin {
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPaneLeft;
    @FXML
    private AnchorPane anchorPaneRight;
    @FXML
    private HBox rootHBox;
    @FXML
    private Button exit;
    @FXML
    private Button addUsr;
    @FXML
    private Button modUsr;

    @FXML
    private Button addCard;
    @FXML
    private Button modCard;
    @FXML
    private Button modPinUsr;
    @FXML
    private Button addHorario;
    @FXML
    private Button modHorario;

    @FXML
    private Button addPrdt;
    @FXML
    private Button modPrdt;
    @FXML
    private Button viewTransacs;

    private void resize() {

        ResizeUtil.resizeGridPane(gridPane, anchorPaneRight);


        HBox.setHgrow(anchorPaneLeft, Priority.ALWAYS);
        HBox.setHgrow(anchorPaneRight, Priority.ALWAYS);

        anchorPaneLeft.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.3)); // 60% for leftPane
        anchorPaneRight.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.7)); // 40% for rightPane



        ResizeUtil.resizeAndPositionButton(exit,anchorPaneLeft,0.9);
    }




    @FXML
    private void initialize() {
        resize();
    }

    @FXML
    private void addUsrClick(javafx.event.ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/CriarUser.fxml");
    }

    @FXML
    private void modUsrClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/ModUser.fxml");
    }

    @FXML
    private void addCardClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/InsertCard.fxml");
    }

    @FXML
    private void modCardClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/UpdateUserCard.fxml");
    }

    @FXML
    private void modPinUsrClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/ChangePIN.fxml");
    }

    @FXML
    private void addHorarioClick(javafx.event.ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/AddHorario.fxml");
    }

    @FXML
    private void modHorarioClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/ModHorario.fxml");
    }

    @FXML
    private void remHorarioClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/DelHorario.fxml");
    }

    @FXML
    private void addPrdtClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/AddProduto.fxml");
    }

    @FXML
    private void modPrdtClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/ModProduto.fxml");
    }

    @FXML
    private void viewTransacsClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/ViewTransacs.fxml");
    }

    @FXML
    private void handleButtonSair(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?", "Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }
}