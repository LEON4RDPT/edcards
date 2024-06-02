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

public class FormUser {
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
        labelNome.setText(pessoaA.getNome());
        labelSaldo.setText(String.valueOf(pessoaA.getSaldo()));

        if (pessoaA instanceof Funcionario) {
            labelTipo.setText("Funcionario");
        } else if (pessoaA instanceof Aluno) {
            labelTipo.setText("Aluno");
        } else if (pessoaA instanceof Admin) {
            labelTipo.setText("Administrador");
        }
    }

    @FXML
    private void handlePosClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/Pos.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(posScene);
        stage.show();
    }
}
