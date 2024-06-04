package com.edcards.edcards.Programa.Controllers.Enums;

public enum IvaEnum {
    REFEICOES(ProdutoEnum.REFEICOES, 0.13),
    TAXA(ProdutoEnum.TAXA, 0),
    FOTOCOPIAS(ProdutoEnum.FOTOCOPIAS, 0.06),
    DOCES(ProdutoEnum.DOCES, 0.13),
    SALGADOS(ProdutoEnum.SALGADOS, 0.13),
    BEBIDAS(ProdutoEnum.BEBIDAS, 0.23),
    FOLHASTESTE(ProdutoEnum.FOLHASTESTE, 0.06),
    SANDES(ProdutoEnum.SANDES, 0.13),
    PAPELARIA(ProdutoEnum.PAPELARIA, 0.13),
    BAGUETES(ProdutoEnum.BAGUETES, 0.13),
    BOLOS(ProdutoEnum.BOLOS, 0.13),
    CAFETARIA(ProdutoEnum.CAFETARIA, 0.13),
    AGUAS(ProdutoEnum.AGUAS, 0.13 );

    private final ProdutoEnum produtoEnum;
    private final double iva;

    IvaEnum(ProdutoEnum produtoEnum, double iva) {
        this.produtoEnum = produtoEnum;
        this.iva = iva;
    }

    public ProdutoEnum getProdutoEnum() {
        return produtoEnum;
    }

    public double getIVA() {
        return iva;
    }
    public static double getIva(ProdutoEnum produtoEnum) {
        for (IvaEnum produtoVatEnum : values()) {
            if (produtoVatEnum.getProdutoEnum() == produtoEnum) {
                return produtoVatEnum.getIVA();
            }
        }
        throw new IllegalArgumentException("Unknown ProdutoEnum: " + produtoEnum);
    }

}