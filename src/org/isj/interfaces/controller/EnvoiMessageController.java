package org.isj.interfaces.controller;

import ar.com.fdvs.dj.domain.constants.Page;
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
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import static org.isj.interfaces.util.Util.activationDesactivationDetails;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.isj.metier.entites.Candidat;
import org.isj.metier.entites.EnvoiMessage;
import org.isj.metier.entites.Message;
import org.isj.metier.facade.EnvoiMessageFacade;

/**
 * Cette classe permet de gérer les envoi de message
 *
 * @author Interface
 */

public class EnvoiMessageController implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private TextField libelle;

    @FXML
    private DatePicker dateEnvoi;

    @FXML
    private TextField message;

    @FXML
    private TextField candidat;

    @FXML
    private TableView<EnvoiMessage> table;
    ObservableList<EnvoiMessage> listeEnvoiMessage = FXCollections.observableArrayList();

    @FXML
    private TableColumn<EnvoiMessage, Long> codeColumn;

    @FXML
    private TableColumn<EnvoiMessage, String> libelleColumn;

    @FXML
    private TableColumn<EnvoiMessage, Candidat> candidatColumn;

    @FXML
    private TableColumn<EnvoiMessage, String> dateEnvoiColumn;

    @FXML
    private TableColumn<EnvoiMessage, Message> messageColumn;

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

    public EnvoiMessageController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        try{
            afficheDetail(null);
            listEnvoiMessage();
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> afficheDetail(newValue)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listEnvoiMessage() throws SQLException {

        if (Connexion.peutLire(EnvoiMessage.class)) {

            filtrer(true);

            table.setItems(listeEnvoiMessage);
            activationDesactivationDetails(gridPane,false);
            codeColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCode()).asObject());
            libelleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibelle()));
            candidatColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCandidat()));
            messageColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Message>(cellData.getValue().getMessage()));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dateEnvoiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getDateEnvoi())));

            ResultSetMetaData resultSetMetaData = new Isj().renvoyerChamp(EnvoiMessage.class);
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
     * @param envoiMessage variable de type EnvoiMessageController
     */
    EnvoiMessage envoiMessageSelectionne = null;
    public void afficheDetail(EnvoiMessage envoiMessage) {


        if (Connexion.peutLire(EnvoiMessage.class)) {
            //Desactivation de tous les TextFields du panneau des détails
            activationDesactivationDetails(gridPane,false);
            if (envoiMessage != null) {
                envoiMessageSelectionne = envoiMessage;
                code.setText(String.valueOf(envoiMessage.getCode()));
                libelle.setText((envoiMessage.getLibelle()));
                candidat.setText(envoiMessage.getCandidat().getNom());
                dateEnvoi.setValue(envoiMessage.getDateEnvoi().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                message.setText(envoiMessage.getMessage().toString());
            } else {
                envoiMessageSelectionne = null;
                code.setText("");
                libelle.setText("");
                dateEnvoi.setValue(null);
                candidat.setText("");
                message.setText("");
            }
        }
    }

    /**
     * Fonction permettant de vider les zones de détails d'un candidat pour en créer un autre
     */
    @FXML
    public void handleNouveau() {

    }

    @FXML
    public void handleModifier() {

    }

    @FXML
    public void handleEnregistrer() {

    }

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

        if (Connexion.peutLire(EnvoiMessage.class)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Appli.class.getResource("../view/selectionChampsReport.fxml"));

            BorderPane page = loader.load();

            SelectionChampsReport selectionChampsReport = loader.getController();

            selectionChampsReport.setAttributs(listAttributs);
            selectionChampsReport.setRequete(requeteFiltrage);
            selectionChampsReport.setTitre("Liste des messages envoyés");
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

    String requeteFiltrage = "select * from envoi_message";

    @FXML
    public void handleFiltrer() {

        filtrer(false);
    }

    EnvoiMessageFacade envoiMessageFacade = new EnvoiMessageFacade();

    private void filtrer(boolean raffraichir) {

        requeteFiltrage = "select * from envoi_message";
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
        listeEnvoiMessage.clear();
        listeEnvoiMessage.addAll(envoiMessageFacade.findAllNative(requeteFiltrage));
    }


}


