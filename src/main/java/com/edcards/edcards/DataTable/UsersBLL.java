package com.edcards.edcards.DataTable;

import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import com.edcards.edcards.FormControllers.ModUserController;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.Enums.AseEnum;
import com.edcards.edcards.Programa.Controllers.Enums.ErrorEnum;
import com.edcards.edcards.Programa.Controllers.Enums.UsuarioEnum;
import com.edcards.edcards.Programa.Controllers.FeedBackController;
import com.edcards.edcards.Programa.Controllers.GlobalVAR;
import com.edcards.edcards.Programa.Controllers.ImageController;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.edcards.edcards.Programa.Controllers.GlobalVAR.Dados.pessoaAtual;


public class UsersBLL {
    public static void deleteAluno(int id) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").delete("aluno_id", id);
    }
    public static void updatePinForUser(int userId, int newPin) {
        DefaultBLL bll = new DefaultBLL("users"); // Supondo que "users" seja o nome da sua tabela
        bll.setOne("pin", newPin, "id", userId); // Atualiza o PIN onde o iduser corresponde
    }
    public static void deleteUser(int id) {
        UsuarioEnum tipo = getTipoUser(id);
        if (tipo == null) {
            return;
        }
        if (tipo == UsuarioEnum.ALUNO) {
            deleteAluno(id);
        }
        new DefaultBLL("usuario").delete("id", id);
    }

    //exists done
    public static boolean existe(int id) {
        return new DefaultBLL("usuario").hasRows("id", id);
    }
    public static boolean existeCC(String cc) {
        return new DefaultBLL("usuario").hasRows("cc", cc);
    }
    public static boolean existeN(int num) {
        return new DefaultBLL("usuario").hasRows("num", num);
    }

    public static boolean isAluno(int id) {
        return new DefaultBLL("usuario").hasRows("id", id);
    }


    public static int inserir(String nfc, String nome, Date dataNc, String morada, UsuarioEnum tipo, String cc, byte[] foto, int num) {
        DefaultBLL bll = new DefaultBLL("usuario");

        if (nfc != null && bll.hasRows("cartao_id", nfc)) {
            FeedBackController.feedbackErro("Este cartão já está associado a um usuário");
            return 0;
        }

        if (bll.hasRows("cc", cc)) {
            FeedBackController.feedbackErro("Este número de cartão de cidadão já está associado a um usuário");
            return 0;
        }

        String validationSql = "SELECT COUNT(*) FROM usuario WHERE num = ? AND tipo != ?";

        try (PreparedStatement validationStmt = bll.getConnection().prepareStatement(validationSql)) {
            validationStmt.setInt(1, num);
            validationStmt.setInt(2, UsuarioEnum.ALUNO.toDbValue());

            try (ResultSet rs = validationStmt.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    FeedBackController.feedbackErro("Este número já está em uso por um administrador ou funcionário");
                    return 0;
                }else {
                    FeedBackController.feedbackConf("Utilizador Registado com sucesso! Nome: " + nome);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            FeedBackController.feedbackErro("Ocorreu um erro ao inserir o utilizador no banco de dados.");
            return -2;
        }

        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("cartao_id", nfc);
        columnValues.put("nome", nome);
        columnValues.put("data_nasc", dataNc);
        columnValues.put("morada", morada);
        columnValues.put("tipo", tipo.toDbValue());
        columnValues.put("cc", cc);
        columnValues.put("foto", foto);
        columnValues.put("num", num);

        return bll.insertAndGetId(columnValues);
    }

    public static void inserirAluno(int idAluno, int num_ee, String email, int numTurma, int numUtente, AseEnum ase) {
        if (getTipoUser(idAluno) != UsuarioEnum.ALUNO) {
            return;
        }

        Map<String, Object> dic = new HashMap<>();
        dic.put("aluno_id", idAluno);
        dic.put("ee_num", num_ee);
        dic.put("email", email);
        dic.put("turma_num", numTurma);
        dic.put("utente_num", numUtente);
        new DefaultBLL("dados_aluno").insert(dic);
    }

    public static void setTipoUser(int id, UsuarioEnum tipo) {
        if (!existe(id)) {
            return;
        }
        if (isAluno(id)) {
            deleteAluno(id);
        }
        new DefaultBLL("usuario").setOne("tipo", tipo.toDbValue(), "id", id);
    }

    public static void setHorarioUser(int id, byte[] horario) {
        if (!existe(id)) {
            return;
        }
        new DefaultBLL("usuario").setOne("horario", horario, "id", id);
    }



    public static void setFotoUser(int id, byte[] foto) {
        if (!existe(id)) {
            return;
        }
        new DefaultBLL("usuario").setOne("foto", foto, "id", id);
    }

    public static void setNomeUser(int id, String nome) {
        if (!existe(id)) {
            return;
        }
        new DefaultBLL("usuario").setOne("nome", nome, "id", id);
    }

    public static void setMoradaUser(int id, String morada) {
        if (!existe(id)) {
            return;
        }
        new DefaultBLL("usuario").setOne("morada", morada, "id", id);
    }

    public static void setCCUser(int id, String cc) {
        if (!existe(id)) {
            return;
        }
        if(existeCC(cc)){
            FeedBackController.feedbackErro("Numero de Cartão de Cidadão já existente");
            ModUserController.d =1;
            return;
        }
        ModUserController.d =0;
        new DefaultBLL("usuario").setOne("cc", cc, "id", id);
    }
    public static void setCodigoUser(int id, String novoNfc) {
        DefaultBLL bll = new DefaultBLL("usuario");
        bll.setOne("cartao_id", novoNfc, "id", id);
    }
    public static void setDataNascUser(int id, Date data) {
        if (data == null) {
            return;
        }

        if (!existe(id)) {
            return;
        }

        new DefaultBLL("usuario").setOne("data_nasc", data, "id", id);
    }

    public static void setEE_numAluno(int id, int numEE) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("ee_num", numEE, "aluno_id", id);
    }

    public static void setEmailAluno(int id, String email) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("email", email, "aluno_id", id);
    }

    public static void setTurmaAluno(int id, int turma) {
        if (!isAluno(id)) {
            return;
        }

        new DefaultBLL("dados_aluno").setOne("turma_num", turma, "aluno_id", id);
    }

    public static void setAseAluno(int id, AseEnum ase) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("ase", ase.toDbValue(), "aluno_id", id);
    }
    public static void setNum(int id, int num) throws SQLException {
        if (!isAluno(id)) {
            return;
        }

        DefaultBLL bll = new DefaultBLL("usuario");
        int tipoUsuario = (int) bll.getOne("tipo", "id", id);

        // Bloco try-with-resources para a conexão
        try (Connection conn = bll.getConnection()) {

            // Consulta SQL para verificar se o número já existe em outro aluno
            String validationSql = "SELECT COUNT(*) FROM usuario WHERE num = ? AND tipo = ? AND id != ?";
            try (PreparedStatement validationStmt = conn.prepareStatement(validationSql)) {

                validationStmt.setInt(1, num);
                validationStmt.setInt(2, tipoUsuario); // Verifica apenas alunos
                validationStmt.setInt(3, id); // Exclui o próprio aluno da verificação

                try (ResultSet rs = validationStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        FeedBackController.feedbackErro("Este número já está em uso por outro Utilizador do mesmo tipo.");
                        return;
                    }
                }
            }
            //Segunda validação
            String validationSql1 = "SELECT COUNT(*) FROM usuario WHERE num = ? AND tipo != ?";
            try (PreparedStatement validationStmt = conn.prepareStatement(validationSql1)) {
                validationStmt.setInt(1, num);
                validationStmt.setInt(2, tipoUsuario);

                try (ResultSet rs = validationStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        FeedBackController.feedbackErro("Este número já está em uso por outro usuário de outro tipo.");
                        return;
                    }
                }
            }

            bll.setOneCustom("num", num, "id", id);
        } // A conexão 'conn' é fechada automaticamente aqui
    }


    public static void setNumUtenteAluno(int id, int num) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("utente_num", num, "aluno_id", id);
    }

    //get done

    public static UsuarioEnum getTipoUser(int idUser) {
        if (!existe(idUser)) {
            return null;
        }
        return UsuarioEnum.fromDbValue((int) new DefaultBLL("usuario").getOne("tipo", "id", idUser));
    }
    public static UsuarioEnum getTipoUserNum(int num) {
        if (!existeN(num)) {
            return null;
        }
        return UsuarioEnum.fromDbValue((int) new DefaultBLL("usuario").getOne("tipo", "num", num));
    }

    public static String getCCUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("cc", "id", id);
    }

    public static Date getDataNcUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return (Date) new DefaultBLL("usuario").getOne("data", "id", id);
    }

    public static String getNFCUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("cartao_id", "id", id);
    }
    public static String getNFCUserA(int num) {
        if (!existeN(num)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("cartao_id", "num", num);
    }

    public static String getNomeUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("nome", "id", id);
    }

    public static String getMoradaUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("morada", "id", id);
    }

    public static Image getFotoUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return GlobalVAR.ImageController.byteArrayToImage((byte[]) new DefaultBLL("usuario").getOne("foto", "id", id));
    }

    public static Image getHorarioUser(int id) {
        if (!existe(id)) {
            return null;
        }
        return GlobalVAR.ImageController.byteArrayToImage((byte[]) new DefaultBLL("usuario").getOne("horario", "id", id));
    }

    //gets aluno //done
    public static String getEmailAluno(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (String) new DefaultBLL("dados_aluno").getOne("email", "aluno_id", id);
    }

    public static AseEnum getAseAluno(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return AseEnum.fromDbValue((int) new DefaultBLL("dados_aluno").getOne("ase", "aluno_id", id));
    }

    public static int getNumEEAluno(int id) {
        if (!isAluno(id)) {
            return 0;
        }
        return (int) new DefaultBLL("dados_aluno").getOne("ee_num", "aluno_id", id);
    }

    public static int getTurmaAluno(int id) {
        if (!isAluno(id)) {
            return 0;
        }
        return (int) new DefaultBLL("dados_aluno").getOne("turma_num", "aluno_id", id);
    }

    public static int getUtenteAluno(int id) {
        if (!isAluno(id)) {
            return 0;
        }
        return (int) new DefaultBLL("dados_aluno").getOne("utente_num", "aluno_id", id);
    }

    public static int getIdbyNumAluno(int num){
        if (!existeN(num)) {
            return -1;
        }
        return (int) new DefaultBLL("usuario").getOne("id", "num" ,num);
    }



    public static List<Integer> getNums(int tipo) {
        return new DefaultBLL("usuario").getUsersByTipo(tipo);
    }

    public static Pessoa transformUser(Map<String, Object> row) {
        if (row == null) {
            return null;
        }

        UsuarioEnum tipo = null;

        for (Map.Entry<String, Object> entry : row.entrySet()) {
            if (entry.getKey().equals("tipo")) {
                tipo = UsuarioEnum.fromDbValue((int) entry.getValue());
                break;
            }
        }

        if (tipo == null) {
            return null;
        }

        Pessoa pessoa = switch (tipo) {
            case ALUNO -> new Aluno((int) row.get("id"));
            case FUNCIONARIO -> new Funcionario((int) row.get("id"));
            case ADMINISTRADOR -> new Admin((int) row.get("id"));
        };

        for (Map.Entry<String, Object> entry : row.entrySet()) {
            switch (entry.getKey()) {
                case "cartao_id" -> pessoa.setCartao((String) entry.getValue());
                case "cc" -> pessoa.setCartaoC((String) entry.getValue());
                case "nome" -> pessoa.setNome((String) entry.getValue());
                case "data_nasc" -> pessoa.setDataNasc((Date) entry.getValue());
                case "morada" -> pessoa.setMorada((String) entry.getValue());
                case "horario" ->
                        pessoa.setHorario(GlobalVAR.ImageController.byteArrayToImage((byte[]) entry.getValue()));
                case "foto" -> pessoa.setFoto(GlobalVAR.ImageController.byteArrayToImage((byte[]) entry.getValue()));
                case "num" -> pessoa.setNum((Integer) entry.getValue());
            }
        }

        if (CartaoBLL.existenteNFC(pessoa.getNumCartao())) {
            pessoa.setSaldo(CartaoBLL.getSaldo(pessoa.getNumCartao()));
            pessoa.setUltimaVezEntrado(CartaoBLL.getLastTimePassed(pessoa.getNumCartao()));
            pessoa.setPin(CartaoBLL.getPin(pessoa.getNumCartao()));
        }


        switch (tipo) {
            case ALUNO:
                if (!isAluno(pessoa.getIduser())) {
                    System.err.println("ERRO - USUARIO TIPO ALUNO SEM DADOS ALUNO!!!");
                    return (Aluno) pessoa;
                }
                Aluno aluno = (Aluno) pessoa;
                aluno.setNumTurma(getTurmaAluno(pessoa.getIduser()));
                aluno.setNumEE(getNumEEAluno(pessoa.getIduser()));
                aluno.setAse(getAseAluno(pessoa.getIduser()));
                aluno.setNumUtente(getUtenteAluno(pessoa.getIduser()));
                aluno.setEmailEE(getEmailAluno(pessoa.getIduser()));
                return aluno;

            case FUNCIONARIO, ADMINISTRADOR:
                return pessoa;
        }
        return null;
    }

    public static Pessoa getUser(int idUser) {
        DefaultBLL bll = new DefaultBLL("usuario");

        if (!existe(idUser)) {
            return null;
        }

        return transformUser(bll.getAllinOne("id", idUser));
    }
    public static Pessoa getUserN(int num) {
        DefaultBLL bll = new DefaultBLL("usuario");

        if (!existe(num)) {
            return null;
        }

        return transformUser(bll.getAllinOne("num", num));
    }



    public static List<Pessoa> getUsers(UsuarioEnum tipo) {
        DefaultBLL bll = new DefaultBLL("usuario");

        List<Map<String, Object>> rows = bll.getAll("tipo", tipo.toDbValue());

        if (rows == null) {
            return null;
        }

        List<Pessoa> pessoas = new ArrayList<>();


        for (Map<String, Object> row : rows) {
            pessoas.add(transformUser(row));
        }
        return pessoas;
    }

    public static List<Pessoa> getUsersAll() {
        DefaultBLL bll = new DefaultBLL("usuario");

        List<Map<String, Object>> rows = bll.getAll();

        if (rows == null) {
            return null;
        }

        List<Pessoa> pessoas = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            pessoas.add(transformUser(row));
        }

        return pessoas;
    }

}
