package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.isj.gestionutilisateurs.Connexion;
import org.isj.interfaces.main.Appli;
import org.isj.interfaces.util.litsenners.AutoCompleteComboBoxListener;
import org.isj.metier.Isj;
import org.isj.metier.entites.*;
import org.isj.metier.facade.CandidatFacade;
import org.isj.metier.facade.ClasseFacade;

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

import static org.isj.interfaces.util.Util.activationDesactivationDetails;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.isj.metier.entites.Candidat;
import org.isj.metier.facade.EnvoiMessageFacade;
import org.isj.metier.facade.EstInscritFacade;

import javax.persistence.*;

/**
 * Cette classe permet de gérer les candidats
 *
 * @author Interface
 */

public class EstInscritController implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private ComboBox<Candidat> candidatInscrit;
    ObservableList<Candidat> listeCandidatInscrit = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Enseignement> enseignement;
    ObservableList<Enseignement> listeEnseignement = FXCollections.observableArrayList();


    @FXML
    private TableView<EstInscrit> table;
    ObservableList<EstInscrit> listeEstInscrit = FXCollections.observableArrayList();

    @FXML
    private TableColumn<EstInscrit, Long> codeColumn;

    @FXML
    private TableColumn<EstInscrit, Long> candidatInscritColumn;


    @FXML
    private TableColumn<EstInscrit, String> enseignementColumn;

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

    public EstInscritController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);

    }

    public void listEstInscrit() throws SQLException {

        if (Connexion.peutLire(Candidat.class)) {

            filtrer(true);

            table.setItems(listeEstInscrit);
            activationDesactivationDetails(gridPane,false);
            codeColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());
            enseignementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibelle()));
            candidatInscritColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCandidatInscrit().getCode()).asObject());

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(EstInscrit.class);
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
     * Fonction permettant d'afficher les détails du message
     *
     * @param enstInscri variable de type EstInscritController
     */
    EstInscrit estInscritSelectionne = null;
    public void afficheDetail(EstInscrit estInscrit) {


        if (Connexion.peutLire(EstInscrit.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);

            if (estInscrit != null) {
                estInscritSelectionne = estInscrit;
                code.setText(String.valueOf(estInscrit.getCode()));
                candidatInscrit.setValue(estInscrit.getCandidatInscrit());
                enseignement.setValue(estInscrit.getEnseignement());


            } else {
                estInscritSelectionne = null;
                code.setText("");
                enseignement.setValue(null);
                candidatInscrit.setValue(null);


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

        if (Connexion.peutLire(EstInscrit.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Envoi message");
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

    String requeteFiltrage = "select * from EstInscrit";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    EstInscritFacade estInscritFacade = new EstInscritFacade();

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from EstInscrit";
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
        listeEstInscrit.clear();
        listeEstInscrit.addAll(estInscritFacade.findAllNative(requeteFiltrage));
    }


}


