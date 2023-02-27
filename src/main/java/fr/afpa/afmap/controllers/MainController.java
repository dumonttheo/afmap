package fr.afpa.afmap.controllers;

import fr.afpa.afmap.HelloApplication;
import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formateur;
import fr.afpa.afmap.model.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @FXML
    private Pane pane;



//dfdbf

    private ArrayList<BatimentFormation> batimentFormationArrayList = new ArrayList<BatimentFormation>();
    private ArrayList<Formation> listeAllFormations = new ArrayList<Formation>();

    private ArrayList<Canvas> canvasArrayList = new ArrayList<>();


    static GraphicsContext gc;

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


    public void initialize() {
        pane.widthProperty().addListener((obs, oldVal, newVal)->{
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
        });
        batFor1.addFormation(cda);
        batFor2.addFormation(electricien);
        batimentFormationArrayList.add(batFor1);
        batimentFormationArrayList.add(batFor2);
        //recuperer toutes les formations de tous les batiments_formation et les afficher dans la comboFormation
        for (BatimentFormation bat : batimentFormationArrayList) {
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
//
//
//    Canvas canvaCDA = new Canvas(22.55);
//        canvasArrayList.add(canvaCDA);
//        canvasArrayList.add(canvaCDABis);


        comboBat.setOnAction(event -> {
            if (comboBat.getSelectionModel().isSelected(0)) {
                comboFormation.setVisible(true);
                labelFormation.setVisible(true);
            } else {
                comboFormation.setVisible(false);
                labelFormation.setVisible(false);
            }
        });
        comboFormation.setOnAction(event -> {
            vBoxBat.getChildren().clear();
            for (BatimentFormation batiment : comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation()) {
                vBoxBat.getChildren().add(new Label(batiment.getNom()));
            }
            labelNomFormateur.setText(comboFormation.getSelectionModel().getSelectedItem().getFormateur().getNom() + " " + comboFormation.getSelectionModel().getSelectedItem().getFormateur().getPrenom());
            labelMailFormateur.setText(comboFormation.getSelectionModel().getSelectedItem().getFormateur().getMail());
            labelTelephoneFormateur.setText(comboFormation.getSelectionModel().getSelectedItem().getFormateur().getNumeroTelephone());


//            if(comboFormation.getSelectionModel().getSelectedItem().getNom() == "CDA"){
//                GraphicsContext gc = canvaCDABis.getGraphicsContext2D();
//                gc.setFill(Color.LIGHTBLUE);
//                gc.fillRect(0,0,300,300);
//                canvaCDABis.setOpacity(1);
//            } else {
//                canvaCDABis.setOpacity(0);
//            }
        });



//        EVENT ON CDA BAT

//        canvaCDA.setOnMouseEntered(event -> {
//            GraphicsContext gc = canvaCDA.getGraphicsContext2D();
//            gc.setFill(Color.LIGHTBLUE);
//            gc.fillRect(0,0,300,300);
//            canvaCDA.setOpacity(1);
//        });
//
//        canvaCDA.setOnMouseExited(event-> {
//            canvaCDA.setOpacity(0);
//        });
//
//        canvaCDA.setOnMouseClicked(event-> {
//            for (Formation form : listeAllFormations){
//                if(form.getNom() == "CDA"){
//                    comboBat.getSelectionModel().selectFirst();
//                    comboFormation.getSelectionModel().select(form);
//                    GraphicsContext gc = canvaCDABis.getGraphicsContext2D();
//                    gc.setFill(Color.LIGHTBLUE);
//                    gc.fillRect(0,0,300,300);
//                    canvaCDA.setOpacity(1);
//                }
//            }
//        });



    }


}
