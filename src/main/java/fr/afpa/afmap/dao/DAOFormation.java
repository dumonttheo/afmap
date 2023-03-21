package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formation;
import fr.afpa.afmap.model.Personnel;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOFormation extends Dao_Common<Formation> {

    @Override
    public Formation find(long id) {
        Formation formation = new Formation();
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM formation JOIN formation_batiment fb ON fb.id_formation = formation.id JOIN batiment b ON b.id_batiment = fb.id_batiment WHERE  id = " + id);
            if (result.first()) {
                BatimentFormation bat = new BatimentFormation(Integer.parseInt(result.getString("numero")), result.getDouble("x1"), result.getDouble("x2"), result.getDouble("width"), result.getDouble("heigth"), Color.RED);
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
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM formation JOIN formation_batiment fb ON fb.id_formation = formation.id_formation JOIN formation_personnel fp ON fp.id_formation = formation.id_formation");
            while (result.next()) {
                DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                ArrayList<BatimentFormation> batiments = daoBatimentFormation.findAllBatimentForOneFormation(result.getInt("id_formation"));

                DAOPersonnel daoPersonnel = new DAOPersonnel();
                ArrayList<Personnel> personnels = daoPersonnel.findAllPersonnelForOneFormation(result.getInt("id_personnel"));

                Formation formation = new Formation(result.getString("nom"), batiments, personnels);
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
}
