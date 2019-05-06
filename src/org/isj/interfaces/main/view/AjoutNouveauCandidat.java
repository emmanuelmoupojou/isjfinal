package org.isj.interfaces.main.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.isj.interfaces.main.Appli;
import org.isj.interfaces.main.util.AutoCompleteComboBoxListener;
import org.isj.traitementmetier.Isj;
import org.isj.traitementmetier.entites.*;
import org.isj.traitementmetier.facade.CandidatFacade;
import org.isj.traitementmetier.facade.ClasseFacade;
import org.isj.traitementmetier.facade.EtudiantFacade;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
    ObservableList<Candidat> listeCandidats=FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> attributs;
    ObservableList<String> listAttributs = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> operateurs;
    ObservableList<String> listOperateurs = FXCollections.observableArrayList("<", ">", "<=", ">=", "=", "!=");

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

    public void listeClasses() {
        listeClasses.addAll(new ClasseFacade().lister());
        classe.setItems(listeClasses);
        classeAutocomplete = new AutoCompleteComboBoxListener<Classe>(classe);
    }

    public void listCandidat() throws SQLException {
        List<Candidat> candidat = new CandidatFacade().findAll();

        listeCandidats.addAll(candidat);
        table.setItems(listeCandidats);
        nomcolumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNom()));
        prenomcolumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getPrenom()));

        ResultSetMetaData resultSetMetaData=new Isj().renvoyerChamp(Candidat.class);
        for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
            try {
                listAttributs.add(resultSetMetaData.getColumnName(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        attributs.setItems(listAttributs);
    }

    public void AfficheCandidat(Candidat et){
        if(et != null){
            nom.setText(et.getNom());
            prenom.setText(et.getPrenom());
            if(et.getSexe().equals(Personne.Sexe.FEMININ)){
                feminin.setSelected(true);
                masculin.setSelected(false);
            }else{
                feminin.setSelected(false);
                masculin.setSelected(true);
            }
            date.setValue(et.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            telephone.setText(Integer.toString(et.getTelephone()));
            nompere.setText(et.getNomDuPere());
            telpere.setText(Integer.toString(et.getTelephoneDuPere()));
            profpere.setText(et.getProfessionDuPere());
            nommere.setText(et.getNomDeLaMere());
            telmere.setText(Integer.toString(et.getTelephoneDeLaMere()));
            profmere.setText(et.getProfessionDelaMere());
        }else{
            nom.setText("");
            prenom.setText("");
            feminin.setSelected(false);
            masculin.setSelected(false);
            date.setValue(null);
            telephone.setText("");
            nompere.setText("");
            telpere.setText("");
            profpere.setText("");
            nommere.setText("");
            telmere.setText("");
            profmere.setText("");
        }
    }


    @FXML
    public void handleNouveauCandidat(){
        nom.setText("");
        prenom.setText("");
        date.setValue(null);
        telephone.setText("");
        nompere.setText("");
        telpere.setText("");
        profpere.setText("");
        nommere.setText("");
        telmere.setText("");
        profmere.setText("");
    }

    @FXML
    public void handleEditCandidat(){

    }

    public boolean verifierValeurs(){
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

    @FXML
    public void handleEnregistrerCandidat(){
        String nomCandidat, prenomCandidat, regionO, ecoleO,emailCandidat, telephoneCandidat, nompereCandidat, profpereCandidat, telpereCandidat, nommereCandidat, profmereCandidat, telmereCandidat;

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
        Personne.Sexe sexe=masculin.isSelected()? Personne.Sexe.MASCULIN: Personne.Sexe.FEMININ;
        Classe classeEtudiant=classe.getValue();
        //date.setValue(et.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Date dateNaissance = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        CandidatFacade candidatFacade = new CandidatFacade();
        //Classe classe = new ClasseFacade().find(new Long(4));
        candidatFacade.enregistrer("","",nomCandidat,prenomCandidat,emailCandidat,Integer.parseInt(telephoneCandidat),dateNaissance, sexe, Personne.Statut.ACTIVE,nompereCandidat,profpereCandidat,Integer.parseInt(telpereCandidat),Integer.parseInt(telmereCandidat),nommereCandidat,profmereCandidat,regionO,ecoleO,classeEtudiant);


    }

    @FXML
    public void handleDeleteCandidat() {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            table.getItems().remove(selectedIndex);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune personne sélectionnée");
            alert.setContentText("Please select a person in the table.");

        }
    }

    @FXML
    public void handleImprimerCandidat(){

    }

    @FXML
    public void handleAjouterCandidat(){

    }
}
