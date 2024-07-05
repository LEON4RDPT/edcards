package com.edcards.edcards.FormControllers;

import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;


public class ControllerPIN {
    public GridPane gridbuttons;
    @FXML
    private HBox myHBox;
    @FXML
    private Button btn01;

    private final int time = 30;

    private int valorAtual;

    private Timeline timer;
    @FXML
    private TextField field1, field2, field3, field4, field5, field6;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    private int tents= 0;

    @FXML
    public void initialize() {
        valorAtual = 0;
        startTimer();
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

        startTimer();
    }

    private void ifButtonPressed(Button button) {
        restartTimer();
        valorAtual = Integer.parseInt(button.getText());
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
            int pin = Integer.parseInt(field1.getText() + field2.getText() + field3.getText() + field4.getText() + field5.getText() + field6.getText());

            Pessoa pessoaAtual = GlobalVAR.Dados.getPessoaAtual();
            if (pessoaAtual != null) {
                int pinDaPessoa = pessoaAtual.getPin();
                if (pin == pinDaPessoa) {
                    try {
                        setStage("/com/edcards/edcards/Main.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    FeedBackController.feedbackErro("Pin Incorreto!");
                    tents++;
                    clean();
                }
            } else {
                FeedBackController.feedbackErro("Nenhum Usuario encontrado!");
                clean();
            }
        }

    }


    private void clean(){
        field1.clear();
        field2.clear();
        field3.clear();
        field4.clear();
        field5.clear();
        field6.clear();

        if (tents >= 3) {
            GlobalVAR.Dados.setPessoaAtual(null);
            goBack();
        }
    }

    @FXML
    private void buttonLimpar(ActionEvent actionEvent) {
        clean();
    }


    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(time), event -> Platform.runLater(() -> {
            GlobalVAR.Dados.setPessoaAtual(null);
            goBack();
        })));
        timer.setCycleCount(1); // Make sure the timer runs only once
        timer.play();
    }

    private void restartTimer() {
        if (timer != null) {
            timer.stop();
            timer.playFromStart();
        }
    }

    private void goBack() {
        try {
            setStage("/com/edcards/edcards/ReadCard.fxml");
        } catch (IOException ignored) {
        }
    }


}