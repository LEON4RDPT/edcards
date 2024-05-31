package com.edcards.edcards;

import com.edcards.edcards.ClassControllers.GlobalVAR;
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
public class ApplicationMainAcessPin extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMainAcessPin.class.getResource("ReadCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Aplicação");
        stage.setScene(scene);
        stage.show();
        GlobalVAR.Dados.setCurrentStage(stage);

        Task<Pessoa> task = new Task<Pessoa>() {
            @Override
            protected Pessoa call() throws Exception {
                return cartaoLido();
            }
        };

        new Thread(task).start();
    }

    public Pessoa cartaoLido(){
        String cartao;
        try {
            cartao = LerCartao.lerIDCartao();
        } catch (CardException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cartao);

        // Open PIN form immediately
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

        // Continue retrieving user's information in the background
        Pessoa pessoa = null;
        try {
            var userByNFC = CartaoBLL.getUserByNFC(cartao);
            if (userByNFC != null) {
                pessoa = UsersBLL.getUser(userByNFC.getIduser());
                System.out.println("ID: " + pessoa.getIduser());
                System.out.println("Número do Cartão: " + pessoa.getNumCartao());
                GlobalVAR.Dados.setPessoaAtual(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pessoa;
    }

    public static void main(String[] args) {
        launch();
    }
}
