package fr.afpa.afmap.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Batiment a usage de formation avec une formation et un formateur
 */
public class BatimentFormation extends Batiment{
    private final ArrayList<Formation> listeFormations = new ArrayList<Formation>();

    public BatimentFormation(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth, Color color){
        super(numero, topLeftX, topLeftY, width, heigth, color);
    }

    public BatimentFormation(int numero, Double[] allPoints, Color color){
        super(numero,allPoints, color);
    }

    public ArrayList<Formation> getListeFormations() {
        return listeFormations;
    }

    public void addFormation(Formation formation){
        this.listeFormations.add(formation);
    }






}
