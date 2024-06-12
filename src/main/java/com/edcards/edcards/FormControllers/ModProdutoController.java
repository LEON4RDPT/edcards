package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.List;

public class ModProdutoController {
    String nome;
    List<Produto> nomes;
    ProdutoEnum categoriaChange, categoria, nomePicker;
    Double preco;

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
        setChoiceEnum();
        categoria = ProdutoEnum.valueOf(String.valueOf(cBoxCategory.getSelectionModel().getSelectedItem()));
        nomes = ProdutoBLL.getALlByEnum(categoria);
        //cBoxName.set;
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
}
