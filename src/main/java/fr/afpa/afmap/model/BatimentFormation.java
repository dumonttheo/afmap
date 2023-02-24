package fr.afpa.afmap.model;

public class BatimentFormation extends Batiment{
    private Formation formation;
    private Formateur formateur;

    public BatimentFormation(int numero, Double topLeftX, Double topLeftY, Double topRightX, Double topRightY, Double bottomLeftX, Double bottomLeftY, Double bottomRightX, Double bottomRightY, Formateur formateur, Formation formation){
        super(numero, topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
        this.formation = formation;
        this.formateur = formation.getFormateur();
    }

    public void setFormation(Formation formation){
        this.formation = formation;
        this.formateur = formation.getFormateur();
    }

    @Override
    public Formation getFormation() {
        return formation;
    }

    @Override
    public Formateur getFormateur() {
        return formateur;
    }

    @Override
    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
}
