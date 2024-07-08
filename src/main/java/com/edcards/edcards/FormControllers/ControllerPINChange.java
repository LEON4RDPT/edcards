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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;


public class ControllerPINChange {

    public GridPane gridbuttons;
    public HBox HboxRoot;
    public AnchorPane leftPane;
    public AnchorPane rightPane;
    @FXML
    private HBox myHBox;
    @FXML
    private Button btn01;

    private int valorAtual;

    @FXML
    private TextField field1, field2, field3, field4, field5, field6;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;




    @FXML
    public void initialize() {

        resize();
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

    private void resize() {
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        leftPane.prefWidthProperty().bind(HboxRoot.widthProperty().multiply(0.3)); // 60% for leftPane
        rightPane.prefWidthProperty().bind(HboxRoot.widthProperty().multiply(0.7)); // 40% for rightPane
    }

    private void ifButtonPressed(Button button) {
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
//                int pinDaPessoa = pessoaAtual.getPin();
//                if (pin == pinDaPessoa) {
//                    try {
//                        timer.stop();
//                        setStage("/com/edcards/edcards/Main.fxml");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    FeedBackController.feedbackErro("Pin Incorreto!");
//                    tents++;
//                    clean();
//                }
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

    }

    @FXML
    private void buttonLimpar() {
        clean();
    }



    public void handleButtonBack(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?", "Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }
}