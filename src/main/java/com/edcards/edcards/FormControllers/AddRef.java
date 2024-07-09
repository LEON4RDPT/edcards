package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.DataTable.RefeicaoBLL;
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
import java.sql.Date;
import java.time.LocalDate;

public class AddRef {

    public HBox leftPane;
    public AnchorPane rightPane;
    public Button exit;
    public HBox rootHBox;

    public AnchorPane leftPane1;
    public AnchorPane leftPane2;
    public Label labelForPicker;
    public DatePicker timePickerRefeicao;
    public Button buttonSetHoje;
    public TextField textBoxPrato;
    public Label labelForCriarRef;
    public Button buttonSetMarc;
    public TextField textBoxSopa;
    public TextField textBoxSobremesa;
    public Button buttonInserirRefeicao;
    public TextArea textArea;

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

        tabelaRefeicoes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // single click
                Produto selectedProduto = tabelaRefeicoes.getSelectionModel().getSelectedItem();
                if (selectedProduto != null) {
                    produtoAtual = selectedProduto;
                }
            }
        });
    }

    private void setRefeicoes() {
        tabelaRefeicoes.setItems(produtos);
        var prods = ProdutoBLL.getALl(ProdutoEnum.REFEICOES);
        if (prods != null) {
            produtos.addAll(prods);
            nomeProduto.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));
        } else {
            FeedBackController.feedbackErro("Nenhuma Refeição registada no programa");
        }

    }

        private void resize() {


            //resize
            ResizeUtil.resizeAndPosition(exit,rightPane,0.9);
            ResizeUtil.resizeAndPositionTextAreaStickWithPane(textArea,rightPane,0.5,0.26,0.18);


            ResizeUtil.resizeAndPosition(labelForPicker,leftPane1,0.1);
            ResizeUtil.resizeAndPosition(timePickerRefeicao,leftPane1,0.2);
            ResizeUtil.resizeAndPosition(buttonSetHoje,leftPane1,0.26);
            ResizeUtil.resizeAndPosition(tabelaRefeicoes,leftPane1,0.60);
            ResizeUtil.resizeAndPosition(buttonSetMarc,leftPane1,0.85);

            ResizeUtil.resizeAndPosition(labelForCriarRef,leftPane2,0.1);
            ResizeUtil.resizeAndPosition(textBoxSopa,leftPane2,0.2);
            ResizeUtil.resizeAndPosition(textBoxPrato,leftPane2,0.35);
            ResizeUtil.resizeAndPosition(textBoxSobremesa,leftPane2,0.50);
            ResizeUtil.resizeAndPosition(buttonInserirRefeicao,leftPane2,0.65);


            //Set percentage
            HBox.setHgrow(leftPane, Priority.ALWAYS);
            HBox.setHgrow(rightPane, Priority.ALWAYS);

            //panes
            leftPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.6));
            rightPane.prefWidthProperty().bind(rootHBox.widthProperty().multiply(0.4));
            leftPane1.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.5));
            leftPane2.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.5));

        }

    public void handleExit() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/Pos.fxml");
        }
    }


    public void handleSetdiaHoje() {
        timePickerRefeicao.setValue(LocalDate.now());
    }

    public void handleSetMarcacao(ActionEvent actionEvent) {
        LocalDate selectedDate = timePickerRefeicao.getValue();
        if (selectedDate == null) {
            FeedBackController.feedbackErro("Data não foi selecionada");
            return;
        }
        if ( selectedDate.isBefore(LocalDate.now())) {
            FeedBackController.feedbackErro("Data Invalida!");
            return;
        }

        if (produtoAtual == null) {
            FeedBackController.feedbackErro("Refeicao não selecionada!");
            return;
        }

        if (FeedBackController.feedbackYesNo("Deseja marcar essa Refeição? \n" + produtoAtual.getNome() + "\nPara: " + selectedDate.toString(),"Confirmação")) {
            RefeicaoBLL.inserirRefeicao(produtoAtual.getIdProduto(), Date.valueOf(selectedDate));
        }


    }

    public void handleInserirRef(ActionEvent actionEvent) {

        StringBuilder str = new StringBuilder();

        if (textBoxSopa.getText() == null ) {
            //feedback
            return;
        } else {
            str.append(textBoxSopa.getText()).append("\n");
        }
        if (textBoxPrato.getText() == null ) {
            //feedback
            return;
        } else {
            str.append(textBoxPrato.getText()).append("\n");
        }
        if (textBoxSobremesa.getText() == null) {
            //feedback
            return;
        } else {
            str.append(textBoxSobremesa.getText());
        }

        if (FeedBackController.feedbackYesNo("Deseja adicionar essa Refeição? \n" + str.toString(),"Confirmação")) {
            var prodid = ProdutoBLL.inserirProduto(str.toString(),ProdutoEnum.REFEICOES,1.46);
            if (prodid != 0) {
                produtos.add(ProdutoBLL.getProduto(prodid));

            }
        }
    }
}
