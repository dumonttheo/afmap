package fr.afpa.afmap.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdminController {
    @FXML
    public Group drawingGroup;
    @FXML
    private ImageView imageViewNombres;
    @FXML
    private ImageView imageViewBat;
    @FXML
    public Pane pane;
    public Double widthHeigth;
    public Double newValeur;


    @FXML
    public void returnToHome(){
        try {
            RootFile.setRoot("main-view2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(){
        //        LISTENER WIDTH
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            //            Change imageViewBat whit to new pan width
            newValeur = newVal.doubleValue();
            imageViewBat.fitWidthProperty().bind(pane.widthProperty());
            imageViewNombres.fitWidthProperty().bind(pane.widthProperty());
//            Instancie width to parameter new val
            widthHeigth = newVal.doubleValue() / 1.61;
        });
    }
}
