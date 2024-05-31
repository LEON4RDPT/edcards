package com.edcards.edcards.Programa.Classes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Refeicao {
    private Produto pratoPrincipal;
    private Produto sopa;
    private Produto sobremesa;


    public Refeicao(Produto pratoPrincipal, Produto sopa, Produto sobremesa) {

        if (!pratoPrincipal.isRefeicao()) {
            this.pratoPrincipal = null;
        }
        else {
            this.pratoPrincipal = pratoPrincipal;
        }

        if (!sopa.isRefeicao()) {
            this.sopa = null;
        } else {
            this.sopa = sopa;
        }

        if (this.sobremesa.isRefeicao()) {
            this.sobremesa = null;
        } else {
            this.sobremesa = sobremesa;
        }
    }


    public Produto getPratoPrincipal() {
        return pratoPrincipal;
    }
    public Produto getSopa() {
        return sopa;
    }
    public Produto getSobremesa() {
        return sobremesa;
    }

    public void setPratoPrincipal(Produto pratoPrincipal) {
        this.pratoPrincipal = pratoPrincipal;
    }
    public void setSopa(Produto sopa) {
        this.sopa = sopa;
    }
    public void setSobremesa(Produto sobremesa) {
        this.sobremesa = sobremesa;
    }

    public Produto[] getProdutos() {

        List<Produto> produtos = new ArrayList<Produto>();

        if (pratoPrincipal != null) {
            produtos.add(pratoPrincipal);
        }
        if (sopa != null) {
            produtos.add(sopa);
        }
        if (sobremesa != null) {
            produtos.add(sobremesa);
        }

        if (produtos.isEmpty()) {
            return null;
        }

        return produtos.toArray(new Produto[0]);
    }
}
