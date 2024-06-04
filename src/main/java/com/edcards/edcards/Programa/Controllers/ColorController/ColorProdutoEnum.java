package com.edcards.edcards.Programa.Controllers.ColorController;


import com.edcards.edcards.Programa.Controllers.Enums.ProdutoEnum;

import java.awt.*;

public enum ColorProdutoEnum {
    REFEICAO(new ColorProduto(ProdutoEnum.REFEICOES, Color.CYAN)),
    TAXA(new ColorProduto(ProdutoEnum.TAXA, Color.GRAY)),
    FOTOCOPIAS(new ColorProduto(ProdutoEnum.FOTOCOPIAS, Color.GREEN)),
    DOCES(new ColorProduto(ProdutoEnum.DOCES, Color.PINK)),
    SALGADOS(new ColorProduto(ProdutoEnum.SALGADOS, Color.ORANGE)),
    BEBIDAS(new ColorProduto(ProdutoEnum.BEBIDAS, Color.CYAN)),
    FOLHASTESTE(new ColorProduto(ProdutoEnum.FOLHASTESTE, Color.YELLOW)),
    SANDES(new ColorProduto(ProdutoEnum.SANDES, Color.MAGENTA)),
    PAPELARIA(new ColorProduto(ProdutoEnum.PAPELARIA, Color.GRAY)),
    BAGUETES(new ColorProduto(ProdutoEnum.BAGUETES, Color.yellow)),
    BOLOS(new ColorProduto(ProdutoEnum.BOLOS, Color.gray)),
    CAFETARIA(new ColorProduto(ProdutoEnum.CAFETARIA, Color.BLACK   )),
    AGUAS(new ColorProduto(ProdutoEnum.AGUAS,Color.blue));

    private final ColorProduto colorProduto;
    ColorProdutoEnum(ColorProduto colorProduto) {
        this.colorProduto = colorProduto;
    }
    public ColorProduto getColorProduto() {
        return colorProduto;
    }
    public javafx.scene.paint.Color convertColorFX() {
        int r = this.getColor().getRed();
        int g = this.getColor().getGreen();
        int b = this.getColor().getBlue();
        int a = this.getColor().getAlpha();
        return javafx.scene.paint.Color.rgb(r, g, b, a / 255.0);
    }
    public static javafx.scene.paint.Color convertColorFX(ColorProdutoEnum colorProduto) {
        int r = colorProduto.getColor().getRed();
        int g = colorProduto.getColor().getGreen();
        int b = colorProduto.getColor().getBlue();
        int a = colorProduto.getColor().getAlpha();
        return javafx.scene.paint.Color.rgb(r, g, b, a / 255.0);
    }
    public static javafx.scene.paint.Color convertColorFX(Color colorProduto) {
        int r = colorProduto.getRed();
        int g = colorProduto.getGreen();
        int b = colorProduto.getBlue();
        int a = colorProduto.getAlpha();
        return javafx.scene.paint.Color.rgb(r, g, b, a / 255.0);
    }

    public Color getColor() {
        return colorProduto.color();
    }

    public static Color getColorByEnum(ProdutoEnum produtoEnum) {
        if (produtoEnum == null) {
            return null;
        }

        var x = ColorProdutoEnum.values();
        for (var prodEnum : x) {
            if (prodEnum.getColorProduto().produtoEnum().equals(produtoEnum)) {
                return prodEnum.getColor();
            }
        }
        return null;


    }
}