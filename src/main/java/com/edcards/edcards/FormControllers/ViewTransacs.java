package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.TransacaoBLL;
import com.edcards.edcards.Programa.Controllers.FeedBackController;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class ViewTransacs  {

    @FXML
    private Button backBtn;
    @FXML
    private TableView vendasGrid;

    @FXML
    private void initialize() {
       int id = GlobalVAR.Dados.getPessoaAtual().getIduser();





    }

    @FXML
    private void handleButtonBack() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?","Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }

    }
}
