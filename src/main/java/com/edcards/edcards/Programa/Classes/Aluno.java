package com.edcards.edcards.Programa.Classes;

import com.edcards.edcards.ClassControllers.Enums.AseEnum;
import javafx.scene.image.Image;

import java.sql.Date;
import java.time.LocalDateTime;


public class Aluno extends Pessoa {

    private AseEnum ase;
    private String emailEE;
    private int numEE;
    private int numUtente;
    private int numTurma;

    public Aluno(int id, String numCartao, String nome, Double saldo, Image foto, LocalDateTime ultimaVezEntrado, String morada, int pin, Date dataNasc, String cartaoC, Image horario, int numTurma, AseEnum ase, String emailEE, int numEE, int numUtente) {
        super(id,numCartao, nome, saldo, foto, ultimaVezEntrado, morada, pin, dataNasc, cartaoC, horario);
        this.ase = ase;
        this.emailEE = emailEE;
        this.numEE = numEE;
        this.numUtente = numUtente;
        this.numTurma = numTurma;
    }

    public Aluno(int id) {
        super(id);
        this.ase = null;
        this.emailEE = "";
        this.numEE = 0;
        this.numUtente = 0;
        this.numTurma = 0;
    }

    public AseEnum getAse() {
        return ase;
    }

    public void setAse(AseEnum ase) {
        this.ase = ase;
    }

    public String getEmailEE() {
        return emailEE;
    }

    public void setEmailEE(String emailEE) {
        this.emailEE = emailEE;
    }

    public int getNumEE() {
        return numEE;
    }

    public void setNumEE(int numEE) {
        this.numEE = numEE;
    }

    public int getNumUtente() {
        return numUtente;
    }

    public void setNumUtente(int numUtente) {
        this.numUtente = numUtente;
    }

    public int getNumTurma() {
        return numTurma;
    }

    public void setNumTurma(int numTurma) {
        this.numTurma = numTurma;
    }
}
