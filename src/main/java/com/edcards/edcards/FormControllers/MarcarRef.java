package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.RefeicaoBLL;
import com.edcards.edcards.FormControllers.Utils.ResizeUtil;
import com.edcards.edcards.Programa.Classes.Refeicao;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class MarcarRef {
    public HBox rootHBox;
    public AnchorPane anchorPaneLeft;
    public AnchorPane anchorPaneRight;
    public Button exit;
    public Button buttonDown;
    public Button buttonUp;
    public TextField buttonDay;
    public VBox vboxRightPane;
    public Label labelRefeicao;
    public Button buttonMarcarRefeicao;
    public Button buttonProximaRefeicao;

    private List<Refeicao> todasRefeicoes;  // Lista com todas as refeições obtidas

    private LocalDate dataAtual;
    private List<Refeicao> refeicaoList = new ArrayList<>();
    private Refeicao refeicaoAtual;
    private int currentRefeicaoIndex = 0;

    @FXML
    private void initialize() {
        dataAtual = LocalDate.now();
        carregarTodasRefeicoes();  // Carrega todas as refeições ao iniciar a aplicação
        atualizarTextoData();

        resizeAll();
    }

    private void resizeAll() {
        rootHBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            anchorPaneLeft.setPrefWidth(width / 4);
            anchorPaneRight.setPrefWidth((width / 4) * 3);
        });

        rootHBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            anchorPaneLeft.setPrefHeight(height);
            anchorPaneRight.setPrefHeight(height);
        });

        double initialWidth = rootHBox.getPrefWidth();
        anchorPaneLeft.setPrefWidth(initialWidth / 4);
        anchorPaneRight.setPrefWidth((initialWidth / 4) * 3);


        double initialHeight = rootHBox.getPrefHeight();
        anchorPaneLeft.setPrefHeight(initialHeight);
        anchorPaneRight.setPrefHeight(initialHeight);

        ResizeUtil.resizeAndPosition(buttonDay,anchorPaneLeft,0.1);
    }


    public void handleButtonSair(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?", "Confirmação")) {
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }

    private void carregarTodasRefeicoes() {
        todasRefeicoes = RefeicaoBLL.getRefeicao();  // Obtém todas as refeições do banco de dados
    }

    private void filtrarRefeicoesPorData(LocalDate data) {
        refeicaoList = todasRefeicoes.stream()
                .filter(refeicao -> refeicao.getDataRefeicao().toLocalDate().equals(data))
                .collect(Collectors.toList());
    }

    private void atualizarTextoData() {
        buttonDay.setText(dataAtual.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        filtrarRefeicoesPorData(dataAtual);

        if (refeicaoList.isEmpty()) {
            labelRefeicao.setText("Nenhuma Refeição marcada para este dia.");
            buttonMarcarRefeicao.setDisable(true);
            buttonProximaRefeicao.setDisable(true);
        } else {
            currentRefeicaoIndex = 0;
            atualizarRefeicaoAtual();
        }
    }

    private void atualizarRefeicaoAtual() {
        if (refeicaoList.isEmpty()) {
            labelRefeicao.setText("Nenhuma Refeição marcada para este dia.");
            buttonMarcarRefeicao.setDisable(true);
            buttonProximaRefeicao.setDisable(true);
        } else {
            refeicaoAtual = refeicaoList.get(currentRefeicaoIndex);
            labelRefeicao.setText(refeicaoAtual.getProduto().getNome());

            // Verificar se a refeição atual já foi marcada pelo usuário
            boolean marcadaPeloUsuario = RefeicaoBLL.marcou(refeicaoAtual.getIdRefeicao(), GlobalVAR.Dados.getPessoaAtual().getIduser());
            buttonMarcarRefeicao.setDisable(marcadaPeloUsuario);
            buttonProximaRefeicao.setDisable(refeicaoList.size() <= 1);
        }
    }

    public void handleButtonMarcarRefeicao(ActionEvent actionEvent) {
        if (refeicaoAtual != null) {
            // Verificar se a refeição já foi marcada pelo usuário antes de tentar marcar novamente
            boolean jaMarcada = RefeicaoBLL.marcou(refeicaoAtual.getIdRefeicao(), GlobalVAR.Dados.getPessoaAtual().getIduser());
            if (jaMarcada) {
                FeedBackController.feedbackErro("Você já marcou esta refeição.");
                return; // Saímos do método sem fazer mais nada
            }

            // Marcar a refeição
            var sucesso = RefeicaoBLL.marcarRefeicao(refeicaoAtual.getIdRefeicao(), GlobalVAR.Dados.getPessoaAtual().getIduser());
            if (sucesso != 0) {
                FeedBackController.feedbackErro("Refeição marcada com sucesso!");
            } else {
                FeedBackController.feedbackErro("Erro ao marcar a refeição.");
            }
        }
    }



    public void handleButtonUp(ActionEvent actionEvent) {
        dataAtual = dataAtual.plusDays(1);
        atualizarTextoData();
    }

    public void handleButtonDown(ActionEvent actionEvent) {
        dataAtual = dataAtual.minusDays(1);
        atualizarTextoData();
    }



    public void handleButtonProximaRefeicao(ActionEvent actionEvent) {
        if (!refeicaoList.isEmpty()) {
            currentRefeicaoIndex = (currentRefeicaoIndex + 1) % refeicaoList.size();
            atualizarRefeicaoAtual();
        }
    }
}
