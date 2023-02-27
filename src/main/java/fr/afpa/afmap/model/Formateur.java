package fr.afpa.afmap.model;

/**
 * Classe formateur, numero de tel et son mail
 */
public class Formateur extends Personne{
    private String numeroTelephone;
    private String mail;

    public Formateur(String nom, String prenom, String numeroTelephone, String mail){
        super(nom, prenom);
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
