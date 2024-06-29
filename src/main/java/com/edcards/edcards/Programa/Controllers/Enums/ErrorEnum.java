package com.edcards.edcards.Programa.Controllers.Enums;

public enum ErrorEnum {
    err0 {
        public String toString() {
            return "Sem conexão à Base de Dados";
        }
    },
    err1 {
        public String toString() {
            return "Erro a ler cartão passe mais devagar...";
        }
    },
    err2 {
        public String toString() {
            return "Cartão não existente na base de dados";
        }
    },
    err3 {
        public String toString() {
            return "Utilizador tipo aluno sem dados Aluno";
        }
    },
    err4 {
        public String toString() {
            return "PIN Incorreto";
        }
    },
    err5 {
        public String toString() {
            return "Precisa de Preencher todos os dados";
        }
    },
    err6 {
        public String toString() {
            return "Nenhum dado inserido";
        }
    },
    err7 {
        public String toString() {
            return "Nome exitente na base de dados";
        }
    },
    err8 {
        public String toString() {
            return "Cartão já existente na base de dados";
        }
    },
    err9 {
        public String toString() {
            return "Utilizador sem saldo no cartão";
        }
    },
    err10 {
        public String toString() {
            return "Foto do horário não presente...";
        }
    },
    err11 {
        public String toString() {
            return "Categoria de Produto errada";
        }
    },
    err12 {
        public String toString() {
            return "Utilizador não encontrado";
        }
    },
    err13 {
        public String toString() {
            return "Cartão já existe na BLL";
        }
    },
    err14 {
        public String toString() {
            return "Cartao com user associado";
        }
    },
    err15 {
        public String toString() {
            return "PIN's não Iguais!!!";
        }
    };

    public int toDbValue() {
        return this.ordinal();
    }

    public static ErrorEnum fromDbValue(int value) {
        return ErrorEnum.values()[value];
    }

    public static String[] getStringValues() {
        return ConfEnum.getStringValues();
    }

}