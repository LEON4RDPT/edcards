package com.edcards.edcards.ClassControllers;

public enum UsuarioEnum {
    ALUNO,
    FUNCIONARIO,
    ADMINISTRADOR;

    public int toDbValue() {return this.ordinal();}
    public static UsuarioEnum fromDbValue(int value) {
        return UsuarioEnum.values()[value];
    }
}
