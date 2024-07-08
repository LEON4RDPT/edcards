package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DAL;
import com.edcards.edcards.DataTable.Settings.DefaultBLL;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class RefeicaoBLL extends DAL {
    public RefeicaoBLL() { super("refeicao"); }

    public static void inserirRefeicao(int produto_id, Date dataRefeicao) {
        DefaultBLL bll = new DefaultBLL("refeicao");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("produto_id", produto_id);
        params.put("data", dataRefeicao);
        bll.insert(params);
    }

}
