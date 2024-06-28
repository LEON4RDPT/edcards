package com.edcards.edcards;

import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.LerCartao;

import javax.smartcardio.CardException;

import static com.edcards.edcards.Programa.Controllers.LerCartao.lerIDCartao;

public class MainNfc {
    public static void main(String[] args) {
        while (true) {

            try {
                var x = lerIDCartao();
                if (x != null) {
                    FeedBackController.feedbackErro(x);
                    return;
                }
            } catch (CardException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
