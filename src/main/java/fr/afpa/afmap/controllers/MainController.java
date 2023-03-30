package fr.afpa.afmap.controllers;


import fr.afpa.afmap.dao.DAOFormation;
import fr.afpa.afmap.dao.DAOService;
import fr.afpa.afmap.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    public MainController() {
    }

    @FXML
    private Label labelFormation;

    private final Label labelService = new Label();
    @FXML
    private ComboBox<String> comboBat;
    @FXML
    private ComboBox<Formation> comboFormation;

    @FXML
    private ComboBox<Service> comboService;

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
    private ImageView fleche;
    @FXML
    private ImageView fleche1;
    public Double widthHeigth;
    private int countFormateurI = 0;


    public Double newValeur;

    private final ArrayList<Shape> squareArrayList = new ArrayList<>();
    private ArrayList<Service> administrativList = new ArrayList<>();


    //creation des listes qui s'afficheront dans les comboBox via l'observableList
    List<String> typeBatimentListe = new ArrayList<>();

    List<Formation> formationList = new ArrayList<>();


    public void goToAdministrator() {
        try {
            RootFile.setRoot("arrayAdmin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void initialize() {
        fleche.setViewOrder(1);
        fleche1.setViewOrder(1);
        fleche.setVisible(false);
        fleche1.setVisible(false);
        scrollPane.setViewOrder(2);
        drawingGroup.setViewOrder(1);
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
                    swapPlacePolygon(newVal.doubleValue(), Math.round(newVal.doubleValue() / 1.51), (Polygon) batiment.getShape(), 1536, 1014, batiment.getAllPoints());
                }
            }

            for (Service service : administrativList) {
                for (Batiment batiment : service.getListBatiment()) {
                    swapPlacePolygon(newVal.doubleValue(), Math.round(newVal.doubleValue() / 1.51), (Polygon) batiment.getShape(), 1536, 1014, batiment.getAllPoints());
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
        gridPaneBat.getChildren().remove(comboService);


        comboBat.setOnAction(event -> setCombobox());


        comboFormation.setOnAction(event -> {
            fleche.setVisible(false);
            fleche1.setVisible(false);
            countFormateurI = 0;
            vBoxBat.getChildren().clear();
            vBoxFormateurs.getChildren().clear();
            if (comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation().size() > 2) {
                fleche.setVisible(true);
            }
            if (comboFormation.getSelectionModel().getSelectedItem().getListePersonnel().size() > 1) {
                fleche1.setVisible(true);
            }
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
            fleche.setVisible(false);
            fleche1.setVisible(false);
            countFormateurI = 0;
            vBoxBat.getChildren().clear();
            vBoxFormateurs.getChildren().clear();
            if (comboService.getSelectionModel().getSelectedItem().getListBatiment().size() > 2) {
                fleche.setVisible(true);
            }
            if (comboService.getSelectionModel().getSelectedItem().getListePersonnel().size() > 1) {
                fleche1.setVisible(true);
            }
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

    }


    /**
     * Generate All Formation whit DAO
     */
    public void getAllFormation() {
        DAOFormation daoFormation = new DAOFormation();
        formationList = daoFormation.findAll();

        DAOService daoService = new DAOService();
        administrativList = daoService.findAll();

    }


    /**
     * Generate all square and give list of rectangle
     */
    public void createAllSquare() {
        for (Formation formation : formationList) {
            for (BatimentFormation batiment : formation.getListeBatimentsFormation()) {
                generetaAPolygon(batiment, formation, null);
            }
        }

        for (Service service : administrativList) {
            for (Batiment batiment : service.getListBatiment()) {
                generetaAPolygon(batiment, null, service);
            }
        }

    }


    /**
     * Generate a Polygon of a batiment
     *
     * @param batiment a batiment of Class Batiment who can be a BatimentFormation or a BatimentAdministratif
     * @param formation a formation, can be null if the batiment is a BatimentAdministratif
     * @param service a service, can be null if the batiment is a BatimentFormation
     *
     */
    public void generetaAPolygon(Batiment batiment, Formation formation, Service service) {
        Polygon polygon = new Polygon();
        for (ArrayList<Double> doubles : batiment.getAllPoints()) {
            polygon.getPoints().addAll(doubles);
        }
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

            if (batiment instanceof BatimentFormation) {
                for (Batiment bat : formation.getListeBatimentsFormation()) {
                    bat.getShape().setFill(bat.getColor());
                }
                //  Change on Combobox all information.
                comboBat.getSelectionModel().selectFirst();
                comboFormation.getSelectionModel().select(formation);

            } else if (batiment instanceof BatimentAdministratif) {

                for (Batiment bat : service.getListBatiment()) {
                    bat.getShape().setFill(bat.getColor());
                }
//  Change on Combobox all information.
                comboService.getSelectionModel().select(service);
                comboBat.getSelectionModel().selectLast();
            }
        });
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
    public void swapPlacePolygon(double newReso, double heigthReso, Polygon polygon, double oldReso, double oldResoHeigth, ArrayList<ArrayList<Double>> allPoints) {
//  Create a list of Double to get all points in a list
        List<Double> newPoints = new ArrayList<>();

        for (ArrayList<Double> points : allPoints) {

//  Add to list NewPoints new Layout X
            newPoints.add(Math.floor(newReso * points.get(0) / oldReso));

//  Add to list NewPoints new Layout Y
            newPoints.add(Math.floor(heigthReso * points.get(1) / oldResoHeigth));
        }

//  Set all points a the polygon
        polygon.getPoints().setAll(newPoints);
    }


    /**
     * Method who is called in initialize, do parameter of combobox and set eventHandler
     */
    public void setCombobox() {
        if (comboBat.getSelectionModel().isSelected(0)) {
            if (gridPaneBat.getChildren().remove(comboService)) {
                gridPaneBat.getChildren().remove(comboService);
                gridPaneBat.getChildren().remove(labelService);
                gridPaneBat.add(comboFormation, 1, 1);
                gridPaneBat.add(labelFormation, 0, 1);
            }
            labelFormation.setVisible(true);
            comboFormation.setVisible(true);
            for (Shape shape : squareArrayList) {
                shape.setFill(Color.TRANSPARENT);
            }
            if (comboFormation.getSelectionModel().getSelectedItem() != null) {
                fleche.setVisible(false);
                fleche1.setVisible(false);
                countFormateurI = 0;
                vBoxBat.getChildren().clear();
                vBoxFormateurs.getChildren().clear();
                if (comboFormation.getSelectionModel().getSelectedItem().getListeBatimentsFormation().size() > 2) {
                    fleche.setVisible(true);
                }
                if (comboFormation.getSelectionModel().getSelectedItem().getListePersonnel().size() > 1) {
                    fleche1.setVisible(true);
                }
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

            }


        } else {
            if (gridPaneBat.getChildren().remove(comboFormation)) {
                gridPaneBat.getChildren().remove(comboFormation);
                gridPaneBat.getChildren().remove(labelFormation);
                gridPaneBat.add(comboService, 1, 1);
                gridPaneBat.add(labelService, 0, 1);
            }

            labelService.setText("Services");

            for (Shape shape : squareArrayList) {
                shape.setFill(Color.TRANSPARENT);
            }

            if (comboService.getSelectionModel().getSelectedItem() != null) {
                fleche.setVisible(false);
                fleche1.setVisible(false);
                countFormateurI = 0;
                vBoxBat.getChildren().clear();
                vBoxFormateurs.getChildren().clear();
                if (comboService.getSelectionModel().getSelectedItem().getListBatiment().size() > 2) {
                    fleche.setVisible(true);
                }
                if (comboService.getSelectionModel().getSelectedItem().getListePersonnel().size() > 1) {
                    fleche1.setVisible(true);
                }
                for (Batiment batiment : comboService.getSelectionModel().getSelectedItem().getListBatiment()) {
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

            }
        }

    }


}


