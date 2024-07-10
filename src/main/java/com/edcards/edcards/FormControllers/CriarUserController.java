package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.Enums.AseEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;

import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;


import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.ImageController.imageToByteArray;

public class CriarUserController {
    public AnchorPane leftPane;
    public AnchorPane rightPane;
    public TextField numAlu;
    public TextField numInterno;
    public Label nf;
    @FXML
    private HBox mainPane;
    @FXML
    private Button backBtn;
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    String nome, morada, email, cc, idCartao, nfc;
    Image imgUser;
    int nus, num, turma, numEE;
    LocalDate data;
    UsuarioEnum tipo;
    AseEnum ase;
    byte[] fotoBLL;
    int num_aluno;
    int num_func;
    @FXML
    private Label cardNumber;
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
        setVerificationControllers();



        AsePicker.getItems().addAll(Arrays.stream(AseEnum.values()).map(Enum::name).toList());
        tipoPicker.getItems().addAll(Arrays.stream(UsuarioEnum.values()).map(Enum::name).toList());
        setImageController();
        aguardarCartao();
    }

// Outros imports...

    private boolean errorOccurred = false; // Add this flag to control the feedback execution

    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning && !errorOccurred) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/POSAdmin.fxml");
                    if (idCartao == null) {
                        // feedback
                        return;
                    }

                    if (CartaoBLL.existenteNFC(idCartao)) {
                        Platform.runLater(() -> {
                            cardNumber.setText(idCartao);
                            nfc = idCartao;
                        });
                    } else {
                        Platform.runLater(() -> {
                            errorOccurred = true; // Set the flag to true to show feedback only once
                            FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err13));
                            //TODO
                        });
                        break; // Exit the loop
                    }
                } catch (Exception e) {
                    System.err.println("Error reading NFC card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    private void setVerificationControllers() {
        nameField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });

        turmaPicker.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });
        numAlu.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });

        numEEfield.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });

        numUtSaudeField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });


        numEEfield.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (numEEfield.getText().length() > 9) {
                    numEEfield.setText(numEEfield.getText().substring(0, 9));
                }
            }
        });

        turmaPicker.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (turmaPicker.getText().length() > 6) {
                    turmaPicker.setText(turmaPicker.getText().substring(0, 6));
                }
            }
        });

        numUtSaudeField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (numUtSaudeField.getText().length() > 9) {
                    numUtSaudeField.setText(numUtSaudeField.getText().substring(0, 9));
                }
            }
        });
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
            if (selectedTipo.equals("ALUNO")) {
                alunoPane.setVisible(true);
                numInterno.setVisible(false);
                nf.setVisible(false);
                num_func = 0;
            } else {
                alunoPane.setVisible(false);
                numInterno.setVisible(true);
                nf.setVisible(true);
            }

        }
    }



    @FXML
    private void inserirBtnClick(ActionEvent event) {
        nome = nameField.getText();
        morada = moradaField.getText();
        cc = nifField.getText();
        imgUser = imageUser.getImage();
        data = dateField.getValue();
        if(cardNumber.getText().equals("Passe o Cartão...")){
            nfc=null;
        }else{
            nfc = cardNumber.getText();
        }

        if (imgUser == null) {
            FeedBackController.feedbackErro("Erro! Insira uma foto!");
            return;
        }

        if (nome == null || nome.isEmpty() || morada == null || morada.isEmpty() || cc == null || cc.isEmpty() || data == null || nfc == null) {
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
        turma = Integer.parseInt(turmaPicker.getText());
        idCartao = nfc;
        ase = AseEnum.valueOf(AsePicker.getValue());
        nus = Integer.parseInt(numUtSaudeField.getText());
        num_aluno = Integer.parseInt(numAlu.getText());


        if (nome!= null || idCartao!= null || turma!= 0 || morada!= null || email!= null || cc!= null || numEE!= 0 || num!= 0 || numEEfield!= null || imgUser!= null || ase!= null || nus!= 0 || num_aluno!=0) {
            FeedBackController.feedbackConf(nome + morada + email + cc + numEE + num + idCartao + imgUser + data + ase + nus + num_aluno);
            var id = UsersBLL.inserir(nfc, nome, Date.valueOf(data), morada, tipo, cc, fotoBLL,0);
            if (id == 0) {
                FeedBackController.feedbackErro("Erro Não foi possivel inserir os Dados!");
            }
            UsersBLL.inserirAluno(id, numEE, email, turma, nus, ase, num_aluno);
        } else {
            FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err5));
        }
    }

    private void inserirAdminOrFuncionario() {
        var id = UsersBLL.inserir(nfc, nome, Date.valueOf(data), morada, tipo, cc, fotoBLL,num_func);
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
