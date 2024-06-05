package com.edcards.edcards.tests;

import com.edcards.edcards.FormControllers.Pos;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class ApplicationMainPOS extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMainPOS.class.getResource("/com/edcards/edcards/Pos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("POS Compras");
        stage.setMinHeight(720);
        stage.setMinWidth(1024);

        stage.setScene(scene);
        Pos posController = fxmlLoader.getController();
        stage.setOnCloseRequest(e -> posController.shutdown());
        GlobalVAR.Dados.setCurrentStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}