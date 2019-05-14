package org.isj.interfaces.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.isj.interfaces.main.Appli;
import java.net.URL;
import java.util.ResourceBundle;

public class RecuperationMotDePasse implements Initializable {

    @FXML
    private RadioButton sms;

    @FXML
    private RadioButton email;

    private Stage dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleOkRecuperation1() {
        try{
            if(sms.isSelected()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Appli.class.getResource("../view/recuperationMotDePasseSMS.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Récupération par SMS");
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                dialogStage.show();
            }else if(email.isSelected()){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Appli.class.getResource("../view/recuperationMotDePasseEmail.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Récupération par EMAIL");
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                dialogStage.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    private void handleOkRecuperationSMS() {

    }

    @FXML
    private void handleOkRecuperationEmail() {

    }

    @FXML
    private void handleAnnuler() {
        dialogStage.close();
    }

}
