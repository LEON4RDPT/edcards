package com.edcards.edcards.FormControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class POSAdmin {
    @FXML
    private Button exit;
    @FXML
    private Button addUsr;
    @FXML
    private Button modUsr;
    @FXML
    private Button modFtUsr;
    @FXML
    private Button addCard;
    @FXML
    private Button modCard;
    @FXML
    private Button modPinUsr;
    @FXML
    private Button addHorario;
    @FXML
    private Button modHorario;
    @FXML
    private Button remHorario;
    @FXML
    private Button addPrdt;
    @FXML
    private Button modPrdt;
    @FXML
    private Button remPrdt;
    @FXML
    private Button viewTransacs;
    @FXML
    public void initialize() {}
    @FXML
    public void addUsrClick(javafx.event.ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/CriarUser.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Criar User");
        stage.setScene(posScene);
        stage.show();
    }

    public void modUsrClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/ModUser.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modificar User");
        stage.setScene(posScene);
        stage.show();
    }

    public void modFtUserClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/ModFotoUser.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Remover User");
        stage.setScene(posScene);
        stage.show();
    }

    public void addCardClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/NotDone/InsertCard.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inserir Cartão");
        stage.setScene(posScene);
        stage.show();
    }

    public void modCardClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/NotDone/UpdateUserCard.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Mudar cartão Usuario");
        stage.setScene(posScene);
        stage.show();
    }

    public void modPinUsrClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/ChangePIN.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Mudar Pin User");
        stage.setScene(posScene);
        stage.show();
    }
    public void addHorarioClick(javafx.event.ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/AddHorario.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Adicionar Horario");
        stage.setScene(posScene);
        stage.show();
    }

    public void modHorarioClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/ModHorario.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modificar Horario");
        stage.setScene(posScene);
        stage.show();
    }

    public void remHorarioClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/DelHorario.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Remover Horario");
        stage.setScene(posScene);
        stage.show();
    }

    public void addPrdtClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/NotDone/AddProduto.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Adicionar Produto");
        stage.setScene(posScene);
        stage.show();
    }

    public void modPrdtClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/NotDone/ModProduto.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modificar Produto");
        stage.setScene(posScene);
        stage.show();
    }
    public void remPrdtClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/NotDone/DelProduto.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Remover Produto");
        stage.setScene(posScene);
        stage.show();
    }
    public void viewTransacsClick(ActionEvent event) throws IOException {
        Parent newSceneParent = FXMLLoader.load(getClass().getResource("/com/edcards/edcards/ViewTransacs.fxml"));
        Scene posScene = new Scene(newSceneParent);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Ver Transações");
        stage.setScene(posScene);
        stage.show();
    }


}