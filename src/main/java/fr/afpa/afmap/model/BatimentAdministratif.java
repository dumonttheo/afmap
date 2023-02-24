package fr.afpa.afmap.model;

public class BatimentAdministratif extends Batiment {
    private String service;

    public BatimentAdministratif(int numero, Double topLeftX, Double topLeftY, Double topRightX, Double topRightY, Double bottomLeftX, Double bottomLeftY, Double bottomRightX, Double bottomRightY, String service){
        super(numero, topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
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
