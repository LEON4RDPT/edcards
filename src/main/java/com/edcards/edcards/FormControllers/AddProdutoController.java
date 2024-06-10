package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddProdutoController {
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
        List<String> categoria = ProdutoBLL.get
        ProdutoBLL.getALlByEnum();
    }
}
