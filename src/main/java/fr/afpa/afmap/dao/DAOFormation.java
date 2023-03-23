package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formation;
import fr.afpa.afmap.model.Personnel;
import javafx.scene.paint.Color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;

public class DAOFormation extends Dao_Common<Formation> {

//    private final DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
//    private final DAOPersonnel daoPersonnel = new DAOPersonnel();


    @Override
    public Formation find(long id) {
        Formation formation = new Formation();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM formation JOIN formation_batiment fb ON fb.id_formation = formation.id JOIN batiment b ON b.id_batiment = fb.id_batiment WHERE  id = " + id);
            if (result.first()) {
                BatimentFormation bat = new BatimentFormation(result.getInt("id_batiment"), Integer.parseInt(result.getString("numero")), result.getString("nom"), result.getDouble("x1"), result.getDouble("x2"), result.getDouble("width"), result.getDouble("heigth"), Color.RED);
//                formation = new Formation(result.getString("nom"), bat);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return formation;
    }

    @Override
    public ArrayList<Formation> findAll() {
        ArrayList<Formation> list = new ArrayList<>();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM formation");
            while (result.next()) {
                DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                ArrayList<BatimentFormation> batiments = daoBatimentFormation.findAllBatimentForOneFormation(result.getInt("id_formation"));
                DAOPersonnel daoPersonnel = new DAOPersonnel();
                ArrayList<Personnel> personnels = daoPersonnel.findAllPersonnelForOneFormation(result.getInt("id_formation"));

                Formation formation = new Formation(result.getInt("id_formation"), result.getString("nom"), batiments, personnels);
                list.add(formation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Formation create(Formation object) {
        return null;
    }

    @Override
    public Formation update(Formation object) {
        return null;
    }

    @Override
    public void delete(Formation object) {

    }

    public ArrayList<Formation> findAllFormationOfOneBatiment(int id) {
        ArrayList<Formation> formations = new ArrayList<>();
        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM formation_batiment fb JOIN formation f ON f.id_formation = fb.id_formation WHERE fb.id_batiment = (?) ;");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                ArrayList<BatimentFormation> batiments = daoBatimentFormation.findAllBatimentForOneFormation(rs.getInt("id_formation"));
                DAOPersonnel daoPersonnel = new DAOPersonnel();
                ArrayList<Personnel> personnels = daoPersonnel.findAllPersonnelForOneFormation(rs.getInt("id_formation"));
                Formation formation = new Formation(rs.getInt("id_formation"), rs.getString("nom"), batiments, personnels);
                formations.add(formation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formations;
    }
}
