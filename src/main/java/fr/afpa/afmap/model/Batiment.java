package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

/**
 * Description batiment afpa avec coordonnées
 */
abstract public class Batiment extends FormeBatiment implements Comparable{
    private int id;
    private final int numero;
    private String nom;




    /**
     * Constructor For Polygon Building
     *
     * @param numero    Number of building
     * @param allPoints Array of points for polygon
     * @param color     Color for building
     */
    public Batiment(int id, int numero, String nom, Double[] allPoints, Color color) {
        super(allPoints, color);
        this.numero = numero;
        this.nom = nom;
        this.id = id;
    }

    /**
     * Set name to a building
     *
     * @param nomBatiment String to give name of building
     */
    public void setNomBatiment(String nomBatiment) {
        this.nom = nomBatiment;
    }

    /**
     * @return Return object to string
     */
    @Override
    public String toString() {
        return this.nom;
    }

    /**
     * Get number of building
     *
     * @return int  number
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Get name of building
     *
     * @return String to get building name
     */
    public String getNom() {
        return nom;
    }

    /**
     * set a name at a building
     *
     * @param nom Name to set at building
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

    @Override
    public int compareTo(Object o) {
        Batiment batiment = (Batiment) o;
        return Integer.compare(this.id, batiment.getId());
    }
}
