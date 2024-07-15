package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import javax.smartcardio.CardException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ControllerReadCard {
    private final Lock lock = new ReentrantLock();
    private final Condition cardAvailable = lock.newCondition();
    @FXML
    private ImageView image;

    private List<String> allNfc;

    @FXML
    public void initialize() {


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

    }



    public String cartaoLido() throws CardException {
        lock.lock();
        try {
            while (true) {
                try {
                    String cartao = LerCartao.lerIDCartao(null);
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

    //optional
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