package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Classes.Refeicao;
import com.edcards.edcards.Programa.Classes.Transacao;
import com.edcards.edcards.Programa.Controllers.ArredondarController;
import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.edcards.edcards.Programa.Controllers.FeedBackController.feedbackErro;

public class TransacaoBLL {

    //exists done!
    public static boolean existe(int id) {
        return new DefaultBLL("transacao").hasRows("id", id);
    }

    public static boolean existeDetalhe(int id) {
        return new DefaultBLL("transacao_detalhe").hasRows("id", id);
    }

    public static boolean temProduto(int id, Produto produto) {
        if (produto == null) {
            return false;
        }
        return new DefaultBLL("transacao_detalhe").hasObject("produto_id", produto.getIdProduto(), "id", id);
    }

    public static boolean temProduto(int id, int idProd) {
        return new DefaultBLL("transacao_detalhe").hasObject("produto_id", idProd, "id", id);
    }

    //delete done!

    private static void deleteTransacaoDetalhes(int idTransacao) {
        new DefaultBLL("transacao_detalhe").delete("transacao_id", idTransacao);
    }

    public static void deleteTransacao(int idTransacao) {
        deleteTransacaoDetalhes(idTransacao);
        new DefaultBLL("transacao").delete("id", idTransacao);
    }

    //set done!

    //OBS: sets não seram usados devido as faturas não poderam ser alteradas depois de serem criadas!!
    //ou seja seram estaticas!


    //get done!
    public static Produto[] getProdutos(int idTransacao) {
        List<Map<String, Object>> arr = new DefaultBLL("transacao_detalhe").getAll("transacao_id", idTransacao);
        if (arr == null) {
            return null;
        }
        List<Produto> produtos = new ArrayList<Produto>();
        for (Map<String, Object> map : arr) {
            Produto produto = ProdutoBLL.transformProduto(map);
            produtos.add(produto);

        }
        return produtos.toArray(new Produto[0]);
    }

    public static double getValorTotal(int idTransacao) {
        if (!existe(idTransacao)) {
            return 0;
        }
        return (Double) new DefaultBLL("transacao").getOne("valor_total", "id", idTransacao);
    }

    public static Pessoa getFuncionario(int idTransacao) {
        if (!existe(idTransacao)) {
            return null;
        }
        int idFunc = (int) new DefaultBLL("transacao").getOne("funcionario_id", "id", idTransacao);
        return UsersBLL.getUser(idFunc);
    }

    public static Pessoa getCliente(int idTransacao) {
        if (!existe(idTransacao)) {
            return null;
        }
        int idFunc = (int) new DefaultBLL("transacao").getOne("cliente_id", "id", idTransacao);
        return UsersBLL.getUser(idFunc);
    }

    public static int getNumProdutos(int idTransacao) {
        var ob = getProdutos(idTransacao);
        if (ob == null) {
            return 0;
        }
        return ob.length;
    }

    public static Timestamp getTransacaoData(int idTrasacao) {
        Object ob = new DefaultBLL("transacao").getOne("created_at", "id", idTrasacao);
        return (ob instanceof Timestamp) ? (Timestamp) ob : null;
    }

    //insert done

