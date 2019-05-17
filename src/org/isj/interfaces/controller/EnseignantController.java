package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.isj.gestionutilisateurs.Connexion;
import org.isj.interfaces.main.Appli;
import org.isj.metier.Isj;
import org.isj.metier.entites.Candidat;
import org.isj.metier.entites.Classe;
import org.isj.metier.entites.Enseignant;
import org.isj.metier.entites.Personne;
import org.isj.metier.facade.CandidatFacade;
import org.isj.metier.facade.ClasseFacade;
import org.isj.metier.entites.Enseignement;
import org.isj.metier.facade.EnseignantFacade;
import org.isj.metier.facade.EnseignementFacade;

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

public class EnseignantController implements Initializable {
    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField email;

    @FXML
    private TextField qualification;

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

    /*@FXML
    private ComboBox<Enseignement> enseignement;
    ObservableList<org.isj.metier.entites.Enseignement> listeEnseignements = FXCollections.observableArrayList();*/

    @FXML
    private TableView<Enseignant> table;
    ObservableList<Enseignant> listeEnseignants = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Enseignant, String> nomcolumn;

    @FXML
    private TableColumn<Enseignant, String> prenomcolumn;

    @FXML
    private TableColumn<Enseignant, Long> codecolumn;

    @FXML
    private TableColumn<Enseignant, String> datecolumn;

    @FXML
    private TableColumn<Enseignant, String> sexecolumn;

    @FXML
    private TableColumn<Enseignant, Integer> telcolumn;

    @FXML
    private TableColumn<Enseignant, String> emailcolumn;

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

