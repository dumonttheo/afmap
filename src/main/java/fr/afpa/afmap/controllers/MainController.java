package fr.afpa.afmap.controllers;

import fr.afpa.afmap.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

    private final Label labelService = new Label();
    @FXML
    private ComboBox<String> comboBat;
    @FXML
    private ComboBox<Formation> comboFormation;

    private final ComboBox<Service> comboService = new ComboBox<>();

    @FXML
    private VBox vBoxBat;

    @FXML
    private ImageView imageViewBat;
    @FXML
    private ImageView imageViewNombres;
    @FXML
    public Pane pane;
    @FXML
    private TitledPane titledPaneBat;
    @FXML
    private TitledPane titledPaneForm;

    @FXML
    private Group drawingGroup;

    @FXML
    private GridPane gridPaneBat;
    @FXML
    private VBox vBoxFormateurs;
@FXML
private ScrollPane scrollPane;
@FXML
private ScrollPane scrollPaneForm;
    @FXML
    private Image image;
    public Double widthHeigth;
    private int countFormateurI = 0;




    public Double getWidthHeigth() {
        return widthHeigth;
    }

    public Double newValeur;

    private final ArrayList<Shape> squareArrayList = new ArrayList<>();
    private final ArrayList<Service> administrativList = new ArrayList<>();


    //creation des listes qui s'afficheront dans les comboBox via l'observableList
    List<String> typeBatimentListe = new ArrayList<>();

    List<Formation> formationList = new ArrayList<>();



    public void initialize() {
        drawingGroup.setViewOrder(1);
//                    imageViewNombres.setViewOrder(-1);
        imageViewBat.setViewOrder(2);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-border-color: transparent; -fx-background-color:transparent;");
        scrollPaneForm.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneForm.setStyle("-fx-border-color: transparent; -fx-background-color:transparent;");

//        Create all formation
        getAllFormation();

//        Create all Square
        createAllSquare();


//        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            //            Change imageViewBat whit to new pan width
            newValeur = newVal.doubleValue();
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
            imageViewNombres.fitWidthProperty().bind(pane.widthProperty());
//            Instancie width to parameter new val
            widthHeigth = newVal.doubleValue() / 1.61;

//            Use Function SwapPlaceRectangle to replace all square on the map

            for (Formation formation : formationList) {
                for (Batiment batiment : formation.getListeBatimentsFormation()) {
                    if (batiment.isASquare()) {
                        swapPlaceRectangle(newVal.doubleValue(), batiment.getX1(), batiment.getY1(), Math.round(newVal.doubleValue() / 1.51), batiment.getWidth(), batiment.getHeigth(), (Rectangle) batiment.getShape(), 1536, 1014);
                    } else {
                        swapPlacePolygon(newVal.doubleValue(), Math.round(newVal.doubleValue() / 1.51), (Polygon) batiment.getShape(), 1536, 1014, batiment.getAllPoints());
                    }
                }
            }
            for (Service service : administrativList) {
                for (Batiment batiment : service.getListBatiment()) {
                    if (batiment.isASquare()) {
                        swapPlaceRectangle(newVal.doubleValue(), batiment.getX1(), batiment.getY1(), Math.round(newVal.doubleValue() / 1.51), batiment.getWidth(), batiment.getHeigth(), (Rectangle) batiment.getShape(), 1536, 1014);
                    } else {
                        swapPlacePolygon(newVal.doubleValue(), Math.round(newVal.doubleValue() / 1.51), (Polygon) batiment.getShape(), 1536, 1014, batiment.getAllPoints());
                    }
                }
            }


        });

        pane.heightProperty().addListener((obs1, oldVal1, newVal1) -> {
//            Instancie width to parameter new val
            widthHeigth = newValeur / 1.61;
        });

