package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.AseEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.ImageController.imageToByteArray;

public class ModUserController {
    public Label cardNumber;
    public Button readCard;
    public TextField confPin;
    public TextField pin;
    public TextField numInterno;
    public Label nf;
    @FXML
    public ComboBox tipoPickerUser;
    public ComboBox<Integer> userPickerAluno;
    @FXML
    private TextField turmaField;
    @FXML
    private TextField numUtSaudeField;

    @FXML
    private ComboBox AsePicker;
    @FXML
    private Pane alunoPane;
    @FXML
    private Button backBtn;

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
    private boolean isDiffrentFoto = false;
    @FXML
    private Button modUser;
    double saldo;
    private Pessoa pessoaAtual;
    private List<Pessoa> users = new ArrayList<>();
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    String nome, morada, email, nif, num, numEE, turma, tipo, idCartao;
    byte[] imgUserBLL;
    int pinC;
    int num_aluno;

    @FXML
    public void initialize() {

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
        tipoPickerUser.setItems(opcoes);
            imageUser.setOnMouseClicked(event -> {
                FileChooser selFoto = new FileChooser();
                selFoto.setTitle("Escolha uma Foto...");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens (*.png, *.jpg)", "*.png", "*.jpg");
                selFoto.getExtensionFilters().add(extFilter);

                File foto = selFoto.showOpenDialog(null);
                var img = imageToByteArray(foto);
            imgUserBLL = !Arrays.equals(img, new byte[0]) ? img : null;
            if (foto != null) {
                Image image = new Image(foto.toURI().toString());
                imageUser.setImage(image);
                isDiffrentFoto = true;
            }
        });
//        aguardarCartao();
    }
    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/POSAdmin.fxml");
                    if (idCartao == null) {
                        //feedback
                        return;
                    }
                    if (CartaoBLL.existenteNFC(idCartao)) {
                        cardNumber.setText(idCartao);

                        int idUser = CartaoBLL.getIdUserByNFC(idCartao);
                        Pessoa user = UsersBLL.getUser(idUser);
                        String userNome = user.getNome();
                        selectUserFromName(userNome);
                    } else {
                        FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err13));
                        isRunning = true;
                    }

                } catch (Exception e) {
                    System.err.println("Error reading NFC card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    private void selectUserFromName(String nomeUser){
        isDiffrentFoto = false;
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
            }
            imageUser.setImage(pessoaAtual.getFoto());
        }
    }
    @FXML
    public void selectUser(ActionEvent event) {
        isDiffrentFoto = false;
        int num = userPickerAluno.getValue();
        Pessoa pessoaAtual = UsersBLL.getUserByNum(num);
        if (pessoaAtual == null) {
            pessoaAtual = UsersBLL.getUserByNumAluno(num);
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

    @FXML
    private void handleModUser(ActionEvent actionEvent) throws IOException {
        if (pessoaAtual == null) {
            FeedBackController.feedbackErro("Erro! Selecione o Usuario!");
            return;
        }
        int id = pessoaAtual.getIduser();
        boolean alterouTipo = false;

        //aluno
        if (tipoPicker.getSelectionModel().getSelectedIndex() == 0) { //caso seja ALuno OU queira inserir dados aluno

            if (numEEfield.getText().isEmpty() || numEEfield.getText().length() != 9) {
                FeedBackController.feedbackErro(ErrorEnum.err5.toString());
                return;
            }
            var numEE = Integer.parseInt(numEEfield.getText());

            if (emailField.getText().isEmpty()) {
                FeedBackController.feedbackErro(ErrorEnum.err5.toString());
                return;
            }
            var email = emailField.getText();

            if (turmaField.getText().isEmpty()) {
                FeedBackController.feedbackErro(ErrorEnum.err5.toString());
                return;
            }
            var turma = Integer.parseInt(turmaField.getText());


            if (numUtSaudeField.getText().isEmpty() || numUtSaudeField.getText().length() != 9) {
                FeedBackController.feedbackErro(ErrorEnum.err5.toString());
                return;
            }
            var numUt = Integer.parseInt(numUtSaudeField.getText());

            if (AsePicker.getSelectionModel().getSelectedIndex() == -1) {
                FeedBackController.feedbackErro(ErrorEnum.err5.toString());
                return;
            }
            var ase = AseEnum.fromDbValue(AsePicker.getSelectionModel().getSelectedIndex());
            if (numInterno.getText().isEmpty()){
                FeedBackController.feedbackErro(ErrorEnum.err5.toString());
            }
            if (pessoaAtual instanceof Aluno) {
                UsersBLL.setEE_numAluno(id,numEE);
                UsersBLL.setTurmaAluno(id,turma);
                UsersBLL.setAseAluno(id,ase);
                UsersBLL.setEmailAluno(id,email);
                UsersBLL.setAseAluno(id,ase);
            }
            else {
                UsersBLL.setTipoUser(pessoaAtual.getIduser(),UsuarioEnum.ALUNO);
                UsersBLL.inserirAluno(pessoaAtual.getIduser(),numEE,email,turma,numUt,ase,num_aluno);
            }
        }
        else {
            if (pessoaAtual instanceof Aluno) { //caso seja aluno e QUEIRA TROCAR
                UsersBLL.deleteAluno(id);
            }


            var enumP = UsuarioEnum.fromDbValue((tipoPicker.getSelectionModel().getSelectedIndex()));
            if (enumP == UsuarioEnum.FUNCIONARIO && !(pessoaAtual instanceof Funcionario)) {
                UsersBLL.setTipoUser(id,UsuarioEnum.FUNCIONARIO);
                alterouTipo = true;
            }
            if (enumP == UsuarioEnum.ADMINISTRADOR && !(pessoaAtual instanceof Admin)) {
                UsersBLL.setTipoUser(id,UsuarioEnum.ADMINISTRADOR);
                alterouTipo = true;

            }
        }
        if (isDiffrentFoto && !Arrays.equals(imgUserBLL, new byte[0])) {
            UsersBLL.setFotoUser(id,imgUserBLL);
        }

        if (!pessoaAtual.getNome().equals(nameField.getText())) {
            UsersBLL.setNomeUser(id,nameField.getText());
        }

        if (!pessoaAtual.getCartaoC().equals(ccField.getText())) {
            UsersBLL.setCCUser(id,ccField.getText());
        }

        if (!pessoaAtual.getMorada().equals(moradaField.getText())) {
            UsersBLL.setMoradaUser(id,moradaField.getText());
        }

        if (!pessoaAtual.getDataNasc().toLocalDate().equals(dateField.getValue())) {
            UsersBLL.setDataNascUser(id, Date.valueOf(dateField.getValue()));
        }
        if(pessoaAtual.getNum() == 0){
            UsersBLL.setnum(id, Integer.parseInt(numInterno.getText()));
        }
        if(!pessoaAtual.getNumCartao().equals(cardNumber.getText())){
            CartaoBLL.setCodigo(pessoaAtual.getNumCartao(), cardNumber.getText());
            UsersBLL.setCodigoUser(id, cardNumber.getText());

        }

        var adminAtual = GlobalVAR.Dados.getPessoaAtual();
        if (pessoaAtual.getIduser() == adminAtual.getIduser()) {
            //caso sejas TU a editar os teus dados e sejas ADMIN
            if (alterouTipo) { //saiu de admin terá de sair do programa!!
                GlobalVAR.Dados.setPessoaAtual(null);
                GlobalVAR.StageController.setStage("/com/edcards/edcards/ReadCard.fxml");
            }
            GlobalVAR.Dados.setPessoaAtual(pessoaAtual);
        }

    }

    public void handleLerCard(ActionEvent actionEvent) {
        //cardNumber.setText("Passe o cartão...");
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/POSAdmin.fxml");
                    if (idCartao == null) {
                        return;
                    }
                    if (CartaoBLL.existenteNFC(idCartao)) {
                        Platform.runLater(() -> {
                            cardNumber.setText(idCartao);
                            saldo = CartaoBLL.getSaldo(idCartao);
                            pinC = CartaoBLL.getPin(idCartao);
                            isRunning = false;
                        });
                    } else {
                        Platform.runLater(() -> FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err13)));
                        isRunning = true;
                    }

                } catch (Exception e) {
                    System.err.println("Error reading NFC card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    public void selectTipoUser(ActionEvent actionEvent) {
        var enumU = UsuarioEnum.valueOf((String) tipoPickerUser.getSelectionModel().getSelectedItem());
        users.clear();
        users = UsersBLL.getUsers(enumU);
        if (users != null) {
            this.users = users;
        } else {
            //feedback
        }

        if (enumU == UsuarioEnum.ALUNO){
            List<Integer> alunoNums = UsersBLL.getAlunoNums(enumU.toDbValue());
            ObservableList<Integer> observableAlunoNums = FXCollections.observableArrayList(alunoNums);
            userPickerAluno.setItems(observableAlunoNums);
        } else if(enumU == UsuarioEnum.ADMINISTRADOR){
            List<Integer> funcNums = UsersBLL.getFuncNums(enumU.toDbValue());
            ObservableList<Integer> observableFuncNums = FXCollections.observableArrayList(funcNums);
            userPickerAluno.setItems(observableFuncNums);
        } else{
            List<Integer> funcNums = UsersBLL.getFuncNums(enumU.toDbValue());
            ObservableList<Integer> observableFuncNums = FXCollections.observableArrayList(funcNums);
            userPickerAluno.setItems(observableFuncNums);
        }
    }
}