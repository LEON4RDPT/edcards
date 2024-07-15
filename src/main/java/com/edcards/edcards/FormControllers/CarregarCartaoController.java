package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Controllers.ArredondarController;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;

public class CarregarCartaoController {

    public AnchorPane leftPane;
    public AnchorPane rightPane;
    public HBox root;
    public Button buttonLimpar;
    public Button buttonAddSaldo;
    @FXML
    private GridPane grid;
    @FXML
    private Label labelnome;
    @FXML
    private Label labelSaldo;
    @FXML
    private Button buttonBack;
    double saldoAAdicionar;
    DecimalFormat fd = new DecimalFormat("#.##");
    @FXML
    private ImageView nota5, nota10, nota20, nota50, m1cent, m2cent, m5cent, m10cent, m20cent, m50cent, m1e, m2e;
    @FXML
    private Label saldoAadicionar;

    private final String saldoAdd = "Saldo a Adicionar";

    int pinDb;
    @FXML
    public void initialize() {

        resizeAll();



        labelnome.setText(GlobalVAR.Dados.getClientePOS().getNome());
        labelSaldo.setText(GlobalVAR.Dados.getClientePOS().getSaldo().toString());



        nota5.setOnMouseClicked((event -> {
            saldoAAdicionar +=  5.00;
            refreshSaldo();
        }));
        nota10.setOnMouseClicked((event -> {
            saldoAAdicionar += 10.0;
            refreshSaldo();
        }));
        nota20.setOnMouseClicked((event -> {

            saldoAAdicionar += 20.0;
            refreshSaldo();
        }));
        nota50.setOnMouseClicked((event -> {
            saldoAAdicionar += 50.0;
            refreshSaldo();
        }));
        m1cent.setOnMouseClicked((event -> {
            saldoAAdicionar += 0.01;
            refreshSaldo();
        }));
        m2cent.setOnMouseClicked((event -> {
            saldoAAdicionar += 0.02;
            refreshSaldo();
        }));
        m5cent.setOnMouseClicked((event -> {
            saldoAAdicionar += 0.05;
            refreshSaldo();
        }));
        m10cent.setOnMouseClicked((event -> {
            saldoAAdicionar += 0.10;
            refreshSaldo();
        }));
        m20cent.setOnMouseClicked((event -> {
            saldoAAdicionar += 0.20;
            refreshSaldo();
        }));
        m50cent.setOnMouseClicked((event -> {
            saldoAAdicionar += 0.50;
            refreshSaldo();
        }));
        m1e.setOnMouseClicked((event -> {
            saldoAAdicionar += 1.00;
            refreshSaldo();
        }));
        m2e.setOnMouseClicked((event -> {
            saldoAAdicionar += 2.00;
            refreshSaldo();
        }));
    }

    private void resizeAll() {
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            leftPane.setPrefWidth(width / 4);
            rightPane.setPrefWidth((width / 4) * 3);
        });

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            leftPane.setPrefHeight(height);
            rightPane.setPrefHeight(height);
        });

        double initialWidth = root.getPrefWidth();
        leftPane.setPrefWidth(initialWidth / 4);
        rightPane.setPrefWidth((initialWidth / 4) * 3);

        double initialHeight = root.getPrefHeight();
        leftPane.setPrefHeight(initialHeight);
        rightPane.setPrefHeight(initialHeight);



        //grid

        ResizeUtil.resizeAndPosition(labelnome,leftPane,0.1);
        ResizeUtil.resizeAndPosition(labelSaldo,leftPane,0.2);
        ResizeUtil.resizeAndPosition(saldoAadicionar,leftPane,0.3);
        ResizeUtil.resizeAndPosition(buttonBack,leftPane,0.9);

        //ResizeUtil.resizeAndCenterMiddleButtons(buttonLimpar,buttonAddSaldo);
        ResizeUtil.resizeAndPosition(buttonAddSaldo,leftPane,0.8);
        ResizeUtil.resizeAndPosition(buttonLimpar,leftPane,0.7);

    }

    @FXML
    public void addSaldo() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmar Carregamento");
        dialog.setHeaderText("Insira o seu PIN:");

        TextField pinField = new TextField();
        dialog.getDialogPane().setContent(pinField);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == buttonTypeOk) {
                try {
                    int pin = Integer.parseInt(pinField.getText());
                    pinDb = GlobalVAR.Dados.getPessoaAtual().getPin();
                    if (pinDb == pin) {
                        var nfc = UsersBLL.getNFCUser(GlobalVAR.Dados.getClientePOS().getIduser());
                        var saldoAntigo = CartaoBLL.getSaldo(nfc);
                        if (nfc == null){
                            FeedBackController.feedbackErro("Nenhum cliente Selecionado!");
                            return;
                        }

                        if (saldoAAdicionar == 0) {
                            FeedBackController.feedbackErro("Impossivel adicionar saldo 0!");
                            return;
                        }
                        if (FeedBackController.feedbackYesNo("Deseja adicionar " + saldoAAdicionar + " €", "Confirmação")) {
                            var x = ArredondarController.roundToTwoDecimalPlacesRetDouble(saldoAntigo + saldoAAdicionar);
                            CartaoBLL.setSaldo(nfc,  + x);
                            FeedBackController.feedbackConf("Saldo adicionado");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void refreshSaldo() {
        if (saldoAAdicionar < 1000) {
            var x = ArredondarController.roundToTwoDecimalPlaces(saldoAAdicionar);
            saldoAadicionar.setText(String.format(saldoAdd + " " + x ));
        } else {
            saldoAadicionar.setText(1000.00 + "€");
            saldoAAdicionar = 1000.00;
        }

    }

    public void limpar() {
        saldoAadicionar.setText(0.00 + "€");
        saldoAAdicionar = 0.00;
    }

    @FXML
    private void handleButtonBack() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.Dados.setClientePOS(UsersBLL.getUser(GlobalVAR.Dados.getClientePOS().getIduser()));
            GlobalVAR.StageController.setStage("/com/edcards/edcards/Pos.fxml");
        }
    }
}
