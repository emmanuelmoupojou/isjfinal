package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.isj.gestionutilisateurs.Connexion;
import org.isj.interfaces.main.Appli;
import org.isj.interfaces.util.beans.ChoixRole;
import org.isj.interfaces.util.litsenners.AutoCompleteComboBoxListener;
import org.isj.metier.Isj;
import org.isj.metier.entites.*;
import org.isj.metier.facade.CandidatFacade;
import org.isj.metier.facade.ClasseFacade;
import org.isj.metier.facade.RoleFacade;

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

/**
 * Cette classe permet de gérer les roles
 *
 * @author Interface
 */

public class RoleController implements Initializable {

    @FXML
    private TextField role;

    @FXML
    private TableView<Role> table;
    ObservableList<Role> listeRole = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Role, Long> codecolumn;

    @FXML
    private TableColumn<Role, String> libellecolumn;

    @FXML
    private TableView<ChoixRole> tableSelection;
    ObservableList<ChoixRole> listeCategorieRole = FXCollections.observableArrayList();

    @FXML
    private TableColumn<ChoixRole, String> categoriecolumn;

    @FXML
    private TableColumn<ChoixRole, Boolean> lecturecolumn;

    @FXML
    private TableColumn<ChoixRole, Boolean> ecriturecolumn;

    @FXML
    private TableColumn<ChoixRole, Boolean> modificationcolumn;

    @FXML
    private TableColumn<ChoixRole, Boolean> suppressioncolumn;

    private ObservableList<String> listeEntites;

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



    public RoleController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try {
            ListeRole();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> ListeEntites((ObservableList<String>) newValue)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Fonction permettant de lister les différents role dans un tableau
     *
     * @throws SQLException
     */
    public void ListeRole() throws SQLException {

        if (Connexion.peutLire(Role.class)) {

            filtrer(true);

            table.setItems(listeRole);
            codecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());
            libellecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibelle()));

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Role.class);
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

    public void ListeEntites(ObservableList<String> listeEntites) {
        this.listeEntites = listeEntites;
        for(int i=0;i<listeEntites.size();i++) {
            listeCategorieRole.add(new ChoixRole(true, true, true, true, listeEntites.get(i)));
        }
        tableSelection.setItems(listeCategorieRole);

        categoriecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorie()));

        tableSelection.setEditable(true);
        lecturecolumn.setCellFactory(column -> new CheckBoxTableCell<>());
        lecturecolumn.setCellValueFactory(cellData -> {
            ChoixRole cellValue = cellData.getValue();
            BooleanProperty property = new SimpleBooleanProperty(cellValue.isEcriture());
            // Add listener to handler change
            property.addListener((observable, oldValue, newValue) -> cellValue.setEcriture(newValue));
            return property;
        });
    }

    /**
     * Fonction permettant de vider les zones de détails d'un role pour en créer un autre
     */
    @FXML
    public void handleNouveau() {

    }

    /**
     * Fonction permettant d'éditer les informations d'un role
     */
    @FXML
    public void handleModifier() {

    }

    /**
     * Fonction permettant d'enregistrer un role dans la base de données
     */
    @FXML
    public void handleEnregistrer() {

    }

    RoleFacade roleFacade = new RoleFacade();

    /**
     * Fonction permettant de supprimer un role dans la base de données
     */
    @FXML
    public void handleSupprimer() {

    }

    private boolean raffraichir = false;

    @FXML
    public void handleRaffraichir() {
        filtrer(true);
    }

    @FXML
    public void handleImprimer() throws IOException {

        if (Connexion.peutLire(Role.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Role");
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

    String requeteFiltrage = "select * from role";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from role";
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
        listeRole.clear();
        listeRole.addAll(roleFacade.findAllNative(requeteFiltrage));
    }


}
