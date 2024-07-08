package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCardController {
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    String idCartao;
    @FXML
    private Text cardNumber;

    public void initialize() {
        aguardarCartao();

    }

    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/POSAdmin.fxml");
                    if (idCartao == null) {
                        return;
                    }
                    if (!CartaoBLL.existenteNFC(idCartao)) {
                        CartaoBLL.getIdUserByNFC(idCartao);
                        cardNumber.setText(idCartao);
                    } else {
                        FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err13));
                        isRunning = true;
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }

    public void insertCardClick(ActionEvent event) throws IOException {
        idCartao = cardNumber.getText();
        if (idCartao != null) {
            CartaoBLL.inserirCartao(idCartao);
        }

    }
}

