package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.Enums.AseEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;


import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.ImageController.imageToByteArray;

public class CriarUserController {
    public AnchorPane leftPane;
    public AnchorPane rightPane;
    @FXML
    private HBox mainPane;
    @FXML
    private Button backBtn;

    String nome, morada, email, cc, idCartao, nfc;
    Image imgUser;
    int nus, num, turma, numEE;
    LocalDate data;
    UsuarioEnum tipo;
    AseEnum ase;
    byte[] fotoBLL;

    @FXML
    private TextField nameField;
    @FXML
    private TextField moradaField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nifField;

    @FXML
    private TextField numEEfield;
    @FXML
    private TextField numUtSaudeField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<String> tipoPicker;
    @FXML
    private TextField turmaPicker;
    @FXML
    private ComboBox<String> AsePicker;
    @FXML
    private ImageView imageUser;

    @FXML
    private Button inserirBtn;
    @FXML
    private Pane alunoPane;

    @FXML
    public void initialize() {
        resize();

        AsePicker.getItems().addAll(Arrays.stream(AseEnum.values()).map(Enum::name).toList());
        tipoPicker.getItems().addAll(Arrays.stream(UsuarioEnum.values()).map(Enum::name).toList());
        setImageController();
    }

    private void resize() {
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        leftPane.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.5)); // 60% for leftPane
        rightPane.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.5)); // 40% for rightPane
    }

    private void setImageController() {
        imageUser.setOnMouseClicked(event -> {
            FileChooser selFoto = new FileChooser();
            selFoto.setTitle("Escolha uma Foto...");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens (*.png, *.jpg)", "*.png", "*.jpg");
            selFoto.getExtensionFilters().add(extFilter);

            File foto = selFoto.showOpenDialog(null);
            var img = imageToByteArray(foto);
            fotoBLL = !Arrays.equals(img, new byte[0]) ? img : null;
            if (fotoBLL != null) {
                Image image = new Image(foto.toURI().toString());
                imageUser.setImage(image);
            }
        });
    }

    @FXML
    private void tipoAction() {
        String selectedTipo = tipoPicker.getValue();


        if (selectedTipo != null) {
            alunoPane.setVisible(selectedTipo.equals("ALUNO"));
        }
    }



    @FXML
    private void inserirBtnClick(ActionEvent event) {
        nome = nameField.getText();
        morada = moradaField.getText();
        cc = nifField.getText();
        imgUser = imageUser.getImage();
        data = dateField.getValue();

        if (imgUser == null) {
            FeedBackController.feedbackErro("Erro! Insira uma foto!");
            return;
        }

        if (nome == null || nome.isEmpty() || morada == null || morada.isEmpty() || cc == null || cc.isEmpty() || data == null) {
            FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err5));
            return;
        }

        tipo = UsuarioEnum.valueOf(tipoPicker.getValue());


        if (tipoPicker.getValue().equals("ALUNO")) {
            inserirAluno();
        } else if (tipoPicker.getValue().equals("ADMIN") || tipoPicker.getValue().equals("FUNCIONARIO")) {
            inserirAdminOrFuncionario();
        }
    }

    private void inserirAluno() {
        email = emailField.getText();
        numEE = Integer.parseInt(numEEfield.getText());
        turma = Integer.parseInt(turmaPicker.toString());

        idCartao = null;
        ase = AseEnum.valueOf(AsePicker.getValue());
        nus = Integer.parseInt(numUtSaudeField.getText());

        if (nome!= null || idCartao!= null || turma!= 0 || morada!= null || email!= null || cc!= null || numEE!= 0 || num!= 0 || numEEfield!= null || imgUser!= null || ase!= null || nus!= 0) {
            FeedBackController.feedbackErro(nome + morada + email + cc + numEE + num + idCartao + imgUser + data + ase + nus);
            var id = UsersBLL.inserir(nfc, nome, Date.valueOf(data), morada, tipo, cc, fotoBLL);
            if (id == 0) {
                FeedBackController.feedbackErro("Erro Não foi possivel inserir os Dados!");
            }
            UsersBLL.inserirAluno(id, numEE, email, turma, nus, ase);
        } else {
            FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err5));
        }
    }

    private void inserirAdminOrFuncionario() {
        var id = UsersBLL.inserir(nfc, nome, Date.valueOf(data), morada, tipo, cc, fotoBLL);
        if (id == 0) {
            FeedBackController.feedbackErro("Erro Dados duplicados!");
        } else {
            FeedBackController.feedbackErro("Usuario Registado com sucesso! Nome: " + nome);
        }
    }

    public void handleButtonBack() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/POSAdmin.fxml");
        }
    }

}
