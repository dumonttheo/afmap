package fr.afpa.afmap.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Batiment a usage de formation avec une formation et un formateur
 */
public class BatimentFormation extends Batiment{
    private final ArrayList<Formation> listeFormations = new ArrayList<Formation>();

    /**
     * Constructor of square formation building
     * @param numero number of building
     * @param topLeftX Layout X at top-left corner of square
     * @param topLeftY Layout Y at top left corner of square
     * @param width Width of square
     * @param heigth Heigth of square
     * @param color Color of square
     */
    public BatimentFormation(int id, int numero, String nom, Double topLeftX, Double topLeftY, Double width, Double heigth, Color color){
        super(id, numero, nom, topLeftX, topLeftY, width, heigth, color);
    }

    /**
     * Constructor of polygon formation building
     * @param numero number of building
     * @param allPoints Array of all points to create a polygon
     * @param color Color of building
     */
    public BatimentFormation(int id, int numero, String nom,  Double[] allPoints, Color color){
        super(id, numero,nom,allPoints, color);
    }

    /**
     * @return array list of Formation in this building
     */
    public ArrayList<Formation> getListeFormations() {
        return listeFormations;
    }

    /**
     * @param formation add a formation to arrayList on this building
     */
    public void addFormation(Formation formation){
        this.listeFormations.add(formation);
    }

}
