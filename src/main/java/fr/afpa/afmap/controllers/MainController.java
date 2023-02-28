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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Pane pane;

    @FXML
    private Group drawingGroup;


    private final ArrayList<BatimentFormation> batimentFormationArrayList = new ArrayList<>();
    private final ArrayList<Formation> listeAllFormations = new ArrayList<>();

    private final ArrayList<Rectangle> squareArrayList = new ArrayList<>();


    //creation des listes qui s'afficheront dans les comboBox via l'observableList
    List<String> typeBatimentListe = new ArrayList<>();
    List<Formation> formationList = new ArrayList<>();

    Double width;


    public void initialize() {

//        Create all formation
        getAllFormation();

//        Create all Square
        createAllRectangle();


//        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
//            Change imageViewBat whit to new pan width
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());

//            Instancie width to parameter new val
            width = newVal.doubleValue();

//            Use Function SwapPlaceRectangle to replace all square on the map
            for (int i = 0; i < squareArrayList.size(); i++) {
                swapPlaceRectangle(newVal.doubleValue(), batimentFormationArrayList.get(i).getTopLeftX(), batimentFormationArrayList.get(i).getTopLeftY(), Math.round(newVal.doubleValue() / 1.51), batimentFormationArrayList.get(i).getWidth(), batimentFormationArrayList.get(i).getHeigth(), squareArrayList.get(i), 1536, 1014);
            }

        });


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
                    squareArrayList.get(i).setFill(formationList.get(i).getCouleur());
                } else {
                    squareArrayList.get(i).setFill(Color.TRANSPARENT);
                }
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
        BatimentFormation batCDA = new BatimentFormation(9, 22.21, 14.35, 60.0, 44.0);
        BatimentFormation batCommerce = new BatimentFormation(9, 26.37, 14.35, 84.0, 44.0);
        BatimentFormation batAPH = new BatimentFormation(8,38.74, 8.09, 45.5,146.5 );
        BatimentFormation batAES = new BatimentFormation(7, 44.80,8.0,43.5, 146.6 );
        BatimentFormation batCarrelage = new BatimentFormation(6, 50.75,8.0,36.0, 146.6 );


//        Create all formateur
        Formateur ludo = new Formateur("Ludo", "Esperce", "0612345678", "ludo@gmail.com");
        Formateur jean = new Formateur("Jean", "Jacques", "0612345678", "jj@gmail.com");


//      Crete All formation whit formateur and building
        Formation cda = new Formation("CDA", ludo, batCDA, Color.RED);
        Formation commerce = new Formation("Commerce", jean, batCommerce, Color.BLUE);
        Formation aph = new Formation("APH", jean, batAPH, Color.GREEN);
        Formation aes = new Formation("AES", jean, batAES, Color.BLUE);
        Formation carrelage = new Formation("Carrelage", jean, batCarrelage, Color.BLUE);


        formationList.add(cda);
        formationList.add(commerce);
        formationList.add(aph);
        formationList.add(aes);
        formationList.add(carrelage);


//        Add formation to a batiment
        batCDA.addFormation(cda);
        batCommerce.addFormation(commerce);
        batAPH.addFormation(aph);
        batAES.addFormation(aes);
        batCarrelage.addFormation(carrelage);


//      Add all formation to array list of batiments
        batimentFormationArrayList.add(batCDA);
        batimentFormationArrayList.add(batCommerce);
        batimentFormationArrayList.add(batAPH);
        batimentFormationArrayList.add(batAES);
        batimentFormationArrayList.add(batCarrelage);

    }


    /**
     * Generate all square and give list of rectangle
     */
    public void createAllRectangle() {

        for (BatimentFormation batiment : batimentFormationArrayList) {

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
                for (Formation formation : batiment.getListeFormations()) {
                    if (!shape.getFill().equals(formation.getCouleur())) {
                        shape.setFill(Color.LIGHTGRAY);
                    }
                }
            });
            shape.setOnMouseExited(event -> {

//                Verify if is clicked before whit Color of square
                for (Formation formation : batiment.getListeFormations()) {
                    if (!shape.getFill().equals(formation.getCouleur())) {
                        shape.setFill(Color.TRANSPARENT);
                    }
                }
            });

            shape.setOnMouseClicked(mouseEvent -> {

//                change all Square to Transparent Color
                for (Rectangle square : squareArrayList) {
                    square.setFill(Color.TRANSPARENT);
                }
//      Change all batiment of formation to Color
                for (Formation formation : batiment.getListeFormations()) {
                    shape.setFill(formation.getCouleur());
                }

//  Change on Combobox all information.
                for (Formation formation : batiment.getListeFormations()) {

                    for (Formation form : listeAllFormations) {
                        if (form.getNom().equals(formation.getNom())) {
                            comboBat.getSelectionModel().selectFirst();
                            comboFormation.getSelectionModel().select(form);
                        }
                    }
                }

            });

            drawingGroup.getChildren().addAll(shape);
        }
    }

}
