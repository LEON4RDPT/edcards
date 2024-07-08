package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.time.LocalDate;

public class AddRef {
    public AnchorPane leftPane;
    public AnchorPane rightPane;
    public Button exit;
    public HBox rootHBox;
    public Button buttonPesq;
    public TextField textBoxNomeRefeicao;
    public AnchorPane leftPane1;
    public AnchorPane leftPane2;
    public Label labelForPicker;
    public DatePicker timePickerRefeicao;
    public Button buttonSetHoje;

    @FXML
    private TableColumn<Produto, String> nomeProduto;

    @FXML
    private TableView<Produto> tabelaRefeicoes;

    private Produto produtoAtual = null;

    private final ObservableList<Produto> produtos = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        resize();

        setRefeicoes();
    }

    private void setRefeicoes() {
        tabelaRefeicoes.setItems(produtos);
        var prods = ProdutoBLL.getALl(ProdutoEnum.REFEICOES);
        if (prods != null) {
            System.out.println("HYDIPGIYDTYAGYDg");
            produtos.addAll(prods);
            nomeProduto.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));
        } else {
            FeedBackController.feedbackErro("Nenhuma Refeição registada no programa");
        }

    }

    private void resize() {


        //resize
        ResizeUtil.resizeAndPosition(exit,rightPane,0.9);
        ResizeUtil.resizeAndPosition(textBoxNomeRefeicao,rightPane,0.1);
        ResizeUtil.resizeAndPosition(buttonPesq,rightPane,0.2);
        ResizeUtil.resizeAndPosition(labelForPicker,leftPane1,0.1);
        ResizeUtil.resizeAndPosition(timePickerRefeicao,leftPane1,0.2);
        ResizeUtil.resizeAndPosition(buttonSetHoje,leftPane1,0.26);
        ResizeUtil.resizeAndPosition(tabelaRefeicoes,leftPane1,0.60);


        //Set percentage
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        //panes
        leftPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.7));
        rightPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.3));
        leftPane1.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.5));
        leftPane2.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.5));

    }

    public void handleExit() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/Pos.fxml");
        }
    }

    public void handlePesquisa() {
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

    public void handleSetdiaHoje() {
        timePickerRefeicao.setValue(LocalDate.now());
    }
}
