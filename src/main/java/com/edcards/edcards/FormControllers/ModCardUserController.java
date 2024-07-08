package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ModCardUserController {
    List<Pessoa> users;
    String user;
    String idCartao;
    int id;
    private volatile boolean isRunning = true;
    private ExecutorService nfcExecutar = Executors.newSingleThreadExecutor();
    private Pessoa pessoaAtual;
    @FXML
    private Text cardNumber;
    @FXML
    private ComboBox<String> userPicker;
    @FXML
    private Text nomeText;
    public void initialize() {
        usersLoad();
        aguardarCartao();

    }
    private void usersLoad() {
        var userLoad = UsersBLL.getUsersAll();
        ObservableList<String> usersLoad = FXCollections.observableArrayList(
                Arrays.stream(Objects.requireNonNull(userLoad).toArray(new Pessoa[0]))
                        .map(Pessoa::getNome)
                        .collect(Collectors.toList())

        );
        userPicker.setItems(usersLoad);
        users = userLoad;
    }
    @FXML
    public void selectUser(ActionEvent event) {
        String nomeUser = userPicker.getValue();

        for (Pessoa p : users) {
            if (p.getNome().equals(nomeUser)) {
                pessoaAtual = p;
            }
        }
        if (pessoaAtual != null) {
            user = (pessoaAtual.getNome());
            id= (pessoaAtual.getIduser());
            nomeText.setText(user);
        }
    }
    private void aguardarCartao() {
        nfcExecutar.submit(() -> {
            while (isRunning) {
                try {
                    String idCartao = LerCartao.lerIDCartao("/com/edcards/edcards/POSAdmin.fxml");
                    if (idCartao == null) {
                        return;
                    }
                        CartaoBLL.getIdUserByNFC(idCartao);
                        cardNumber.setText(idCartao);

                } catch (Exception e) {
                    System.err.println("Error reading NFC card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    public void insertCardClick(ActionEvent event) throws IOException {
        idCartao = cardNumber.getText();
        if (idCartao != null) {
            UsersBLL.setCodigoUser(id,idCartao);
        }

    }
}
