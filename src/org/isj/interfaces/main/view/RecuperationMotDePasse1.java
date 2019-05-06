package org.isj.interfaces.main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.isj.interfaces.main.Appli;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecuperationMotDePasse1 implements Initializable {

    @FXML
    private Button ok;

    @FXML
    private Button Cancel;

    private Stage dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleOk() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("view/recuperationMotDePasse2.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Récupération par SMS");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
        }catch (Exception e){
            e.getMessage();
        }
    }

    @FXML
    private void handleAnnuler() {
        dialogStage.close();
    }

}
