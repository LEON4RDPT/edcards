package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddProdutoController {
    String nome;
    ProdutoEnum categoria;
    Double preco;
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Double> priceValue;
    @FXML
    ComboBox<String>cBoxCategory;
    @FXML
    private Button addPrdt, backBtn;
    @FXML
    public void initialize(){
        setChoiceEnum();
    }
    private void setChoiceEnum() {

        var items = cBoxCategory.getItems();
        items.addAll(ProdutoEnum.getStringValues());
        cBoxCategory.getSelectionModel().select(0);

    }
    @FXML
    private void addPrdtClick(ActionEvent event) {
        categoria = ProdutoEnum.valueOf(String.valueOf(cBoxCategory.getSelectionModel().getSelectedItem()));
        nome = nameField.getText();
        preco = priceValue.getValue();
        ProdutoBLL.inserirProduto(nome,categoria, preco, true);
    }
}
