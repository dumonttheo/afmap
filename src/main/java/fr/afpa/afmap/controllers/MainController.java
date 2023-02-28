package fr.afpa.afmap.controllers;

import fr.afpa.afmap.HelloApplication;
import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formateur;
import fr.afpa.afmap.model.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
    private Group drawingGroup;


    private ArrayList<BatimentFormation> batimentFormationArrayList = new ArrayList<BatimentFormation>();
    private ArrayList<Formation> listeAllFormations = new ArrayList<Formation>();

    private ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();


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

        shapeCDA.setFill(Color.TRANSPARENT);

        shapeCDA.setCursor(Cursor.HAND);

        shapeCDA.setOnMouseEntered(event-> {
            if(!shapeCDA.getFill().equals(Color.BLUE)){
            shapeCDA.setFill(Color.RED);
            }
        });
        shapeCDA.setOnMouseExited(event-> {
            if(shapeCDA.getFill().equals(Color.RED)){
                shapeCDA.setFill(Color.TRANSPARENT);
            }
        });

        shapeCDA.setOnMouseClicked(mouseEvent -> {
            shapeCDA.setFill(Color.BLUE);
            for (Formation form : listeAllFormations){
                if(form.getNom() == "CDA"){
                    comboBat.getSelectionModel().selectFirst();
                    comboFormation.getSelectionModel().select(form);
                }
            }
        });

        drawingGroup.getChildren().addAll(shapeCDA);





//        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
            width = newVal.doubleValue();

            changePlaceRectangle(newVal.doubleValue(), 22.21, 14.35 , Math.round(newVal.doubleValue() / 1.51), 60 , 44 ,shapeCDA, 1536, 1014);

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


            if(comboFormation.getSelectionModel().getSelectedItem().getNom() == "CDA"){
                shapeCDA.setFill(Color.LIGHTBLUE);
            } else {
                shapeCDA.setFill(Color.TRANSPARENT);
            }
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


    /**
     * Manage a Rectangle to follow rezizing window
     *
     * @param newReso New Resolution after rezizing (only width)
     * @param x   Layout X. Distance between left image and top-left point of rectangle
     * @param y   Layout Y. Distance between Top image and top-left point of rectangle
     * @param heigthReso New Resolution heigth after rezizing
     * @param firstWidth First width of rectangle (on initialization)
     * @param firstHeigth First heigth of rectangle (on initialization)
     * @param rectangle Rectangle to manage
     * @param oldReso First Width at initilisation
     * @param oldResoHeigth First Heigth at initilisation
     */

    public static void changePlaceRectangle(double newReso, double x, double y, double heigthReso, double firstWidth, double firstHeigth, Rectangle rectangle, double oldReso, double oldResoHeigth) {

        double reso = 0;
        double resoHeigth = 0;

//        Check if new Resolution is different to last resolution
        if (oldReso != newReso) {
//            Calcul distance X Layout (can be negativ number)
            reso = Math.floor(newReso * x / 100) - Math.floor(oldReso * x / 100);

//            Calcul distance Y Layout (can be negativ number)
            resoHeigth = Math.floor(heigthReso * y / 100) - Math.floor(oldResoHeigth * y / 100);

        }

//        Calcul ratio of width at the first resolution
        double ratioWidth = firstWidth * 100 / oldReso;

//        Calcul ration of heigth at the first Resolution
        double ratioHeigth = firstHeigth * 100 / oldResoHeigth;

//  Set rectangle whit new values
        rectangle.setLayoutY(resoHeigth);
        rectangle.setLayoutX(reso);
        rectangle.setWidth(newReso * ratioWidth / 100);
        rectangle.setHeight( Math.floor(heigthReso * ratioHeigth / 100));

}

}
