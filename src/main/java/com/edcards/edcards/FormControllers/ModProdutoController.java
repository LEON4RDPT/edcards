package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ConfEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class ModProdutoController {
    public CheckBox disp;
    String nome, choice;
    List<Produto> nomes, prod;
    ProdutoEnum categoriaChange, categoria, nomePicker;
    boolean disponivel;
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
    public void initialize() {
        priceValue.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                0.00,
                1000.0,
                0.05,
                0.05
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
        categoria = ProdutoEnum.valueOf(cBoxCategory.getSelectionModel().getSelectedItem());
        nome = nameField.getText();
        preco = priceValue.getValue();
        disponivel = disp.isSelected();
        var id = prodt.getIdProduto();
        ProdutoBLL.setNome(id, nome);
        ProdutoBLL.setTipo(id, categoria);
        ProdutoBLL.setPreco(id, preco);
        ProdutoBLL.setDisp(id, disponivel);
        FeedBackController.feedbackConf(String.valueOf(ConfEnum.conf10));
    }

    public void loadNomes(ActionEvent event) {
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
        prodt = ProdutoBLL.getProduto(produto);
        if (prodt == null) {
            //feedback
            return;
        }
        nameField.setText(prodt.getNome());
        cBoxChangeCategory.setValue(prodt.getTipo().name());
        priceValue.getValueFactory().setValue(prodt.getPreco());
        disp.setSelected(prodt.isDisponivel());

    }

    @FXML
    private void handleButtonBack(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            GlobalVAR.StageController.setStage("/com/edcards/edcards/POSAdmin.fxml");
        }
    }
}
