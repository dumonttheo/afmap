package fr.afpa.afmap.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Description batiment afpa avec coordonnées
 */
abstract public class Batiment extends FormeBatiment {
    private int numero;
    private String nom;

    public Batiment(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth, Color color){
        super(topLeftX, topLeftY, width, heigth, color);
        this.numero = numero;
        this.nom = "Batiment " + numero;
    }

    public Batiment(int numero, Double[] allPoints, Color color){
        super(allPoints, color);
        this.numero = numero;
        this.nom = "Batiment " + numero;
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







}
