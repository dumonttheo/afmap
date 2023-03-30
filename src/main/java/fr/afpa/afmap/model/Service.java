package fr.afpa.afmap.model;

import java.util.ArrayList;

/**
 *  Service class to go whit Administrativ Building
 */
public class Service implements Comparable{
    private int id;
    private String nom;
    private ArrayList<Personnel> listePersonnel = new ArrayList<>();

    private ArrayList<BatimentAdministratif> listBatiment = new ArrayList<>();


    /**
     * @param nom Name of service
     */
    public Service(String nom, ArrayList<BatimentAdministratif> batimentAdministratifs, ArrayList<Personnel> personnels){
        this.nom = nom;
        this.listBatiment = batimentAdministratifs;
        this.listePersonnel = personnels;
    }

    public Service(int id, String nom, ArrayList<BatimentAdministratif> batimentAdministratifs, ArrayList<Personnel> personnels){
        this.id = id;
        this.nom = nom;
        this.listBatiment = batimentAdministratifs;
        this.listePersonnel = personnels;
    }

    public Service() {

    }


    /**
     * @return Get name of this service
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom set name of this service
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    /**
     * @return ArrayList of Personnel
     */
    public ArrayList<Personnel> getListePersonnel() {
        return listePersonnel;
    }


    public ArrayList<BatimentAdministratif> getListBatiment() {
        return listBatiment;
    }

    /**
     * @return Name of formation
     */
    @Override
    public String toString() {
        return this.nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        Service service = (Service) o;
        return Integer.compare(this.id, service.getId());
    }
}
