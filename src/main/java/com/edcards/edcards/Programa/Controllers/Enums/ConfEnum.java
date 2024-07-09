package com.edcards.edcards.Programa.Controllers.Enums;

public enum ConfEnum {
    conf0 {
        public String toString() {
            return "Utilizador registado com sucesso";
        }
    },
    conf1 {
        public String toString() {
            return "Utilizador alterado com sucesso";
        }
    },
    conf2 {
        public String toString() {
            return "Utilizador apagado com sucesso";
        }
    },
    conf3 {
        public String toString() {
            return "PIN alterado com sucesso";
        }
    },
    conf4 {
        public String toString() {
            return "Horario adicionado com Sucesso";
        }
    },
    conf5 {
        public String toString() {
            return "Horario alterado com Sucesso";
        }
    },
    conf6{
        public String toString() {
            return "Horario alterado com Sucesso";
        }
    },
    conf7 {
        public String toString() {
            return "Horario Removido com Sucesso";
        }
    },
    conf8 {
        public String toString() {
            return "Cartão do utilizador alterado com Sucesso";
        }
    },
    conf9 {
        public String toString() {
            return "Produto Adicionado com Sucesso";
        }
    },
    conf10 {
        public String toString() {
            return "Produto Alterado com Sucesso";
        }
    },
    conf11 {
        public String toString() {
            return "Produto Removido com Sucesso";
        }
    },
    conf12 {
        public String toString() {
            return "Saldo adicionado com sucesso";
        }
    }, conf13 {
        public String toString() {
            return "Compra realizada e registada com sucesso";
        }
    },
    conf14 {
        public String toString() {
            return "Refeição marcada com sucesso";
        }
    },
    conf15 {
        public String toString() {
            return "Refeição desmarcada com sucesso";
        }
    },
    conf16 {
        public String toString() {
            return "PIN's Iguais. Pin alterado com sucesso";
        }
    };


    public int toDbValue() {
        return this.ordinal();
    }

    public static ErrorEnum fromDbValue(int value) {
        return ErrorEnum.values()[value];
    }

    public static String[] getStringValues() {
        ErrorEnum[] values = ErrorEnum.values();
        String[] stringValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            stringValues[i] = values[i].toString();
        }
        return stringValues;
    }
}
