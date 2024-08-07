package com.edcards.edcards.Programa.Classes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Refeicao {
    private Produto produto;
    private Date dataRefeicao;
    private int idRefeicao;


    public Refeicao(Produto produto, Date dataRefeicao) {
        this.produto = produto;
        this.dataRefeicao = dataRefeicao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Date getDataRefeicao() {
        return dataRefeicao;
    }

    public void setDataRefeicao(Date dataRefeicao) {
        this.dataRefeicao = dataRefeicao;
    }

    public Refeicao() {
        produto = null;
        dataRefeicao = null;
        idRefeicao = 0;
    }

    public int getIdRefeicao() {
        return idRefeicao;
    }

    public void setIdRefeicao(int idRefeicao) {
        this.idRefeicao = idRefeicao;
    }
}
