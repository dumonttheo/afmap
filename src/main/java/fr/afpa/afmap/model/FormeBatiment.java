package fr.afpa.afmap.model;

import javafx.scene.shape.Line;

import java.util.ArrayList;

public abstract class FormeBatiment {

    private double x1;
    private double y1;
    private double width;
    private double heigth;

    protected Double[] allPoints;

    public FormeBatiment(double x1, double y1, double width, double heigth) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.heigth = heigth;
    }

    public FormeBatiment(Double[] allPoints)  {
        this.allPoints = allPoints;
    }

    public double getWidth() {
        return width;
    }
    public double getHeigth() {
        return heigth;
    }
    public  Double[] getAllPoints(){
        return allPoints;
    }
}
