package com.edcards.edcards.ClassControllers;

import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class    GlobalVAR {
    public static class Dados {

        private static Pessoa pessoaAtual;
        private static Pessoa ClientePOS;

        public static Stage getCurrentStage() {
            return currentStage;
        }

        public static void setCurrentStage(Stage currentStage) {
            Dados.currentStage = currentStage;
        }

        public static Stage currentStage;

        public static void setPessoaAtual(Pessoa pessoa) {
            pessoaAtual = pessoa;
        }
        public static Pessoa getPessoaAtual() {
            return pessoaAtual;
        }

        public static void setCartaoAtual(String cartao) {
        }

        public static Pessoa getClientePOS() {
            return ClientePOS;
        }

        public static void setClientePOS(Pessoa clientePOS) {
            ClientePOS = clientePOS;
        }
    }




    public static class ImageController {
        public static Image byteArrayToImage(byte[] byteArray){
            if (byteArray == null) {
                return null;
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

            try {
                // Read the byte array into a BufferedImage
                BufferedImage bufferedImage = ImageIO.read(bais);
                if (bufferedImage == null) {
                    throw new IOException("BufferedImage is null after reading byte array");
                }

                // Convert BufferedImage to WritableImage (which is a subclass of Image)
                WritableImage writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
                PixelWriter pixelWriter = writableImage.getPixelWriter();

                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    for (int x = 0; x < bufferedImage.getWidth(); x++) {
                        int argb = bufferedImage.getRGB(x, y);
                        pixelWriter.setArgb(x, y, argb);
                    }
                }

                return writableImage;
            } catch (IOException e) {
                throw new RuntimeException("Error reading image from byte array", e);
            }

        }


        public static byte[] imageToByteArray(BufferedImage image, String format) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, format, baos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return baos.toByteArray();
        }

    }

}
