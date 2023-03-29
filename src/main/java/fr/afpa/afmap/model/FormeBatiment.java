package fr.afpa.afmap.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 *  Form of Building, Separate Polygon to square
 */
public abstract class FormeBatiment {

    private double x1;
    private double y1;
    private double width;
    private double heigth;
    private Color color;
    protected Double[] allPoints;
    private Shape shape;

    /**
     * Constructor for square building
     * @param x1 Layout X of the top-left corner of square
     * @param y1 Layout Y of the top-left corner of square
     * @param width width of square
     * @param heigth heigth of square
     * @param color Color of square
     */
    public FormeBatiment(double x1, double y1, double width, double heigth, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.heigth = heigth;
        this.color = color;
    }

    /**
     * Constructor for polygon building
     * @param allPoints ArrayList for building
     * @param color Color of building
     */
    public FormeBatiment(Double[] allPoints, Color color)  {
        this.allPoints = allPoints;
        this.color = color;
    }

    /**
     * @return Get width of square
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return get heigth of square
     */
    public double getHeigth() {
        return heigth;
    }

    /**
     * @return get color of building
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color Set color of building
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return get top left-corner Layoyt X
     */
    public double getX1() {
        return x1;
    }

    /**
     * @return get top-left Corner layout Y
     */
    public double getY1() {
        return y1;
    }

    /**
     * @return Function to know if building is a square or a polygon
     */
    public boolean isASquare(){
        return this.allPoints == null;
    }

    /**
     * @return get array of points
     */
    public  Double[] getAllPoints(){
        return allPoints;
    }

    /**
     * @return get Shape of Building
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @param shape Set a shape of building
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
