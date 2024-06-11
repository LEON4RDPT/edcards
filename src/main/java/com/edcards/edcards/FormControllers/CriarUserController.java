package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CriarUserController {
    String nome, morada, email, nif, num, numEE, turma, tipo, idCartao;
    Image imgUser;
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    @FXML
    private TextField nameField, moradaField, emailField, nifField, numField, numEEfield;
    @FXML
    private DatePicker dateField ;
    @FXML
    private ComboBox<String> tipoPicker;
    @FXML
    private ComboBox<String> turmaPicker;
    @FXML
    private ImageView imageUser;
    @FXML
    private Text cardID;
    @FXML
    private Button inserirBtn;

    @FXML
    public void initialize(){
        nameField = new TextField();
        moradaField = new TextField();
        emailField = new TextField();
        nifField = new TextField();
        numField = new TextField();
        numEEfield = new TextField();


        ObservableList<String> opcoes = FXCollections.observableArrayList(
                "Administrador", "Aluno", "Funcionário"
        );

        tipoPicker.setItems(opcoes);
        aguardarCartao();

        imageUser.setOnMouseClicked(event -> {
            FileChooser selFoto = new FileChooser();
            selFoto.setTitle("Escolha uma Foto...");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens (*.png, *.jpg)", "*.png", "*.jpg");
            selFoto.getExtensionFilters().add(extFilter);

            File foto = selFoto.showOpenDialog(null);

            if (foto != null) {
                Image image = new Image(foto.toURI().toString());
                imageUser.setImage(image);
            }
        });
    }

    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao();
                    cardID.setText(idCartao);
                    break;

                } catch (Exception ignored) {
                }
            }
        });
    }
    @FXML
    private void inserirBtnClick(ActionEvent event) throws IOException {
        if (nome == null || turma == null || morada == null || email == null || nif == null || numEE == null || num == null || numEEfield == null || imgUser == null || idCartao == null) {
            nome = nameField.getText();
            morada = moradaField.getText();
            email = emailField.getText();
            nif = nifField.getText();
            numEE = numEEfield.getText();
            num = numField.getText();
            turma = turmaPicker.toString();
            tipo = tipoPicker.toString();
            idCartao = cardID.toString();
            imgUser = imageUser.getImage();
            //todo
        }else {
            //todo
        }
    }
}
