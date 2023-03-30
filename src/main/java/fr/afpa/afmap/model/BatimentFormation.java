package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Batiment a usage de formation avec une formation et un formateur
 */
public class BatimentFormation extends Batiment{
    private final ArrayList<Formation> listeFormations = new ArrayList<>();



    /**
     * Constructor of polygon formation building
     * @param numero number of building
     * @param allPoints Array of all points to create a polygon
     * @param color Color of building
     */
    public BatimentFormation(int id, int numero, String nom,  ArrayList<ArrayList<Double>> allPoints, Color color){
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
