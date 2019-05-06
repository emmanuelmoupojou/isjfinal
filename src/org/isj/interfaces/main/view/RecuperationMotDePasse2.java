package org.isj.interfaces.main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.isj.interfaces.main.Appli;

import java.net.URL;
import java.util.ResourceBundle;

public class RecuperationMotDePasse2 implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleOkR2() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("view/recuperationMotDePasse5.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirmation du code");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
        }catch (Exception e){
            e.getMessage();
        }
    }
}
