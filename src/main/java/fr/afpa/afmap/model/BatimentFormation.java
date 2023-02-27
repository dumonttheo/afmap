package fr.afpa.afmap.model;

import java.util.ArrayList;

/**
 * Batiment a usage de formation avec une formation et un formateur
 */
public class BatimentFormation extends Batiment{
    private ArrayList<Formation> listeFormations = new ArrayList<Formation>();
    private ArrayList<Formateur> listeFormateurs = new ArrayList<Formateur>();

    public BatimentFormation(int numero, Double topLeftX, Double topLeftY, Double topRightX, Double topRightY, Double bottomLeftX, Double bottomLeftY, Double bottomRightX, Double bottomRightY){
        super(numero, topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
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
