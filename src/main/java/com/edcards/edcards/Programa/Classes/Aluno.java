package com.edcards.edcards.Programa.Classes;

import com.edcards.edcards.Programa.Controllers.Enums.AseEnum;
import javafx.scene.image.Image;

import java.sql.Date;
import java.time.LocalDateTime;


public class Aluno extends Pessoa {

    private AseEnum ase;
    private String emailEE;
    private int numEE;
    private int numUtente;
    private int numTurma;
    private int num_aluno;

    public Aluno(int id, String numCartao, String nome,int num_func ,Double saldo, Image foto, LocalDateTime ultimaVezEntrado, String morada, int pin, Date dataNasc, String cartaoC, Image horario, int numTurma, AseEnum ase, String emailEE, int numEE, int numUtente, int num_aluno) {
        super(id, numCartao, nome, saldo, foto, ultimaVezEntrado, morada, pin, dataNasc, cartaoC, horario, num_func);
        this.ase = ase;
        this.num_aluno = num_aluno;
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
        this.num_aluno = 0;
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

    public int getNum_aluno() {
        return num_aluno;
    }

    public void setNum_aluno(int num_aluno) {
        this.num_aluno = num_aluno;
    }
}
