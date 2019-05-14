package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.*;
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
import org.isj.metier.entites.HistoriqueNote;
import org.isj.metier.entites.Note;
import org.isj.metier.facade.HistoriqueNoteFacade;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.isj.interfaces.util.Util.activationDesactivationDetails;

public class HistoriqueNoteController implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private TextField libelle;

    @FXML
    private TextField note;

    @FXML
    private TextField valeur_note;

    @FXML
    private TableView<HistoriqueNote> table;
    ObservableList<HistoriqueNote> listeHistoriqueNote = FXCollections.observableArrayList();

    @FXML
    private TableColumn<HistoriqueNote, String> libellecolumn;

    @FXML
    private TableColumn<HistoriqueNote, Double> valeurNotecolumn;

    @FXML
    private TableColumn<HistoriqueNote, Long> codecolumn;

    @FXML
    private TableColumn<HistoriqueNote, Long> noteColumn;

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

    public HistoriqueNoteController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try{
            listHistoriqueNote();
            afficheDetail(null);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant de lister les différentes notes dans un tableau (numero, valeur)
     *
     * @throws SQLException
     */
    public void listHistoriqueNote() throws SQLException {

        if (Connexion.peutLire(HistoriqueNote.class)) {

            filtrer(true);

            table.setItems(listeHistoriqueNote);
            activationDesactivationDetails(gridPane, false);
            libellecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibelle()));
            noteColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getNote().getCode()).asObject());
            valeurNotecolumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValeurNote()).asObject());
            codecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(HistoriqueNote.class);
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
     * Fonction permettant d'afficher les détails d'une note archivé
     *
     * @param historiqueNote variable de type CandidatController
     */

    HistoriqueNote historiqueNoteSelectionne = null;

    public void afficheDetail(HistoriqueNote historiquenote) {


        if (Connexion.peutLire(HistoriqueNote.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);

            if (historiquenote != null) {
                historiqueNoteSelectionne = historiquenote;
                code.setText(String.valueOf(historiquenote.getCode()));

                libelle.setText(historiquenote.getLibelle());
                note.setText(historiquenote.getNote().toString());
                valeur_note.setText(Double.toString(historiquenote.getValeurNote()));
            } else {
                code.setText("");
                libelle.setText("");
                valeur_note.setText("");
                note.setText("");
            }
        }
    }

    /**
     * Fonction permettant de vider les zones de détails d'une note pour en créer un autre
    */
    @FXML
    public void handleNouveau() {

        if (Connexion.peutEcrire(Note.class)) {
            /*noteSelectionne = null;
            //Raactivation de tous TextField du panneau des détails
            activationDesactivationDetails(gridPane,true);
            code.setText("");
            libelle.setText("");
            numero_table.setText("");
            evaluation.setValue(null);
            valeur_note.setText("");
            description.setText("");*/
        }
    }

    /**
     * Fonction permettant d'éditer les informations d'un candidat
     */
    @FXML
    public void handleModifier() {

        if (historiqueNoteSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        else if (Connexion.peutModifier(HistoriqueNote.class)) {
            activationDesactivationDetails(gridPane,true);
        }
    }

    /**
     * Fonction permettant d'enregistrer une note dans la base de données
     */


    HistoriqueNoteFacade historiqueNoteFacade = new HistoriqueNoteFacade();

    /**
     * Fonction permettant de supprimer une note dans la base de données
     */
    @FXML
    public void handleSupprimer() {
        /*
        if (Connexion.peutSupprimer(HistoriqueNote.class)) {
            try {

                if (historiqueNoteSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = historiqueNoteFacade.remove(historiqueNoteSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(historiqueNoteSelectionne);
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
        }*/
    }

    private boolean raffraichir = false;

    @FXML
    public void handleEnregistrer() {
        /*
        if (Connexion.peutLire(Note.class) || Connexion.peutModifier(Note.class)) {
            try {
                String libelleNote, numeroTableNote, valeurNote, descriptionNote;
                libelleNote = libelle.getText();
                numeroTableNote = numero_table.getText();
                valeurNote = valeur_note.getText();
                descriptionNote = description.getText();
                Evaluation evaluationNote = evaluation.getSelectionModel().getSelectedItem();

                String resultat;
                if (noteSelectionne == null)
                    resultat = noteFacade.enregistrer(libelleNote, descriptionNote, Double.valueOf(valeurNote), Integer.parseInt(numeroTableNote), null,null,evaluationNote);
                else
                    resultat = noteFacade.modifier(noteSelectionne, libelleNote, descriptionNote, Double.valueOf(valeurNote), Integer.parseInt(numeroTableNote),null, null, evaluationNote);

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

        }*/
    }

    @FXML
    public void handleRaffraichir() {
        filtrer(true);
    }

    @FXML
    public void handleImprimer() throws IOException {

        if (Connexion.peutLire(HistoriqueNote.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Historique des Notes");
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

    String requeteFiltrage = "select * from historique_note";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from historique_note";
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
        listeHistoriqueNote.clear();
        listeHistoriqueNote.addAll(historiqueNoteFacade.findAllNative(requeteFiltrage));
    }

}
