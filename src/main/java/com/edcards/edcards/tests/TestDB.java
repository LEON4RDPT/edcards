package com.edcards.edcards.tests;

import com.edcards.edcards.DataTable.UsersBLL;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;

public class TestDB {
    public static void main(String[] args) {
        UsersBLL.setTipoUser(2, UsuarioEnum.FUNCIONARIO);

    }
}
