package fr.afpa.afmap.controllers;

import fr.afpa.afmap.controllers.popup.AddFormation;
import fr.afpa.afmap.dao.DAOBatimentFormation;
import fr.afpa.afmap.dao.DAOBatimentService;
import fr.afpa.afmap.model.BatimentAdministratif;
import fr.afpa.afmap.model.BatimentFormation;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminController {

    @FXML
    private Button drawingButton;
    @FXML
    private TextField nameBatiment;
    @FXML
    private ComboBox<String> comboBatiment;
    @FXML
    public Group drawingGroup;
    @FXML
    private ImageView imageViewNombres;
    @FXML
    private ImageView imageViewBat;
    @FXML
    public Pane pane;
    @FXML
    public TableColumn<ArrayList<Double>, String> xColumn;
    @FXML
    public TableColumn<ArrayList<Double>, String> yColumn;

    @FXML
    public TableView<ArrayList<Double>> coordonnatesTableView;

    @FXML
    public ColorPicker colorPickerBuilding;

    @FXML
    public TextField numberBatiment;

    public Double widthHeigth;
    public Double newValeur;

    private final ObservableList<String> listOfTypeOfBatiment = FXCollections.observableArrayList();
    private final ObservableList<ArrayList<Double>> listOfPoints = FXCollections.observableArrayList();
    private boolean drawing = false;

    private final ArrayList<Line> allLine = new ArrayList<>();

    private Polygon polygon;

    private Thread thread;


    /**
     * EventHandler on FXML to return on HomePage of Admin
     */
    @FXML
    public void returnToHome() {
        try {
            RootFile.setRoot("arrayAdmin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        //        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            //            Change imageViewBat whit to new pan width
            newValeur = newVal.doubleValue();
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
            imageViewNombres.fitWidthProperty().bind(pane.widthProperty());
//            Instancie width to parameter new val
            widthHeigth = newVal.doubleValue() / 1.61;
        });

//        Add on comboBox two Type of Batiment
        listOfTypeOfBatiment.add("Formation");
        listOfTypeOfBatiment.add("Administratif");
        comboBatiment.setItems(listOfTypeOfBatiment);

//        Set on table view a observable list of arrayList of Double (Points)
        coordonnatesTableView.setItems(listOfPoints);
//        Insert whit callback into a cell value of first index of arrayList (Layout X)
        xColumn.setCellValueFactory(values -> {
            StringProperty str = new SimpleStringProperty();
            ArrayList<Double> val = values.getValue();
            str.set(val.get(0).toString());
            return str;
        });
        //        Insert whit callback into a cell value of second index of arrayList (Layout Y)
        yColumn.setCellValueFactory(values -> {
            StringProperty str = new SimpleStringProperty();
            ArrayList<Double> val = values.getValue();
            str.set(val.get(1).toString());
            return str;
        });

//        Accept editable row in table view
        coordonnatesTableView.setEditable(true);
        xColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn());

//        No sortable tableview
        xColumn.setSortable(false);
        yColumn.setSortable(false);
//        CallBack to do a refresh of array who get all points, refresh view whit a new polygon and point for view (Line)
        yColumn.setOnEditCommit(event -> {
//            Get arrayList of double of edit.
            ArrayList<Double> points = event.getRowValue();
//            Check on all line the point who is modified
            for (Line line : allLine) {
                if (line.getStartY() == points.get(1)) {
                    if (line.getStartX() == points.get(0)) {
//                        Update coordonates of line for view
                        line.setStartY(Double.parseDouble(event.getNewValue()));
                        line.setEndY(Double.parseDouble(event.getNewValue()));
                    }
                }
            }
//            Update Point List
            points.remove(1);
            points.add(1, Double.valueOf(event.getNewValue()));
            previewBuilding();
        });

        xColumn.setOnEditCommit(event -> {
            ArrayList<Double> points = event.getRowValue();
            for (Line line : allLine) {
                if (line.getStartY() == points.get(1)) {
                    if (line.getStartX() == points.get(0)) {
                        line.setStartX(Double.parseDouble(event.getNewValue()));
                        line.setEndX(Double.parseDouble(event.getNewValue()) + 0.5);
                    }
                }
            }
            points.remove(0);
            points.add(0, Double.valueOf(event.getNewValue()));
            previewBuilding();
        });

    }

    /**
     * Set eventHandler on Add a buildingBatiment (Click for a new peak)
     */
    private void setEventForBuildingBatiment() {
//        Event handler on click
        pane.setOnMouseClicked(event -> {
//            Create a arrayList of points to add on arrayList of ArrayList of point
            ArrayList<Double> points = new ArrayList<>();
            points.add(event.getX());
            points.add(event.getY());
            listOfPoints.add(points);

//            Need to create a line for a point in view
            Line line = new Line(event.getX(), event.getY(), event.getX() + 0.5, event.getY());
            line.setStrokeWidth(4);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
            line.setStroke(colorPickerBuilding.getValue());
            allLine.add(line);
            drawingGroup.getChildren().add(line);
            line.setCursor(Cursor.HAND);

//         Set eventHandler Drag and Drop of point to see in real time the new polygon
            line.setOnMouseDragged(event1 -> {
//                Change Line emplacement and add this emplacement to corner of points
                line.setStartX(event1.getX());
                line.setEndX(event1.getX() + 0.5);
                line.setStartY(event1.getY());
                line.setEndY(event1.getY());
                points.clear();
                points.add(event1.getX());
                points.add(event1.getY());

//                Use a thread for do refresh of polygon when we moove it
//                Use Platform.runLater for refresh view
                thread = new Thread(() -> Platform.runLater(() -> {
//                    Remove polygon of drawing group to not see this in view
                    drawingGroup.getChildren().remove(polygon);
                    Polygon poly = new Polygon();
//                    Create polygon
                    for (ArrayList<Double> points1 : listOfPoints) {
                        poly.getPoints().addAll(points1);
                    }
//                    Add to view this polygon and delete old polygon
                    polygon = poly;
                    drawingGroup.getChildren().add(poly);
                    poly.setFill(colorPickerBuilding.getValue());
                }));
                thread.start();
            });

//            Event Handler On Release Mouse to preview Building and refresh coordonate on table view
            line.setOnMouseReleased(event1 -> {
                thread.interrupt();
                previewBuilding();
                coordonnatesTableView.refresh();
            });

        });
    }

    /**
     * Method to preview building on View
     */
    @FXML
    private void previewBuilding() {
//        Clear drawing group and add Lines (Point, click of user)
        drawingGroup.getChildren().clear();
        drawingGroup.getChildren().addAll(allLine);
//        Created a Polygon and set all points
        Polygon polygon = new Polygon();
        this.polygon = polygon;
        for (ArrayList<Double> points : listOfPoints) {
            polygon.getPoints().addAll(points);
        }
        drawingGroup.getChildren().add(polygon);
        polygon.setFill(colorPickerBuilding.getValue());
    }

    /**
     * Start building is a eventHandler to click on pane and see the peak of polygon
     */
    @FXML
    private void startBuilding() {
        if (!drawing) {
            setEventForBuildingBatiment();
            drawingButton.setText("Arrêter de dessiner");
            drawing = true;
        } else {
            pane.setOnMouseClicked(event -> {
            });
            drawingButton.setText("Commencer à dessiner");
            drawing = false;
        }
    }

    /**
     * addBatiment for adding in database a Batiment
     */
    @FXML
    private void addBatiment() {
//        Set polygon
        polygon = new Polygon();
        ArrayList<ArrayList<Double>> arraylistOfPoints = new ArrayList<>();
        for (ArrayList<Double> points : listOfPoints) {
            polygon.getPoints().addAll(points);
            arraylistOfPoints.add(points);
        }

//        Verify all information as correct for adding a batiment in database
        if (!nameBatiment.getText().isEmpty() && !numberBatiment.getText().isEmpty()) {
            String batimentName = nameBatiment.getText();
            String batimentNumber = numberBatiment.getText();
            if (AddFormation.matchToPattern(batimentName)) {
                if (onlyNumberRegexMatch(batimentNumber)) {
                    if (polygon.getPoints().size() > 0) {
                        if (comboBatiment.getSelectionModel().getSelectedItem() != null) {
                            if (colorPickerBuilding.getValue() != null) {
                                if (comboBatiment.getSelectionModel().getSelectedItem().equals("Formation")) {
                                    BatimentFormation bat = new BatimentFormation(0, Integer.parseInt(batimentNumber), batimentName, arraylistOfPoints, colorPickerBuilding.getValue());
                                    DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                                    daoBatimentFormation.create(bat);
                                } else if (comboBatiment.getSelectionModel().getSelectedItem().equals("Administratif")) {
                                    BatimentAdministratif bat = new BatimentAdministratif(0, Integer.parseInt(batimentNumber), batimentName, arraylistOfPoints, colorPickerBuilding.getValue());
                                    DAOBatimentService daoBatimentService = new DAOBatimentService();
                                    daoBatimentService.create(bat);
                                }
                                try {
                                    RootFile.setRoot("arrayAdmin");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method for compare if string is composed of number
     * @param str String to compare
     * @return boolean, if string is matches to onlyNumber
     */
    public static boolean onlyNumberRegexMatch(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
