package com.edcards.edcards.Programa.Classes;

import java.util.List;

public class Transacao {
    private int idTransacao;
    private Pessoa cliente;
    private Funcionario funcionario;
    private List<Produto> produtos;
    private double valorpago;

    public Transacao(int idTransacao, Pessoa cliente, Funcionario funcionario, List<Produto> produtos) {
        this.idTransacao = idTransacao;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.produtos = produtos;
        for (Produto produto : produtos) {
            valorpago += produto.getPreco();
        }
    }

    public Transacao(int idTransacao, Pessoa cliente, Funcionario funcionario) {
        this.idTransacao = idTransacao;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.produtos = null;
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
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
        valorpago+=produto.getPreco();
    }
    public void addProduct(Produto[] produtoArr) {
        for (Produto produto : produtoArr) {
            produtos.add(produto);
            valorpago+=produto.getPreco();
        }
    }
    public void removeProduct(Produto produto){
        for (Produto prod : produtos) {
            if (prod.getIdProduto() == produto.getIdProduto()) {
                produtos.remove(prod);
                valorpago-=prod.getPreco();
            }
        }
    }
}
