package com.edcards.edcards.Programa.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transacao {
    private int idTransacao;
    private Pessoa cliente;
    private Pessoa funcionario;
    private List<Produto> produtos;
    private double valorpago;

    public Transacao(int idTransacao, Pessoa cliente, Pessoa funcionario, List<Produto> produtos) {
        this.idTransacao = idTransacao;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.produtos = produtos;
        produtos.forEach(produto -> valorpago += produto.getPreco());
    }

    public void setValorpago(double valorpago) {
        this.valorpago = valorpago;
    }

    public Transacao(int idTransacao, Pessoa cliente, Pessoa funcionario) {
        this.idTransacao = idTransacao;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.produtos = new ArrayList<Produto>();
        this.valorpago = 0;
    }
    public Transacao(int idTransacao) {
        this.idTransacao = idTransacao;
        this.cliente = null;
        this.funcionario = null;
        this.produtos = new ArrayList<Produto>();
        this.valorpago = 0;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Pessoa getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Pessoa funcionario) {
        this.funcionario = funcionario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public double getValorpago() {
        return valorpago;
    }

    public void addProduct(Produto produto) {
        this.produtos.add(produto);
    }

    public void addProduct(Produto[] produtoArr) {
        Collections.addAll(produtos, produtoArr);
    }

    public void removeProduct(Produto produto) {
        produtos.removeIf(prod -> prod.getIdProduto() == produto.getIdProduto());
    }
}
