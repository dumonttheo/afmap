package fr.afpa.afmap.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *  Form of Building, Separate Polygon to square
 */
public abstract class FormeBatiment {

    private Color color;
    protected Double[] allPoints;
    private Shape shape;


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
