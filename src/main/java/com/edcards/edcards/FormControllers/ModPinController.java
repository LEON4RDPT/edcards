package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.ConfEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModPinController {
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    public GridPane gridbuttons;
    public Label pinText;
    public Button backBtn;
    public Button btnConf;
    public Button btnClr;
    @FXML
    private HBox myHBox;
    @FXML
    private Button btn01;

    private int valorAtual, pin1, pin2;
    boolean scnd = false;

    @FXML
    private TextField field1, field2, field3, field4, field5, field6;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    @FXML
    public void initialize() {
        valorAtual = 0;
        btn0.setOnAction(event -> ifButtonPressed(btn0));
        btn1.setOnAction(event -> ifButtonPressed(btn1));
        btn2.setOnAction(event -> ifButtonPressed(btn2));
        btn3.setOnAction(event -> ifButtonPressed(btn3));
        btn4.setOnAction(event -> ifButtonPressed(btn4));
        btn5.setOnAction(event -> ifButtonPressed(btn5));
        btn6.setOnAction(event -> ifButtonPressed(btn6));
        btn7.setOnAction(event -> ifButtonPressed(btn7));
        btn8.setOnAction(event -> ifButtonPressed(btn8));
        btn9.setOnAction(event -> ifButtonPressed(btn9));
    }

    private void ifButtonPressed(Button button) {
        valorAtual = Integer.parseInt(button.getText());
        if (scnd == false) {
            if (field1.getText().isEmpty()) {
                field1.setText(String.valueOf(valorAtual));
            } else if (field2.getText().isEmpty()) {
                field2.setText(String.valueOf(valorAtual));
            } else if (field3.getText().isEmpty()) {
                field3.setText(String.valueOf(valorAtual));
            } else if (field4.getText().isEmpty()) {
                field4.setText(String.valueOf(valorAtual));
            } else if (field5.getText().isEmpty()) {
                field5.setText(String.valueOf(valorAtual));
            } else if (field6.getText().isEmpty()) {
                field6.setText(String.valueOf(valorAtual));
                pin1 = Integer.parseInt(field1.getText() + field2.getText() + field3.getText() + field4.getText() + field5.getText() + field6.getText());
                System.out.println(pin1);
                clean();
                scnd = true;
            }
        }else if (scnd == true) {
            if (field1.getText().isEmpty()) {
                field1.setText(String.valueOf(valorAtual));
            } else if (field2.getText().isEmpty()) {
                field2.setText(String.valueOf(valorAtual));
            } else if (field3.getText().isEmpty()) {
                field3.setText(String.valueOf(valorAtual));
            } else if (field4.getText().isEmpty()) {
                field4.setText(String.valueOf(valorAtual));
            } else if (field5.getText().isEmpty()) {
                field5.setText(String.valueOf(valorAtual));
            } else if (field6.getText().isEmpty()) {
                field6.setText(String.valueOf(valorAtual));
                pin2 = Integer.parseInt(field1.getText() + field2.getText() + field3.getText() + field4.getText() + field5.getText() + field6.getText());
                System.out.println(pin2);
                clean();
                if (pin1 == pin2){
                    System.out.println(ConfEnum.conf16);
                    pin1=0;
                    pin2=0;
                } else{
                    System.err.println(ErrorEnum.err15);
                    scnd = false;
                    pin1=0;
                    pin2=0;
                }
            }
        }

    }


    private void clean() {
        field1.clear();
        field2.clear();
        field3.clear();
        field4.clear();
        field5.clear();
        field6.clear();
    }

    @FXML
    private void buttonLimpar(ActionEvent actionEvent) {
        clean();
    }

    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao();
                    int user = CartaoBLL.getIdUserByNFC(idCartao);
                    UsersBLL.getUser(user);
                    break;

                } catch (Exception ignored) {
                }
            }
        });
    }
}
