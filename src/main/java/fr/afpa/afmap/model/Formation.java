package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Formation, nom, personnel
 */
public class Formation {
    private String nom;
    private ArrayList<Personnel> listePersonnel = new ArrayList<>();
    private ArrayList<BatimentFormation> listeBatimentsFormation = new ArrayList<BatimentFormation>();

    public Formation(String nom, BatimentFormation batimentFormation) {
        this.nom = nom;
        this.listeBatimentsFormation.add(batimentFormation);
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public ArrayList<BatimentFormation> getListeBatimentsFormation() {
        return this.listeBatimentsFormation;
    }

    public void addPersonnel(Personnel personnel){
        listePersonnel.add(personnel);
    }

    public ArrayList<Personnel> getListePersonnel() {
        return listePersonnel;
    }

    public void addBatiment(BatimentFormation batimentFormation) {
        this.listeBatimentsFormation.add(batimentFormation);
    }

    public void removeBatiment(BatimentFormation batimentFormation) {
        this.listeBatimentsFormation.remove(batimentFormation);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
