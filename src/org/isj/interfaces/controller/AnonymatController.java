package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.stage.Stage;
import org.isj.gestionutilisateurs.Connexion;
import org.isj.interfaces.main.Appli;
import org.isj.interfaces.util.litsenners.AutoCompleteComboBoxListener;
import org.isj.metier.Isj;
import org.isj.metier.entites.*;
import org.isj.metier.facade.AnonymatFacade;
import org.isj.metier.facade.EvaluationFacade;
import org.isj.metier.facade.NoteFacade;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AnonymatController implements Initializable{

    @FXML
    private TextField numeroAnonymat;

    @FXML
    private ComboBox<Note> note;
    ObservableList<Note> listnote = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Evaluation> evaluation;
    ObservableList<Evaluation> listeEvaluation = FXCollections.observableArrayList();


    @FXML
    private TableView<Anonymat> table;
    ObservableList<Anonymat> listeAnonymat = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Anonymat, Integer> numAnocolumn;

    @FXML
    private TableColumn<Anonymat, Long> notecolumn;

    @FXML
    private TableColumn<Anonymat, Long> evalcolumn;


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

    public AnonymatController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try {
            listAnonymat();
            afficheDetail(null);
            listeEvaluation();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> afficheDetail(newValue)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    AutoCompleteComboBoxListener<Evaluation> classeAutocomplete;

    /**
     * Fonction permettant de lister les différentes classes auxquelles peut appartenir un candidat
     */
    public void listeEvaluation() {
        listeEvaluation.addAll(new EvaluationFacade().lister());
        evaluation.setItems(listeEvaluation);
        //classeAutocomplete = new AutoCompleteComboBoxListener<Evaluation>(evaluation);
    }

    public void listeNote() {
        listnote.addAll(new NoteFacade().lister());
        note.setItems(listnote);
        //classeAutocomplete = new AutoCompleteComboBoxListener<Evaluation>(evaluation);
    }

    /**
     * Fonction permettant de lister les différents anonymats dans un tableau (nom et prénom)
     *
     * @throws SQLException
     */
    public void listAnonymat() throws SQLException {

        if (org.isj.gestionutilisateurs.Connexion.peutLire(Anonymat.class)) {

            List<Anonymat> anonymat = new AnonymatFacade().findAll();

            listeAnonymat.addAll(anonymat);
            table.setItems(listeAnonymat);
            numAnocolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroAnonymat()).asObject());
            evalcolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getEvaluation().getCode()).asObject());
            notecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getNote().getCode()).asObject());

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Anonymat.class);
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
     * Fonction permettant d'afficher les détails d'un anonymat
     *
     */

    Anonymat anonymatSelectionne = null;

    public void afficheDetail(Anonymat anonymat) {

        if (anonymat != null) {
            anonymatSelectionne = anonymat;
            numeroAnonymat.setText(String.valueOf(anonymat.getNumeroAnonymat()));
            numeroAnonymat.setDisable(true);
            note.setValue(anonymat.getNote());
            note.setDisable(true);
            evaluation.setValue(anonymat.getEvaluation());
            evaluation.setDisable(true);
        } else {
            anonymatSelectionne = null;
            numeroAnonymat.setText("");
            numeroAnonymat.setDisable(true);
            note.setValue(null);
            evaluation.setValue(null);
        }

    }

    /**
     * Fonction permettant de vider les zones de détails d'un anonymat pour en créer un autre
     */
    @FXML
    public void handleNouveau() {

        if (org.isj.gestionutilisateurs.Connexion.peutEcrire(Anonymat.class)) {
            anonymatSelectionne = null;
            numeroAnonymat.setText("");
            numeroAnonymat.setDisable(true);
            note.setValue(null);
            evaluation.setValue(null);
            note.setDisable(false);
            evaluation.setDisable(false);
        }
    }

    /**
     * Fonction permettant d'éditer les informations d'un anonymat
     */
    @FXML
    public void handleModifier() {

        if (anonymatSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        if (org.isj.gestionutilisateurs.Connexion.peutModifier(Anonymat.class)) {
            numeroAnonymat.setDisable(true);
            note.setDisable(false);
            evaluation.setDisable(false);
        }
    }


    /**
     * Fonction permettant d'enregistrer un anonymat dans la base de données
     */
    @FXML
    public void handleEnregistrer() {

        if (org.isj.gestionutilisateurs.Connexion.peutLire(Anonymat.class) || org.isj.gestionutilisateurs.Connexion.peutModifier(Anonymat.class)) {
            try {
                String numAnonymat;
                numAnonymat = numeroAnonymat.getText();
                Note noteAnonymat = note.getSelectionModel().getSelectedItem();
                Evaluation evaluationAnonymat = evaluation.getSelectionModel().getSelectedItem();


                String resultat;
                if (anonymatSelectionne == null)
                    resultat = anonymatFacade.enregistrer("", "",Integer.parseInt(numAnonymat), noteAnonymat, evaluationAnonymat);
                else
                    resultat = anonymatFacade.modifier(anonymatSelectionne, "", "", Integer.parseInt(numAnonymat), noteAnonymat, evaluationAnonymat);

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


    AnonymatFacade anonymatFacade = new AnonymatFacade();

    /**
     * Fonction permettant de supprimer un anonymat dans la base de données
     */
    @FXML
    public void handleSupprimer() {

        if (org.isj.gestionutilisateurs.Connexion.peutSupprimer(Anonymat.class)) {
            try {

                if (anonymatSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = anonymatFacade.remove(anonymatSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(anonymatSelectionne);
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

    @FXML
    public void handleImprimer() throws IOException {

        if (Connexion.peutLire(Anonymat.class)) {
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

    String requeteFiltrage = "select * from anonymat";

    @FXML
    public void handleFiltrer() {

        requeteFiltrage = "select * from anonymat";

        String listeCriteres = "";
        for (int i = 0; i < listeFiltrage.getItems().size(); i++) {
            if (requeteFiltrage.contains(" where "))
                listeCriteres = listeCriteres + " and " + listeFiltrage.getItems().get(i);
            else
                listeCriteres = " where " + listeFiltrage.getItems().get(i);
        }
        requeteFiltrage = requeteFiltrage + listeCriteres;
        listeAnonymat.clear();
        listeAnonymat.addAll(anonymatFacade.findAllNative(requeteFiltrage));
    }
}
