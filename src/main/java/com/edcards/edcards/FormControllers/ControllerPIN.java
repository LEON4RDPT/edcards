package com.edcards.edcards.FormControllers;

import com.edcards.edcards.ClassControllers.GlobalVAR;
import com.edcards.edcards.Programa.Classes.Pessoa;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;


public class ControllerPIN {
    private int valorAtual;

    @FXML
    private TextField field1, field2, field3, field4, field5, field6;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    private int pin;
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
            pin = Integer.parseInt(field1.getText() + field2.getText() + field3.getText() + field4.getText() + field5.getText() + field6.getText());
            System.out.println(pin);

            Pessoa pessoaAtual = GlobalVAR.Dados.getPessoaAtual();
            if (pessoaAtual != null) {
                int pinDaPessoa = pessoaAtual.getPin();
                if (pin == pinDaPessoa) {
                    try {
                        GlobalVAR.Dados.setPessoaAposPin(pessoaAtual);
                        System.out.println(pessoaAtual.getNome() + " " +  pessoaAtual.getSaldo() + " " + pessoaAtual.getNumCartao());
                        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/com/edcards/edcards/Main.fxml"));
                        Parent mainAppRoot = mainAppLoader.load();
                        Scene mainAppScene = new Scene(mainAppRoot);
                        GlobalVAR.Dados.getCurrentStage().setScene(mainAppScene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O PIN digitado não corresponde ao cartão.", "Validação", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                System.err.println("Nenhuma pessoa encontrada");
            }
        }
    }


}