package com.edcards.edcards.FormControllers;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.smartcardio.CardException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerReadCard implements Initializable {
    private List<String> allNfc = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var x = CartaoBLL.getAllCards();
        if (x == null) {
            System.out.println("No cards available in the database.");
            return;
        }
        allNfc = List.of(x);

        Task<String> initialTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return cartaoLido();
            }
        };

        initialTask.setOnSucceeded(event -> {
            var card = initialTask.getValue();
            if (card!= null) {
                Platform.runLater(() -> {
                    try {
                        GlobalVAR.StageController.setStage("/com/edcards/edcards/PIN.fxml");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runAdditionalLogic(card);
                    }
                }).start();
            }
        });

        new Thread(initialTask).start();
    }

    public String cartaoLido() {
        while (true) {
            try {
                String cartao = LerCartao.lerIDCartao();

                if (!allNfc.contains(cartao)) {
                    continue;
                }
                return cartao;
            } catch (CardException e) {
                e.printStackTrace();
                break; // Exit the loop on CardException
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit the loop on InterruptedException
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void runAdditionalLogic(String card) {
        while (true) {
            try {
                var userByNFC = CartaoBLL.getUserByNFC(card);
                if (userByNFC!= null) {
                    var pessoa = UsersBLL.getUser(userByNFC.getIduser());
                    GlobalVAR.Dados.setPessoaAtual(pessoa);
                    System.out.println("DEU"); //feedback
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}