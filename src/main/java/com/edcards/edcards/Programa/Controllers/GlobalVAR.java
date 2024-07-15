package com.edcards.edcards.Programa.Controllers;

import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Pessoa;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class GlobalVAR {
    public static class StageController {
        public static void setStage(String fxml) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(StageController.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = GlobalVAR.Dados.getCurrentStage();

            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            GlobalVAR.Dados.setCurrentStage(stage);
        }
    }

    public static class Dados {

        public static Pessoa pessoaAtual;
        private static Pessoa clientePOS;
        public static Stage currentStage;


        public static void setCurrentStage(Stage currentStage) {
            Dados.currentStage = currentStage;
        }

        public static void confirmExit() {
            if (pessoaAtual == null) {
                return;
            }
            if (pessoaAtual instanceof Admin) {
                if (FeedBackController.feedbackYesNo("Tem a certeza que quer fechar a aplicação?", "Confimação?")) {
                    Platform.exit(); // Exit the application
                    System.exit(0);
                }
            }
        }


        public static Stage getCurrentStage() {
            return currentStage;
        }

        public static void setPessoaAtual(Pessoa pessoa) {
            pessoaAtual = pessoa;
        }

        public static Pessoa getPessoaAtual() {
            return pessoaAtual;
        }



        public static void setCartaoAtual(String cartao) {
        }

        public static Pessoa getClientePOS() {
            return clientePOS;
        }

        public static void setClientePOS(Pessoa clientePOS) {
            Dados.clientePOS = clientePOS;
        }


        //GUI HAHAHAHAHAH
    }


    public static class ImageController {
        public static Image byteArrayToImage(byte[] byteArray) {
            if (byteArray == null) {
                return null;
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

            try {
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


        public static byte[] imageToByteArray(File imageFile)  {
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                byte[] bytes = new byte[(int) imageFile.length()];
                fis.read(bytes);
                return bytes;
            } catch (NullPointerException | IOException ignored) {

            }
            return new byte[0];
        }

    }

}
