package fr.afpa.afmap.model;

import java.util.ArrayList;

public class Service {
    private String nom;
    private ArrayList<Personnel> listePersonnel = new ArrayList<>();

    public Service(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void addPersonnel(Personnel personnel){
        listePersonnel.add(personnel);
    }

    public ArrayList<Personnel> getListePersonnel() {
        return listePersonnel;
    }
}
