package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.RefeicaoBLL;
import com.edcards.edcards.DataTable.TransacaoBLL;
import com.edcards.edcards.Programa.Classes.Refeicao;
import com.edcards.edcards.Programa.Classes.Transacao;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.StageController.setStage;

public class VerRef {
    public HBox rootHBox;
    public AnchorPane anchorPaneLeft;
    public AnchorPane anchorPaneRight;
    public Button exit;
    public Button buttonDown;
    public Button buttonUp;
    public TextField buttonDay;

    private LocalDate dataAtual;
    private List<Refeicao> refeicaoList = new ArrayList<>();

    public void handleButtonSair(ActionEvent actionEvent) throws IOException {
        if (FeedBackController.feedbackYesNo("Deseja Sair?","Confirmação")) {
            GlobalVAR.Dados.setPessoaAtual(null);
            setStage("/com/edcards/edcards/Main.fxml");
        }
    }
    private void atualizarTextoData() {
        // Atualiza o texto do botão com a data formatada
        buttonDay.setText(dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        var x = RefeicaoBLL.getRefeicao(Date.valueOf(dataAtual));
        if (x == null) {
            FeedBackController.feedbackErro("Nenhuma Refeição marcada para este dia.");
        }


    }

    @FXML
    private void initialize() {
        dataAtual = LocalDate.now();
        atualizarTextoData();
    }

    public void handleButtonUp(ActionEvent actionEvent) {
        dataAtual = dataAtual.plusDays(1);
        atualizarTextoData();
    }

    public void handleButtonDown(ActionEvent actionEvent) {
        // Retrocede para o dia anterior
        dataAtual = dataAtual.minusDays(1);
        atualizarTextoData();


    }
}
