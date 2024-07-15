package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class CartaoBLL {

    public static void setEntrouSaiu(String nfc, boolean es) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.setOne("Entrou",es,"codigo",nfc);
    }

    public static boolean getEntSaiu(String nfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        var e_s = bll.getOne("entrou","codigo",nfc);
        if (e_s == null) {
            return false;
        }
        return (boolean) e_s;
    }


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

    public static void setPin(String nfc, int pin) {
        if (!existenteNFC(nfc)) {
            //feedback
            return;
        }

        if (String.valueOf(pin).length() == 6) {
            DefaultBLL bll = new DefaultBLL("cartao");
            bll.setOne("pin", pin, "codigo", nfc);
            FeedBackController.feedbackConf("Pin alterado com sucesso!");

        } else {
            //feedback
        }



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

    public static void setNewCard(String nfc, int pin, Double saldo ,boolean entrou, LocalDateTime uvp){
        if (!existenteNFC(nfc)) {
            FeedBackController.feedbackErro(String.valueOf(ErrorEnum.err2));
            return;
        }
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.updateRow("pin", pin, "codigo", nfc);
        bll.updateRow("saldo", saldo, "codigo", nfc);
        bll.updateRow("entrou", entrou, "codigo", nfc);
        bll.updateRow("ultima_vez_passou", uvp, "codigo", nfc);
    }

    public static void setCodigo(String nfc, String novoNfc) {
        DefaultBLL bll = new DefaultBLL("cartao");
        bll.setOne("codigo", novoNfc, "codigo", nfc);
    }



    public static boolean existenteNFC(String nfc) {
        return new DefaultBLL("cartao").hasRows("codigo", nfc);
    }

    public static int getIdUserByNFC(String nfc) {
        if (!existenteNFC(nfc)) {
            return 0;
        }
        return (int) new DefaultBLL("usuario").getOne("id", "cartao_id", nfc);
    }

    public static int getNumUserByNFC(String nfc) {
        if (!existenteNFC(nfc)) {
            return 0;
        }
        return (int) new DefaultBLL("usuario").getOne("num", "cartao_id", nfc);
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
