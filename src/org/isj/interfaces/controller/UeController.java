package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleIntegerProperty;
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
import org.isj.metier.facade.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.isj.interfaces.util.Util.activationDesactivationDetails;

public class UeController implements Initializable {
    @FXML
    private TextField code;

    @FXML
    private TextField codeUe;

    @FXML
    private ComboBox<Module> module;
    ObservableList<Module> listModule = FXCollections.observableArrayList();

    @FXML
    private TextField libelle;

    @FXML
    private TextField description;

    @FXML
    private ComboBox<Specialite> specialite;
    ObservableList<Specialite> listSpecialite = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Niveau> niveau;
    ObservableList<Niveau> listNiveau = FXCollections.observableArrayList();

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
    private TableView<UE> table;
    ObservableList<UE> listeUes = FXCollections.observableArrayList();

    @FXML
    private TableColumn<UE, String> codeUeColumn;

    @FXML
    private TableColumn<UE, String> moduleColumn;

    @FXML
    private TableColumn<UE, String> libelleColumn;

    @FXML
    private TableColumn<UE, Integer> niveauColumn;

    @FXML
    private TableColumn<UE, String> specialiteColumn;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try{
            listeNiveaux();
            listeSpecialites();
            listeModules();
            listUe();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> AfficheUe(newValue)));
        }catch(Exception e){
            e.getMessage();
        }
    }

    //AutoCompleteComboBoxListener<Niveau> classeAutocomplete;

    public void listeNiveaux() {
        listNiveau.addAll(new NiveauFacade().lister());
        niveau.setItems(listNiveau);
        //classeAutocomplete = new AutoCompleteComboBoxListener<Niveau>(niveau);
    }

    public void listeSpecialites() {
        listSpecialite.addAll(new SpecialiteFacade().lister());
        specialite.setItems(listSpecialite);
        //classeAutocomplete1 = new AutoCompleteComboBoxListener<Specialite>(specialite);
    }

    public void listeModules() {
        listModule.addAll(new ModuleFacade().lister());
        module.setItems(listModule);
        //classeAutocomplete2 = new AutoCompleteComboBoxListener<Module>(module);
    }


    public void listUe() throws SQLException {

        if(Connexion.peutLire(UE.class)){
            filtrer(true);
            table.setItems(listeUes);
            codeUeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeUE()));
            libelleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibelle()));
            moduleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModule().getLibelle()));
            specialiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialite().getLibelle()));
            niveauColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNiveau().getNumero()).asObject());

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(UE.class);
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

    UE ueSelectionne = null;

    public void AfficheUe(UE ue){

        if (Connexion.peutLire(Sms.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane, false);

            if(ue != null){
                ueSelectionne = ue;
                code.setText(String.valueOf(ue.getCode()));
                code.setDisable(true);
                codeUe.setText(ue.getCodeUE());
                codeUe.setDisable(true);
                module.setValue(ue.getModule());
                module.setDisable(true);
                libelle.setText(ue.getLibelle());
                libelle.setDisable(true);
                description.setText(ue.getDescription());
                description.setDisable(true);
                niveau.setValue(ue.getNiveau());
                niveau.setDisable(true);
                specialite.setValue(ue.getSpecialite());
                specialite.setDisable(true);
            }else{
                ueSelectionne = null;
                code.setText("");
                code.setDisable(true);
                codeUe.setText("");
                codeUe.setDisable(false);
                module.setValue(null);
                module.setDisable(false);
                libelle.setText("");
                libelle.setDisable(false);
                description.setText("");
                description.setDisable(false);
                niveau.setValue(null);
                niveau.setDisable(false);
                specialite.setValue(null);
                specialite.setDisable(false);
            }
        }

    }

    @FXML
    public void handleNouvelleUe(){
        if (Connexion.peutEcrire(Sms.class)) {
            ueSelectionne = null;
            //Raactivation de tous TextField du panneau des détails
            activationDesactivationDetails(gridPane,true);
            code.setText("");
            code.setText("");
            codeUe.setText("");
            module.setValue(null);
            libelle.setText("");
            description.setText("");
            niveau.setValue(null);
            specialite.setValue(null);
        }
    }

    @FXML
    public void handleEditerUe(){
        if (ueSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        else if (Connexion.peutModifier(UE.class)) {
            activationDesactivationDetails(gridPane,true);
        }
    }

    UEFacade ueFacade = new UEFacade();

    @FXML
    public void handleEnregistrerUe(){

        if (Connexion.peutLire(Sms.class) || Connexion.peutModifier(Sms.class)) {
            try{
                String codeUE, libelleUE, descriptionUE;
                codeUE = codeUe.getText();
                Module moduleUE = module.getValue();
                libelleUE = libelle.getText();
                descriptionUE = description.getText();
                Niveau niveauUe = niveau.getValue();
                Specialite specialiteUe = specialite.getValue();

                String resultat;
                if(ueSelectionne == null){
                    resultat = ueFacade.enregistrer(libelleUE, descriptionUE, codeUE,UE.Statut.ACTIVE, moduleUE, niveauUe, specialiteUe);
                }else{
                    resultat = ueFacade.modifier(ueSelectionne, libelleUE, descriptionUE, codeUE, UE.Statut.ACTIVE, moduleUE, niveauUe, specialiteUe);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(Appli.getPrimaryStage);
                alert.setTitle("ISJ");
                alert.setHeaderText("Resultat de l'opération");
                alert.setContentText(resultat.toUpperCase() + " !");
                alert.show();
            }catch (Exception e) {
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
    public void handleSupprimerUe() {

        if (Connexion.peutSupprimer(UE.class)) {
            try {

                if (ueSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = ueFacade.remove(ueSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(ueSelectionne);
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

    String requeteFiltrage = "select * from ue";

    @FXML
    public void handleImprimerUe() throws IOException {

        if (Connexion.peutLire(UE.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Unités d'enseignements");
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

        requeteFiltrage = "select * from ue";
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
        listeUes.clear();
        listeUes.addAll(ueFacade.findAllNative(requeteFiltrage));
    }

    private boolean raffraichir = false;

    @FXML
    public void handleRaffraichir() {
        filtrer(true);
    }

}
