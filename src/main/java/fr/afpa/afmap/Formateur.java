package fr.afpa.afmap;

public class Formateur extends Personne{
    private int numeroTelephone;
    private String mail;

    public Formateur(String nom, String prenom, int numeroTelephone, String mail){
        super(nom, prenom);
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
    }

    public int getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(int numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
