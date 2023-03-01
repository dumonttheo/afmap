package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

/**
 * Description batiment afpa avec coordonnées
 */
abstract public class Batiment extends FormeBatiment {
    private int numero;
    private String nom;

    /**
     * Constructor for Square Building
     * @param numero Number of Building
     * @param topLeftX  Layout X to left corner of square
     * @param topLeftY  Layout Y to left corner of square
     * @param width Width of square
     * @param heigth heigth of square
     * @param color Color of the Batiment
     */

    public Batiment(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth, Color color){
        super(topLeftX, topLeftY, width, heigth, color);
        this.numero = numero;
        this.nom = "Batiment " + numero;
    }

    /**
     * Constructor For Polygon Building
     * @param numero Number of building
     * @param allPoints Array of points for polygon
     * @param color Color for building
     */
    public Batiment(int numero, Double[] allPoints, Color color){
        super(allPoints, color);
        this.numero = numero;
        this.nom = "Batiment " + numero;
    }

    /**
     * Set name to a building
     * @param nomBatiment String to give name of building
     */
    public void setNomBatiment(String nomBatiment){
        this.nom = nomBatiment;
    }

    /**
     * @return Return object to string
     */
    @Override
    public String toString()  {
        return this.nom;
    }

    /**
     * Get number of building
     * @return int  number
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Set number of building
     * @param numero  number to set at building
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Get name of building
     * @return String to get building name
     */
    public String getNom() {
        return nom;
    }

    /**
     * set a name at a building
     * @param nom Name to set at building
     */
    public void setNom(String nom) {
        this.nom = nom;
    }







}
