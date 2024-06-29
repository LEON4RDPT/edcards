package com.edcards.edcards.Programa.Controllers.Enums;

public enum AseEnum {
    ASE_A,
    ASE_B,
    ASE_C;

    public int toDbValue() {
        return this.ordinal();
    }

    public static AseEnum fromDbValue(int value) {
        return AseEnum.values()[value];
    }
}
