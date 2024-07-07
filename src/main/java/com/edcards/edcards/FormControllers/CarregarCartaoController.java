package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;

public class CarregarCartaoController {
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
    private Text saldoAadicionar;

    @FXML
    public void initialize() {

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

    @FXML
    public void addSaldo() {

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
            CartaoBLL.setSaldo(nfc, saldoAntigo + saldoAAdicionar);
            FeedBackController.feedbackErro("Saldo adicionado");
        }






    }

    public void refreshSaldo() {
        if (saldoAAdicionar < 1000) {
            saldoAadicionar.setText(String.format("%.2f€", saldoAAdicionar));
        } else {
            saldoAadicionar.setText(1000.00 + "€");
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
