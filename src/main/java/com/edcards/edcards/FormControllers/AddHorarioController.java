package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.ConfEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.ImageController.imageToByteArray;

public class AddHorarioController {
    private final boolean isRunning = true;
    private final ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    public ComboBox tipoPickerUser;
    @FXML
    private ImageView HorarioUser;
    private byte[] image;
    private File foto;
    @FXML
    private ComboBox<Integer> userPickerAluno;
    @FXML
    private Text nomeText;
    Pessoa pessoa;
    List<Pessoa> users;
    int id;
    private Image imgh;
    @FXML
    public void initialize() {
        //usersLoad();
        ObservableList<String> opcoes = FXCollections.observableArrayList(
                Arrays.stream(UsuarioEnum.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
        tipoPickerUser.setItems(opcoes);
        HorarioUser.setOnMouseClicked(event -> {
            FileChooser selFoto = new FileChooser();
            selFoto.setTitle("Escolha uma Foto...");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens (*.png, *.jpg)", "*.png", "*.jpg");
            selFoto.getExtensionFilters().add(extFilter);

            foto = selFoto.showOpenDialog(null);
            var img = imageToByteArray(foto);
            image = !Arrays.equals(img, new byte[0]) ? img : null;
            if (image != null) {
                Image image = new Image(foto.toURI().toString());
                HorarioUser.setImage(image);
            }
        });
        aguardarCartao();
    }

//    private void usersLoad() {
//        var userLoad = UsersBLL.getUsersAll();
//        ObservableList<String> usersLoad = FXCollections.observableArrayList(
//                Arrays.stream(Objects.requireNonNull(userLoad).toArray(new Pessoa[0]))
//                        .map(Pessoa::getNome)
//                        .collect(Collectors.toList())
//        );
//        userPicker.setItems(usersLoad);
//        users = userLoad;
//    }

    public void loadUser(ActionEvent event) {
        int numUser = userPickerAluno.getValue();

        for (Pessoa p : users) {
            if (p.getNum() == numUser) {
                pessoa = p;
            }
        }
        if (pessoa != null) {
            HorarioUser.setImage(null);
            id= (pessoa.getIduser());
            nomeText.setText(pessoa.getNome());
            imgh = pessoa.getHorario();
            HorarioUser.setImage(imgh);
        }
    }

    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao(null);
                    int user = CartaoBLL.getIdUserByNFC(idCartao);
                    Pessoa userr = UsersBLL.getUser(user);
                    nomeText.setText(userr.getNome());
                    id = userr.getIduser();
                    HorarioUser.setImage(userr.getHorario());
                    break;
                } catch (Exception ignored) {
                }
            }
        });
    }
    @FXML
    public void addHorarioBtn(ActionEvent event) throws IOException {
        if (image != null) {
            UsersBLL.setHorarioUser(id,image);
            FeedBackController.feedbackConf(String.valueOf(ConfEnum.conf4));
        }

    }
    @FXML
    private void handleButtonBack() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/POSAdmin.fxml");
        }
    }

    public void remHorarioBtn(ActionEvent actionEvent) {
        UsersBLL.setHorarioUser(id, null);
        FeedBackController.feedbackConf(String.valueOf(ConfEnum.conf7));
        HorarioUser.setImage(null);
    }

    @FXML
    public void selectTipoUser(ActionEvent actionEvent) {
        var enumU = UsuarioEnum.valueOf((String) tipoPickerUser.getSelectionModel().getSelectedItem());
        users = UsersBLL.getUsers(enumU);
        if (users != null) {
            this.users = users;
        } else {
            //feedback
        }

        if (enumU == UsuarioEnum.ALUNO){
            List<Integer> alunoNums = UsersBLL.getNums(UsuarioEnum.ALUNO.toDbValue());
            ObservableList<Integer> observableAlunoNums = FXCollections.observableArrayList(alunoNums);
            userPickerAluno.setItems(observableAlunoNums);
        } else if (enumU == UsuarioEnum.ADMINISTRADOR){
            List<Integer> adminNums = UsersBLL.getNums(UsuarioEnum.ADMINISTRADOR.toDbValue());
            ObservableList<Integer> observableFuncNums = FXCollections.observableArrayList(adminNums);
            userPickerAluno.setItems(observableFuncNums);
        } else if(enumU == UsuarioEnum.FUNCIONARIO) {
            List<Integer> funcNums = UsersBLL.getNums(UsuarioEnum.FUNCIONARIO.toDbValue());
            ObservableList<Integer> observableFuncNums = FXCollections.observableArrayList(funcNums);
            userPickerAluno.setItems(observableFuncNums);
        }
    }
}
