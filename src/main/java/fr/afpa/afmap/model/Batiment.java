package fr.afpa.afmap.model;

import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Description batiment afpa avec coordonnées
 */
abstract public class Batiment extends FormeBatiment {
    private int numero;
    private String nom;

    private Double topLeftX;
    private Double topLeftY;

    public Batiment(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth){
        super(topLeftX, topLeftY, width, heigth);
        this.numero = numero;
        this.nom = "Batiment " + numero;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;

    }

    public Batiment(int numero, Double topLeftX, Double topLeftY, ArrayList<Line> lineArrayList){
        super(lineArrayList);
        this.numero = numero;
        this.nom = "Batiment " + numero;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;

    }

    public void setNomBatiment(String nomBatiment){
        this.nom = nomBatiment;
    }

    @Override
    public String toString()  {
        return this.nom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTopLeftX() {
        return topLeftX;
    }

    public void setTopLeftX(Double topLeftX) {
        this.topLeftX = topLeftX;
    }

    public Double getTopLeftY() {
        return topLeftY;
    }

    public void setTopLeftY(Double topLeftY) {
        this.topLeftY = topLeftY;
    }





}
