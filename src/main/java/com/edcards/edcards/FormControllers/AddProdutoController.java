package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class AddProdutoController  {
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
        categoria = ProdutoEnum.valueOf(String.valueOf(cBoxCategory.getSelectionModel().getSelectedItem())); //works
        nome = nameField.getText(); //todo check
        preco = priceValue.getValue(); //todo check
        ProdutoBLL.inserirProduto(nome, categoria, preco, true);
        FeedBackController.feedbackConf("Produto Registado com sucesso!");
    }

    @FXML
    private void handleButtonVoltar(ActionEvent actionEvent) throws IOException {
        setStage("/com/edcards/edcards/POSAdmin.fxml");
    }
}
