package fr.afpa.afmap.model;

import java.util.ArrayList;

/**
 * Formation, nom, personnel
 */
public class Formation {
    private int id;
    private String nom;
    private ArrayList<Personnel> listePersonnel = new ArrayList<>();
    private ArrayList<BatimentFormation> listeBatimentsFormation = new ArrayList<>();

    /**
     * @param nom               Name of formation
     * @param batimentFormation Batiment to class batimentFormation
     */
    public Formation(String nom, ArrayList<BatimentFormation> batimentFormation, ArrayList<Personnel> personnels) {
        this.nom = nom;
        this.listeBatimentsFormation = batimentFormation;
        this.listePersonnel = personnels;
    }

    public Formation(int id , String nom, ArrayList<BatimentFormation> batimentFormation, ArrayList<Personnel> personnels) {
        this.id = id;
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
     * @return Array List of Personnel
     */
    public ArrayList<Personnel> getListePersonnel() {
        return listePersonnel;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
