package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import org.isj.metier.entites.*;
import org.isj.metier.facade.EtudiantFacade;
import org.isj.metier.facade.FiliereFacade;
import org.isj.metier.facade.NiveauFacade;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.isj.interfaces.util.Util.activationDesactivationDetails;

public class EtudiantController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private RadioButton masculin;

    @FXML
    private RadioButton feminin;

    @FXML
    private DatePicker date;

    @FXML
    private TextField telephone;

    @FXML
    private TextField code;

    @FXML
    private TextField codeauthentification;

    @FXML
    private TextField email;

    @FXML
    private TextField ecoleOrigine;

    @FXML
    private TextField regionOrigine;

    @FXML
    private TextField nompere;

    @FXML
    private TextField profpere;

    @FXML
    private TextField telpere;

    @FXML
    private TextField nommere;

    @FXML
    private TextField profmere;

    @FXML
    private TextField telmere;

    @FXML
    private TextField matricule;

    @FXML
    private ComboBox<Niveau> niveau;
    ObservableList<Niveau> listeNiveau = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Filiere> filiere;
    ObservableList<Filiere> listeFiliere = FXCollections.observableArrayList();

    @FXML
    private TableView<Etudiant> table;
    ObservableList<Etudiant> listeEtudiants = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Etudiant, String> nomcolumn;

    @FXML
    private TableColumn<Etudiant, String> prenomcolumn;

    @FXML
    private TableColumn<Etudiant, Long> codecolumn;

    @FXML
    private TableColumn<Etudiant, Date> datecolumn;

    @FXML
    private TableColumn<Etudiant, Personne.Sexe> sexecolumn;

    @FXML
    private TableColumn<Etudiant, Integer> telcolumn;

    @FXML
    private TableColumn<Etudiant, String> emailcolumn;

    @FXML
    private TableColumn<Etudiant, String> matriculecolumn;

    @FXML
    private TableColumn<Etudiant, String> filierecolumn;

    @FXML
    private TableColumn<Etudiant, Long> niveaucolumn;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try{
            listeFilieres();
            listeNiveaux();
            listEtudiants();
            afficheDetail(null, null);
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> afficheDetail(newValue, newValue)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listeFilieres() {
        listeFiliere.addAll(new FiliereFacade().lister());
        filiere.setItems(listeFiliere);
    }

    public void listeNiveaux() {
        listeNiveau.addAll(new NiveauFacade().lister());
        niveau.setItems(listeNiveau);
    }

    public void listEtudiants() throws SQLException {

        if ((Connexion.peutLire(Etudiant.class))){

            filtrer(true);

            table.setItems(listeEtudiants);
            activationDesactivationDetails(gridPane,false);
            nomcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
            prenomcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
            emailcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            telcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTelephone()).asObject());
            sexecolumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSexe()));
            matriculecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricule()));
            //niveaucolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().get));
            //filierecolumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getF));
            codecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            datecolumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateNaissance()));

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Etudiant.class);
            ResultSetMetaData resultSetMetaData1 = new Isj().renvoyerChamp(Candidat.class);
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                try {
                    listAttributs.add(resultSetMetaData.getColumnName(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 1; i <= resultSetMetaData1.getColumnCount(); i++) {
                try {
                    listAttributs.add(resultSetMetaData1.getColumnName(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            attributs.setItems(listAttributs);
        }
    }

    Etudiant etudiantSelectionne = null;
    Candidat candidatSelectionne = null;

    public void afficheDetail(Etudiant etudiant, Candidat candidat) {


        if (Connexion.peutLire(Etudiant.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);

            if (etudiant != null) {
                etudiantSelectionne = etudiant;
                code.setText(String.valueOf(etudiant.getCode()));
                codeauthentification.setText(String.valueOf(etudiant.getCodeAuthentification()));
                matricule.setText(etudiant.getMatricule());
                nom.setText(candidat.getNom());
                prenom.setText(candidat.getPrenom());
                if (candidat.getSexe().equals(Personne.Sexe.FEMININ)) {
                    feminin.setSelected(true);
                    masculin.setSelected(false);
                } else {
                    feminin.setSelected(false);
                    masculin.setSelected(true);
                }
                date.setValue(candidat.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                telephone.setText(Integer.toString(candidat.getTelephone()));
                email.setText(candidat.getEmail());
                ecoleOrigine.setText(candidat.getEcoleOrigine());
                regionOrigine.setText(candidat.getRegionOrigine());
                nompere.setText(candidat.getNomDuPere());
                telpere.setText(Integer.toString(candidat.getTelephoneDuPere()));
                profpere.setText(candidat.getProfessionDuPere());
                nommere.setText(candidat.getNomDeLaMere());
                telmere.setText(Integer.toString(candidat.getTelephoneDeLaMere()));
                profmere.setText(candidat.getProfessionDelaMere());
                matricule.setText(etudiant.getMatricule());
            } else {
                etudiantSelectionne = null;
                code.setText("");
                codeauthentification.setText("");
                matricule.setText("");
                nom.setText("");
                prenom.setText("");
                masculin.setSelected(false);
                date.setValue(null);
                telephone.setText("");
                email.setText("");
                ecoleOrigine.setText("");
                regionOrigine.setText("");
                nompere.setText("");
                telpere.setText("");
                profpere.setText("");
                nommere.setText("");
                telmere.setText("");
                profmere.setText("");
                matricule.setText("");
            }
        }
    }

    @FXML
    public void handleNouveau() {

        if (Connexion.peutEcrire(Etudiant.class)) {
            etudiantSelectionne = null;
            //Réactivation de tous TextField du panneau des détails
            activationDesactivationDetails(gridPane,true);
            code.setText("");
            codeauthentification.setText("");
            nom.setText("");
            prenom.setText("");
            feminin.setSelected(false);
            masculin.setSelected(true);
            date.setValue(null);
            telephone.setText("");
            email.setText("");
            ecoleOrigine.setText("");
            regionOrigine.setText("");
            nompere.setText("");
            telpere.setText("");
            profpere.setText("");
            nommere.setText("");
            telmere.setText("");
            profmere.setText("");
        }
    }

    @FXML
    public void handleModifier() {

        if (etudiantSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        else if (Connexion.peutModifier(Etudiant.class)) {
            activationDesactivationDetails(gridPane,true);
        }
    }

    EtudiantFacade etudiantFacade = new EtudiantFacade();
    @FXML
    public void handleEnregistrer() {

        if (Connexion.peutLire(Etudiant.class) || Connexion.peutModifier(Etudiant.class)) {
            try {
                String matriculeEtudiant, nomEtudiant, prenomEtudiant, regionO, ecoleO, emailEtudiant, telephoneEtudiant, nompereEtudiant, profpereEtudiant, telpereEtudiant, nommereEtudiant, profmereEtudiant, telmereEtudiant;
                nomEtudiant = nom.getText();
                prenomEtudiant = prenom.getText();
                telephoneEtudiant = telephone.getText();
                regionO = regionOrigine.getText();
                ecoleO = ecoleOrigine.getText();
                emailEtudiant= email.getText();
                nompereEtudiant = nompere.getText();
                profpereEtudiant = profpere.getText();
                telpereEtudiant = telpere.getText();
                nommereEtudiant = nommere.getText();
                profmereEtudiant = profmere.getText();
                telmereEtudiant = telmere.getText();
                matriculeEtudiant = matricule.getText();
                Personne.Sexe sexe = masculin.isSelected() ? Personne.Sexe.MASCULIN : Personne.Sexe.FEMININ;
                Date dateNaissance = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

                String resultat;
                if (etudiantSelectionne == null)
                    resultat = etudiantFacade.enregistrer("", "", nomEtudiant, prenomEtudiant, emailEtudiant, Integer.parseInt(telephoneEtudiant), dateNaissance, sexe, Personne.Statut.ACTIVE, nompereEtudiant, profpereEtudiant, Integer.parseInt(telmereEtudiant), Integer.parseInt(telpereEtudiant), nommereEtudiant, profmereEtudiant, regionO, ecoleO, matriculeEtudiant, "", null);
                else
                    resultat = etudiantFacade.modifier(etudiantSelectionne, "", "", nomEtudiant, prenomEtudiant, emailEtudiant, Integer.parseInt(telephoneEtudiant), dateNaissance, sexe, Personne.Statut.ACTIVE, nompereEtudiant, profpereEtudiant, Integer.parseInt(telmereEtudiant), Integer.parseInt(telpereEtudiant), nommereEtudiant, profmereEtudiant, regionO, ecoleO, matriculeEtudiant, "", null);

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

        if (Connexion.peutSupprimer(Etudiant.class)) {
            try {

                if (etudiantSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = etudiantFacade.remove(etudiantSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(etudiantSelectionne);
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

    @FXML
    public void handleRaffraichir() {
        filtrer(true);
    }

    @FXML
    public void handleImprimer() throws IOException {

        if (Connexion.peutLire(Etudiant.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Etudiants");
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

    String requeteFiltrage = "select * from etudiant";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from etudiant";
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
        listeEtudiants.clear();
        listeEtudiants.addAll(etudiantFacade.findAllNative(requeteFiltrage));
    }
}
