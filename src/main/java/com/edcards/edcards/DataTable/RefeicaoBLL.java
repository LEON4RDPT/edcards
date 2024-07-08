package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DAL;
import com.edcards.edcards.DataTable.Settings.DefaultBLL;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class RefeicaoBLL extends DAL {
    public RefeicaoBLL() { super("refeicao"); }

    public static boolean existeRefeicao(int id) {
        return new DefaultBLL("refeicao").hasRows("id_ref", id);
    }

    public static boolean existeRefeicao(Date data) {
        return new DefaultBLL("refeicao").hasRows("data", data);
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


    public static void marcarRefeicao(int idRefeicao, int idUsuario) {
        if (!existeRefeicao(idRefeicao)) {
            //feedback
            return;
        }

        DefaultBLL bll = new DefaultBLL("refeicao_marcada");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refeicao_id", idRefeicao);
        params.put("usuario_id", idUsuario);
        bll.insert(params);
    }

}
