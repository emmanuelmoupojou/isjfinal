package org.isj.interfaces.main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.isj.interfaces.main.Appli;
import org.isj.traitementmetier.Isj;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.isj.traitementmetier.facade.AbstractFacade.utilisateurCourant;

public class SeConnecter implements Initializable {
    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button connexion;

    private Stage dialogStage;


    public void setDialogStage(Stage dialogStage) {

        this.dialogStage = dialogStage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public boolean isInputValid() {
        String errorMessage = "";

        if (login.getText() == null || login.getText().length() == 0) {
            errorMessage += "Login non valide!\n";
        }
        if (password.getText() == null || password.getText().length() == 0) {
            errorMessage += "Mot de passe non valide!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            return false;
        }
    }


    @FXML
    public void handleOubli() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("view/recuperationMotDePasse1.fxml"));
        AnchorPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Récupérer le mot de passe");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        dialogStage.show();
    }


    @FXML
    public void handleSeConnecter(){
        try{
            String l;
            String p;
            if(isInputValid()){
                l = login.getText();
                p = password.getText();
                //System.out.println(l);
                //System.out.println(p);
                utilisateurCourant = new Isj().authentification(l, p);

                if(utilisateurCourant != null){
                    if(utilisateurCourant.getLibelle()!="mot de passe incorrect") {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Appli.class.getResource("view/ajoutNouveauCandidat.fxml"));
                        BorderPane page = loader.load();
                        Stage dialogStage = new Stage();
                        dialogStage.setTitle("Liste des candidats");
                        Scene scene = new Scene(page);
                        dialogStage.setScene(scene);
                        dialogStage.show();
                    }else{

                    }
                }else {
                    //System.out.println("Informations fausses");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Appli.class.getResource("view/seConnecter.fxml"));
                    AnchorPane page = loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("seConnecter");
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);
                    dialogStage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
