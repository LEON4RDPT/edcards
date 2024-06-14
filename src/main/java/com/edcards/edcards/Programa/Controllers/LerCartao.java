package com.edcards.edcards.Programa.Controllers;

import javax.smartcardio.*;
import java.util.concurrent.TimeUnit;

public class LerCartao {

    public static String lerIDCartao() throws CardException, InterruptedException {
        TerminalFactory factory = TerminalFactory.getDefault();
        CardTerminal terminal = factory.terminals().list().get(0);

        while (true) {
            try {
                if (terminal.isCardPresent()) {
                    Card card = terminal.connect("*");
                    CardChannel channel = card.getBasicChannel();

                    CommandAPDU commandAPDU = new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0xCA, 0x00, 0x00, 0x00});
                    ResponseAPDU responseAPDU = channel.transmit(commandAPDU);

                    byte[] responseData = responseAPDU.getBytes();
                    if (responseAPDU.getSW() != 0x9000) {
                        System.out.println("Não foi possível ler o ID do cartão. Tente novamente.");
                        card.disconnect(false);
                        TimeUnit.SECONDS.sleep(2); // Espera 2 segundos antes de tentar novamente
                        continue;
                    }

                    byte[] uid = new byte[responseAPDU.getData().length];
                    System.arraycopy(responseData, 0, uid, 0, uid.length);

                    String idCartao = byteArrayToHexString(uid);

                    card.disconnect(false);
                    return idCartao;
                }
            } catch (CardException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }
}
