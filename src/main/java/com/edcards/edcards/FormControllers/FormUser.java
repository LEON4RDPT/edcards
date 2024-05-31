package com.edcards.edcards.FormControllers;

import com.edcards.edcards.ClassControllers.GlobalVAR;
import com.edcards.edcards.ClassControllers.UsuarioEnum;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class FormUser {
    @FXML
    Image imageUser;
    @FXML
    Button btnPos;
    @FXML
    Button btnHorario;
    @FXML
    Button btnHistComp;
    @FXML
    Button btnMarcRef;
    @FXML
    Button btnRefMarc;
    @FXML
    String labelNome;
    @FXML
    String labelSaldo;
    @FXML
    String labelTipo;

    @FXML
    public void initialize(){

        var pessoa = GlobalVAR.Dados.getPessoaAtual();
        imageUser = pessoa.getFoto();
        labelNome = pessoa.getNome();
        labelSaldo = String.valueOf(pessoa.getSaldo());

        if (pessoa instanceof Funcionario){
            labelTipo = "Funcionario";
        } else if (pessoa instanceof Aluno){
            labelTipo = "Aluno";
        } else if (pessoa instanceof Admin){
            labelTipo = "Administrador";
        }



    }
}
