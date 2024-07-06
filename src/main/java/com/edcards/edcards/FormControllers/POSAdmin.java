package com.edcards.edcards.FormControllers;

import com.edcards.edcards.Programa.Controllers.FeedBackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class POSAdmin {
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
    private Button remHorario;
    @FXML
    private Button addPrdt;
    @FXML
    private Button modPrdt;
    @FXML
    private Button viewTransacs;

    @FXML
    public void initialize() {

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