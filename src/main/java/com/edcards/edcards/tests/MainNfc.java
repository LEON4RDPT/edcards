package com.edcards.edcards.tests;

import javax.smartcardio.CardException;

import static com.edcards.edcards.Programa.Controllers.LerCartao.lerIDCartao;

public class MainNfc {
    public static void main(String[] args) {
        while (true) {

            try {
                var x = lerIDCartao(null);
                if (x != null) {
                    System.out.println(x);
                    return;
                }
            } catch (CardException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
