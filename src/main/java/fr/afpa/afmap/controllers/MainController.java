package fr.afpa.afmap.controllers;

import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formateur;
import fr.afpa.afmap.model.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public Label labelNomFormateur;
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
    private ImageView imageViewBat;
    @FXML
    public Pane pane;

    @FXML
    private Group drawingGroup;

    @FXML
    private Image image;
    public Double widthHeigth;

    public Double getWidthHeigth() {
        return widthHeigth;
    }

    private final ArrayList<BatimentFormation> batimentFormationArrayList = new ArrayList<>();
    private final ArrayList<Formation> listeAllFormations = new ArrayList<>();

    private final ArrayList<Shape> squareArrayList = new ArrayList<>();



    //creation des listes qui s'afficheront dans les comboBox via l'observableList
    List<String> typeBatimentListe = new ArrayList<>();
    List<Formation> formationList = new ArrayList<>();

    Double width;



    public void initialize() {

//        Create all formation
        getAllFormation();

//        Create all Square
        createAllSquare();


//        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
//            Change imageViewBat whit to new pan width
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
            pane.setMaxHeight((double) newVal/1.51);
//            Instancie width to parameter new val
            widthHeigth = newVal.doubleValue()/1.51;

//            Use Function SwapPlaceRectangle to replace all square on the map
            for (int i = 0; i < squareArrayList.size(); i++) {

                if (batimentFormationArrayList.get(i).isASquare()) {
                    swapPlaceRectangle(newVal.doubleValue(), batimentFormationArrayList.get(i).getTopLeftX(), batimentFormationArrayList.get(i).getTopLeftY(), Math.round(newVal.doubleValue() / 1.51), batimentFormationArrayList.get(i).getWidth(), batimentFormationArrayList.get(i).getHeigth(), (Rectangle) squareArrayList.get(i), 1536, 1014);
                } else {
                    swapPlacePolygon(newVal.doubleValue(), Math.round(newVal.doubleValue() / 1.51), (Polygon) squareArrayList.get(i), 1536, 1014, batimentFormationArrayList.get(i).getAllPoints());
                }
            }

        });

        image.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });


        //recuperer toutes les formations de tous les batiments_formation et les afficher dans la comboFormation
        System.out.println(batimentFormationArrayList);
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

            for (int i = 0; i < formationList.size(); i++) {
                if (comboFormation.getSelectionModel().getSelectedItem().getNom().equals(formationList.get(i).getNom())) {
                    System.out.println(squareArrayList);
                } else {
                    squareArrayList.get(i).setFill(Color.TRANSPARENT);
                }
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

    }


    /**
     * Manage a Rectangle to follow rezizing window
     *
     * @param newReso       New Resolution after rezizing (only width)
     * @param x             Layout X. Distance between left image and top-left point of rectangle
     * @param y             Layout Y. Distance between Top image and top-left point of rectangle
     * @param heigthReso    New Resolution heigth after rezizing
     * @param firstWidth    First width of rectangle (on initialization)
     * @param firstHeigth   First heigth of rectangle (on initialization)
     * @param rectangle     Rectangle to manage
     * @param oldReso       First Width at initilisation
     * @param oldResoHeigth First Heigth at initilisation
     */

    public static void swapPlaceRectangle(double newReso, double x, double y, double heigthReso, double firstWidth, double firstHeigth, Rectangle rectangle, double oldReso, double oldResoHeigth) {

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
        rectangle.setHeight(Math.floor(heigthReso * ratioHeigth / 100));

    }


    /**
     * Generate All Formation
     */
    public void getAllFormation() {

//        Create all buildings
        BatimentFormation batCDA = new BatimentFormation(9, 20.70, 14.76, 60.0, 44.0);
        BatimentFormation batCommerce = new BatimentFormation(9, 25.00, 14.76, 84.0, 44.0);
        BatimentFormation batAPH = new BatimentFormation(8, 37.60, 8.25, 45.5, 149.0);
        BatimentFormation batAES = new BatimentFormation(7, 43.75, 8.25, 42.5, 149.0);
        BatimentFormation batCarrelage = new BatimentFormation(6, 49.83, 8.30, 37.0, 149.0);
        BatimentFormation batOldCDA = new BatimentFormation(58, new Double[]{
                474.0, 613.0,
                502.0, 613.0,
                502.0, 620.0,
                532.0, 620.0,
                532.0, 613.0,
                560.0, 613.0,
                560.0, 648.0,
                532.0, 648.0,
                532.0, 641.0,
                502.0, 641.0,
                502.0, 648.0,
                474.0, 648.0
        });
        BatimentFormation batMaconMain = new BatimentFormation(25, 20.05,51.6,100.0,110.0);
        BatimentFormation batMaconDepotFirst = new BatimentFormation(25, new Double[]{
            399.0, 683.0,
                500.0,683.0,
                500.0,730.0,
                399.0,730.0
        });


//        Create all formateur
        Formateur ludo = new Formateur("Ludo", "Esperce", "0612345678", "ludo@gmail.com");
        Formateur jean = new Formateur("Jean", "Jacques", "0612345678", "jj@gmail.com");


//      Crete All formation whit formateur and building
        Formation cda = new Formation("CDA", ludo, batCDA, Color.RED);
        Formation commerce = new Formation("Commerce", jean, batCommerce, Color.BLUE);
        Formation aph = new Formation("APH", jean, batAPH, Color.GREEN);
        Formation aes = new Formation("AES", jean, batAES, Color.BLUE);
        Formation carrelage = new Formation("Carrelage", jean, batCarrelage, Color.BLUE);
        Formation oldCDA = new Formation("OLD CDA", ludo, batOldCDA, Color.PINK);
        Formation macon = new Formation("Macon", jean, batMaconMain, Color.BROWN);


        formationList.add(cda);
        formationList.add(commerce);
        formationList.add(aph);
        formationList.add(aes);
        formationList.add(carrelage);
        formationList.add(oldCDA);
        formationList.add(macon);
        macon.addBatiment(batMaconDepotFirst);


//        Add formation to a batiment
        batCDA.addFormation(cda);
        batCommerce.addFormation(commerce);
        batAPH.addFormation(aph);
        batAES.addFormation(aes);
        batCarrelage.addFormation(carrelage);
        batOldCDA.addFormation(oldCDA);
        batMaconMain.addFormation(macon);
        batMaconDepotFirst.addFormation(macon);


//      Add all formation to array list of batiments
        batimentFormationArrayList.add(batCDA);
        batimentFormationArrayList.add(batCommerce);
        batimentFormationArrayList.add(batAPH);
        batimentFormationArrayList.add(batAES);
        batimentFormationArrayList.add(batCarrelage);
        batimentFormationArrayList.add(batOldCDA);
        batimentFormationArrayList.add(batMaconMain);
//        batimentFormationArrayList.add(batMaconDepotFirst);


    }


    /**
     * Generate all square and give list of rectangle
     */
    public void createAllSquare() {

        for (Formation formation : formationList) {
            for (BatimentFormation batiment : formation.getListeBatimentsFormation()) {
                if (batiment.isASquare()) {

//       Generare a square
                    Rectangle shape = new Rectangle(batiment.getTopLeftX() * 1536 / 100, batiment.getTopLeftY() * 1014 / 100, batiment.getWidth(), batiment.getHeigth());

//      Add shape to ArrayList
                    squareArrayList.add(shape);

//      Fill Color Transparent
                    shape.setFill(Color.TRANSPARENT);


//      Set Cursor to Hand
                    shape.setCursor(Cursor.HAND);


//      Event Listener of square
                    shape.setOnMouseEntered(event -> {
//                Verify if is clicked before whit Color of square
                        if (!shape.getFill().equals(formation.getCouleur())) {
                            shape.setFill(Color.LIGHTGRAY);

                        }
                    });
                    shape.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                        if (!shape.getFill().equals(formation.getCouleur())) {
                            shape.setFill(Color.TRANSPARENT);
                        }
                    });

                    shape.setOnMouseClicked(mouseEvent -> {

//                change all Square to Transparent Color
                        for (Shape square : squareArrayList) {
                            square.setFill(Color.TRANSPARENT);
                        }
//      Change all batiment of formation to Color
                        for (BatimentFormation ignored : formation.getListeBatimentsFormation()) {
                            shape.setFill(formation.getCouleur());
                        }

//  Change on Combobox all information.
                        for (Formation formationL : formationList) {

                            if (formationL.getNom().equals(formation.getNom())) {
                                comboBat.getSelectionModel().selectFirst();
                                comboFormation.getSelectionModel().select(formationL);

                            }
                        }

                    });

                    drawingGroup.getChildren().addAll(shape);
                } else {
                    Polygon polygon = new Polygon();
                    polygon.getPoints().addAll(batiment.getAllPoints());
//                    polygon.setFill(Color.TRANSPARENT);
                    polygon.setCursor(Cursor.HAND);

                    drawingGroup.getChildren().add(polygon);

                    squareArrayList.add(polygon);


                    //      Event Listener of square
                    polygon.setOnMouseEntered(event -> {
//                Verify if is clicked before whit Color of square
                        if (!polygon.getFill().equals(formation.getCouleur())) {
                            polygon.setFill(Color.LIGHTGRAY);

                        }
                    });

                    polygon.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                        if (!polygon.getFill().equals(formation.getCouleur())) {
                            polygon.setFill(Color.TRANSPARENT);
                        }
                    });

                    polygon.setOnMouseClicked(event -> {
//                        change all Square to Transparent Color
                        for (Shape square : squareArrayList) {
                            square.setFill(Color.TRANSPARENT);
                        }
//      Change all batiment of formation to Color
                        for (BatimentFormation ignored : formation.getListeBatimentsFormation()) {
                            polygon.setFill(formation.getCouleur());
                        }

//  Change on Combobox all information.
                        for (Formation formationL : formationList) {

                            if (formationL.getNom().equals(formation.getNom())) {
                                comboBat.getSelectionModel().selectFirst();
                                comboFormation.getSelectionModel().select(formationL);

                            }
                        }
                    });


                }
            }


        }

    }


    /**
     * Change Layout x and y for responsiv polygon
     *
     * @param newReso       New resolution
     * @param heigthReso    New Heigth resolution
     * @param polygon       Polygon to moove
     * @param oldReso       Old width Resolution
     * @param oldResoHeigth old Heigth Resolution
     * @param allPoints     Get All points at the first polygon
     */
    public void swapPlacePolygon(double newReso, double heigthReso, Polygon polygon, double oldReso, double oldResoHeigth, Double[] allPoints) {
//  Check if new resolution is different than old resolution

//  Create a list of Double to get all points in a list
            List<Double> newPoints = new ArrayList<>();

            for (int i = 0; i < allPoints.length; i++) {
//  Check if i is divisible by 2 for get only layout X
                if (i % 2 == 0) {
//  Add to list NewPoints new Layout X
                    newPoints.add(Math.floor(newReso * allPoints[i] / oldReso));
                } else {
//  Add to list NewPoints new Layout Y
                    newPoints.add(Math.floor(heigthReso * allPoints[i] / oldResoHeigth));
                }
            }
//  Set all points a the polygon
            polygon.getPoints().setAll(newPoints);
        }



}
