package fr.afpa.afmap.model;

/**
 * Classe formateur, numero de tel et son mail
 */
public class Personnel implements Comparable {
    private int id;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private String mail;

    /**
     * @param nom             name of personnel
     * @param prenom          firstname of personnel
     * @param numeroTelephone phone number of personnel
     * @param mail            mail of personnel
     */
    public Personnel(String nom, String prenom, String numeroTelephone, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
    }

    public Personnel(int id, String nom, String prenom, String numeroTelephone, String mail) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
    }

    public Personnel() {
    }


    /**
     * @return phoneNumber
     */
    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    /**
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @return get name of personnal
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom set name of personnal
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return get firstname of personnal
     */
    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return this.nom + " " + this.prenom;
    }

    @Override
    public int compareTo(Object o) {
        Personnel personnel = (Personnel) o;
        return Integer.compare(this.id, personnel.getId());
    }
}
