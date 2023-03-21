package fr.afpa.afmap.model;

import java.util.ArrayList;

/**
 * Formation, nom, personnel
 */
public class Formation {
    private String nom;
    private ArrayList<Personnel> listePersonnel = new ArrayList<>();
    private ArrayList<BatimentFormation> listeBatimentsFormation = new ArrayList<BatimentFormation>();

    /**
     * @param nom               Name of formation
     * @param batimentFormation Batiment to class batimentFormation
     */
    public Formation(String nom, ArrayList<BatimentFormation> batimentFormation, ArrayList<Personnel> personnels) {
        this.nom = nom;
        this.listeBatimentsFormation = batimentFormation;
        this.listePersonnel = personnels;
    }

    public Formation() {
    }

    /**
     * @return Name of formation
     */
    @Override
    public String toString() {
        return this.nom;
    }

    /**
     * @return Return list of BatimentFormation on this formation
     */
    public ArrayList<BatimentFormation> getListeBatimentsFormation() {
        return this.listeBatimentsFormation;
    }

    /**
     * @param personnel Add a personnel to this formation
     */
    public void addPersonnel(Personnel personnel) {
        listePersonnel.add(personnel);
    }

    /**
     * @return Array List of Personnel
     */
    public ArrayList<Personnel> getListePersonnel() {
        return listePersonnel;
    }

    /**
     * @param batimentFormation Add a building in this formation
     */
    public void addBatiment(BatimentFormation batimentFormation) {
        this.listeBatimentsFormation.add(batimentFormation);
    }

    /**
     * @param batimentFormation Remove a building on this formation
     */
    public void removeBatiment(BatimentFormation batimentFormation) {
        this.listeBatimentsFormation.remove(batimentFormation);
    }

    /**
     * @return Get name of the formation
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom Set name of the formation
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


}
