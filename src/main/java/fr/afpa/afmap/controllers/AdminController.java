package fr.afpa.afmap.controllers;

import fr.afpa.afmap.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminController {

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
    public TableColumn<Double, Double> xColumn;
    @FXML
    public TableColumn<Double, Double> yColumn;

    @FXML
    public ColorPicker colorPickerBuilding;

    public Double widthHeigth;
    public Double newValeur;

    private final ObservableList<String> listOfTypeOfBatiment = FXCollections.observableArrayList();
    private final ObservableList<ArrayList<Double>> listOfPoints = FXCollections.observableArrayList();


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
        setEventForBuildingBatiment();
    }

    private void setEventForBuildingBatiment() {

        pane.setOnMouseClicked(event -> {
            ArrayList<Double> points = new ArrayList<>();
            points.add(event.getX());
            points.add(event.getY());
            listOfPoints.add(points);
            Line line = new Line(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
            line.setStrokeWidth(5);
            line.setStroke(colorPickerBuilding.getValue());
            drawingGroup.getChildren().add(line);
        });
    }

    @FXML
    private void previewBuilding() {
        Polygon polygon = new Polygon();
        for (ArrayList<Double> points : listOfPoints) {
            polygon.getPoints().addAll(points);
        }
        drawingGroup.getChildren().add(polygon);
        polygon.setFill(colorPickerBuilding.getValue());

    }
}
