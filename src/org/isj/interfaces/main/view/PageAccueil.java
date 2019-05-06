package org.isj.interfaces.main.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class PageAccueil implements Initializable {
    @FXML
    Button utilisateur;
    @FXML
    Button etudiant;
    @FXML
    Button etat;
    @FXML
    Button messagerie;

    private boolean okClick = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