    public static int insertTransacao(Produto[] produtos, int idUser, int idFunc) {

        if (!UsersBLL.existe(idUser) || !UsersBLL.existe(idFunc)) {
            feedbackErro("Usuarios Nao Existem!");
            return -1; //TEEM DE EXESTIR!!
        }

        if (UsersBLL.getTipoUser(idFunc) == UsuarioEnum.ALUNO) {
            feedbackErro("Aluno nao pode Vender!");

            return -1; //ALUNO NAO PODE VENDER!!!
        }

        double valorTotal = 0;

        for (Produto produto : produtos) {
            ArredondarController.roundToTwoDecimalPlaces(valorTotal += produto.getPreco());
        }

        double saldo = ArredondarController.roundToTwoDecimalPlaces(CartaoBLL.getSaldo(UsersBLL.getNFCUser(idUser)));

        if (saldo < valorTotal) {
            feedbackErro("Saldo Insufeciente!");

            return -1;
        }
        saldo -= valorTotal;
        saldo = ArredondarController.roundToTwoDecimalPlaces(saldo);

        Map<String, Object> transacaoCol = new HashMap<>();
        transacaoCol.put("cliente_id", idUser);
        transacaoCol.put("funcionario_id", idFunc);
        transacaoCol.put("valor_total", valorTotal);
        int idTransacaoCriada = new DefaultBLL("transacao").insertAndGetId(transacaoCol);

        if (idTransacaoCriada == 0) {
            feedbackErro("Erro Interno");
            return -1;
        }

        for (Produto produto : produtos) {
            Map<String, Object> transacaoDadosCol = new HashMap<>();
            transacaoDadosCol.put("produto_id", produto.getIdProduto());
            transacaoDadosCol.put("transacao_id", idTransacaoCriada);
            transacaoDadosCol.put("valorpago", produto.getPreco());
            new DefaultBLL("transacao_detalhe").insert(transacaoDadosCol);
        }

        CartaoBLL.setSaldo(UsersBLL.getNFCUser(idUser), saldo);
        return 0;
    }

    public static void insertRefeicaoTrasacao(Refeicao refeicao, int idUser, int idFunc) {

        Produto[] produtos = refeicao.getProdutos();
        if (produtos == null) {
            return;
        }
        insertTransacao(produtos, idUser, idFunc);

    }

    public static boolean isRefeicaoUm(int idTransacaoDetalhe) {
        if (!existeDetalhe(idTransacaoDetalhe)) {
            return false;
        }

        Object idProduto = new DefaultBLL("transacao_detalhe").getOne("produto_id", "id", idTransacaoDetalhe);
        return ProdutoBLL.isRefeicao((int) idProduto);
    }

    public static boolean isRefeicao(int idTrasacao) {
        if (!existe(idTrasacao)) {
            return false;
        }
        Produto[] produtos = getProdutos(idTrasacao);

        if (produtos == null) {
            return false;
        }
        for (Produto produto : produtos) {
            if (!produto.isRefeicao()) {
                return false;
            }
        }
        return true;
    }




    public static Transacao getTransacao(int idTransacao) {
        if (!existe(idTransacao)) {
            //feedback
            return null;
        }
        DefaultBLL bll = new DefaultBLL("transacao");
        var x = bll.getAllinOne("id", idTransacao);
        return transfromTrasacao(x);

    }

    public static List<Transacao> getAllTransacoes(int idUser) {
        if (!UsersBLL.existe(idUser)) {
            //feedback
            return null;
        }

        DefaultBLL bll = new DefaultBLL("transacao");
        var x = bll.getAll("cliente_id",idUser);
        if (x == null) {
            return null;
        }

        ArrayList<Transacao> transacoes = new ArrayList<>();
        for (var transac : x) {
            transacoes.add(transfromTrasacao(transac));
        }

        return transacoes;
    }



    private static Transacao transfromTrasacao(Map<String, Object> row) {

        Transacao transacao = new Transacao(0);
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            switch (entry.getKey()) {
                case "id" -> transacao.setIdTransacao((int)entry.getValue());
                case "cliente_id" -> transacao.setCliente(UsersBLL.getUser((int)entry.getValue()));
                case "funcionario_id" -> transacao.setFuncionario(UsersBLL.getUser((int)entry.getValue()));
                case "valor_total" -> transacao.setValorpago((double)entry.getValue());
                default -> { }
            }
        }

        DefaultBLL bll = new DefaultBLL("transacao_detalhe");
        var x = bll.getList("produto_id","transacao_id",transacao.getIdTransacao());
        //returns a list of ids of produtos
        assert x != null;

        List<Integer> produtoIds = x.stream()
                .map(obj -> (int) obj)
                .toList();

        for (var prod : produtoIds) {
            transacao.addProduct(ProdutoBLL.getProduto(prod));
        }
        return transacao;
    }
}
