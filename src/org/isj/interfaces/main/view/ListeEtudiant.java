package org.isj.interfaces.main.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.isj.interfaces.main.Appli;
import org.isj.traitementmetier.Isj;
import org.isj.traitementmetier.entites.*;
import org.isj.traitementmetier.facade.EtudiantFacade;
import org.isj.traitementmetier.facade.FiliereFacade;
import org.isj.traitementmetier.facade.SpecialiteFacade;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class ListeEtudiant implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private TextField matricule;

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
    private ComboBox<String> niveau;
    ObservableList<String> listeNiveaux = FXCollections.observableArrayList("1", "2", "3", "4", "5");

    @FXML
    private ComboBox<String> filiere;
    ObservableList<String> listeFilieres = FXCollections.observableArrayList();

    @FXML
    private ComboBox specialite;
    ObservableList<String> listeSpecialites = FXCollections.observableArrayList();

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
    private TableView<Etudiant> table;

    @FXML
    private TableColumn<Etudiant, String> matcolumn;

    @FXML
    private TableColumn<Etudiant, String> nomcolumn;

    @FXML
    private TableColumn<Etudiant, String> prenomcolumn;

    ObservableList<Etudiant> listeEtudiants=FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> attributs;
    ObservableList<String> listAttributs = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> operateurs;
    ObservableList<String> listOperateurs = FXCollections.observableArrayList("<", ">", "<=", ">=", "=", "!=");

    public ListeEtudiant() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operateurs.setItems(listOperateurs);
        niveau.setItems(listeNiveaux);
        try {
            listeFilieres();
            listeSpecialites();
            listEtud();
            AffichePersonne(null);
            table.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> AffichePersonne(newValue)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listeSpecialites() {
        List<Specialite> specialites = new SpecialiteFacade().findAll();
        //System.out.println(etudiants.get(0));

        for(int i=0;i<specialites.size();i++)
            listeSpecialites.add(specialites.get(i).getLibelle());

        specialite.setItems(listeSpecialites);
    }

    private void listeFilieres() {
        List<Filiere> filieres = new FiliereFacade().findAll();
        //System.out.println(etudiants.get(0));

        for(int i=0;i<filieres.size();i++)
        listeFilieres.add(filieres.get(i).getLibelle());

        filiere.setItems(listeFilieres);
    }

    public void listEtud() throws SQLException {
        List<Etudiant> etudiants = new EtudiantFacade().findAll();
        //System.out.println(etudiants.get(0));

        listeEtudiants.addAll(etudiants);

        table.setItems(listeEtudiants);
        matcolumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getMatricule()));
        nomcolumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNom()));
        prenomcolumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getPrenom()));

        ResultSetMetaData resultSetMetaData=new Isj().renvoyerChamp(Etudiant.class);
        ResultSetMetaData resultSetMetaData2=new Isj().renvoyerChamp(Candidat.class);

        for(int i=1;i<=resultSetMetaData.getColumnCount();i++) {
            try {
                listAttributs.add(resultSetMetaData.getColumnName(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(int i=1;i<=resultSetMetaData2.getColumnCount();i++) {
            try {
                listAttributs.add(resultSetMetaData2.getColumnName(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        attributs.setItems(listAttributs);
    }

    public void AffichePersonne(Etudiant et){
        if(et != null){
            code.setText(et.getCodeAuthentification());
            matricule.setText(et.getMatricule());
            nom.setText(et.getNom());
            prenom.setText(et.getPrenom());
            if(et.getSexe().equals(Personne.Sexe.FEMININ)){
                feminin.setSelected(true);
                masculin.setSelected(false);
            }else{
                feminin.setSelected(false);
                masculin.setSelected(true);
            }
            filiere.setValue(et.getClasse().getSpecialite().getFiliere().getLibelle());
            specialite.setValue(et.getClasse().getSpecialite().getLibelle()
            );
            niveau.setValue(et.getClasse().getNiveau().getLibelle());
            date.setValue(et.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            telephone.setText(Integer.toString(et.getTelephone()));
            nompere.setText(et.getNomDuPere());
            telpere.setText(Integer.toString(et.getTelephoneDuPere()));
            profpere.setText(et.getProfessionDuPere());
            nommere.setText(et.getNomDeLaMere());
            telmere.setText(Integer.toString(et.getTelephoneDeLaMere()));
            profmere.setText(et.getProfessionDelaMere());
        }else{
            code.setText("");
            matricule.setText("");
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
    }

    @FXML
    public void handleNouveau() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appli.class.getResource("view/AjoutNouvelEtudiant.fxml"));
        AnchorPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ajouter un étudiant");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        dialogStage.show();
    }
}
