package com.edcards.edcards.FormControllers;

//import com.edcards.edcards.DataBase.BLL;
import com.edcards.edcards.Programa.Classes.Aluno;
import com.edcards.edcards.Programa.Controllers.LerCartao;
import javafx.fxml.Initializable;

import javax.smartcardio.CardException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ControllerReadCard implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


//    Aluno aluno = new Aluno("5B09593B","leo",0.0,null,true,"adada",2007,"0/0/0000",219892,true,91,true,new Turma(1,1,null,null));
//    Timer timer = new Timer();
//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            LerCartao lerCartao = new LerCartao();
//            String idCartao = "";
//            try {
//                idCartao = lerCartao.returnIDCartao();
//            } catch (CardException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(idCartao);
//
//            if (idCartao != "") {
//                if (idCartao.equals(aluno.getNumCartao())) {
//                    System.out.println(idCartao);
////                    ResultSet dt = BLL.Cartao.queryCartao(idCartao);
//
//                }
//
//            }
//        }
//    };
//    private void load(String numCartao) throws SQLException {
//
//
//
//
//    }
//
//    private void onLoad() {
//        timer.scheduleAtFixedRate(timerTask, 0, 3000);
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        onLoad();
//    }
}
