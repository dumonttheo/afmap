package fr.afpa.afmap.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BatimentAdministratif extends Batiment {
    private ArrayList<Service> listeServices = new ArrayList<>();

    public BatimentAdministratif(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth, Color color){
        super(numero, topLeftX, topLeftY,  width,  heigth, color);
    }
    public BatimentAdministratif(int numero, Double[] allPoints, Color color){
        super(numero,  allPoints, color);
    }
    @Override
    public void setNomBatiment(String nomBatiment) {
        super.setNomBatiment(nomBatiment);
    }

    public void addService(Service service){
        listeServices.add(service);
    }

    public ArrayList<Service> getListeServices() {
        return listeServices;
    }
}
