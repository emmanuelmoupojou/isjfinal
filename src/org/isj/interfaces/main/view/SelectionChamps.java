package org.isj.interfaces.main.view;

import ar.com.fdvs.dj.domain.constants.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.isj.etats.dynamicreports.SimpleDynamicReport;
import org.isj.traitementmetier.entites.Candidat;

import java.net.URL;
import java.util.*;

public class SelectionChamps implements Initializable {

    @FXML
    private TableView<Choix> tableSelection=new TableView();
    private ObservableList<Choix> listeChoix=FXCollections.observableArrayList();

    @FXML
    private TableColumn<Choix, CheckBox> imprimer;

    @FXML
    private TableColumn<Choix, String> categorie;
    private ObservableList<String> listeAttributs;

    private String requete;
    private String titre;
    private String sousTitre;
    private Page orientation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imprimer = new TableColumn("Imprimer");
        categorie = new TableColumn("Categorie");

        tableSelection.getColumns().addAll(categorie,imprimer);

        for(String attribut:listeAttributs) {
            listeChoix.add(new Choix(true,attribut));
        }
        tableSelection.setItems(listeChoix);
    }

    @FXML
    public void handleOkSelectionChamps(){

        List<String> champsSelectionnes=new ArrayList<String>();
        for(int i=0;i<tableSelection.getItems().size();i++){
            if(tableSelection.getItems().get(i).isSelected()){
                champsSelectionnes.add(tableSelection.getItems().get(i).getCategorie());
            }
        }
        new SimpleDynamicReport().printDynamicReport(
                getRequete(),
                champsSelectionnes,
                getTitre(),
                getSousTitre(),
                true,
                true,
                getOrientation());
    }

    public void setAttributs(ObservableList<String> listAttributs) {
        this.listeAttributs=listAttributs;
    }

    public String getRequete() {
        return requete;
    }

    public void setRequete(String requete) {
        this.requete = requete;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }

    public Page getOrientation() {
        return orientation;
    }

    public void setOrientation(Page orientation) {
        this.orientation = orientation;
    }
}