//  Add in ComboBox all Formation whit an ArrayList FormationList
        ObservableList<Formation> formationObservableList = FXCollections.observableArrayList(formationList);
        ObservableList<Service> servicesObservableList = FXCollections.observableArrayList(administrativList);

        comboFormation.setItems(formationObservableList);
        comboService.setItems(servicesObservableList);


        //ajouter les deux types de batiments dans la comboBat
        typeBatimentListe.add("Plateau formation");
        typeBatimentListe.add("Administratif");
        ObservableList<String> typeBatListe = FXCollections.observableArrayList(typeBatimentListe);
        comboBat.setItems(typeBatListe);

        //ComboFormation invisible
        comboFormation.setVisible(false);
        labelFormation.setVisible(false);


        comboBat.setOnAction(event -> {
            setCombobox();

        });


        comboFormation.setOnAction(event -> {
            countFormateurI = 0;
            vBoxBat.getChildren().clear();
            vBoxFormateurs.getChildren().clear();
            for (BatimentFormation batiment : comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation()) {
                vBoxBat.getChildren().add(new Label(batiment.getNom()));
            }
            if (comboFormation.getSelectionModel().getSelectedItem().getListePersonnel().size() > 1) {
                titledPaneForm.setText("Formateurs");
            } else {
                titledPaneForm.setText("Formateur");
            }
            if (comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation().size() > 1) {
                titledPaneBat.setText("Batiments");
            } else {
                titledPaneBat.setText("Batiment");
            }
            for (Personnel personnel : comboFormation.getSelectionModel().getSelectedItem().getListePersonnel()) {
                int countFormateurs = comboFormation.getSelectionModel().getSelectedItem().getListePersonnel().size();
                countFormateurI++;
                vBoxFormateurs.getChildren().add(new Label(personnel.getNom() + " " + personnel.getPrenom()));
                vBoxFormateurs.getChildren().add(new Label(personnel.getMail()));
                vBoxFormateurs.getChildren().add(new Label(personnel.getNumeroTelephone()));
                if (countFormateurI != countFormateurs) {
                    vBoxFormateurs.getChildren().add(new Label(" "));
                }


            }

            for (Shape shape : squareArrayList) {
                shape.setFill(Color.TRANSPARENT);
            }

            Formation formationSelected = comboFormation.getSelectionModel().getSelectedItem();
            for (Batiment batForm : formationSelected.getListeBatimentsFormation()) {
                batForm.getShape().setFill(batForm.getColor());
            }

        });

        comboService.setOnAction(actionEvent -> {
            countFormateurI = 0;
            vBoxBat.getChildren().clear();
            vBoxFormateurs.getChildren().clear();
            for (BatimentAdministratif batiment : comboService.getSelectionModel().getSelectedItem().getListBatiment()) {
                vBoxBat.getChildren().add(new Label(batiment.getNom()));
            }
            if (comboService.getSelectionModel().getSelectedItem().getListePersonnel().size() > 1) {
                titledPaneForm.setText("Personnels");
            } else {
                titledPaneForm.setText("Personnel");
            }
            if (comboService.getSelectionModel().getSelectedItem().getListBatiment().size() > 1) {
                titledPaneBat.setText("Batiments");
            } else {
                titledPaneBat.setText("Batiment");
            }
            for (Personnel personnel : comboService.getSelectionModel().getSelectedItem().getListePersonnel()) {
                int countFormateurs = comboService.getSelectionModel().getSelectedItem().getListePersonnel().size();
                countFormateurI++;
                vBoxFormateurs.getChildren().add(new Label(personnel.getNom() + " " + personnel.getPrenom()));
                vBoxFormateurs.getChildren().add(new Label(personnel.getMail()));
                vBoxFormateurs.getChildren().add(new Label(personnel.getNumeroTelephone()));
                if (countFormateurI != countFormateurs) {
                    vBoxFormateurs.getChildren().add(new Label(" "));
                }


            }

            for (Shape shape : squareArrayList) {
                shape.setFill(Color.TRANSPARENT);
            }

            Service serviceSelected = comboService.getSelectionModel().getSelectedItem();
            for (Batiment batForm : serviceSelected.getListBatiment()) {
                batForm.getShape().setFill(batForm.getColor());
            }

        });


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
        BatimentFormation batCDA = new BatimentFormation(9, 20.70, 14.76, 60.0, 44.0, Color.RED);
        BatimentFormation batCommerce = new BatimentFormation(9, 25.00, 14.76, 84.0, 44.0, Color.BLUE);
        BatimentFormation batAPH = new BatimentFormation(8, 37.60, 8.25, 45.5, 149.0, Color.BLUE);
        BatimentFormation batAES = new BatimentFormation(7, 43.75, 8.25, 42.5, 149.0, Color.LIGHTGREEN);
        BatimentFormation batCarrelage = new BatimentFormation(6, 49.83, 8.30, 37.0, 149.0, Color.CHARTREUSE);
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
        }, Color.PINK);
        BatimentFormation batMaconMain = new BatimentFormation(25, 20.05, 51.6, 100.0, 110.0, Color.BROWN);
        BatimentFormation batMaconDepotFirst = new BatimentFormation(25, new Double[]{
                399.0, 678.0,
                500.0, 679.0,
                500.0, 730.0,
                399.0, 730.0
        }, Color.BROWN);
        BatimentFormation batMaconDepotSecond = new BatimentFormation(26, new Double[]{
                180.0, 680.0,
                318.0, 710.0,
                306.0, 774.0,
                180.0, 774.0
        }, Color.PINK);
        BatimentAdministratif batAccueil = new BatimentAdministratif(3, new Double[]{
                1051.0, 184.0,
                1083.0, 169.0,
                1086.0, 175.0,
                1099.0, 175.0,
                1099.0, 171.0,
                1119.0, 171.0,
                1119.0, 197.0,
                1110.0, 197.0,
                1110.0, 219.0,
                1119.0, 219.0,
                1119.0, 280.0,
                1105.0, 280.0,
                1105.0, 289.0,
                1072.0, 289.0,
                1072.0, 220.0,
                1050.0, 220.0

        }, Color.LIGHTSALMON);


