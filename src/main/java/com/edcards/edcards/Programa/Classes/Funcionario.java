package com.edcards.edcards.Programa.Classes;

import javafx.scene.image.Image;

import java.sql.Date;
import java.time.LocalDateTime;

public class Funcionario extends Pessoa {
    public Funcionario(String numCartao, String nome,int num_func ,Double saldo, Image foto, LocalDateTime entrou, String morada, int pin, Date dataNasc, String cartaoC, int numFuncionario, Image horario) {
        super(numFuncionario, numCartao, nome ,saldo, foto, entrou, morada, pin, dataNasc, cartaoC, horario, num_func);
    }

    public Funcionario(int idUser) {
        super(idUser);
    }

}
