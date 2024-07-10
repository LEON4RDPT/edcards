package com.edcards.edcards.Programa.Classes;

import javafx.scene.image.Image;

import java.sql.Date;
import java.time.LocalDateTime;

public abstract class Pessoa {

    private final int iduser;
    private String numCartao;
    private String nome;
    private Double saldo;
    private Image foto;
    private LocalDateTime ultimaVezEntrado;
    private String morada;
    private int pin;
    private Date dataNasc;
    private String cartaoC;
    private Image horario;
    private int num;

    public Pessoa(int iduser, String numCartao, String nome, Double saldo, Image foto, LocalDateTime ultimaVezEntrado, String morada, int pin, Date dataNasc, String cartaoC, Image horario, int num) {
        this.iduser = iduser;
        this.numCartao = numCartao;
        this.nome = nome;
        this.saldo = saldo;
        this.foto = foto;
        this.ultimaVezEntrado = ultimaVezEntrado;
        this.morada = morada;
        this.pin = pin;
        this.dataNasc = dataNasc;
        this.cartaoC = cartaoC;
        this.horario = horario;
        this.num = num;
    }

    public Pessoa(int iduser) {
        this.iduser = iduser;
        this.num = 0;
        this.numCartao = "";
        this.nome = "";
        this.saldo = 0.0;
        this.foto = null;
        this.horario = null;
        this.ultimaVezEntrado = null;
        this.morada = "";
        this.pin = 0;
        this.dataNasc = null;
        this.cartaoC = "";
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }


    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public LocalDateTime getUltimaVezEntrado() {
        return ultimaVezEntrado;
    }

    public void setUltimaVezEntrado(LocalDateTime ultimaVezEntrado) {
        this.ultimaVezEntrado = ultimaVezEntrado;
    }

    public Image getHorario() {
        return horario;
    }

    public void setHorario(Image horario) {
        this.horario = horario;
    }

    public int getIduser() {
        return iduser;
    }

    public String getCartaoC() {
        return cartaoC;
    }

    public void setCartaoC(String cartaoC) {
        this.cartaoC = cartaoC;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
