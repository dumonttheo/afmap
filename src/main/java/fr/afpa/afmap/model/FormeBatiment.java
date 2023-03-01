package fr.afpa.afmap.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class FormeBatiment {

    private double x1;
    private double y1;
    private double width;
    private double heigth;
    private Color color;
    protected Double[] allPoints;
    private Shape shape;

    public FormeBatiment(double x1, double y1, double width, double heigth, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.heigth = heigth;
        this.color = color;
    }

    public FormeBatiment(Double[] allPoints, Color color)  {
        this.allPoints = allPoints;
        this.color = color;
    }

    public double getWidth() {
        return width;
    }
    public double getHeigth() {
        return heigth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public boolean isASquare(){
        return this.allPoints == null;
    }
    public  Double[] getAllPoints(){
        return allPoints;
    }

    public Shape getShape() {
        return shape;
    }
    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
