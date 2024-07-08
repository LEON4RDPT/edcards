package com.edcards.edcards;

import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainExeNoCard extends Application {
    private int id = 2;
    @Override
    public void start(Stage stage) throws IOException {
        GlobalVAR.Dados.setPessoaAtual(UsersBLL.getUser(id));
        FXMLLoader fxmlLoader = new FXMLLoader(MainExe.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Aplicação");
        stage.setScene(scene);

        GlobalVAR.Dados.setCurrentStage(stage);
        GlobalVAR.Dados.getCurrentStage().setOnCloseRequest(event -> {
            event.consume(); // Consume the close event to prevent the stage from closing
            GlobalVAR.Dados.confirmExit();
        });
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}