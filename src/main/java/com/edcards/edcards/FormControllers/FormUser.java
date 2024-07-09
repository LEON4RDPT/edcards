package com.edcards.edcards.FormControllers;

import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class FormUser {

    @FXML
    private TextArea textBox;
    @FXML
    private Button btnEntradasSaidas;
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




    private void resizeComponents() {
        // Ensure the panes are resized according to the proportions
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Bind the widths of the panes to the width of the root HBox
        leftPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.7)); // 60% for leftPane
        rightPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.3)); // 40% for rightPane

        ResizeUtil.pinImageToCenter(imageUser,leftPane,0.5,0.5);
        imageUser.setPreserveRatio(false);

        //buttons
        ResizeUtil.resizeAndPositionButton(btnPosAdmin, rightPane, 0.32);
        ResizeUtil.resizeAndPositionButton(btnEntradasSaidas, rightPane, 0.39);
        ResizeUtil.resizeAndPositionButton(btnPos, rightPane, 0.46);
        ResizeUtil.resizeAndPositionButton(btnMarcRef, rightPane, 0.53);
        ResizeUtil.resizeAndPositionButton(btnHorario, rightPane, 0.60);
        ResizeUtil.resizeAndPositionButton(btnRefMarc, rightPane, 0.67);
        ResizeUtil.resizeAndPositionButton(btnHistComp, rightPane, 0.74);
        ResizeUtil.resizeAndPositionButton(btnChangePin, rightPane, 0.81);
        ResizeUtil.resizeAndPositionButton(exit, rightPane, 0.88);



        //labels

        ResizeUtil.resizeAndPositionTextAreaStickWithPane(textBox, rightPane, 0.8,0.26,0.18);
    }



    @FXML
    private void initialize() {
        resizeComponents();
        setHiddenButtons();
        Pessoa pessoaA = GlobalVAR.Dados.getPessoaAtual();
        imageUser.setImage(pessoaA.getFoto());

        StringBuilder str = new StringBuilder();
        str.append("Nome: ").append(pessoaA.getNome());
        str.append("\n");
        str.append("Saldo: ").append(pessoaA.getSaldo()).append("€");
        str.append("\n");
        switch (pessoaA) {
            case Funcionario ignored -> str.append("Tipo: Funcionario");
            case Aluno ignored -> str.append("Tipo: Aluno");
            case Admin ignored -> str.append("Tipo: Administrador");
            default -> {
            }
        }
        textBox.setText(str.toString());

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
                btnEntradasSaidas.setVisible(false);
                break;
            default:
                break   ;
        }
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?","Confirmação")) {
            GlobalVAR.Dados.setPessoaAtual(null);
            setStage("/com/edcards/edcards/ReadCard.fxml");
        }

    }

    @FXML
    private void handlePosClick(ActionEvent event) throws IOException {
        setStage("/com/edcards/edcards/POS.fxml");
    }

    @FXML
    private void handleEntradas(ActionEvent actionEvent) throws IOException {
        setStage("/com/edcards/edcards/EntradasSaidas.fxml");
    }

    @FXML
    private void handleSeeHorario()  throws IOException {

        if (GlobalVAR.Dados.getPessoaAtual().getHorario() != null) {
            setStage("/com/edcards/edcards/SeeHorario.fxml");
        } else {
            FeedBackController.feedbackErro("Usuario não possui horario!");
        }
    }

    @FXML
    private void handleSeeHistComp() throws IOException {
        setStage("/com/edcards/edcards/ViewTransacs.fxml");

    }

    @FXML
    private void handlePinChange() throws IOException {
        setStage("/com/edcards/edcards/ModPinUser.fxml");
    }

    public void handleButtonVerRef(ActionEvent actionEvent) throws IOException {
        setStage("/com/edcards/edcards/VerRefeicoes.fxml");

    }
}
