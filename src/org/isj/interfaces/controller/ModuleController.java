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
import org.isj.metier.facade.ModuleFacade;
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
 * Cette classe permet de gérer les modules
 *
 * @author Interface
 */

public class ModuleController implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private TextField libelle;

    @FXML
    private TextArea description;

    @FXML
    private TableView<Module> table;
    ObservableList<Module> listeModule = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Module, Long> codecolumn;

    @FXML
    private TableColumn<Module, String> libellecolumn;

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

    public ModuleController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try {
            ListeModule();
            afficheDetail(null);
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> afficheDetail(newValue)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /**
     * Fonction permettant de lister les différents module dans un tableau
     *
     * @throws SQLException
     */
    public void ListeModule() throws SQLException {

        if (Connexion.peutLire(Module.class)) {

            filtrer(true);

            table.setItems(listeModule);
            activationDesactivationDetails(gridPane,false);
            codecolumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());
            libellecolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibelle()));

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(Module.class);
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
     * Fonction permettant d'afficher les détails d'un module
     *
     * @param candidat variable de type ModuleController
     */

    Module moduleSelectionne = null;

    public void afficheDetail(Module module) {


        if (Connexion.peutLire(Module.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);

            if (module != null) {
                moduleSelectionne = module;
                code.setText(String.valueOf(module.getCode()));
                libelle.setText(module.getLibelle());
                description.setText(module.getDescription());
            } else {
                moduleSelectionne = null;
                code.setText("");
                libelle.setText("");
                description.setText("");
            }
        }
    }

    /**
     * Fonction permettant de vider les zones de détails d'un module pour en créer un autre
     */
    @FXML
    public void handleNouveau() {

        if (Connexion.peutEcrire(Module.class)) {
            moduleSelectionne = null;
            //Raactivation de tous TextField du panneau des détails
            activationDesactivationDetails(gridPane,true);
            code.setText("");
            libelle.setText("");
            description.setText("");
        }
    }

    /**
     * Fonction permettant d'éditer les informations d'un module
     */
    @FXML
    public void handleModifier() {

        if (moduleSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Appli.getPrimaryStage);
            alert.setTitle("ISJ");
            alert.setHeaderText("Aucune donnée à modifier");
            alert.setContentText("Veuillez sélectionner une ligne dans le tableau.");
            alert.show();
        }
        else if (Connexion.peutModifier(Module.class)) {
            activationDesactivationDetails(gridPane,true);
        }
    }

    ModuleFacade moduleFacade = new ModuleFacade();

    /**
     * Fonction permettant d'enregistrer un module dans la base de données
     */
    @FXML
    public void handleEnregistrer() {

        if (Connexion.peutLire(Module.class) || Connexion.peutModifier(Module.class)) {
            try {
                String libelleModule, descriptionModule;
                libelleModule = libelle.getText();
                descriptionModule = description.getText();
                String resultat;
                if (moduleSelectionne == null)
                    resultat = moduleFacade.enregistrer(libelleModule, descriptionModule);
                else
                    resultat = moduleFacade.modifier(moduleSelectionne, libelleModule, descriptionModule);

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


    /**
     * Fonction permettant de supprimer un module dans la base de données
     */
    @FXML
    public void handleSupprimer() {

        if (Connexion.peutSupprimer(Module.class)) {
            try {

                if (moduleSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ISJ");
                    alert.setHeaderText("Confirmation de Suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer la donnée ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String suppression = moduleFacade.remove(moduleSelectionne);
                        if (suppression != null && suppression.equalsIgnoreCase("succes"))
                            table.getItems().remove(moduleSelectionne);
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

        if (Connexion.peutLire(Module.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des Module");
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

    String requeteFiltrage = "select * from module";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from module";
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
        listeModule.clear();
        listeModule.addAll(moduleFacade.findAllNative(requeteFiltrage));
    }
}
