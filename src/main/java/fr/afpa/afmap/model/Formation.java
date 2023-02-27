package fr.afpa.afmap.model;

import java.util.ArrayList;

/**
 * Formation, nom, formateur
 */
public class Formation {
    private String nom;
    private Formateur formateur;
    private ArrayList<BatimentFormation> listeBatimentsFormation = new ArrayList<BatimentFormation>();

    public Formation(String nom, Formateur formateur, BatimentFormation batimentFormation) {
        this.nom = nom;
        this.formateur = formateur;
        this.listeBatimentsFormation.add(batimentFormation);
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public ArrayList<BatimentFormation> getListeBatimentsFormation() {
        return this.listeBatimentsFormation;
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

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
}
