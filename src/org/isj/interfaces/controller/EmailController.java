package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.isj.gestionutilisateurs.Connexion;
import org.isj.interfaces.main.Appli;
import org.isj.metier.Isj;
import org.isj.metier.entites.Email;
import org.isj.metier.entites.Sms;
import org.isj.metier.facade.EmailFacade;
import org.isj.metier.facade.SmsFacade;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.isj.interfaces.util.Util.activationDesactivationDetails;

public class EmailController implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private TextField destinataire;

    @FXML
    private TextField emetteur;

    @FXML
    private TextArea contenu;

    @FXML
    private TextField objet;

    @FXML
    private TableView<Email> table;
    ObservableList<Email> listeEmail = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Email, Long> codecolumn;

    @FXML
    private TableColumn<Email, String> destinatairecolumn;

    @FXML
    private TableColumn<Email, String> emetteurcolumn;

    @FXML
    private TableColumn<Email, String> objetcolumn;

    @FXML
    private ComboBox<String> attributs;
    ObservableList<String> listAttributs = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> operateurs;
    ObservableList<String> listOperateurs = FXCollections.observableArrayList("<", ">", "<=", ">=", "=", "!=", "like", "in");

    @FXML
    private TextField valeurs;

    @FXML
    private ListView<String> listeFiltrage;

    @FXML
    private GridPane gridPane;

    public EmailController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try{
            listEmail();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> afficheDetail(newValue)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void listEmail() throws SQLException {

        if (Connexion.peutLire(Email.class)) {

            filtrer(true);

            table.setItems(listeEmail);
            activationDesactivationDetails(gridPane,false);
            codecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());
            destinatairecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestinataire()));
            emetteurcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmetteur()));
            objetcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObjet()));




            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Email.class);
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                try {
                    listAttributs.add(resultSetMetaData.getColumnName(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            attributs.setItems(listAttributs);
        }
    }
    Email emailSelectionne = null;

    public void afficheDetail(Email email) {


        if (Connexion.peutLire(Email.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);

            if (email != null) {
                emailSelectionne = email;
                code.setText(String.valueOf(email.getCode()));

                destinataire.setText(email.getDestinataire());
                emetteur.setText(email.getEmetteur());
                contenu.setText(email.getContenu());
                objet.setText(email.getObjet());

            } else {
                emailSelectionne = null;
                code.setText("");
                destinataire.setText("");
                emetteur.setText("");
                contenu.setText("");
                objet.setText("");

            }

        }
    }
    @FXML
    public void handleNouveau() {

        if (Connexion.peutEcrire(Email.class)) {
            emailSelectionne = null;
            //Raactivation de tous TextField du panneau des détails
            activationDesactivationDetails(gridPane,true);
            code.setText("");
            destinataire.setText("");
            emetteur.setText("");
            contenu.setText("");
            objet.setText("");

        }
    }
    @FXML
    public void handleModifier() {

        if (emailSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        else if (Connexion.peutModifier(Email.class)) {
            activationDesactivationDetails(gridPane,true);
        }
    }

    EmailFacade emailFacade = new EmailFacade();


    @FXML
    public void handleEnregistrer() {

        if (Connexion.peutLire(Email.class) || Connexion.peutModifier(Email.class)) {
            try {
                String destinaireEmail, emetteurEmail, contenuEmail, objetEmail ;
                destinaireEmail= destinataire.getText();
                emetteurEmail= emetteur.getText();
                contenuEmail= contenu.getText();
                objetEmail= objet.getText();

                String resultat;
                if (emailSelectionne == null)
                    resultat = emailFacade.enregistrer("","",destinaireEmail, emetteurEmail, contenuEmail,  objetEmail );
                else
                    resultat = emailFacade.modifier(emailSelectionne,"","",contenuEmail,destinaireEmail,emetteurEmail,objetEmail,null);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(Appli.getPrimaryStage);
                alert.setTitle("ISJ");
                alert.setHeaderText("Resultat de l'opération");
                alert.setContentText(resultat.toUpperCase() + " !");
                alert.show();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(Appli.getPrimaryStage);
                alert.setTitle("ISJ");
                alert.setHeaderText("Erreur lors l'opération");
                alert.setContentText(e.getMessage() + " !");
                alert.show();
            }

        }
    }

    @FXML
    public void handleSupprimer() {

        if (Connexion.peutSupprimer(Email.class)) {
            try {

                if (emailSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = emailFacade.remove(emailSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(emailSelectionne);
                        else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.initOwner(Appli.getPrimaryStage);
                            alert.setTitle("ISJ");
                            alert.setHeaderText("La donnée ne peut être supprimée.");
                            alert.setContentText("Il est possible qu'une contrainte d'intégrité empêche la suppression de la donnée.");
                            alert.show();
                        }
                    } else {
                        alert.close();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(Appli.getPrimaryStage);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Aucune donnée sélectionnée.");
                    alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
                    alert.show();
                }

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
    private boolean raffraichir = false;

    String requeteFiltrage = "select * from email";

    @FXML
    public void handleRaffraichir() {
        filtrer(true);
    }


    @FXML
    public void handleImprimer() throws IOException {

        if (Connexion.peutLire(Email.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Candidats");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            selectionChampsReport.setSousTitre("Imprime à " + format.format(new Date()));
            selectionChampsReport.setOrientation(Page.Page_A4_Landscape());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sélection des choix");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
        }
    }

    @FXML
    public void handleAjouterCritere() {
        try {
            String attribut, operateur, valeur;
            attribut = attributs.getValue();
            operateur = operateurs.getValue();
            valeur = valeurs.getText();
            String critere = attribut + " " + operateur + " " + valeur;
            listeFiltrage.getItems().add(critere);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSupprimerCritere() {
        try {
            int selectedIndex = listeFiltrage.getSelectionModel().getSelectedIndex();
            listeFiltrage.getItems().remove(selectedIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from email";
        if (raffraichir == false) {
            String listeCriteres = "";
            for (int i = 0; i < listeFiltrage.getItems().size(); i++) {
                if (requeteFiltrage.contains(" where "))
                    listeCriteres = listeCriteres + " and " + listeFiltrage.getItems().get(i);
                else
                    listeCriteres = " where " + listeFiltrage.getItems().get(i);
            }
            requeteFiltrage = requeteFiltrage + listeCriteres;
        }
        listeEmail.clear();
        listeEmail.addAll(emailFacade.findAllNative(requeteFiltrage));
    }
}







