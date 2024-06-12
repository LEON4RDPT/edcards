package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import com.edcards.edcards.Programa.Controllers.NIFValidator;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CriarUserController {
    String nome, morada, email, nif, num, numEE, turma, idCartao,ase, nfc;
    Image imgUser;
    int nus;
    LocalDate data;
    UsuarioEnum tipo;
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    @FXML
    private TextField nameField, moradaField, emailField, nifField, numField, numEEfield, numUtSaudeField;
    @FXML
    private DatePicker dateField ;
    @FXML
    private ComboBox<String> tipoPicker;
    @FXML
    private ComboBox<String> turmaPicker;
    @FXML
    private ComboBox<String> AsePicker;
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
        if (tipoPicker.getValue().equals("Aluno")) {
            if (nome != null || cardID != null||turma != null || morada != null || email != null || nif != null || numEE != null || num != null || numEEfield != null || imgUser != null || idCartao != null || ase != null || nus != 0) {

                nome = nameField.getText();
                morada = moradaField.getText();
                email = emailField.getText();
                nif = nifField.getText();
                numEE = numEEfield.getText();
                num = numField.getText();
                turma = turmaPicker.toString();
                tipo = UsuarioEnum.valueOf(tipoPicker.getValue());
                idCartao = cardID.toString();
                imgUser = imageUser.getImage();
                data = dateField.getValue();
                ase = AsePicker.getValue();
                nus = Integer.parseInt(numUtSaudeField.getText());
                System.out.println(nome + morada + email + nif + numEE + num + idCartao + imgUser + data + ase + nus);
                if (NIFValidator.isValidNIF(nif)) {
                    //UsersBLL.inserir(nfc, nome, Date.valueOf(data), morada, tipo, nif );
                }

            } else {
                System.out.println(ErrorEnum.err5);
            }
        } else {

        }
    }

}
