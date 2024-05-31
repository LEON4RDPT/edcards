package com.edcards.edcards.DataTable;

import com.edcards.edcards.ClassControllers.AseEnum;
import com.edcards.edcards.ClassControllers.GlobalVAR;
import com.edcards.edcards.ClassControllers.UsuarioEnum;
import com.edcards.edcards.Programa.Classes.Admin;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Classes.Funcionario;
import com.edcards.edcards.Programa.Classes.Pessoa;
import com.edcards.edcards.Programa.Controllers.ImageController;
import com.edcards.edcards.DataTable.Settings.DefaultBLL;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersBLL {
    private static void deleteAluno(int id) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").delete("id",id);
    }
    public static void deleteUser(int id) {
        UsuarioEnum tipo = getTipoUser(id);
        if (tipo == null) {
            return;
        }
        if (tipo == UsuarioEnum.ALUNO) {
            deleteAluno(id);
        }
        new DefaultBLL("usuario").delete("id",id);
    }

    //exists done
    public static boolean existe(int id) {
        return new DefaultBLL("usuario").hasRows("id",id);
    }
    public static boolean isAluno(int id) {
        return new DefaultBLL("usuario").hasRows("id", id) && new DefaultBLL("dados_aluno").hasRows("id", id);
    }

    //insert done (todo blob to image!!)

    public static void inserir(String nfc, String nome, Date dataNc, String morada, UsuarioEnum tipo, String cc) {
        DefaultBLL bll = new DefaultBLL("usuario");

        if (bll.hasRows("cartao_id",nfc)) {
            return;
        }
        if (bll.hasRows("cc",cc)) {
            return;
        }

        if (!CartaoBLL.existenteNFC(nfc)) {
            return;
        }

        Map<String, Object> columnValues = new HashMap<>();
        columnValues.put("cartao_id", nfc);
        columnValues.put("nome",nome);
        columnValues.put("data_nasc", dataNc);
        columnValues.put("morada", morada);
        columnValues.put("tipo", tipo.toDbValue());
        columnValues.put("cc",cc);
        bll.insert(columnValues);

    }

    public static void inserirAluno(int idAluno, int num_ee, String email, int numTurma, int numUtente, AseEnum ase) {
        if (getTipoUser(idAluno) != UsuarioEnum.ALUNO) {
            return;
        }

        Map<String,Object> dic = new HashMap<>();
        dic.put("aluno_id",idAluno);
        dic.put("ee_num",num_ee);
        dic.put("email",email);
        dic.put("turma_num",numTurma);
        dic.put("utente_num",numUtente);

        new DefaultBLL("dados_aluno").insert(dic);
    }

    //set todo (horario , foto)
    public static void setTipoUser(int id, UsuarioEnum tipo) {
        if (!existe(id)) {
            return;
        }
        if (isAluno(id)) {
            deleteAluno(id);
        }
        new DefaultBLL("usuario").setOne("tipo",tipo.toDbValue(),"id",id);
    }
    public static void setHorarioUser(int id, File horario) {
        if (!existe(id)) {
            return;
        }

        //todo IMG TO BLOB
        try {
            new DefaultBLL("usuario").setOne("horario", ImageController.convertImgToByteArr(horario),"id",id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setFotoUser(int id, File foto) {
        if (!existe(id)) {
            return;
        }

        //todo IMG TO BLOB
        try {
            new DefaultBLL("usuario").setOne("foto",ImageController.convertImgToByteArr(foto),"id",id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setNomeUser(int id, String nome) {
        if (!existe(id)) {
            return;
        }
        new DefaultBLL("usuario").setOne("nome",nome,"id",id);
    }
    public static void setMoradaUser(int id, String morada) {
        if (!existe(id)) {
            return;
        }
        new DefaultBLL("usuario").setOne("morada",morada,"id",id);
    }
    public static void setCCUser(int id, String cc) {
       if (!existe(id)) {
           return;
       }
       new DefaultBLL("usuario").setOne("cc",cc,"id",id);
    }

    public static void setDataNascUser(int id,Date data) {
        if (data == null) {
            return;
        }

        if (!existe(id)) {
            return;
        }

        new DefaultBLL("usuario").setOne("data",data,"id",id);
    }

    public static void setEE_numAluno(int id, int numEE) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("ee_num",numEE,"id",id);
    }
    public static void setEmailAluno(int id, String email) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("email",email,"id",id);
    }
    public static void setTurmaAluno(int id, int turma) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("turma",turma,"id",id);
    }

    public static void setAseAluno(int id,AseEnum ase) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("ase",ase.toDbValue(),"id",id);
    }
    public static void setNumUtenteAluno(int id,int num) {
        if (!isAluno(id)) {
            return;
        }
        new DefaultBLL("dados_aluno").setOne("utente_num",num,"id",id);
    }

    //get done

    public static UsuarioEnum getTipoUser(int idUser) {
        if (!existe(idUser)) {
            return null;
        }
        return UsuarioEnum.fromDbValue( (int) new DefaultBLL("usuario").getOne("tipo","id",idUser));
    }
    public static String getCCUser(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("cc","id",id);
    }
    public static Date getDataNcUser(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (Date) new DefaultBLL("usuario").getOne("data","id",id);
    }
    public static String getNFCUser(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("cartao_id","id",id);
    }
    public static String getNomeUser(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("nome","id",id);
    }
    public static String getMoradaUser(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (String) new DefaultBLL("usuario").getOne("morada","id",id);
    }

    //todo getFoto()
    public static Image getFotoUser(int id) {
        return null;
    }
    //todo getHorario()
    public static Image getHorarioUser(int id) {
        return null;
    }

    //gets aluno //done
    public static String getEmailAluno(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return (String) new DefaultBLL("dados_aluno").getOne("email","id",id);
    }
    public static AseEnum getAseAluno(int id) {
        if (!isAluno(id)) {
            return null;
        }
        return AseEnum.fromDbValue((int) new DefaultBLL("dados_aluno").getOne("ase","id",id));
    }
    public static int getNumEEAluno(int id) {
        if (!isAluno(id)) {
            return 0;
        }
        return (int) new DefaultBLL("dados_aluno").getOne("ee_num","id",id);
    }
    public static int getTurmaAluno(int id) {
        if (!isAluno(id)) {
            return 0;
        }
        return (int) new DefaultBLL("dados_aluno").getOne("turma_num","id",id);
    }
    public static int getUtenteAluno(int id) {
        if (!isAluno(id)) {
            return 0;
        }
        return (int) new DefaultBLL("dados_aluno").getOne("utente_num","id",id);
    }

    //get todo img to blob!!

    public static Pessoa transformUser(Map<String,Object> row) {
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
                case "horario" -> pessoa.setHorario(GlobalVAR.ImageController.byteArrayToImage((byte[]) entry.getValue()));
                case "foto" -> pessoa.setFoto(GlobalVAR.ImageController.byteArrayToImage((byte[]) entry.getValue()));


            }
        }

        if (!CartaoBLL.existenteNFC(pessoa.getNumCartao())) {
            return null;
        }

        pessoa.setSaldo(CartaoBLL.getSaldo(pessoa.getNumCartao()));
        pessoa.setUltimaVezEntrado(CartaoBLL.getLastTimePassed(pessoa.getNumCartao()));
        pessoa.setPin(CartaoBLL.getPin(pessoa.getNumCartao()));

        switch (tipo) {
            case ALUNO:
                if (!isAluno(pessoa.getIduser())) {
                    System.out.println("ERRO - USUARIO TIPO ALUNO SEM DADOS ALUNO!!!");
                    return null;
                }
                Aluno aluno = (Aluno) pessoa;
                aluno.setNumTurma(getTurmaAluno(pessoa.getIduser()));
                aluno.setNumEE(getTurmaAluno(pessoa.getIduser()));
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

        return transformUser(bll.getAllinOne("id",idUser));
    }

    public static List<Pessoa> getUsers(UsuarioEnum tipo) {
        DefaultBLL bll = new DefaultBLL("usuario");

        List<Map<String, Object>> rows = bll.getAll("tipo",tipo.toDbValue());

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
