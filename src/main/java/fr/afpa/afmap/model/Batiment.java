package fr.afpa.afmap.model;

import java.util.ArrayList;

/**
 * Description batiment afpa avec coordonnées
 */
abstract public class Batiment {
    private int numero;
    private String nom = "Batiment " + numero;

    private Double topLeftX;
    private Double topLeftY;
    private Double topRightX;
    private Double topRightY;
    private Double bottomLeftX;
    private Double bottomLeftY;
    private Double bottomRightX;
    private Double bottomRightY;
    private Formation formation;
    private Formateur formateur;

    public Batiment(int numero, Double topLeftX, Double topLeftY, Double topRightX, Double topRightY, Double bottomLeftX, Double bottomLeftY, Double bottomRightX, Double bottomRightY){
        this.numero = numero;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.topRightX = topRightX;
        this.topRightY = topRightY;
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    public void setNomBatiment(String nomBatiment){
        this.nom = nomBatiment;
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

    public Double getTopRightX() {
        return topRightX;
    }

    public void setTopRightX(Double topRightX) {
        this.topRightX = topRightX;
    }

    public Double getTopRightY() {
        return topRightY;
    }

    public void setTopRightY(Double topRightY) {
        this.topRightY = topRightY;
    }

    public Double getBottomLeftX() {
        return bottomLeftX;
    }

    public void setBottomLeftX(Double bottomLeftX) {
        this.bottomLeftX = bottomLeftX;
    }

    public Double getBottomLeftY() {
        return bottomLeftY;
    }

    public void setBottomLeftY(Double bottomLeftY) {
        this.bottomLeftY = bottomLeftY;
    }

    public Double getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(Double bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public Double getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(Double bottomRightY) {
        this.bottomRightY = bottomRightY;
    }

    public Formation getFormation() {
        return formation;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
}
