package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.RefeicaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Classes.Refeicao;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.edcards.edcards.Programa.Controllers.ColorController.ColorController.setButtonColor;
import static com.edcards.edcards.Programa.Controllers.ColorController.ColorController.setButtonColorBack;
import static com.edcards.edcards.Programa.Controllers.GlobalVAR.Dados.getPessoaAtual;
import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;
import static sun.security.util.KnownOIDs.Data;

public class VerRefMarcAll {
    private volatile boolean isRunning = true;
    private final ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    private boolean isProcessingCartao = false;
    public TextArea textArea;
    int idRefeicao;
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
    private HBox rootHBox;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Button buttonVoltar;
    @FXML
    private Button infoB1;
    @FXML
    private Button infoB2;
    @FXML
    private Button infoB3;
    @FXML
    private Button infoB4;
    @FXML
    private Button infoB5;
    @FXML
    private Button infoB6;
    @FXML
    private Button infoB7;
    @FXML
    private Button infoB8;
    @FXML
    private Button infoB9;
    @FXML
    private Button infoB10;
    @FXML
    private Button infoB11;
    @FXML
    private Button infoB12;
    @FXML
    private Button infoB13;
    @FXML
    private Button infoB14;
    @FXML
    private Button infoB15;
    @FXML
    private Button infoB16;
    @FXML
    private Button infoB17;
    @FXML
    private Button infoB18;
    @FXML
    private Button infoB19;
    @FXML
    private Button infoB20;
    @FXML
    private Button infoB21;
    @FXML
    private Button infoB22;
    @FXML
    private Button infoB23;
    @FXML
    private Button infoB24;
    private Button[] btns = new Button[23];
    private Refeicao[] refeicoes = new Refeicao[23];
    private List<Refeicao> listaProdutosDisponiveis = new ArrayList<>();
    private int buttonPage = 1;
    LocalDate data;

    private void loadbtns() {
        btns = new Button[]{
                infoB1, infoB2, infoB3, infoB4,
                infoB5, infoB6, infoB7, infoB8,
                infoB9, infoB10, infoB11, infoB12,
                infoB13, infoB14, infoB15, infoB16,
                infoB17, infoB18, infoB19, infoB20,
                infoB21, infoB22, infoB23, infoB24
        };
    }

    @FXML
    private void initialize() {
        //var id = GlobalVAR.Dados.getPessoaAtual().getIduser();
        //listaProdutosDisponiveis.clear();
        //listaProdutosDisponiveis.addAll(Objects.requireNonNull(RefeicaoBLL.getRefeicaoByIdUser(id)));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Refeicao> refeicoesFiltradas = new ArrayList<>();
        for (var ref : listaProdutosDisponiveis) {
            if (!ref.getDataRefeicao().before(now)) {
                refeicoesFiltradas.add(ref);
            }
        }

        listaProdutosDisponiveis = refeicoesFiltradas;
        data = LocalDate.now();
        loadbtns();
        resizeAll();
        changeTextBox();

        setMarc();
        aguardarCartao();
    }
    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/Main.fxml");
                    if (idCartao == null) {
                        continue; // Skip if no card read
                    }

                    if (!isProcessingCartao) {
                        isProcessingCartao = true;

                        int id = CartaoBLL.getIdUserByNFC(idCartao);
                        Pessoa user = UsersBLL.getUser(id);

                        // Fetch the meal for today (assuming there's only one meal per day)
                        List<Refeicao> refeicoes = RefeicaoBLL.getRefeicao(Date.valueOf(LocalDate.now()));
                        if (refeicoes != null && !refeicoes.isEmpty()) {
                            Refeicao refeicaoToday = refeicoes.get(0); // Get the first (and only) meal
                            idRefeicao = refeicaoToday.getIdRefeicao();

                            // Check if the user has marked this meal
                            boolean marcou = RefeicaoBLL.marcou(idRefeicao, id);

                            if (marcou) {
                                textArea.setText("Utilizador " + user.getNome() + " tem refeição.");
                            } else {
                                textArea.setText("Utilizador " + user.getNome() + " não marcou a refeição.");
                            }
                        } else {
                            textArea.setText("Não há refeição definida para hoje.");
                        }
                    }
                } catch (Exception e) {
                    // Handle exceptions (e.g., log the error)
                    System.err.println("Error reading card or processing meal: " + e.getMessage());
                } finally {
                    isProcessingCartao = false;
                }
            }
        });
    }
    private void setMarc() {

        for (var button : btns) {
            setButtonColorBack(button);
        }
        textNum.setText(String.valueOf(buttonPage));


        int btnPg = 24 * buttonPage;
        int btnPgOld = btnPg - 24;

        refeicoes = new Refeicao[refeicoes.length]; //clear
        List<Refeicao> refeicoes1 = new ArrayList<>();

        for (int page = btnPgOld; page < btnPg; page++) {
            if (page >= Objects.requireNonNull(listaProdutosDisponiveis).size()) {
                refeicoes1.add(null);
                continue;
            }
            refeicoes1.add(listaProdutosDisponiveis.get(page));
        }

        refeicoes = refeicoes1.toArray(new Refeicao[0]);

        for (int i = 0; i < btns.length; i++) {
            btns[i].setWrapText(true);

            if (refeicoes[i] == null) {
                btns[i].setText("NULL");
                btns[i].setVisible(false);
                continue;
            }
            btns[i].setVisible(true);
            btns[i].setText(refeicoes[i].getProduto().getNome());

        }

        //color
        for (int slot = 0; slot < btns.length; slot++) {
            if (refeicoes[slot] == null) {
                continue;
            }
            setButtonColor(btns[slot], refeicoes[slot].getProduto().getTipo());

        }
    }

    @FXML
    private void handleButtonClickMarc(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        for (int i = 0; i < btns.length; i++) {
            if (refeicoes[i] == null) {
                return;
            }
            if (clickedButton == btns[i]) {
                setText(refeicoes[i]);
                break;
            }
        }

    }

    private void setText(Refeicao ref) {
        Produto produto = ref.getProduto();
        StringBuilder str = new StringBuilder();
        str.append("Nome: \n").append(produto.getNome());
        str.append("\n");
        str.append("Data: ").append(ref.getDataRefeicao());
        textArea.setText(str.toString());

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
        ResizeUtil.resizeAndCenterMiddleButtons(buttonBack, buttonUp, buttonsBack);
        ResizeUtil.resizeAndCenterText(textNum, buttonsBack);
        ResizeUtil.resizeAndPositionTextArea(textArea, leftPane, 0.15);

    }
    @FXML
    private void buttonBackClick() {
        if (buttonPage > 1) {
            buttonPage--;
        }
        changeTextBox();
    }
    @FXML
    private void buttonUpClick() {
        int num = buttonPage * 24;
        if (num <= listaProdutosDisponiveis.size()) {
            buttonPage++;
        }
        changeTextBox();
    }

    private void changeTextBox() {
        textNum.setText(String.valueOf(buttonPage));
    }

    @FXML
    private void handleButtonClickVoltar() throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja sair?", "Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }
}
