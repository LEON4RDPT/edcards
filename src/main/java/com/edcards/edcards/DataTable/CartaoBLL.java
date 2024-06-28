package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class CartaoBLL {
    public static String[] getAllCards() {
        DefaultBLL bll = new DefaultBLL("cartao");
        var x = bll.getAll("codigo", "INNER JOIN usuario ON cartao.codigo = usuario.cartao_id"); //check if exists on the 2 tables!
        if (x == null) {
            return null;
        }
        return x.toArray(new String[0]);
    }

    public static void inserirCartao(String nfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        if (existenteNFC(nfc)) {
            return;
        }
        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("codigo", nfc);
        columnValues.put("saldo", 0);
        columnValues.put("ultima_vez_passou", null);
        columnValues.put("pin", 123456);
        bll.insert(columnValues);

    }

    public static void inserirCartao(String nfc, int pin) {
        DefaultBLL bll = new DefaultBLL("cartao");
        if (existenteNFC(nfc)) {
            return;
        }
        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("codigo", nfc);
        columnValues.put("saldo", 0);
        columnValues.put("ultima_vez_passou", null);
        columnValues.put("pin", pin);
        bll.insert(columnValues);
    }

    public static void setNfcDiferente(String nfcAntigo, String nfcNovo) {
        DefaultBLL bll = new DefaultBLL("cartao");
        if (existenteNFC(nfcNovo)) {
            return;
        }

        if (!existenteNFC(nfcAntigo)) {
            //o antigo tem de existir para ser trocado!
            return;
        }

        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("codigo", nfcNovo);
        columnValues.put("saldo", getSaldo(nfcAntigo));
        columnValues.put("ultima_vez_passou", getLastTimePassed(nfcAntigo));
        columnValues.put("pin", getPin(nfcAntigo));
        bll.insert(columnValues);

        new DefaultBLL("usuario").setOne("cartao_id", nfcNovo, "cartao_id", nfcAntigo);
        bll.delete("codigo", nfcAntigo);
    }


    public static void deleteALL() {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.deleteALL();
    }

    public static void deleteOne(String nfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.delete("codigo", nfc);
    }

    public static double getSaldo(String nfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        var value = bll.getOne("saldo", "codigo", nfc);
        if (value == null) {
            return 0;
        }
        return (double) value;
    }

    public static void setSaldo(String nfc, double saldo) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.setOne("saldo", saldo, "codigo", nfc);
    }

    public static int getPin(String nfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        Object ob = bll.getOne("pin", "codigo", nfc);
        if (ob == null) {
            return 0;
        }
        return (int) ob;
    }

    public static void setLastTimePassed(String nfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.setOne("ultima_vez_passou", LocalDateTime.now(), "codigo", nfc);
    }

    public static void setLastTimePassed(String nfc, LocalDateTime datetime) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.setOne("ultima_vez_passou", datetime, "codigo", nfc);
    }

    public static LocalDateTime getLastTimePassed(String nfc) {
        var ob = new DefaultBLL("cartao").getOne("ultima_vez_passou", "codigo", nfc);
        if (ob == null) {
            return null;
        }
        return (LocalDateTime) ob;
    }

    public static void setCodigo(String nfc, String novoNfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.setOne("codigo", novoNfc, "codigo", nfc);
    }

    //todo entradas e saidas


    public static boolean existenteNFC(String nfc) {
        return new DefaultBLL("cartao").hasRows("codigo", nfc);
    }

    public static int getIdUserByNFC(String nfc) {
        if (!existenteNFC(nfc)) {
            return 0;
        }
        return (int) new DefaultBLL("usuario").getOne("id", "cartao_id", nfc);
    }

    public static Pessoa getUserByNFC(String nfc) {
        if (!existenteNFC(nfc)) {
            return null;
        }
        int id = (int) new DefaultBLL("usuario").getOne("id", "cartao_id", nfc);
        return UsersBLL.getUser(id);
    }


    public static boolean getPinValid(int PIN, int idPessoa) {
        if (!UsersBLL.existe(idPessoa)) {
            return false;
        }
        String nfc = UsersBLL.getNFCUser(idPessoa);
        return new DefaultBLL("cartao").hasObject("pin", PIN, "codigo", nfc);
    }

    public static boolean getPinValid(int PIN, String nfc) {
        if (!existenteNFC(nfc)) {
            return false;
        }
        return new DefaultBLL("cartao").hasObject("pin", PIN, "codigo", nfc);
    }


}
