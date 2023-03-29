package fr.afpa.afmap.controllers;

import fr.afpa.afmap.controllers.popup.AddFormation;
import fr.afpa.afmap.dao.DAOBatimentFormation;
import fr.afpa.afmap.dao.DAOBatimentService;
import fr.afpa.afmap.model.Batiment;
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

public class UpdateBatimentController {
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

    private Batiment batiment;


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
        xColumn.setSortable(false);
        yColumn.setSortable(false);

        xColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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

    public void setBatiment(Batiment batiment) {
        listOfTypeOfBatiment.add("Formation");
        listOfTypeOfBatiment.add("Administratif");
        this.batiment = batiment;

        if (batiment instanceof BatimentFormation) {
            comboBatiment.getSelectionModel().select("Formation");
        } else {
            comboBatiment.getSelectionModel().select("Administratif");

        }

        nameBatiment.setText(batiment.getNom());
        numberBatiment.setText(String.valueOf(batiment.getNumero()));
        colorPickerBuilding.setValue(batiment.getColor());
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i < batiment.getAllPoints().length; i++) {
            if (i % 2 == 0 && i != 0) {
                listOfPoints.add(points);
                points = new ArrayList<>();
                points.add(batiment.getAllPoints()[i]);
            } else {
                points.add(batiment.getAllPoints()[i]);
            }
            if (i == batiment.getAllPoints().length - 1) {
                listOfPoints.add(points);
            }
        }
        previewBuilding();
        createLine();

    }


    public void createLine() {
        for (ArrayList<Double> list : listOfPoints) {
            Line line = new Line(list.get(0), list.get(1), list.get(0) + 0.5, list.get(1));
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
                list.clear();
                list.add(event1.getX());
                list.add(event1.getY());
                thread = new Thread(() -> Platform.runLater(() -> {
                    drawingGroup.getChildren().remove(polygon);
                    Polygon poly = new Polygon();
                    for (ArrayList<Double> points : listOfPoints) {
                        poly.getPoints().addAll(points);
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
        }
    }

    @FXML
    public void updateBatiment() {
        polygon = new Polygon();
        for (ArrayList<Double> points : listOfPoints) {
            polygon.getPoints().addAll(points);
        }
        if (!nameBatiment.getText().isEmpty() && !numberBatiment.getText().isEmpty()) {
            String batimentName = nameBatiment.getText();
            String batimentNumber = numberBatiment.getText();
            if (AddFormation.matchToPattern(batimentName)) {
                if (AdminController.onlyNumberRegexMatch(batimentNumber)) {
                    if (polygon.getPoints().size() > 0) {
                        if (comboBatiment.getSelectionModel().getSelectedItem() != null) {
                            if (colorPickerBuilding.getValue() != null) {
                                Double[] doubles = new Double[listOfPoints.size() * 2];
                                int i = 0;
                                for (ArrayList<Double> doublesArrayList : listOfPoints) {
                                    for (Double doubl : doublesArrayList) {
                                        doubles[i] = doubl;
                                        i++;
                                    }
                                }
                                if (comboBatiment.getSelectionModel().getSelectedItem().equals("Formation")) {
                                    BatimentFormation bat = new BatimentFormation(this.batiment.getId(), Integer.parseInt(batimentNumber), batimentName, doubles, colorPickerBuilding.getValue());
                                    DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                                    daoBatimentFormation.update(bat);
                                } else if (comboBatiment.getSelectionModel().getSelectedItem().equals("Administratif")) {
                                    BatimentAdministratif bat = new BatimentAdministratif(this.batiment.getId(), Integer.parseInt(batimentNumber), batimentName, doubles, colorPickerBuilding.getValue());
                                    DAOBatimentService daoBatimentService = new DAOBatimentService();
                                    daoBatimentService.update(bat);

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


}
