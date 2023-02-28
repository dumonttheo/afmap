package fr.afpa.afmap.model;

import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Batiment a usage de formation avec une formation et un formateur
 */
public class BatimentFormation extends Batiment{
    private ArrayList<Formation> listeFormations = new ArrayList<Formation>();
    private ArrayList<Formateur> listeFormateurs = new ArrayList<Formateur>();

    public BatimentFormation(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth){
        super(numero, topLeftX, topLeftY, width, heigth);
    }

    public BatimentFormation(int numero, Double[] allPoints){
        super(numero,allPoints);
    }



    public ArrayList<Formation> getListeFormations() {
        return listeFormations;
    }

    public void setListeFormations(ArrayList<Formation> listeFormations) {
        this.listeFormations = listeFormations;
    }

    public void addFormation(Formation formation){
        this.listeFormations.add(formation);
        this.listeFormateurs.add(formation.getFormateur());
    }






}
