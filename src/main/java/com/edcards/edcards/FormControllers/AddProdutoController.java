package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddProdutoController {
    String nome;
    ProdutoEnum categoria;
    Double preco;
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Double> priceValue;
    @FXML
    ComboBox<String> cBoxCategory;
    @FXML
    private Button addPrdt, backBtn;

    @FXML
    public void initialize() {
        priceValue.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.0,
                1000.0,
                0.5,
                0.50
        ));
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
        ProdutoBLL.inserirProduto(nome, categoria, preco, true);
    }
}
