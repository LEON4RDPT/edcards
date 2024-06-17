package com.edcards.edcards.FormControllers;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FormUser {
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
        Pessoa pessoaA = GlobalVAR.Dados.getPessoaAposPin();
        imageUser.setImage(pessoaA.getFoto());
        labelNome.setText("Nome: " + pessoaA.getNome());
        labelSaldo.setText("Saldo: " + String.valueOf(pessoaA.getSaldo()) + "€");

        switch (pessoaA) {
            case Funcionario funcionario -> labelTipo.setText("Tipo: Funcionario");
            case Aluno aluno -> labelTipo.setText("Tipo: Aluno");
            case Admin admin -> labelTipo.setText("Tipo: Administrador");
            default -> {
            }
        }
    }

    @FXML
    private void handlePosClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/edcards/edcards/POSAdmin.fxml")));
        Scene posAdminScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(posAdminScene);
        stage.show();
    }
}
