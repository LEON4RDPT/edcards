package com.edcards.edcards.FormControllers;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javax.smartcardio.CardException;
import java.util.ArrayList;
import java.util.List;

public class EntradasSaidasController {
    private List<String> allNfc = new ArrayList<>();
    public Label entsai;
    public Label nome;
    public Pane mainPane;
    String nomePessoa, es;
    boolean e_s, s_e;
    private double paneWidth = 200;
    private double paneHeight = 262;
    private double hSpacing = 15;
    private double vSpacing = 15;
    private int panesPerRow = 4;
    private int currentRow = 0;
    private int currentCol = 0;
    private int paneCount = 0;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane paneContainer;
    @FXML
    public void initialize() {
        var x = CartaoBLL.getAllCards();
        if (x == null) {
            System.out.println("No cards available in the database.");
            return;
        }
        allNfc = List.of(x);
    }


    private void addPane() {
        paneCount++;
        Pane pane = new Pane();
        pane.setPrefSize(paneWidth, paneHeight);
        

        Label label = new Label("Pane " + paneCount);
        pane.getChildren().add(label);

        paneContainer.getChildren().add(0, pane); // Add to the beginning

        for (int i = 0; i < paneContainer.getChildren().size(); i++) {
            Pane p = (Pane) paneContainer.getChildren().get(i);
            int col = i % panesPerRow;
            int row = i / panesPerRow;
            p.setLayoutX(col * (paneWidth + hSpacing));
            p.setLayoutY(row * (paneHeight + vSpacing));
        }
    }

    public String cartaoLido() {
        while (true) {
            try {
                String cartao = LerCartao.lerIDCartao();

                if (!allNfc.contains(cartao)) {
                    continue;
                }

                try {
                    var userByNFC = CartaoBLL.getUserByNFC(cartao);
                    if (userByNFC != null) {
                        nomePessoa = UsersBLL.getNomeUser(userByNFC.getIduser());
                        e_s=CartaoBLL.getEntSaiu(cartao);
                        if(!e_s){
                            es= "Entrou";
                            s_e=true;
                        }else{
                            es= "Saiu";
                            s_e=false;
                        }
                        System.out.println(nomePessoa);
                        System.out.println(s_e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return cartao;
            } catch (CardException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

