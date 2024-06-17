package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class delProdutoController {
    String nome;
    List<Produto> nomes;
    ProdutoEnum categoriaa;
    Produto prodt;
    @FXML
    private ComboBox cBoxName;
    @FXML
    private ComboBox cBoxCategory;
    @FXML
    private Text prdtNome;
    @FXML
    private Button remPrdt, VoltarBtn;

    @FXML
    public void initialize() {
        setChoiceEnum();
    }

    private void setChoiceEnum() {

        var items = cBoxCategory.getItems();
        items.addAll(ProdutoEnum.getStringValues());
        cBoxCategory.getSelectionModel().select(0);

    }

    @FXML
    public void loadNomes(ActionEvent event) {
        categoriaa = ProdutoEnum.valueOf(String.valueOf(cBoxCategory.getSelectionModel().getSelectedItem()));
        nomes = ProdutoBLL.getALlByEnum(categoriaa);
        if (nomes != null) {
            ObservableList<String> nomesList = FXCollections.observableArrayList();
            for (Produto p : nomes) {
                nomesList.add(p.getNome());
            }
            cBoxName.setItems(nomesList);
        }
    }
}

