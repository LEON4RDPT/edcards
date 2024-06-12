package com.edcards.edcards;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.smartcardio.CardException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainExe extends Application {
    List<String> allNfc = new ArrayList<String>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainExe.class.getResource("ReadCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Aplicação");
        stage.setScene(scene);
        stage.show();
        GlobalVAR.Dados.setCurrentStage(stage);

        var x = CartaoBLL.getAllCards();
        if (x == null) {
            //feedback no cards!!
            return;
        }
        allNfc = List.of(x);




        Task<Pessoa> task = new Task<Pessoa>() {
            @Override
            protected Pessoa call() throws Exception {
                return cartaoLido();
            }
        };

        task.setOnSucceeded(event -> {
            Pessoa pessoa = task.getValue();
            if (pessoa != null) {
                Platform.runLater(() -> {
                    try {
                        FXMLLoader pinLoader = new FXMLLoader(getClass().getResource("PIN.fxml"));
                        Parent pinRoot = pinLoader.load();
                        Scene pinScene = new Scene(pinRoot);
                        GlobalVAR.Dados.getCurrentStage().setScene(pinScene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        new Thread(task).start();
    }

    public Pessoa cartaoLido() {

        while (true) {
            try {
                String cartao = LerCartao.lerIDCartao();
                System.out.println(cartao); //feedback
                Pessoa pessoa = null;
                try {
                    var userByNFC = CartaoBLL.getUserByNFC(cartao);
                    if (userByNFC != null) {
                        pessoa = UsersBLL.getUser(userByNFC.getIduser());
                        System.out.println("ID: " + pessoa.getIduser());
                        System.out.println("Número do Cartão: " + pessoa.getNumCartao());
                        GlobalVAR.Dados.setPessoaAtual(pessoa);
                        return pessoa;
                    } else {
                        System.err.println("Cartão não encontrado na DB.");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (CardException | InterruptedException e) {
                throw new RuntimeException(e);
            }






        }
    }

    public static void main(String[] args) {
        launch();
    }
}
