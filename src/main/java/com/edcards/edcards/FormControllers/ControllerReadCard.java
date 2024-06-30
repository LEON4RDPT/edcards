package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

import javax.smartcardio.CardException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ControllerReadCard implements Initializable {
    private final Lock lock = new ReentrantLock();
    private final Condition cardAvailable = lock.newCondition();

    private List<String> allNfc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var x = CartaoBLL.getAllCards();
        if (x == null) {
            FeedBackController.feedbackErro("No cards available in the database.");
            return;
        }
        allNfc = List.of(x);

        Task<String> initialTask = new Task<String>() {
            @Override
            protected String call() {
                try {
                    return cartaoLido();
                } catch (CardException e) {
                    return null;
                }
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



    public String cartaoLido() throws CardException {
        lock.lock();
        try {
            while (true) {
                try {
                    String cartao = LerCartao.lerIDCartao();
                    if (cartao == null) {
                        return null;
                    }

                    if (allNfc == null || allNfc.isEmpty() || !allNfc.contains(cartao)) {
                        cardAvailable.await(100, TimeUnit.MILLISECONDS); // wait for 100ms
                        continue;
                    }
                    return cartao;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // Restore interrupted status

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }

    }

    public void runAdditionalLogic(String card) {
        while (true) {
            try {
                var userByNFC = CartaoBLL.getUserByNFC(card);
                if (userByNFC!= null) {
                    var pessoa = UsersBLL.getUser(userByNFC.getIduser());
                    GlobalVAR.Dados.setPessoaAtual(pessoa);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}