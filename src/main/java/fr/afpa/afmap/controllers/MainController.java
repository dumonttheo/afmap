package fr.afpa.afmap.controllers;

import fr.afpa.afmap.HelloApplication;
import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formateur;
import fr.afpa.afmap.model.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Group drawingGroup;


//dfdbf

    private ArrayList<BatimentFormation> batimentFormationArrayList = new ArrayList<BatimentFormation>();
    private ArrayList<Formation> listeAllFormations = new ArrayList<Formation>();

    private ArrayList<Canvas> canvasArrayList = new ArrayList<>();


    //ajout batiments
    BatimentFormation batFor1 = new BatimentFormation(64, 22.21, 9.55, 27.41, 9.55, 22.21, 13.10, 27.41, 13.10);
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

    Double width;


    public void initialize() {

        Rectangle shapeCDA = new Rectangle(341.0,146.0, 60,44);
        shapeCDA.setFill(Color.RED);
        drawingGroup.getChildren().add(shapeCDA);


//  Main Canvas Fill
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();


//        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
            System.out.println(pane.getHeight());
            width = newVal.doubleValue();

            changePlaceRectangle(newVal.doubleValue(), 22.21, 14.35 , Math.round(newVal.doubleValue() / 1.51), shapeCDA, 1536, 1014);

        });

//        LISTENER HEIGHT
        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            mainCanvas.setWidth(width);
            mainCanvas.setHeight(newVal.doubleValue());
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
//
//        pane.setOnMouseMoved(mouseEvent -> {
//            System.out.println("---------------------");
//            System.out.println("Mouse en X : " + mouseEvent.getX());
//            System.out.println("Mouse en Y : " + mouseEvent.getY());
//            System.out.println("Taille de l'image :" + imageViewBat.fitWidthProperty().get());
//            System.out.println("Hauteur de l'image : " + pane.getHeight());
//        });


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


    public static double getLayout(double x, double reso) {
        return x * reso / 100;
    }


    public static void changePlaceRectangle(double newReso, double x, double y, double heigthReso, Rectangle rectangle, double oldReso, double oldResoHeigth) {

//

//        rectangle.
        double reso = 0;
        double resoHeigth = 0;
        if (oldReso != newReso) {
            reso = Math.floor(newReso * x / 100) - Math.floor(oldReso * x / 100);
            resoHeigth = Math.floor(heigthReso * y / 100) - Math.floor(oldResoHeigth * y / 100);

        }


        rectangle.setLayoutY(resoHeigth);
        rectangle.setLayoutX(reso);

}


}
