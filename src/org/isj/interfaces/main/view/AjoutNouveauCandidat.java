package org.isj.interfaces.main.view;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.isj.etats.dynamicreports.SimpleDynamicReport;
import org.isj.interfaces.main.Appli;
import org.isj.interfaces.main.util.AutoCompleteComboBoxListener;
import org.isj.traitementmetier.Isj;
import org.isj.traitementmetier.entites.*;
import org.isj.traitementmetier.facade.CandidatFacade;
import org.isj.traitementmetier.facade.ClasseFacade;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Cette classe permet de gérer les candidats
 * @author Interface
 */
public class AjoutNouveauCandidat implements Initializable {

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
    private ComboBox<Classe> classe;
    ObservableList<Classe> listeClasses = FXCollections.observableArrayList();

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
    private TableView<Candidat> table;

    @FXML
    private TableColumn<Candidat, String> nomcolumn;

    @FXML
    private TableColumn<Candidat, String> prenomcolumn;
    ObservableList<Candidat> listeCandidats = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> attributs;
    ObservableList<String> listAttributs = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> operateurs;
    ObservableList<String> listOperateurs = FXCollections.observableArrayList("<", ">", "<=", ">=", "=", "!=");

    @FXML
    private TextField valeurs;

    @FXML
    private ListView<String> listeFiltrage;

