package com.edcards.edcards.Programa.Controllers.ColorController;

import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class ColorController {
    public static void setButtonColor(Button button, ProdutoEnum produtoEnum) {
        Color color = ColorProdutoEnum.convertColorFX(ColorProdutoEnum.getColorByEnum(produtoEnum));

        String colorStyle =
                String.format("-fx-background-color: rgba(%d, %d, %d, %f);",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                color.getOpacity());
                button.setStyle(colorStyle);
    }

    public static void setButtonColorBack(Button button) {
        String currentStyle = button.getStyle();
        String newStyle = currentStyle.replace("-fx-background-color:", "");
        button.setStyle(newStyle);
    }
}
