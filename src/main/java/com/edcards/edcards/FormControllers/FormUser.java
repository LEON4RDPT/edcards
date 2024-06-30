package com.edcards.edcards.FormControllers;

import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class FormUser {
    @FXML
    private Button btnChangePin;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Button btnPosAdmin;
    @FXML
    private HBox rootHBox;
    @FXML
    private Button exit;
    @FXML
    private ImageView imageUser;
    @FXML
    private Button btnPos;
    @FXML
    private Button btnHorario;
    @FXML
    private Button btnHistComp;
    @FXML
    private Button btnMarcRef;
    @FXML
    private Button btnRefMarc;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelSaldo;
    @FXML
    private Label labelTipo;



    private void resizeComponents() {
        // Ensure the panes are resized according to the proportions
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Bind the widths of the panes to the width of the root HBox
        leftPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.6)); // 60% for leftPane
        rightPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.4)); // 40% for rightPane

        ResizeUtil.pinImageToCenter(imageUser,leftPane,0.5,0.5);
        imageUser.setPreserveRatio(false);

        //buttons

        ResizeUtil.resizeAndPositionButton(btnHistComp, rightPane, 0.34);
        ResizeUtil.resizeAndPositionButton(btnPos, rightPane, 0.42);
        ResizeUtil.resizeAndPositionButton(btnMarcRef, rightPane, 0.50);
        ResizeUtil.resizeAndPositionButton(btnHorario, rightPane, 0.58);
        ResizeUtil.resizeAndPositionButton(btnRefMarc, rightPane, 0.66);
        ResizeUtil.resizeAndPositionButton(btnPosAdmin, rightPane, 0.74);
        ResizeUtil.resizeAndPositionButton(btnChangePin, rightPane, 0.82);
        ResizeUtil.resizeAndPositionButton(exit, rightPane, 0.9);


        //labels

        ResizeUtil.resizeAndPositionLabel(labelNome, rightPane, 0.1);
        ResizeUtil.resizeAndPositionLabel(labelTipo, rightPane, 0.2);
        ResizeUtil.resizeAndPositionLabel(labelSaldo, rightPane, 0.3);
    }



    @FXML
    private void initialize() {
        resizeComponents();
        setHiddenButtons();
        Pessoa pessoaA = GlobalVAR.Dados.getPessoaAtual();
        imageUser.setImage(pessoaA.getFoto());
        labelNome.setText("Nome: " + pessoaA.getNome());
        labelSaldo.setText("Saldo: " + pessoaA.getSaldo() + "€");

        switch (pessoaA) {
            case Funcionario ignored -> labelTipo.setText("Tipo: Funcionario");
            case Aluno ignored -> labelTipo.setText("Tipo: Aluno");
            case Admin ignored -> labelTipo.setText("Tipo: Administrador");
            default -> {
            }
        }

    }

    @FXML
    private void handlePosAdmClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/POSAdmin.fxml");

    }

    private void setHiddenButtons() {
        switch (GlobalVAR.Dados.getPessoaAtual()) {
            case Funcionario ignored:
                btnPosAdmin.setVisible(false);
                break;
            case Aluno ignored:
                btnPosAdmin.setVisible(false);
                btnPos.setVisible(false);
                break;
            default:
                break   ;
        }
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) throws IOException {
        GlobalVAR.Dados.setPessoaAtual(null);
        setStage("/com/edcards/edcards/ReadCard.fxml");
    }

    @FXML
    private void handlePosClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/POS.fxml");
    }

}
