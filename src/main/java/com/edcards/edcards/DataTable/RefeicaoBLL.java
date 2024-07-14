package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DAL;
import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.Programa.Classes.Produto;
import com.edcards.edcards.Programa.Classes.Refeicao;
import com.edcards.edcards.Programa.Controllers.FeedBackController;

import java.sql.Date;
import java.util.*;

public class RefeicaoBLL extends DAL {
    public RefeicaoBLL() { super("refeicao"); }

    public static boolean existeRefeicao(int id) {
        return new DefaultBLL("refeicao").hasRows("id_ref", id);
    }

    public static boolean existeRefeicao(Date data) {
        return new DefaultBLL("refeicao").hasRows("data", data);
    }

//2024-07-10

    public static List<Refeicao> getRefeicao(Date data) {
        if (existeRefeicao(data)) {
            DefaultBLL bll = new DefaultBLL("refeicao");
            var x = bll.getAll("data", data);
            List<Refeicao> refeicoes = new ArrayList<>();
            for (var row : x) {
                Refeicao refeicao = new Refeicao();
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    switch (entry.getKey()) {
                        case "produto_id" -> refeicao.setProduto(ProdutoBLL.getProduto((int) entry.getValue()));
                        case "data" -> refeicao.setDataRefeicao((Date) entry.getValue());
                        case "id_ref" -> refeicao.setIdRefeicao((int) entry.getValue());
                        default -> {
                        }
                    }
                }
                refeicoes.add(refeicao);
            }
            return refeicoes;
        } else {
            return null;
        }

    }



    public static List<Refeicao> getRefeicaoByIdUser(int id) {
        DefaultBLL bll = new DefaultBLL("refeicao_marcada");
        List<Object> x = bll.getAllWhereType("refeicao_id", "usuario_id", id);
        List<Refeicao> refeicoes = new ArrayList<>();
        var list = x.stream()
                .filter(obj -> obj instanceof Integer)
                .map(obj -> (Integer) obj)
                .toList();

        Collections.emptyList();
        for (var ob : list) {
            Refeicao refeicao = getRefeicaoById(ob);
            refeicoes.add(refeicao);
        }
        return refeicoes;

    }

    private static Refeicao getRefeicaoById(int id) {
        DefaultBLL bll = new DefaultBLL("refeicao");
        var x = bll.getAll("id_ref", id);
        Refeicao refeicao = new Refeicao();
        for (Map.Entry<String, Object> entry : x.getFirst().entrySet()) {
            switch (entry.getKey()) {
                case "produto_id" -> refeicao.setProduto(ProdutoBLL.getProduto((int) entry.getValue()));
                case "data" -> refeicao.setDataRefeicao((Date) entry.getValue());
                case "id_ref" -> refeicao.setIdRefeicao((int) entry.getValue());
                default -> {
                }
            }
        }
        return refeicao;


    }


    public static List<Refeicao> getRefeicao() {
        DefaultBLL bll = new DefaultBLL("refeicao");
        var x = bll.getAll();
        List<Refeicao> refeicoes = new ArrayList<>();
        for (var row : x) {
            Refeicao refeicao = new Refeicao();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                switch (entry.getKey()) {
                    case "produto_id" -> refeicao.setProduto(ProdutoBLL.getProduto((int) entry.getValue()));
                    case "data" -> refeicao.setDataRefeicao((Date) entry.getValue());
                    case "id_ref" -> refeicao.setIdRefeicao((int) entry.getValue());
                    default -> {
                    }
                }
            }
            refeicoes.add(refeicao);
        }
        return refeicoes;


    }

    //id_marc != id_ref //IMPORTANT!
    public static boolean marcouRefeicao(int idUser, int idRefMarc) {
        return new DefaultBLL("refeicao_marcada").hasObject("usuario_id",idUser,"id_marc",idRefMarc);

    }


    public static void inserirRefeicao(int produto_id, Date dataRefeicao) {
        DefaultBLL bll = new DefaultBLL("refeicao");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("produto_id", produto_id);
        params.put("data", dataRefeicao);
        bll.insert(params);
    }

    public static boolean marcou(int idRef, int idUsuario) {
        return new DefaultBLL("refeicao_marcada").hasObject("usuario_id",idUsuario,"refeicao_id",idRef);
    }



    public static int marcarRefeicao(int idRefeicao, int idUsuario) {
        if (!existeRefeicao(idRefeicao)) {
            //feedback
            return 0;
        }

        if (!UsersBLL.existe(idUsuario)) {
            //feedback
            return 0;
        }

        var pessoa = UsersBLL.getUser(idUsuario);

        if (pessoa.getSaldo() < 1.46) {
            //feedback
            FeedBackController.feedbackErro("Não tem saldo sufeciente!");
            return 0;
        }

        pessoa.setSaldo(pessoa.getSaldo()-1.46);
        DefaultBLL bll = new DefaultBLL("refeicao_marcada");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refeicao_id", idRefeicao);
        params.put("usuario_id", idUsuario);
        return bll.insertAndGetId(params);
    }

}
