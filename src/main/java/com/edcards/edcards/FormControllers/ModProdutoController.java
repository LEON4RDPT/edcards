package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ModProdutoController {
    String nome, choice;
    List<Produto> nomes, prod;
    ProdutoEnum categoriaChange, categoria, nomePicker;
    Double preco;
    Produto prodt;
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Double> priceValue;
    @FXML
    ComboBox<String> cBoxCategory, cBoxChangeCategory, cBoxName;
    @FXML
    private Button modPrdt, backBtn;
    @FXML
    public void initialize(){
        priceValue.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0,
                1000.0,
                0.5,
                0.50
        ));
        setChoiceEnum();

    }
    private void setChoiceEnum() {

        var itemsU = cBoxChangeCategory.getItems();
        itemsU.addAll(ProdutoEnum.getStringValues());
        cBoxChangeCategory.getSelectionModel().select(0);
        var items = cBoxCategory.getItems();
        items.addAll(ProdutoEnum.getStringValues());
        cBoxCategory.getSelectionModel().select(0);

    }
    @FXML
    private void modPrdtClick(ActionEvent event) {
        categoria = ProdutoEnum.valueOf(String.valueOf(cBoxCategory.getSelectionModel()));
        nome = nameField.getText();
        preco = priceValue.getValue();
        ProdutoBLL.inserirProduto(nome,categoria, preco, true);
    }

    public void loadNomes(ActionEvent eventvent) {
        categoria = ProdutoEnum.valueOf(String.valueOf(cBoxCategory.getSelectionModel().getSelectedItem()));
        nomes = ProdutoBLL.getALlByEnum(categoria);
        if (nomes != null) {
            ObservableList<String> nomesList = FXCollections.observableArrayList();
            for (Produto p : nomes) {
                nomesList.add(p.getNome());
            }
            cBoxName.setItems(nomesList);
        }
    }

    public void loadProduto(ActionEvent actionEvent) {
        String produto = cBoxName.getSelectionModel().getSelectedItem();
        prodt= ProdutoBLL.getProduto(produto);
        prodt.getIdProduto();

        nameField.setText(prodt.getNome());
        cBoxChangeCategory.setValue(prodt.getTipo().name());
        priceValue.getValueFactory().setValue(prodt.getPreco());
    }
}
