package com.edcards.edcards.FormControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

import static java.lang.Math.round;

public class CarregarCartaoController {
    double money, saldoAAdicionar;
    DecimalFormat fd = new DecimalFormat("#.##");
    @FXML
    private ImageView nota5, nota10, nota20,nota50,m1cent,m2cent,m5cent,m10cent,m20cent,m50cent,m1e,m2e;
    @FXML
    private Text saldoAadicionar;
    @FXML
    public void initialize(){
        nota5.setOnMouseClicked((event -> {
            money = 5.00;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        nota10.setOnMouseClicked((event -> {
            money = 10.00;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        nota20.setOnMouseClicked((event -> {
            money = 20.00;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        nota50.setOnMouseClicked((event -> {
            money = 50.00;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m1cent.setOnMouseClicked((event -> {
            money = 0.01;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m2cent.setOnMouseClicked((event -> {
            money = 0.02;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m5cent.setOnMouseClicked((event -> {
            money = 0.05;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m10cent.setOnMouseClicked((event -> {
            money = 0.10;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m20cent.setOnMouseClicked((event -> {
            money = 0.20;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m50cent.setOnMouseClicked((event -> {
            money = 0.50;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m1e.setOnMouseClicked((event -> {
            money = 1.00;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
        m2e.setOnMouseClicked((event -> {
            money = 2.00;
            saldoAAdicionar+= money;
            refreshSaldo();
        }));
    }
    @FXML
    public void addSaldo(ActionEvent actionEvent) {

    }
    public void refreshSaldo(){
        if (saldoAAdicionar<1000)
        {saldoAadicionar.setText(String.format("%.2f€", saldoAAdicionar));
        }else{
            saldoAadicionar.setText(1000.00+"€");
        }

    }

    public void limpar(ActionEvent event) {
        saldoAadicionar.setText(0.00+"€");
        saldoAAdicionar = 0.00;
    }
}