    public AjoutNouveauCandidat() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try {
            listCandidat();
            AfficheCandidat(null);
            listeClasses();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> AfficheCandidat(newValue)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    AutoCompleteComboBoxListener<Classe> classeAutocomplete;
    /**
     * Fonction permettant de lister les différentes classes auxquelles peut appartenir un candidat
     */
    public void listeClasses() {
        listeClasses.addAll(new ClasseFacade().lister());
        classe.setItems(listeClasses);
        classeAutocomplete = new AutoCompleteComboBoxListener<Classe>(classe);
    }

    /**
     * Fonction permettant de lister les différents candidats dans un tableau (nom et prénom)
     * @throws SQLException
     */
    public void listCandidat() throws SQLException {
        List<Candidat> candidat = new CandidatFacade().findAll();

        listeCandidats.addAll(candidat);
        table.setItems(listeCandidats);
        nomcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));

        ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Candidat.class);
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            try {
                listAttributs.add(resultSetMetaData.getColumnName(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        attributs.setItems(listAttributs);
    }

    /**
     * Fonction permettant d'afficher les détails d'un candidat
     * @param candidat variable de type Candidat
     */
    public void AfficheCandidat(Candidat candidat) {
        if (candidat != null) {
            if (candidat != null) {
                nom.setText(candidat.getNom());
                nom.setDisable(true);
                prenom.setText(candidat.getPrenom());
                prenom.setDisable(true);
                if (candidat.getSexe().equals(Personne.Sexe.FEMININ)) {
                    feminin.setSelected(true);
                    masculin.setSelected(false);
                } else {
                    feminin.setSelected(false);
                    masculin.setSelected(true);
                }
                feminin.setDisable(true);
                masculin.setDisable(true);
                date.setValue(candidat.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                date.setDisable(true);
                telephone.setText(Integer.toString(candidat.getTelephone()));
                telephone.setDisable(true);
                email.setText(candidat.getEmail());
                email.setDisable(true);
                ecoleOrigine.setText(candidat.getEcoleOrigine());
                ecoleOrigine.setDisable(true);
                regionOrigine.setText(candidat.getRegionOrigine());
                regionOrigine.setDisable(true);
                classe.setValue(candidat.getClasse());
                classe.setDisable(true);
                nompere.setText(candidat.getNomDuPere());
                nompere.setDisable(true);
                telpere.setText(Integer.toString(candidat.getTelephoneDuPere()));
                telpere.setDisable(true);
                profpere.setText(candidat.getProfessionDuPere());
                profpere.setDisable(true);
                nommere.setText(candidat.getNomDeLaMere());
                nommere.setDisable(true);
                telmere.setText(Integer.toString(candidat.getTelephoneDeLaMere()));
                telmere.setDisable(true);
                profmere.setText(candidat.getProfessionDelaMere());
                profmere.setDisable(true);
            } else {
                nom.setText("");
                prenom.setText("");
                feminin.setSelected(false);
                masculin.setSelected(false);
                date.setValue(null);
                telephone.setText("");
                email.setText("");
                ecoleOrigine.setText("");
                regionOrigine.setText("");
                classe.setValue(null);
                nompere.setText("");
                telpere.setText("");
                profpere.setText("");
                nommere.setText("");
                telmere.setText("");
                profmere.setText("");
            }
        }
    }

    /**
     * Fonction permettant de vider les zones de détails d'un candidat pour en créer un autre
     */
    @FXML
    public void handleNouveauCandidat() {
        nom.setText("");
        prenom.setText("");
        feminin.setSelected(false);
        masculin.setSelected(false);
        date.setValue(null);
        telephone.setText("");
        email.setText("");
        ecoleOrigine.setText("");
        regionOrigine.setText("");
        classe.setValue(null);
        nompere.setText("");
        telpere.setText("");
        profpere.setText("");
        nommere.setText("");
        telmere.setText("");
        profmere.setText("");
        nom.setDisable(false);
        prenom.setDisable(false);
        feminin.setDisable(false);
        masculin.setDisable(false);
        date.setDisable(false);
        telephone.setDisable(false);
        email.setDisable(false);
        ecoleOrigine.setDisable(false);
        regionOrigine.setDisable(false);
        classe.setDisable(false);
        nompere.setDisable(false);
        telpere.setDisable(false);
        profpere.setDisable(false);
        nommere.setDisable(false);
        telmere.setDisable(false);
        profmere.setDisable(false);
    }

    /**
     * Fonction permettant d'éditer les informations d'un candidat
     */
    @FXML
    public void handleEditCandidat() {
        nom.setDisable(false);
        prenom.setDisable(false);
        feminin.setDisable(false);
        masculin.setDisable(false);
        date.setDisable(false);
        telephone.setDisable(false);
        email.setDisable(false);
        ecoleOrigine.setDisable(false);
        regionOrigine.setDisable(false);
        classe.setDisable(false);
        nompere.setDisable(false);
        telpere.setDisable(false);
        profpere.setDisable(false);
        nommere.setDisable(false);
        telmere.setDisable(false);
        profmere.setDisable(false);
    }

    /**
     * Fonction permettant de vérifier les informations entrées par l'utilisateur
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
        if (nompere.getText() == null || nompere.getText().length() == 0) {
            errorMessage += "Nom du père non saisi!\n";
        }
        if (profpere.getText() == null || profpere.getText().length() == 0) {
            errorMessage += "Profession du père non saisie!\n";
        }
        if (telpere.getText() == null || telpere.getText().length() == 0) {
            errorMessage += "Numéro de téléphone du père non saisi!\n";
        }
        if (nommere.getText() == null || nommere.getText().length() == 0) {
            errorMessage += "Nom de la mère non saisi!\n";
        }
        if (profmere.getText() == null || profmere.getText().length() == 0) {
            errorMessage += "Profession de la mère non saisie!\n";
        }
        if (telmere.getText() == null || telmere.getText().length() == 0) {
            errorMessage += "Numéro de téléphone de la mère non saisi!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fonction permettant d'enregistrer un candidat dans la base de données
     */
    @FXML
    public void handleEnregistrerCandidat() {
        String nomCandidat, prenomCandidat, regionO, ecoleO, emailCandidat, telephoneCandidat, nompereCandidat, profpereCandidat, telpereCandidat, nommereCandidat, profmereCandidat, telmereCandidat;

        nomCandidat = nom.getText();
        prenomCandidat = prenom.getText();
        telephoneCandidat = telephone.getText();
        regionO = regionOrigine.getText();
        ecoleO = ecoleOrigine.getText();
        emailCandidat = email.getText();
        nompereCandidat = nompere.getText();
        profpereCandidat = profpere.getText();
        telpereCandidat = telpere.getText();
        nommereCandidat = nommere.getText();
        profmereCandidat = profmere.getText();
        telmereCandidat = telmere.getText();
        Personne.Sexe sexe = masculin.isSelected() ? Personne.Sexe.MASCULIN : Personne.Sexe.FEMININ;
        Classe classeEtudiant = classe.getValue();
        //date.setValue(et.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Date dateNaissance = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        //Classe classe = new ClasseFacade().find(new Long(4));
        candidatFacade.enregistrer("", "", nomCandidat, prenomCandidat, emailCandidat, Integer.parseInt(telephoneCandidat), dateNaissance, sexe, Personne.Statut.ACTIVE, nompereCandidat, profpereCandidat, Integer.parseInt(telpereCandidat), Integer.parseInt(telmereCandidat), nommereCandidat, profmereCandidat, regionO, ecoleO, classeEtudiant);


    }


    CandidatFacade candidatFacade = new CandidatFacade();
    /**
     * Fonction permettant de supprimer un candidat dans la base de données
     */
    @FXML
    public void handleSupprimerCandidat() {
        try {
            Candidat selectedCandidat = table.getSelectionModel().getSelectedItem();
            if (selectedCandidat != null) {
                table.getItems().remove(selectedCandidat);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(Appli.getPrimaryStage);
                alert.setTitle("Aucune sélection.");
                alert.setHeaderText("Aucune personne sélectionnée.");
                alert.setContentText("Veuillez sélectionner une personne dans la table.");
            }
            candidatFacade.remove(selectedCandidat);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    public void handleImprimerCandidat() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("view/selectionChamps.fxml"));

        BorderPane page = loader.load();

        SelectionChamps selectionChamps=loader.getController();

        selectionChamps.setAttributs(listAttributs);
        selectionChamps.setRequete(requeteFiltrage);
        selectionChamps.setTitre("Liste des Candidats");
        selectionChamps.setSousTitre("Imprime à "+new Date());
        selectionChamps.setOrientation(Page.Page_A4_Landscape());

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Sélection des choix");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();

    }

    @FXML
    public void handleAjouterCandidat() {
        try {
            String attribut, operateur, valeur;
            attribut = attributs.getValue();
            operateur = operateurs.getValue();
            valeur = valeurs.getText();
            String critere = attribut + operateur + valeur;
            listeFiltrage.getItems().add(critere);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    public void handleSupprimerFiltrageCandidat() {
        try {
            int selectedIndex = listeFiltrage.getSelectionModel().getSelectedIndex();
            listeFiltrage.getItems().remove(selectedIndex);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    String requeteFiltrage = "select * from candidat";

    @FXML
    public void handleFiltrerCandidat(){
        String listeCriteres = "";
        for (int i = 0; i < listeFiltrage.getItems().size(); i++) {
            if (i == 0)
                listeCriteres = " where "+listeFiltrage.getItems().get(i);
            else
                listeCriteres = listeCriteres + " and " + listeFiltrage.getItems().get(i);
        }
        requeteFiltrage = requeteFiltrage + listeCriteres;
        listeCandidats.clear();
        listeCandidats.addAll(candidatFacade.findAllNative(requeteFiltrage));
    }
}