//        Create all formateur
        Personnel ludo = new Personnel("Ludo", "Esperce", "0612345678", "ludo@gmail.com");
        Personnel jean = new Personnel("Jean", "Jacques", "0612345678", "jj@gmail.com");
        Personnel secretaire = new Personnel("Micheline", "Micheline", "0123456789", "micheline.micheline@gmail.com");


//      Crete All formation whit formateur and building
        Formation cda = new Formation("CDA", batCDA);
        Formation commerce = new Formation("Commerce", batCommerce);
        Formation aph = new Formation("APH", batAPH);
        Formation aes = new Formation("AES", batAES);
        Formation carrelage = new Formation("Carrelage", batCarrelage);
        Formation oldCDA = new Formation("OLD CDA", batOldCDA);
        Formation macon = new Formation("Maçon", batMaconMain);
        Service accueil = new Service("Accueil");

        accueil.addPersonnel(secretaire);
        accueil.addBatiment(batAccueil);

        batAccueil.addService(accueil);


        formationList.add(cda);
        formationList.add(commerce);
        formationList.add(aph);
        formationList.add(aes);
        formationList.add(carrelage);
        formationList.add(oldCDA);
        formationList.add(macon);
        administrativList.add(accueil);

        cda.addPersonnel(ludo);
        cda.addPersonnel(jean);

        macon.addBatiment(batMaconDepotFirst);
        macon.addBatiment(batMaconDepotSecond);

