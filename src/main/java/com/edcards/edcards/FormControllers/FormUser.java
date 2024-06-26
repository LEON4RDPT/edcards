package com.edcards.edcards.FormControllers;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class FormUser {
    public Button btnPosAdmin;
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
    @FXML
    public void initialize() {
        setHiddenButtons();
        Pessoa pessoaA = GlobalVAR.Dados.getPessoaAtual();
        imageUser.setImage(pessoaA.getFoto());
        labelNome.setText("Nome: " + pessoaA.getNome());
        labelSaldo.setText("Saldo: " + pessoaA.getSaldo() + "€");

        switch (pessoaA) {
            case Funcionario funcionario -> labelTipo.setText("Tipo: Funcionario");
            case Aluno aluno -> labelTipo.setText("Tipo: Aluno");
            case Admin admin -> labelTipo.setText("Tipo: Administrador");
            default -> { }
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
                break;
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
