package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

public class AddRef {
    public AnchorPane leftPane;
    public AnchorPane rightPane;
    public Button exit;
    public HBox rootHBox;
    public Button buttonPesq;
    public TextField textBoxNomeRefeicao;
    public AnchorPane leftPane1;
    public AnchorPane leftPane2;

    private Produto produtoAtual;

    @FXML
    public void initialize() {
        resize();


    }

    private void resize() {


        //buttons
        ResizeUtil.resizeAndPositionButton(exit,rightPane,0.9);
        ResizeUtil.resizeAndPositionTextbox(textBoxNomeRefeicao,rightPane,0.1);
        ResizeUtil.resizeAndPositionButton(buttonPesq,rightPane,0.2);


        //Set percentage
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);
        leftPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.7)); // 60% for leftPane
        rightPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.3)); // 40% for rightPane

        leftPane1.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.5));
        leftPane2.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.5));
    }

    public void handleExit() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/Pos.fxml");
        }
    }

    public void handlePesquisa(ActionEvent actionEvent) {
        var str = textBoxNomeRefeicao.getText();
        if (str.isEmpty()) {
            FeedBackController.feedbackErro("Nada foi escrito!");
            return;
        }
        var prod = ProdutoBLL.getProduto(str);
        if (prod == null) {
            FeedBackController.feedbackErro("Não foi encontrado nenhuma refeição com esse nome!");
            return;
        }
        if (prod.getTipo() != ProdutoEnum.REFEICOES) {
            FeedBackController.feedbackErro("Produto selecionado não é refeição!");
        } else {
            produtoAtual = prod;
        }

    }
}
