package fr.afpa.afmap;

public class Formation {
    private String nom;
    private Formateur formateur;
    public Formation(String nom, Formateur formateur){
        this.nom = nom;
        this.formateur = formateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
}
