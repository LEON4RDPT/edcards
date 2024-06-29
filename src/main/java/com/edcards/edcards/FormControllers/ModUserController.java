package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.AseEnum;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.commons.io.filefilter.FalseFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModUserController {
    @FXML
    private TextField turmaField;
    @FXML
    private TextField numUtSaudeField;
    @FXML
    private TextField numFieldAse;
    @FXML
    private ComboBox AsePicker;
    @FXML
    private Pane alunoPane;
    @FXML
    private Button backBtn;
    String nome, morada, email, nif, num, numEE, turma, tipo, idCartao;
    Image imgUser;
    List<Pessoa> users;
    @FXML
    private TextField nameField;
    @FXML
    private TextField moradaField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField ccField;
    @FXML
    private TextField numEEfield;
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<String> tipoPicker;
    @FXML
    private ComboBox<String> userPicker;
    @FXML
    private ImageView imageUser;

    @FXML
    private Button modUser;

    private Pessoa pessoaAtual;

    @FXML
    public void initialize() {

        usersLoad();

        ObservableList<String> opcoes = FXCollections.observableArrayList(
                Arrays.stream(UsuarioEnum.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );

        ObservableList<String> opcoesAse = FXCollections.observableArrayList(
                Arrays.stream(AseEnum.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
        AsePicker.setItems(opcoesAse);

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
        String nomeUser = userPicker.getValue();

        for (Pessoa p : users) {
            if (p.getNome().equals(nomeUser)) {
                pessoaAtual = p;
            }
        }

        if (pessoaAtual != null) {
            nameField.setText(pessoaAtual.getNome());
            dateField.setValue(pessoaAtual.getDataNasc().toLocalDate());
            ccField.setText(pessoaAtual.getCartaoC());
            moradaField.setText(pessoaAtual.getMorada());

            switch (pessoaAtual) {
                case Aluno ignored -> tipoPicker.getSelectionModel().select(0);
                case Funcionario ignored -> tipoPicker.getSelectionModel().select(1);
                case Admin ignored -> tipoPicker.getSelectionModel().select(2);
                case null, default -> { }
            }
            try {
                if (alunoPane.isVisible() && pessoaAtual instanceof Aluno) {
                    numEEfield.setText(String.valueOf(((Aluno) pessoaAtual).getNumEE()));
                    emailField.setText(((Aluno) pessoaAtual).getEmailEE());
                    turmaField.setText(String.valueOf(((Aluno) pessoaAtual).getNumTurma()));
                    numUtSaudeField.setText(String.valueOf(((Aluno) pessoaAtual).getNumUtente()));
                    AsePicker.getSelectionModel().select(((Aluno) pessoaAtual).getAse().toString());
                }
            } catch (NullPointerException ignored) {
                //do nothing dados do aluno nao existem.
            }
            imageUser.setImage(pessoaAtual.getFoto());
            //data


        }


   }

    private void usersLoad() {
        var userLoad = UsersBLL.getUsersAll();
        ObservableList<String> usersLoad = FXCollections.observableArrayList(
                Arrays.stream(Objects.requireNonNull(userLoad).toArray(new Pessoa[0]))
                        .map(Pessoa::getNome)
                        .collect(Collectors.toList())

        );
        userPicker.setItems(usersLoad);
        users = userLoad;
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/POSAdmin.fxml");
        }
    }

    @FXML
    private void handleTipoChanged(ActionEvent actionEvent) {
        try {
            alunoPane.setVisible(tipoPicker.getSelectionModel().getSelectedItem().equals("ALUNO"));
        } catch (NullPointerException ignored) {

        }
    }
}