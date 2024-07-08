package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.TransacaoBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Classes.Transacao;
import com.edcards.edcards.Programa.Controllers.FeedBackController;

import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Timestamp;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class ViewTransacs  {
    @FXML
    private TableColumn<Produto, String> nomeColumn;
    @FXML
    private TableColumn<Produto, Double> precoColumn;
    @FXML
    private TableColumn<Produto, String> tipoColumn;
    @FXML
    private TableView<Produto> productsTable;

    @FXML
    private TableView<Transacao> transactionsTable;
    @FXML
    private TableColumn<Transacao, Timestamp> dataTransacaoColumn;
    @FXML
    private TableColumn<Transacao, Double> valorPagoColumn;

    private ObservableList<Transacao> transactions = FXCollections.observableArrayList();

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();


    @FXML
    private Button backBtn;


    @FXML
    private void initialize() {
        setMethods();


       int id = GlobalVAR.Dados.getPessoaAtual().getIduser();
       var trans = TransacaoBLL.getAllTransacoes(id);
       if (trans == null) {
           //feedback;
       } else {
           transactions.addAll(trans);
           dataTransacaoColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDataTransacao()));
           valorPagoColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getValorpago()));
           transactionsTable.setItems(transactions);
       }



    }

    private void setMethods() {
        transactionsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection!= null) {
                setProductsTable();
            }
        });
    }

    private void setProductsTable() {
        Transacao selectedTransacao = transactionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransacao != null) {
            produtos.clear();
            produtos.addAll(selectedTransacao.getProdutos()); // Assuming Transacao has a getProdutos method
            productsTable.setItems(produtos);

            nomeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));
            precoColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPreco()));
            tipoColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTipo()).asString());
        }

    }

    @FXML
    private void handleButtonBack() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?","Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }

    }
}
