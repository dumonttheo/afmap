package fr.afpa.afmap.controllers;

import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formateur;
import fr.afpa.afmap.model.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public Label labelNomFormateur;
    @FXML
    public Label changerceLabel;
    @FXML
    public Label labelTelephoneFormateur;
    @FXML
    public Label labelMailFormateur;
    @FXML
    private Label labelFormation;
    @FXML
    private ComboBox<String> comboBat;
    @FXML
    private ComboBox<Formation> comboFormation;
    @FXML
    private VBox vBoxBat;
    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox hboxBat;
@FXML
private ImageView imageViewBat;


    private ArrayList<BatimentFormation> batimentFormationArrayList = new ArrayList<BatimentFormation>();
    private ArrayList<Formation> listeAllFormations = new ArrayList<Formation>();
    private Double widthBorderPane = 0.0;

    //ajout batiments
    BatimentFormation batFor1 = new BatimentFormation(64, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
    BatimentFormation batFor2 = new BatimentFormation(125, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);

    //ajout formateurs
    Formateur ludo = new Formateur("Ludo", "Esperce", "0612345678", "ludo@gmail.com");
    Formateur jean = new Formateur("Jean", "Jacques", "0612345678", "jj@gmail.com");
    //ajout formations
    Formation cda = new Formation("CDA", ludo, batFor1);
    Formation electricien = new Formation("Electricien", jean, batFor2);

    //creation des listes qui s'afficheront dans les comboBox via l'observableList
    List<String> typeBatimentListe = new ArrayList<String>();
    List<Formation> formationList = new ArrayList<Formation>();



    public void initialize(){
        batFor1.addFormation(cda);
        batFor2.addFormation(electricien);
        batimentFormationArrayList.add(batFor1);
        batimentFormationArrayList.add(batFor2);
        //recuperer toutes les formations de tous les batiments_formation et les afficher dans la comboFormation
        for (BatimentFormation bat : batimentFormationArrayList){
            List<Formation> formationsDuBatiment = bat.getListeFormations();
            listeAllFormations.addAll(formationsDuBatiment);
        }
        ObservableList<Formation> formationObservableList = FXCollections.observableArrayList(listeAllFormations);
        comboFormation.setItems(formationObservableList);

        //ajouter les deux types de batiments dans la comboBat
        typeBatimentListe.add("Plateau formation");
        typeBatimentListe.add("Administratif");
        ObservableList<String> typeBatListe = FXCollections.observableArrayList(typeBatimentListe);
        comboBat.setItems(typeBatListe);

        //ComboFormation invisible
        comboFormation.setVisible(false);
        labelFormation.setVisible(false);

        comboBat.setOnAction(event -> {
            System.out.println(borderPane.widthProperty().get());
            if(comboBat.getSelectionModel().isSelected(0)){
                comboFormation.setVisible(true);
                labelFormation.setVisible(true);
            }else {
                comboFormation.setVisible(false);
                labelFormation.setVisible(false);
            }
        });
        comboFormation.setOnAction(event -> {
            vBoxBat.getChildren().clear();
            for(BatimentFormation batiment : comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation()){
                vBoxBat.getChildren().add(new Label(batiment.getNom()));
            }
            labelNomFormateur.setText(comboFormation.getSelectionModel().getSelectedItem().getFormateur().getNom() + " " + comboFormation.getSelectionModel().getSelectedItem().getFormateur().getPrenom());
            labelMailFormateur.setText(comboFormation.getSelectionModel().getSelectedItem().getFormateur().getMail());
            labelTelephoneFormateur.setText(comboFormation.getSelectionModel().getSelectedItem().getFormateur().getNumeroTelephone());
        });



    }



}
