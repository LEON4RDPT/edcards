package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.ProdutoBLL;
import com.edcards.edcards.DataTable.TransacaoBLL;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.ArredondarController;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.edcards.edcards.Programa.Controllers.ArredondarController.roundToTwoDecimalPlaces;
import static com.edcards.edcards.Programa.Controllers.ArredondarController.roundToTwoDecimalPlacesRetDouble;
import static com.edcards.edcards.Programa.Controllers.ColorController.ColorController.setButtonColor;
import static com.edcards.edcards.Programa.Controllers.ColorController.ColorController.setButtonColorBack;
import static com.edcards.edcards.Programa.Controllers.FeedBackController.feedbackErro;
import static com.edcards.edcards.Programa.Controllers.FeedBackController.feedbackYesNo;
import static com.edcards.edcards.Programa.Controllers.GlobalVAR.Dados.getClientePOS;
import static com.edcards.edcards.Programa.Controllers.GlobalVAR.Dados.getPessoaAtual;
import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class Pos {

    private volatile boolean isRunning = true;
    private final ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    public TextArea textArea;
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
    private List<Produto> listaProdutosDisponiveis = new ArrayList<>();
    private final List<Produto> fatura = new ArrayList<>();
    private int buttonPage = 1;
    private boolean isProcessingCartao = false;


    private void loadbtns() {
        btns = new Button[]{
                button1, button2, button3, button4,
                button5, button6, button7, button8,
                button9, button10, button11, button12,
                button13, button14, button15, button16,
                button17, button18, button19, button20,
                button21, button22, button23, button24
        };
    }

    @FXML
    private void initialize() {
        loadMethods();

        loadbtns();
        resizeAll();
        setChoiceEnum();


        changeTextBox();

        //aguardarCARTAO tem de ser o ultimo!!
        aguardarCartao();

    }

    private void loadMethods() {
        choiceBoxItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Handle the new value
            handleChoiceBoxChangeSize();
        });
    }

    private void handleChoiceBoxChangeSize() {
        buttonPage = 1;
        textNum.setText("1");
    }


    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/Main.fxml");
                    if (idCartao == null) {
                        continue;
                    }


                    //caso nao seja o mesmo funcionario.
                    if (!idCartao.equals(getPessoaAtual().getNumCartao())) {
                        if (!isProcessingCartao) {
                            isProcessingCartao = true;
                            cartaoCliente(idCartao);
                        }
                    } else {
                        FeedBackController.feedbackErro("Erro! Funcionario não pode ser cliente ao mesmo tempo!");
                        continue;
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }

    public void shutdown() {
        if (!nfcExecutar.isShutdown()) {
            isRunning = false;
            nfcExecutar.shutdown();

        }
    }

    private void cartaoCliente(String idCartao) {
        Platform.runLater(() -> {
            try {
                var pess = CartaoBLL.getUserByNFC(idCartao);
                assert pess != null;
                FeedBackController.feedbackErro("Pessoa: " + pess.getNome() + " Carregada!");
                GlobalVAR.Dados.setClientePOS(pess);
                changeTextBox();
            } finally {
                isProcessingCartao = false;
            }
        });
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
        }
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
        List<Produto> produtos1 = new ArrayList<>();

        for (int page = btnPgOld; page < btnPg; page++) {
            if (page >= Objects.requireNonNull(listaProdutosDisponiveis).size()) {
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
                btns[i].setVisible(false);
                continue;
            }
            btns[i].setVisible(true);
            btns[i].setText(produtos[i].getNome());


        }

        //color
        for (int slot = 0; slot < btns.length; slot++) {
            if (produtos[slot] == null) {
                continue;
            }
            setButtonColor(btns[slot], produtos[slot].getTipo());

        }
    }

    @FXML
    //BUTTONS POS
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
        changeTextBox();
    }

    private void resizeAll() {


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
        ResizeUtil.resizeAndPositionButton(buttonVoltar, leftPane, 0.95);
        ResizeUtil.resizeAndPositionButton(buttonRefeicao, leftPane, 0.85);
        ResizeUtil.resizeAndPositionButton(buttonRemoveL, leftPane, 0.75);
        ResizeUtil.resizeAndPositionButton(buttonRemoveA, leftPane, 0.65);
        ResizeUtil.resizeAndPositionButton(buttonVender, leftPane, 0.55);
        ResizeUtil.resizeAndPositionChoiceBox(choiceBoxItem, leftPane, 0.45);
        ResizeUtil.resizeAndPositionButton(buttonAdd, leftPane, 0.35);
        ResizeUtil.resizeAndCenterMiddleButtons(buttonBack, buttonUp, buttonsBack);
        ResizeUtil.resizeAndCenterText(textNum, buttonsBack);
        ResizeUtil.resizeAndPositionTextArea(textArea, leftPane, 0.15);

    }
    @FXML
    private void buttonBackClick() {
        if (buttonPage > 1) {
            buttonPage--;
            setProdutosButton();
        }
    }
    @FXML
    private void buttonUpClick() {
        int num = buttonPage * 24;
        if (num <= listaProdutosDisponiveis.size()) {
            buttonPage++;
            setProdutosButton();

        }
    }

    @FXML
    private void handleButtonClickVender() {
        if (fatura.isEmpty()) {
            feedbackErro("Nenhum Produto Selecionado!");
            return;
        }

        var cliente = getClientePOS();
        if (cliente == null) {
            feedbackErro("Nenhum cliente Selecionado!");
            return;
        }

        double valorTotal = roundToTwoDecimalPlacesRetDouble(fatura.stream().mapToDouble(Produto::getPreco).sum());

        if (cliente.getSaldo() >= valorTotal) {
            //FAQ insertTrasacao automaticamente remove o saldo!!!

            var funcionario = getPessoaAtual();
            var x = TransacaoBLL.insertTransacao(fatura.toArray(new Produto[0]), cliente.getIduser(), funcionario.getIduser());
            if (x == 0) {
                feedbackErro("Operação Sucedida");
            }
            GlobalVAR.Dados.setClientePOS(null);
            fatura.clear();
            changeTextBox();
            aguardarCartao();
        } else {
            feedbackErro("Usuario não tem saldo");

        }
    }


    private void changeTextBox() {


        textArea.clear();
        // instance var
        double saldo;
        String nomeCliente;
        // set var
        if (getClientePOS() != null) { //existe!
            saldo = getClientePOS().getSaldo();
            nomeCliente = getClientePOS().getNome();

        } else {
            saldo = 0;
            nomeCliente = "Nenhum";
        }

        // Mapa para armazenar produtos e suas quantidades
        Map<String, Integer> produtosQuantidade = new HashMap<>();
        Map<String, Produto> produtosInfo = new HashMap<>();

        // Contagem das quantidades de produtos e armazenar produto
        for (var produto : fatura) {
            String nomeProduto = produto.getNome();
            produtosQuantidade.put(nomeProduto, produtosQuantidade.getOrDefault(nomeProduto, 0) + 1);
            if (!produtosInfo.containsKey(nomeProduto)) {
                produtosInfo.put(nomeProduto, produto);
            }
        }

        // Construção da string de fatura
        StringBuilder textInputBuilder = new StringBuilder();
        textInputBuilder.append("Funcionario: ").append(getPessoaAtual().getNome()).append("\n\n");
        textInputBuilder.append("-- DADOS CLIENTE --\n\n");
        textInputBuilder.append("Cliente: ").append(nomeCliente).append("\n");
        textInputBuilder.append("Saldo: ").append(saldo).append("\n\n");
        textInputBuilder.append("----- FATURA -----\n");

        for (var entry : produtosQuantidade.entrySet()) {
            String nomeProduto = entry.getKey();
            int quantidade = entry.getValue();
            Produto produto = produtosInfo.get(nomeProduto);

            // Adiciona o produto à string de fatura com sua quantidade
            textInputBuilder.append("Produto: ").append(nomeProduto).append("\n");
            textInputBuilder.append("Quantidade: ").append(quantidade).append("\n");
            textInputBuilder.append("Preço por unidade: ").append(produto.getPreco()).append("€\n");
            textInputBuilder.append("Preço Total: ").append(ArredondarController.roundToTwoDecimalPlaces(produto.getPreco() * quantidade)).append("€\n");
            textInputBuilder.append("Iva: ").append(produto.getIva()).append(" %\n");
            textInputBuilder.append("---------------\n");
        }
        textArea.setText(textInputBuilder.toString());
    }
    @FXML
    private void handleButtonClickRemoverAll() {
        if (feedbackYesNo("Deseja Limpar tudo?")) {
            fatura.clear();
            GlobalVAR.Dados.setClientePOS(null);
            changeTextBox();
            aguardarCartao();

        }
    }
    @FXML
    private void handleButtonClickRemoverLast() {
        if (fatura.isEmpty()) {
            return;
        }
        if (feedbackYesNo("Deseja Remover Ultimo?")) {
            fatura.removeLast();
            changeTextBox();
        }
    }
    @FXML
    private void handleChoiceBoxChange() {
        setProdutosButton();
    }
    @FXML
    private void handleButtonClickVoltar() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            shutdown();
            GlobalVAR.Dados.setClientePOS(null);
            setStage("/com/edcards/edcards/Main.fxml");

        }
    }
    @FXML
    private void handleButtonAddSaldo() throws IOException {
        if (GlobalVAR.Dados.getClientePOS() != null) {
            setStage("/com/edcards/edcards/CarregarCartao.fxml");
        } else {
            FeedBackController.feedbackErro("Nenhum Cliente Selecionado");

        }

    }
    @FXML
    private void handleRefeicao() throws IOException {
        setStage("/com/edcards/edcards/AddRef.fxml");
    }
}
