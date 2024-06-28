package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddHorarioController {
    private final boolean isRunning = true;
    private final ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    @FXML
    private ImageView HorarioUser;
    @FXML
    private ComboBox userPicker;
    @FXML
    private Text nomeText;
    Pessoa pessoa;
    List<Pessoa> users;

    @FXML
    public void initialize() {
        usersLoad();
        aguardarCartao();
        HorarioUser.setOnMouseClicked(event -> {
            FileChooser selFoto = new FileChooser();
            selFoto.setTitle("Escolha uma Foto...");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens (*.png, *.jpg)", "*.png", "*.jpg");
            selFoto.getExtensionFilters().add(extFilter);

            File foto = selFoto.showOpenDialog(null);

            if (foto != null) {
                Image image = new Image(foto.toURI().toString());
                HorarioUser.setImage(image);
            }
        });
    }

    private void usersLoad() {
        ObservableList<String> usersLoad = FXCollections.observableArrayList(
                UsersBLL.getUsersAll().toString()
        );
    }

    public void loadUser(ActionEvent event) {
        pessoa = (Pessoa) userPicker.getSelectionModel().getSelectedItem();
        nomeText.setText(pessoa.getNome());
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
}
