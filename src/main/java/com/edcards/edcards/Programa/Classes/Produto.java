package com.edcards.edcards.Programa.Classes;

import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;

public class Produto {

    private boolean disponivel;
    private String nome;
    private double preco;
    private int idProduto;
    private ProdutoEnum tipo;

    public Produto(boolean disponivel, String nome, double preco, int idProduto, ProdutoEnum tipo) {
        this.disponivel = disponivel;
        this.nome = nome;
        this.preco = preco;
        this.idProduto = idProduto;
        this.tipo = tipo;
    }
    public Produto(int id) {
        this.disponivel = false;
        this.nome = "";
        this.preco = 0;
        this.idProduto = id;
        this.tipo = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public ProdutoEnum getTipo() {
        return tipo;
    }
    public void setId(int id) {this.idProduto = id;}

    public void setTipo(ProdutoEnum tipo) {
        this.tipo = tipo;
    }

    public boolean isRefeicao() {
        return tipo == ProdutoEnum.REFEICOES;
    }
}
