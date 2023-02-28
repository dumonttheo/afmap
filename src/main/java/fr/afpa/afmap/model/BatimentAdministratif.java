package fr.afpa.afmap.model;

import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BatimentAdministratif extends Batiment {
    private String service;

    public BatimentAdministratif(int numero, Double topLeftX, Double topLeftY, Double width, Double heigth, String service){
        super(numero, topLeftX, topLeftY,  width,  heigth);
        this.service = service;
    }
    public BatimentAdministratif(int numero, Double[] allPoints,  String service){
        super(numero,  allPoints);
        this.service = service;
    }
    @Override
    public void setNomBatiment(String nomBatiment) {
        super.setNomBatiment(nomBatiment);
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