    public EnseignantController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try {
            listEnseignant();
            afficheDetail(null);
          //  listeEnseignements();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> afficheDetail(newValue)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //AutoCompleteComboBoxListener<Classe> classeAutocomplete;

    /**
     * Fonction permettant de lister les différents enseignements que l'on peut affecter à un enseignant
     */
    /*public void listeEnseignements() {
        listeEnseignements.addAll(new EnseignementFacade().lister());
        enseignement.setItems(listeEnseignements);
        //classeAutocomplete = new AutoCompleteComboBoxListener<Classe>(classe);
    }*/

    /**
     * Fonction permettant de lister les différents enseignants dans un tableau
     *
     * @throws SQLException
     */
    public void listEnseignant() throws SQLException {

        if (Connexion.peutLire(Enseignant.class)) {

            filtrer(true);

            table.setItems(listeEnseignants);
            activationDesactivationDetails(gridPane,false);
            nomcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
            prenomcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
            emailcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            telcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTelephone()).asObject());
            sexecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexe().toString()));
            codecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            datecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getDateNaissance())));

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Enseignant.class);
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

    /**
     * Fonction permettant d'afficher les détails d'un enseignant
     *
     * @param enseignant variable de type EnseignantController
     */

    Enseignant enseignantSelectionne = null;

    public void afficheDetail(Enseignant enseignant) {


        if (Connexion.peutLire(Enseignant.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);

            if (enseignant != null) {
                enseignantSelectionne = enseignant;
                code.setText(String.valueOf(enseignant.getCode()));

                nom.setText(enseignant.getNom());
                prenom.setText(enseignant.getPrenom());
                if (enseignant.getSexe().equals(Personne.Sexe.FEMININ)) {
                    feminin.setSelected(true);
                    masculin.setSelected(false);
                } else {
                    feminin.setSelected(false);
                    masculin.setSelected(true);
                }
                date.setValue(enseignant.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                telephone.setText(Integer.toString(enseignant.getTelephone()));
                email.setText(enseignant.getEmail());
                qualification.setText(enseignant.getQualification());
                //enseignement.setValue(enseignant.getEnseignements().get(0));
            } else {
                enseignantSelectionne = null;
                code.setText("");
                nom.setText("");
                prenom.setText("");
                masculin.setSelected(false);
                date.setValue(null);
                telephone.setText("");
                email.setText("");
                //enseignement.setValue(null);
                qualification.setText("");
            }
        }
    }


    /**
     * Fonction permettant de vider les zones de détails d'un enseignant pour en créer un autre
     */
    @FXML
    public void handleNouveau() {

        if (Connexion.peutEcrire(Enseignant.class)) {
            enseignantSelectionne = null;
            //Reactivation de tous TextField du panneau des détails
            activationDesactivationDetails(gridPane,true);
            code.setText("");
            nom.setText("");
            prenom.setText("");
            feminin.setSelected(false);
            masculin.setSelected(true);
            date.setValue(null);
            telephone.setText("");
            email.setText("");
            qualification.setText("");
            //enseignement.setValue(null);
        }
    }

    /**
     * Fonction permettant d'éditer les informations d'un enseignant
     */
    @FXML
    public void handleModifier() {

        if (enseignantSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        else if (Connexion.peutModifier(Enseignant.class)) {
            activationDesactivationDetails(gridPane,true);
        }
    }

    /**
     * Fonction permettant de vérifier les informations entrées par l'utilisateur
     *
     * @return
     */
    public boolean verifierValeurs() {
        String errorMessage = "";

        if (nom.getText() == null || nom.getText().length() == 0) {
            errorMessage += "Nom mal écrit!\n";
        }
        if (prenom.getText() == null || prenom.getText().length() == 0) {
            errorMessage += "Prénom mal écrit!\n";
        }
        if ((feminin.getText() == null || feminin.getText().length() == 0) || (masculin.getText() == null || masculin.getText().length() == 0)) {
            errorMessage += "rien coché!\n";
        }
        /*if (date.getValue() == null || date.getValue().compareTo(null) == 0) {
            errorMessage += "rien coché!\n";
        }*/
        if (telephone.getText() == null || telephone.getText().length() == 0) {
            errorMessage += "Numéro de téléphone non saisi!\n";
        }
        if (qualification.getText() == null || qualification.getText().length() == 0) {
            errorMessage += "qualification non saisie!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fonction permettant d'enregistrer un enseignant dans la base de données
     */
    @FXML
    public void handleEnregistrer() {

        if (Connexion.peutLire(Enseignant.class) || Connexion.peutModifier(Enseignant.class)) {
            try {
                String nomEnseignant, prenomEnseignant,emailEnseignant, telephoneEnseignant, qualificationEnseignant;
                nomEnseignant = nom.getText();
                prenomEnseignant = prenom.getText();
                telephoneEnseignant = telephone.getText();
                emailEnseignant = email.getText();
                qualificationEnseignant = qualification.getText();
                Personne.Sexe sexe = masculin.isSelected() ? Personne.Sexe.MASCULIN : Personne.Sexe.FEMININ;
              //  Enseignement enseignementEnseignant = enseignement.getSelectionModel().getSelectedItem();
                //date.setValue(et.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                Date dateNaissance = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

                //Classe classe = new ClasseFacade().find(new Long(4));
                String resultat;
                if (enseignantSelectionne == null) {
                    Enseignant enseignant = new Enseignant("","",nomEnseignant, prenomEnseignant, emailEnseignant, Integer.parseInt(telephoneEnseignant), dateNaissance, sexe,Personne.Statut.ACTIVE, qualificationEnseignant);
                    resultat = enseignantFacade.create(enseignant);
                    /*enseignant.getEnseignements().add(enseignementEnseignant);
                    enseignantFacade.merge(enseignant);*/
                }else {
                    resultat = new EnseignantFacade().modifier(enseignantSelectionne, "", "", nomEnseignant, prenomEnseignant, emailEnseignant, Integer.parseInt(telephoneEnseignant), dateNaissance, sexe, Personne.Statut.ACTIVE, qualificationEnseignant);
                }
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


    EnseignantFacade enseignantFacade = new EnseignantFacade();

    /**
     * Fonction permettant de supprimer un enseignant dans la base de données
     */
    @FXML
    public void handleSupprimer() {

        if (Connexion.peutSupprimer(Enseignant.class)) {
            try {

                if (enseignantSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = enseignantFacade.remove(enseignantSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(enseignantSelectionne);
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

        if (Connexion.peutLire(Enseignant.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Enseignants");
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

    String requeteFiltrage = "select * from enseignant";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from enseignant";
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
        listeEnseignants.clear();
        listeEnseignants.addAll(enseignantFacade.findAllNative(requeteFiltrage));
    }

    @FXML
    public void handleEtudiant() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Etudiant.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des étudiants");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleNote() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Note.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des notes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleAnonymat() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Anonymat.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des anonymats");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleDiscipline() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Discipline.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des disciplines");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleEstInscrit() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/EstInscrit.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des candidats inscrits");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }


    @FXML
    public void handleAnneeAcademique() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/AnneeAcademique.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des années académiques");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleSemestre() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Semestre.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des semestres");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleEvaluation() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Evaluation.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des évaluations");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleTypeEvaluation() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/TypeEvaluation.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des types d'évaluation");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleHistoriqueNote() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/HistoriqueNote.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste de l'historique des notes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }


    @FXML
    public void handleClasse() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Classe.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des classes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleFiliere() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Filiere.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des filières");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleNiveau() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Niveau.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des niveaux");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleSpecialite() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Specialite.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des spécialités");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }


    @FXML
    public void handleCandidat() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Candidat.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des candidats");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleEnseignement() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Enseignement.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des enseignements");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleUe() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Ue.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des unités d'enseignement");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleModule() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Module.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des modules");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }


    @FXML
    public void handleUtilisateur() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Utilisateur.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des utilisateurs");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleRole() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Role.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des roles");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleDroit() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Droit.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des droits");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }


    @FXML
    public void handleSms() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Sms.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des Sms");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleEmail() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Email.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des Email");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleEnvoiMessage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/EnvoiMessage.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Liste des messages envoyés");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
    @FXML
    public void handleenvoyer(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("../view/Typemessage.fxml"));
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Type de message");
        Scene scene = new Scene(page);
        dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.setScene(scene);
        dialogStage.show();

    }
}
