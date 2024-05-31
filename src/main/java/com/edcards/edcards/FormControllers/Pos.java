package com.edcards.edcards.FormControllers;

import com.edcards.edcards.ClassControllers.ProdutoEnum;
import com.edcards.edcards.FormControllers.Utils.ColorController.ColorController;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.DataTable.ProdutoBLL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import static com.edcards.edcards.FormControllers.Utils.ColorController.ColorController.setButtonColor;
import static com.edcards.edcards.FormControllers.Utils.ColorController.ColorController.setButtonColorBack;

public class Pos {

    @FXML
    private GridPane buttonGrid;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonUp;
    @FXML
    private AnchorPane buttonsBack;
    @FXML
    private TextField textNum;
    @FXML
    private ChoiceBox choiceBoxItem;
    @FXML
    private HBox rootHBox;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Button buttonVoltar;
    @FXML
    private Button buttonRefeicao;
    @FXML
    private Button buttonRemoveL;
    @FXML
    private Button buttonRemoveA;
    @FXML
    private Button buttonVender;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;
    @FXML
    private Button button16;
    @FXML
    private Button button17;
    @FXML
    private Button button18;
    @FXML
    private Button button19;
    @FXML
    private Button button20;
    @FXML
    private Button button21;
    @FXML
    private Button button22;
    @FXML
    private Button button23;
    @FXML
    private Button button24;
    private Button[] btns = new Button[23];
    private Produto[] produtos = new Produto[23];
    private List<Produto> listaProdutosDisponiveis = new ArrayList<Produto>();
    private List<Produto> fatura = new ArrayList<Produto>();
    private int buttonPage = 1  ;
    @FXML
    private void initialize() {



        rootHBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            leftPane.setPrefWidth(width / 4);
            rightPane.setPrefWidth((width / 4) * 3);
        });

        rootHBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            leftPane.setPrefHeight(height);
            rightPane.setPrefHeight(height);
        });

        double initialWidth = rootHBox.getPrefWidth();
        leftPane.setPrefWidth(initialWidth / 4);
        rightPane.setPrefWidth((initialWidth / 4) * 3);

        double initialHeight = rootHBox.getPrefHeight();
        leftPane.setPrefHeight(initialHeight);
        rightPane.setPrefHeight(initialHeight);


        btns = new Button[]{
                button1, button2, button3, button4,
                button5, button6, button7, button8,
                button9, button10, button11, button12,
                button13, button14, button15, button16,
                button17, button18, button19, button20,
                button21, button22, button23, button24
        };

        resizeButtons();
        setChoiceEnum();

    }
    private void setChoiceEnum() {
        var items = choiceBoxItem.getItems();
        items.add("TUDO");
        items.addAll(ProdutoEnum.getStringValues());
        choiceBoxItem.getSelectionModel().select(0);

    }
    private void setProdutosButton() {
        for (var button : btns) {
            setButtonColorBack(button);
        }https://motleybytes.com/w/JavaFxFonts
        textNum.setText(String.valueOf(buttonPage));
        listaProdutosDisponiveis.clear();

        if (choiceBoxItem.getValue().toString().equals("TUDO")) {
            listaProdutosDisponiveis = ProdutoBLL.getALlPOS();
        } else {
            String tipo = choiceBoxItem.getValue().toString();
            ProdutoEnum prodEnum = ProdutoEnum.valueOf(tipo);
            listaProdutosDisponiveis = ProdutoBLL.getALlByEnum(prodEnum);

        }

        int btnPg = 24 * buttonPage;
        int btnPgOld = btnPg - 24;

        produtos = new Produto[produtos.length]; //clear
        List<Produto> produtos1 = new ArrayList<Produto>();

        for (int page = btnPgOld; page < btnPg ; page++) {
            if (page >= listaProdutosDisponiveis.size() ){
                produtos1.add(null);
                continue;
            }
            produtos1.add(listaProdutosDisponiveis.get(page));
        }

        produtos = produtos1.toArray(new Produto[0]);

        for (int i = 0; i < btns.length; i++) {
            btns[i].setWrapText(true);

            if (produtos[i] == null) {
                btns[i].setText("NULL");
                continue;
            }

            btns[i].setText(produtos[i].getNome());


        }

        //color
        for (int slot = 0; slot < btns.length; slot++) {
            if (produtos[slot] == null) {
                continue;
            }
            setButtonColor(btns[slot],produtos[slot].getTipo());

        }
    }
    @FXML
    private void handleButtonClickPos(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        for (int i = 0; i < btns.length; i++) {
            if (produtos[i] == null) {
                return;
            }
            if (clickedButton == btns[i]) {
                fatura.add(produtos[i]);
                break;
            }
        }

    }
    private void resizeButtons() {
        ResizeUtil.resizeAndPositionButton(buttonVoltar, leftPane, 0.95);
        ResizeUtil.resizeAndPositionButton(buttonRefeicao, leftPane, 0.85);
        ResizeUtil.resizeAndPositionButton(buttonRemoveL, leftPane, 0.75);
        ResizeUtil.resizeAndPositionButton(buttonRemoveA, leftPane, 0.65);
        ResizeUtil.resizeAndPositionButton(buttonVender, leftPane, 0.55);
        ResizeUtil.resizeAndPositionChoiceBox(choiceBoxItem,leftPane,0.45);
        ResizeUtil.resizeAndPositionButton(buttonAdd, leftPane, 0.35);
        ResizeUtil.resizeAndCenterMiddleButtons(buttonBack,buttonUp,buttonsBack);
        ResizeUtil.resizeAndCenterText(textNum,buttonsBack);

    }
    public void buttonBackClick(ActionEvent actionEvent) {
        if (buttonPage > 1) {
            buttonPage--;
            setProdutosButton();
        }
    }
    public void buttonUpClick(ActionEvent actionEvent) {
        int num = buttonPage * 24;
        if (num <= listaProdutosDisponiveis.size()) {
            buttonPage++;
            setProdutosButton();

        }
    }
    //VENDER
    public void handleButtonClickVender(ActionEvent actionEvent) {
        if (fatura.isEmpty()) {
            return;
        }
        double valorTotal = fatura.stream().mapToDouble(Produto::getPreco).sum();
        System.out.println(valorTotal);
        //todo FATURA!! BLL
    }
    public void handleButtonClickRemoverAll(ActionEvent actionEvent) {
        fatura.clear();
    }
    public void handleButtonClickRemoverLast(ActionEvent actionEvent) {
        if (fatura.isEmpty()){
            return;
        }
        fatura.removeLast();
    }
    public void handleChoiceBoxChange(ActionEvent actionEvent) {
        setProdutosButton();
    }
}
