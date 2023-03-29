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

        listOfTypeOfBatiment.add("Formation");
        listOfTypeOfBatiment.add("Administratif");

        comboBatiment.setItems(listOfTypeOfBatiment);
        coordonnatesTableView.setItems(listOfPoints);
        xColumn.setCellValueFactory(values -> {
            StringProperty str = new SimpleStringProperty();
            ArrayList<Double> val = values.getValue();
            str.set(val.get(0).toString());
            return str;
        });
        yColumn.setCellValueFactory(values -> {
            StringProperty str = new SimpleStringProperty();
            ArrayList<Double> val = values.getValue();
            str.set(val.get(1).toString());
            return str;
        });

        coordonnatesTableView.setEditable(true);
        xColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        xColumn.setSortable(false);
        yColumn.setSortable(false);
        yColumn.setOnEditCommit(event -> {
            ArrayList<Double> points = event.getRowValue();
            for (Line line : allLine) {
                if (line.getStartY() == points.get(1)) {
                    if (line.getStartX() == points.get(0)) {
                        line.setStartY(Double.parseDouble(event.getNewValue()));
                        line.setEndY(Double.parseDouble(event.getNewValue()));
                    }
                }
            }
            points.remove(1);
            points.add(1, Double.valueOf(event.getNewValue()));

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
        });
    }

    private void setEventForBuildingBatiment() {
        pane.setOnMouseClicked(event -> {
            ArrayList<Double> points = new ArrayList<>();
            points.add(event.getX());
            points.add(event.getY());
            listOfPoints.add(points);

            Line line = new Line(event.getX(), event.getY(), event.getX() + 0.5, event.getY());
            line.setStrokeWidth(4);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
            line.setStroke(colorPickerBuilding.getValue());
            allLine.add(line);
            drawingGroup.getChildren().add(line);
            line.setCursor(Cursor.HAND);


            line.setOnMouseDragged(event1 -> {
                line.setStartX(event1.getX());
                line.setEndX(event1.getX() + 0.5);
                line.setStartY(event1.getY());
                line.setEndY(event1.getY());
                points.clear();
                points.add(event1.getX());
                points.add(event1.getY());
                thread = new Thread(() -> Platform.runLater(() -> {
                    drawingGroup.getChildren().remove(polygon);
                    Polygon poly = new Polygon();
                    for (ArrayList<Double> points1 : listOfPoints) {
                        poly.getPoints().addAll(points1);
                    }
                    polygon = poly;
                    drawingGroup.getChildren().add(poly);
                    poly.setFill(colorPickerBuilding.getValue());
                }));
                thread.start();

            });

            line.setOnMouseReleased(event1 -> {
                thread.interrupt();
                previewBuilding();
                coordonnatesTableView.refresh();
            });

        });
    }

    @FXML
    private void previewBuilding() {
        drawingGroup.getChildren().clear();
        drawingGroup.getChildren().addAll(allLine);
        Polygon polygon = new Polygon();
        this.polygon = polygon;
        for (ArrayList<Double> points : listOfPoints) {
            polygon.getPoints().addAll(points);
        }
        drawingGroup.getChildren().add(polygon);
        polygon.setFill(colorPickerBuilding.getValue());
    }

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

    @FXML
    private void addBatiment() {
        polygon = new Polygon();
        for (ArrayList<Double> points : listOfPoints) {
            polygon.getPoints().addAll(points);
        }
        if (!nameBatiment.getText().isEmpty() && !numberBatiment.getText().isEmpty()) {
            String batimentName = nameBatiment.getText();
            String batimentNumber = numberBatiment.getText();
            if (AddFormation.matchToPattern(batimentName)) {
                if (onlyNumberRegexMatch(batimentNumber)) {
                    if (polygon.getPoints().size() > 0) {
                        if (comboBatiment.getSelectionModel().getSelectedItem() != null) {
                            if (colorPickerBuilding.getValue() != null) {
                                Double[] doubles = new Double[listOfPoints.size() * 2];
                                int i = 0;
                                for (ArrayList<Double> doublesArrayList : listOfPoints){
                                    for (Double doubl : doublesArrayList){
                                        doubles[i] = doubl;
                                        i++;
                                    }
                                }
                                if (comboBatiment.getSelectionModel().getSelectedItem().equals("Formation")) {
                                    BatimentFormation bat = new BatimentFormation(0, Integer.parseInt(batimentNumber), batimentName, doubles, colorPickerBuilding.getValue());
                                    DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                                    daoBatimentFormation.create(bat);
                                } else if (comboBatiment.getSelectionModel().getSelectedItem().equals("Administratif")) {
                                    BatimentAdministratif bat = new BatimentAdministratif(0, Integer.parseInt(batimentNumber), batimentName, doubles, colorPickerBuilding.getValue());
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

    public static boolean onlyNumberRegexMatch(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
