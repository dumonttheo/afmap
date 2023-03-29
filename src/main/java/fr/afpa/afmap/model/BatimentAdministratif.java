package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Administrativ Building class who extend of Batiment Class
 */
public class BatimentAdministratif extends Batiment {
    private final ArrayList<Service> listeServices = new ArrayList<>();



    /**
     * Constructor of polygon administrativ building
     * @param numero number of Administrativ building
     * @param allPoints Array of all points to create a polygon
     * @param color Color of building
     */
    public BatimentAdministratif(int id, int numero, String nom, Double[] allPoints, Color color){
        super(id, numero,  nom, allPoints, color);
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
