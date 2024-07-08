package com.edcards.edcards.FormControllers;

import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class SeeHorario {
    @FXML
    private HBox rootHBox;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private ImageView horarioUser;
    @FXML
    private AnchorPane rightPane;

    @FXML
    private Button exit;

    private void resize() {
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Bind the widths of the panes to the width of the root HBox
        leftPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.7)); // 60% for leftPane
        rightPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.3)); // 40% for rightPane

        ResizeUtil.pinImageToCenter(horarioUser,leftPane,0.5,0.5);
        horarioUser.setPreserveRatio(false);

        //buttons

        ResizeUtil.resizeAndPositionButton(exit, rightPane, 0.9);

    }

    public void initialize() {
        resize();

        setHorario();
    }

    private void setHorario() {
        var img = GlobalVAR.Dados.getPessoaAtual().getHorario();
        horarioUser.setImage(img);
    }

    @FXML
    private void handleExit() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?", "Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }
}