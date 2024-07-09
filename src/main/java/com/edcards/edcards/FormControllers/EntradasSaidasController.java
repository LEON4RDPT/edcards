package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
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
import javafx.scene.layout.Pane;

import javax.smartcardio.CardException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class EntradasSaidasController {
    public ImageView userImage;
    private List<String> allNfc = new ArrayList<>();
    public Label entsai;
    public Label nome;
    public Pane mainPane;
    String nomePessoa, es, cartao;
    Image img;
    Character tipo;
    boolean e_s, s_e;
    private final int currentRow = 0;
    private final int currentCol = 0;
    private int paneCount = 0;
    private final Set<String> processedCards = new HashSet<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane paneContainer;

    @FXML
    public void initialize() throws InterruptedException {
        var x = CartaoBLL.getAllCards();
        if (x == null) {
            System.out.println("No cards available in the database.");
            return;
        }
        allNfc = List.of(x);
        waits();
    }
    private void waits() throws InterruptedException {
        //executor.submit(this::cartaoLido);
        TimeUnit.SECONDS.sleep(1);
    }
    @FXML
    private void addPane(String nomePessoa, String es, Image img) {
        paneCount++;
        Pane pane = new Pane();
        double paneHeight = 262;
        double paneWidth = 200;
        pane.setPrefSize(paneWidth, paneHeight);


        Label nameLabel = new Label(nomePessoa);
        Label entsaiLabel = new Label(es);
        ImageView userImageView = new ImageView(img);
        userImageView.setFitWidth(147);
        userImageView.setFitHeight(169);
        userImageView.setLayoutX(27);
        userImageView.setLayoutY(0);
        nameLabel.setLayoutX(0);
        nameLabel.setLayoutY(164);
        nameLabel.setPrefWidth(200);
        nameLabel.setPrefHeight(30);
        entsaiLabel.setPrefWidth(200);
        entsaiLabel.setPrefHeight(30);
        entsaiLabel.setLayoutX(0);
        entsaiLabel.setLayoutY(200);
        pane.getChildren().addAll(nameLabel, entsaiLabel, userImageView);

        paneContainer.getChildren().addFirst(pane);

        for (int i = 0; i < paneContainer.getChildren().size(); i++) {
            Pane p = (Pane) paneContainer.getChildren().get(i);
            int panesPerRow = 5;
            int col = i % panesPerRow;
            int row = i / panesPerRow;
            double hSpacing = 15;
            p.setLayoutX(col * (paneWidth + hSpacing));
            double vSpacing = 15;
            p.setLayoutY(row * (paneHeight + vSpacing));
        }
    }
    public String cartaoLido() {
        while (true) {
            try {
                cartao="";
                cartao = LerCartao.lerIDCartao("/com/edcards/edcards/Main.fxml");
                if (!allNfc.contains(cartao)) {
                    continue;
                }
                var userByNFC = CartaoBLL.getUserByNFC(cartao);
                if (userByNFC != null) {
                    var pessoa = UsersBLL.getUser(userByNFC.getIduser());
                    switch (Objects.requireNonNull(pessoa)) {
                        case Funcionario funcionario -> tipo= 'f';
                        case Aluno aluno -> tipo ='a';
                        case Admin admin -> tipo = 'f';
                        default -> { }
                    }

                    if (tipo == 'a'){
                    nomePessoa = pessoa.getNome();
                    e_s = CartaoBLL.getEntSaiu(cartao);
                    img = pessoa.getFoto();
                    userImage.setImage(img);

                    if (!e_s) {
                        s_e = true;
                        es = "Entrou";
                        CartaoBLL.setEntrouSaiu(cartao, true);
                    } else {
                        s_e = false;
                        es = "Saiu";
                        CartaoBLL.setEntrouSaiu(cartao, false);
                    }
                    Platform.runLater(() -> {
                        nome.setText(nomePessoa);
                        entsai.setText(es);
                    });
                    Platform.runLater(() -> addPane(nomePessoa, es, img));
                }
                    }
            } catch (CardException | InterruptedException e) {
                e.printStackTrace();
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @FXML
    private void handleAlunoSemCartaoButton() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Aluno sem Cartão");
        dialog.setHeaderText("Insira o número do aluno:");

        TextField numeroAlunoField = new TextField();
        dialog.getDialogPane().setContent(numeroAlunoField);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == buttonTypeOk) {
                try {
                    int id = Integer.parseInt(numeroAlunoField.getText());
                    cartao = UsersBLL.getNFCUser(id);

                    if (cartao == null) {
                        FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err2));
                        return;
                    }
                    if (!allNfc.contains(cartao)) {
                        return;
                    }

                    var userByNFC = CartaoBLL.getUserByNFC(cartao);
                    if (userByNFC != null) {
                        var pessoa = UsersBLL.getUser(userByNFC.getIduser());
                        UsuarioEnum tipoU = UsersBLL.getTipoUser(id);
                        switch (tipoU) {
                            case ALUNO -> tipo ='a';
                            default -> tipo='e';
                        }

                        if (tipo == 'a') {
                            nomePessoa = pessoa.getNome();
                            e_s = CartaoBLL.getEntSaiu(cartao);
                            img = pessoa.getFoto();

                            s_e = !e_s;
                            es = s_e ? "Entrou" : "Saiu";

                            CartaoBLL.setEntrouSaiu(cartao, s_e);
                            userImage.setImage(img);
                            nome.setText(nomePessoa);
                            entsai.setText(es);
                            addPane(nomePessoa, es, img);
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }
}

