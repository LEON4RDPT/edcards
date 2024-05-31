package com.edcards.edcards.Programa.Classes;

import javafx.scene.image.Image;

import java.sql.Date;
import java.time.LocalDateTime;

public class Admin extends Pessoa {

    public Admin(String numCartao, String nome, Double saldo, Image foto, LocalDateTime entrou, String morada, int pin, Date dataNasc, String cartaoC, Image horario, int id) {
        super(id, numCartao, nome, saldo, foto, entrou, morada, pin, dataNasc, cartaoC, horario);
    }

    public Admin(int idUser) {
        super(idUser);
    }
}
