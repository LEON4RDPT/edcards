package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DAL;
import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoBLL extends DAL {
    public ProdutoBLL() {
        super("produto");
    }

    public static void inserirProduto(String Nome, ProdutoEnum tipo, double preco) {
        DefaultBLL bll = new DefaultBLL("produto");
        if (existenteNome(Nome)) {
            return;
        }
        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("nome", Nome);
        columnValues.put("disponivel", 1);
        columnValues.put("tipo", tipo.toDbValue());
        columnValues.put("preco", preco);
        bll.insert(columnValues);

    }

    public static void inserirProduto(Produto produto) {
        DefaultBLL bll = new DefaultBLL("produto");
        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("nome", produto.getNome());
        if (produto.isDisponivel()) {
            columnValues.put("disponivel", 1);
        } else {
            columnValues.put("disponivel", 0);
        }
        columnValues.put("tipo", produto.getTipo().toDbValue());
        columnValues.put("preco", produto.getPreco());
        columnValues.put("id", produto.getIdProduto());
        bll.insert(columnValues);

    }

    public static void inserirProduto(String Nome, ProdutoEnum tipo, double preco, boolean disponivel) {
        DefaultBLL bll = new DefaultBLL("produto");
        if (existenteNome(Nome)) {
            return;
        }

        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("nome", Nome);

        if (disponivel) {
            columnValues.put("disponivel", 1);
        } else {
            columnValues.put("disponivel", 0);

        }
        columnValues.put("tipo", tipo.toDbValue());
        columnValues.put("preco", preco);
        bll.insert(columnValues);

    }

    public static void setDisp(int id, boolean disp) {
        DefaultBLL bll = new DefaultBLL("produto");
        if (disp) {
            bll.setOne("disponivel", 1, "id", id);

        } else {
            bll.setOne("disponivel", 0, "id", id);
        }
    }

    public static double getPreco(int id) {
        DefaultBLL bll = new DefaultBLL("produto");
        var value = bll.getOne("preco", "id", id);
        if (value == null) {
            return 0;
        }
        return (double) value;
    }

    public static boolean getDisp(int id) {
        DefaultBLL bll = new DefaultBLL("produto");
        var value = bll.getOne("disponivel", "id", id);
        if (value == null) {
            return false;
        }
        return (boolean) value;
    }

    public static ProdutoEnum getTipo(int id) {
        DefaultBLL bll = new DefaultBLL("produto");
        var value = bll.getOne("tipo", "id", id);
        if (value == null) {
            return null;
        }
        return ProdutoEnum.fromDbValue((int) value);
    }

    public static String getNome(int id) {
        DefaultBLL bll = new DefaultBLL("produto");
        var value = bll.getOne("nome", "id", id);
        if (value == null) {
            return "";
        }
        return (String) value;
    }

    public static void setPreco(int id, double preco) {
        DefaultBLL bll = new DefaultBLL("produto");
        bll.setOne("preco", preco, "id", id);
    }

    public static void setNome(int id, String nome) {
        DefaultBLL bll = new DefaultBLL("produto");
        bll.setOne("nome", nome, "id", id);
    }

    public static void setTipo(int id, ProdutoEnum tipo) {
        DefaultBLL bll = new DefaultBLL("produto");
        bll.setOne("tipo", tipo.toDbValue(), "id", id);
    }

    public static Produto getProduto(String nome) {
        DefaultBLL bll = new DefaultBLL("produto");
        Map<String, Object> row = bll.getAllinOne("nome", nome);

        if (row == null) {
            return null;
        }


        return transformProduto(row);
    }

    public static Produto getProduto(int id) {
        DefaultBLL bll = new DefaultBLL("produto");
        Map<String, Object> row = bll.getAllinOne("id", id);

        if (row == null) {
            return null;
        }

        var prod = transformProduto(row);
        return prod;
    }

    public static Produto transformProduto(Map<String, Object> ob) {
        Produto produto = new Produto(0);
        for (Map.Entry<String, Object> entry : ob.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    produto.setId((int) entry.getValue());
                    break;
                case "nome":
                    produto.setNome((String) entry.getValue());
                    break;
                case "tipo":
                    produto.setTipo(ProdutoEnum.fromDbValue((int) entry.getValue()));
                    break;
                case "disponivel":
                    produto.setDisponivel((boolean) entry.getValue());
                    break;
                case "preco":
                    produto.setPreco((double) entry.getValue());
                    break;
            }
        }
        return produto;
    }

    public static List<Produto> getALl() {

        DefaultBLL bll = new DefaultBLL("produto");
        List<Map<String, Object>> rows = bll.getAll();
        List<Produto> produtos = new ArrayList<Produto>();

        if (rows == null) {
            return null;
        }

        for (Map<String, Object> row : rows) {
            Produto produto = transformProduto(row);
            produtos.add(produto);

        }

        return produtos;
    }

    public static List<Produto> getALl(ProdutoEnum tipo) {

        DefaultBLL bll = new DefaultBLL("produto");
        List<Map<String, Object>> rows = bll.getAll("tipo", tipo.toDbValue());
        List<Produto> produtos = new ArrayList<Produto>();

        if (rows == null) {
            return null;
        }

        for (Map<String, Object> row : rows) {
            Produto produto = transformProduto(row);
            produtos.add(produto);

        }

        return produtos;
    }


    public static List<Produto> getALlByEnum(ProdutoEnum produtoEnum) {

        DefaultBLL bll = new DefaultBLL("produto");
        List<Map<String, Object>> rows = bll.getAll("tipo", produtoEnum.toDbValue());
        List<Produto> produtos = new ArrayList<Produto>();

        if (rows == null) {
            return null;
        }

        for (Map<String, Object> row : rows) {
            Produto produto = transformProduto(row);
            produtos.add(produto);

        }

        return produtos;
    }

    public static List<Produto> getALlPOS() {

        DefaultBLL bll = new DefaultBLL("produto");
        List<Map<String, Object>> rows = bll.getAllOrdered("tipo", "ASC", ProdutoEnum.REFEICOES.toDbValue(), "tipo");
        List<Produto> produtos = new ArrayList<Produto>();

        if (rows == null) {
            return null;
        }

        for (Map<String, Object> row : rows) {
            Produto produto = transformProduto(row);
            produtos.add(produto);

        }

        return produtos;
    }

    public static boolean existenteNome(String Nome) {
        return new DefaultBLL("produto").hasRows("nome", Nome);
    }

    public static void deleteAll() {
        new DefaultBLL("produto").deleteALL();
    }

    public static void deleteOne(int id) {
        DefaultBLL bll = new DefaultBLL("produto");
        bll.delete("id", id);
    }

    public static boolean isRefeicao(int id) {
        return getTipo(id) == ProdutoEnum.REFEICOES;
    }

    public static Produto[] getRefeicoesDia() {
        List<Produto> list = getALl();
        List<Produto> refeicoes = new ArrayList<Produto>();
        if (list == null) {
            return null;
        }
        for (Produto produto : list) {
            if (produto.isRefeicao() && produto.isDisponivel()) {
                refeicoes.add(produto);
            }
        }
        return refeicoes.toArray(new Produto[0]);
    }

    public void setAllRefeicoesIndisponiveis() {
        Produto[] produtos = getRefeicoesDia();
        if (produtos == null) {
            return;
        }
        for (Produto produto : produtos) {
            setDisp(produto.getIdProduto(), false);
        }
    }
}