//        Add formation to a batiment
        batCDA.addFormation(cda);
        batCommerce.addFormation(commerce);
        batAPH.addFormation(aph);
        batAES.addFormation(aes);
        batCarrelage.addFormation(carrelage);
        batOldCDA.addFormation(oldCDA);
        batMaconMain.addFormation(macon);
        batMaconDepotFirst.addFormation(macon);
        batMaconDepotSecond.addFormation(macon);
    }


    /**
     * Generate all square and give list of rectangle
     */
    public void createAllSquare() {

        for (Formation formation : formationList) {
            for (BatimentFormation batiment : formation.getListeBatimentsFormation()) {
                if (batiment.isASquare()) {

//       Generare a square
                    Rectangle shape = new Rectangle(batiment.getX1() * 1536 / 100, batiment.getY1() * 1014 / 100, batiment.getWidth(), batiment.getHeigth());

//      Add shape to ArrayList
                    squareArrayList.add(shape);
                    batiment.setShape(shape);
//      Fill Color Transparent
                    shape.setFill(Color.TRANSPARENT);


//      Set Cursor to Hand
                    shape.setCursor(Cursor.HAND);


//      Event Listener of square
                    shape.setOnMouseEntered(event -> {
//                Verify if is clicked before whit Color of square
                        if (!shape.getFill().equals(batiment.getColor())) {
                            shape.setFill(Color.LIGHTGRAY);

                        }
                    });
                    shape.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                        if (!shape.getFill().equals(batiment.getColor())) {
                            shape.setFill(Color.TRANSPARENT);
                        }
                    });

                    shape.setOnMouseClicked(mouseEvent -> {

//                change all Square to Transparent Color
                        for (Shape square : squareArrayList) {
                            square.setFill(Color.TRANSPARENT);
                        }
//      Change all batiment of formation to Color
                        for (Batiment bat : formation.getListeBatimentsFormation()) {
                            bat.getShape().setFill(bat.getColor());
                        }

//  Change on Combobox all information.
                        comboBat.getSelectionModel().selectFirst();
                        comboFormation.getSelectionModel().select(formation);

                    });

                    drawingGroup.getChildren().addAll(shape);
                } else {
                    Polygon polygon = new Polygon();
                    polygon.getPoints().addAll(batiment.getAllPoints());
                    polygon.setFill(Color.TRANSPARENT);
                    polygon.setCursor(Cursor.HAND);

                    drawingGroup.getChildren().add(polygon);

                    batiment.setShape(polygon);
                    squareArrayList.add(polygon);


                    //      Event Listener of square
                    polygon.setOnMouseEntered(event -> {
//                Verify if is clicked before whit Color of square
                        if (!polygon.getFill().equals(batiment.getColor())) {
                            polygon.setFill(Color.LIGHTGRAY);

                        }
                    });

                    polygon.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                        if (!polygon.getFill().equals(batiment.getColor())) {
                            polygon.setFill(Color.TRANSPARENT);
                        }
                    });

                    polygon.setOnMouseClicked(event -> {
//                        change all Square to Transparent Color
                        for (Shape square : squareArrayList) {
                            square.setFill(Color.TRANSPARENT);
                        }
//      Change all batiment of formation to Color
                        for (Batiment bat : formation.getListeBatimentsFormation()) {
                            bat.getShape().setFill(bat.getColor());
                        }

//  Change on Combobox all information.
                        comboBat.getSelectionModel().selectFirst();
                        comboFormation.getSelectionModel().select(formation);
                    });


                }
            }


        }

        for (Service service : administrativList) {
            for (Batiment batiment : service.getListBatiment()) {
                if (batiment.isASquare()) {

//       Generare a square
                    Rectangle shape = new Rectangle(batiment.getX1() * 1536 / 100, batiment.getY1() * 1014 / 100, batiment.getWidth(), batiment.getHeigth());

//      Add shape to ArrayList
                    squareArrayList.add(shape);
                    batiment.setShape(shape);
//      Fill Color Transparent
                    shape.setFill(Color.TRANSPARENT);


//      Set Cursor to Hand
                    shape.setCursor(Cursor.HAND);


//      Event Listener of square
                    shape.setOnMouseEntered(event -> {
//                Verify if is clicked before whit Color of square
                        if (!shape.getFill().equals(batiment.getColor())) {
                            shape.setFill(Color.LIGHTGRAY);

                        }
                    });
                    shape.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                        if (!shape.getFill().equals(batiment.getColor())) {
                            shape.setFill(Color.TRANSPARENT);
                        }
                    });

                    shape.setOnMouseClicked(mouseEvent -> {

//                change all Square to Transparent Color
                        for (Shape square : squareArrayList) {
                            square.setFill(Color.TRANSPARENT);
                        }
//      Change all batiment of formation to Color
                        for (Batiment bat : service.getListBatiment()) {
                            bat.getShape().setFill(bat.getColor());
                        }

//  Change on Combobox all information.
                        comboBat.getSelectionModel().selectLast();
                        comboService.getSelectionModel().select(service);

                    });

                    drawingGroup.getChildren().addAll(shape);
                } else {
                    Polygon polygon = new Polygon();
                    polygon.getPoints().addAll(batiment.getAllPoints());
                    polygon.setFill(Color.TRANSPARENT);
                    polygon.setCursor(Cursor.HAND);

                    drawingGroup.getChildren().add(polygon);

                    batiment.setShape(polygon);
                    squareArrayList.add(polygon);


                    //      Event Listener of square
                    polygon.setOnMouseEntered(event -> {
//                Verify if is clicked before whit Color of square
                        if (!polygon.getFill().equals(batiment.getColor())) {
                            polygon.setFill(Color.LIGHTGRAY);

                        }
                    });

                    polygon.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                        if (!polygon.getFill().equals(batiment.getColor())) {
                            polygon.setFill(Color.TRANSPARENT);
                        }
                    });

                    polygon.setOnMouseClicked(event -> {
//                        change all Square to Transparent Color
                        for (Shape square : squareArrayList) {
                            square.setFill(Color.TRANSPARENT);
                        }
//      Change all batiment of formation to Color
                        for (Batiment bat : service.getListBatiment()) {
                            bat.getShape().setFill(bat.getColor());
                        }

//  Change on Combobox all information.
                        comboBat.getSelectionModel().selectLast();
                        comboService.getSelectionModel().select(service);
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

    public void setCombobox() {
        if (comboBat.getSelectionModel().isSelected(0)) {
            if (gridPaneBat.getChildren().remove(comboService)){
                gridPaneBat.getChildren().remove(comboService);
                gridPaneBat.getChildren().remove(labelService);
                gridPaneBat.add(comboFormation, 1, 1);
                gridPaneBat.add(labelFormation, 0, 1);
            }
            labelFormation.setVisible(true);
            comboFormation.setVisible(true);
            for (Shape shape : squareArrayList){
                shape.setFill(Color.TRANSPARENT);
            }
            if (comboFormation.getSelectionModel().getSelectedItem() != null){
                for (Batiment batiment : comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation()){
                    batiment.getShape().setFill(batiment.getColor());
                }
            }

        }else {
            if (gridPaneBat.getChildren().remove(comboFormation)){
                gridPaneBat.getChildren().remove(comboFormation);
                gridPaneBat.getChildren().remove(labelFormation);
                gridPaneBat.add(comboService, 1, 1);
                gridPaneBat.add(labelService, 0, 1);
            }
            labelService.setText("Services");

            for (Shape shape : squareArrayList){
                shape.setFill(Color.TRANSPARENT);
            }
            if (comboService.getSelectionModel().getSelectedItem() != null){
                for (Batiment batiment : comboService.getSelectionModel().getSelectedItem().getListBatiment()){
                    batiment.getShape().setFill(batiment.getColor());
                }
            }

        }


    }



}
