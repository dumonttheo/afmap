package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Administrativ Building class who extend of Batiment Class
 */
public class BatimentAdministratif extends Batiment {
    private ArrayList<Service> listeServices = new ArrayList<>();

    /**
     * Constructor of square administrativ building
     * @param numero number of administrativ building
     * @param topLeftX  Layout X in top-left corner
     * @param topLeftY  Layout Y in top-left corner
     * @param width Width of square
     * @param heigth Heigth of square
     * @param color Color of building
     */
    public BatimentAdministratif(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth, Color color){
        super(numero, topLeftX, topLeftY,  width,  heigth, color);
    }

    /**
     * Constructor of polygon administrativ building
     * @param numero number of Administrativ building
     * @param allPoints Array of all points to create a polygon
     * @param color Color of building
     */
    public BatimentAdministratif(int numero, Double[] allPoints, Color color){
        super(numero,  allPoints, color);
    }

    /**
     * "@Override" function who set name of building
     * @param nomBatiment String to give name of building
     */
    @Override
    public void setNomBatiment(String nomBatiment) {
        super.setNomBatiment(nomBatiment);
    }

    /**
     * Adding a service in arrayList
     * @param service Service object by Service class
     */
    public void addService(Service service){
        listeServices.add(service);
    }

    /**
     *
     * @return Array list of services by a building
     */
    public ArrayList<Service> getListeServices() {
        return listeServices;
    }
}
