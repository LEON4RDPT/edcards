package com.edcards.edcards.tests;

import com.edcards.edcards.DataTable.CartaoBLL;
import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;

public class TestDB {
    public static void main(String[] args) {
//        CartaoBLL.setNfcDiferente("787F8319","5B11713B");
        CartaoBLL.setNfcDiferente("5B11713B","787F8319");
    }
}
