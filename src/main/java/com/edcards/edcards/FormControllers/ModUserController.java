package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModUserController {
    String nome, morada, email, nif, num, numEE, turma, tipo, idCartao;
    Image imgUser;
    private final boolean isRunning = true;
    private final ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    List<Pessoa> users;
    @FXML
    private TextField nameField, moradaField, emailField, nifField, numField, numEEfield;
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<String> tipoPicker;
    @FXML
    private ComboBox<String> turmaPicker;
    @FXML
    private ComboBox<String> userPicker;
    @FXML
    private ImageView imageUser;
    @FXML
    private Text cardID;
    @FXML
    private Button modUser;
    @FXML
    public void initialize() {

        usersLoad();
        aguardarCartao();
        ObservableList<String> opcoes = FXCollections.observableArrayList(
                "Administrador", "Aluno", "Funcionário"
        );
        users = UsersBLL.getUsersAll();
        tipoPicker.setItems(opcoes);

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
    @FXML
    public void selectUser(ActionEvent event) {

    }
    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao();
                    int user = CartaoBLL.getIdUserByNFC(idCartao);
                    UsersBLL.getUser(user);
                    break;

                } catch (Exception ignored) {
                }
            }
        });
    }
    private void usersLoad() {
        ObservableList<String> usersLoad = FXCollections.observableArrayList(
                UsersBLL.getUsersAll().toString()
        );
    }
}