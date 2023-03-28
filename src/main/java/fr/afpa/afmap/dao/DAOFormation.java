package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formation;
import fr.afpa.afmap.model.Personnel;
import javafx.scene.paint.Color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.util.ArrayList;

public class DAOFormation extends Dao_Common<Formation> {

//    private final DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
//    private final DAOPersonnel daoPersonnel = new DAOPersonnel();


    @Override
    public Formation find(long id) {
        Formation formation = new Formation();
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM formation WHERE id_formation = (?);", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
            if (result.first()) {
                DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
                DAOPersonnel daoPersonnel = new DAOPersonnel();
                formation = new Formation(result.getInt("id_formation"), result.getString("nom"), daoBatimentFormation.findAllBatimentForOneFormation(result.getInt("id_formation")), daoPersonnel.findAllPersonnelForOneFormation(result.getInt("id_formation")));
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
        try {
            PreparedStatement prepared = connect.prepareStatement("INSERT INTO formation (nom) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            prepared.setString(1, object.getNom());
            prepared.executeUpdate();
            ResultSet resultSet = prepared.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                for (BatimentFormation batimentFormation : object.getListeBatimentsFormation()) {
                    PreparedStatement ps = connect.prepareStatement("INSERT INTO formation_batiment (id_batiment , id_formation) VALUES (?,?);");
                    ps.setInt(1, batimentFormation.getId());
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                for (Personnel personnel : object.getListePersonnel()) {
                    PreparedStatement ps = connect.prepareStatement("INSERT INTO formation_personnel (id_personnel , id_formation) VALUES (?,?);");
                    ps.setInt(1, personnel.getId());
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                object = find(id);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return object;
    }

    @Override
    public Formation update(Formation object) {
        try {
            PreparedStatement deleteBatimentQuery = connect.prepareStatement("DELETE FROM formation_batiment WHERE id_formation = (?);");
            deleteBatimentQuery.setInt(1, object.getId());
            deleteBatimentQuery.executeUpdate();

            PreparedStatement deletePersonnelQuery = connect.prepareStatement("DELETE FROM formation_personnel WHERE id_formation = (?);");
            deletePersonnelQuery.setInt(1, object.getId());
            deletePersonnelQuery.executeUpdate();

            PreparedStatement updateFormationNameQuery = connect.prepareStatement("UPDATE formation SET nom = (?) WHERE id_formation = (?);");
            updateFormationNameQuery.setString(1, object.getNom());
            updateFormationNameQuery.setInt(2, object.getId());
            updateFormationNameQuery.executeUpdate();

            for (BatimentFormation batimentFormation : object.getListeBatimentsFormation()) {
                PreparedStatement addBatimentToFormationQuery = connect.prepareStatement("INSERT INTO formation_batiment (id_batiment, id_formation) VALUES (?,?)");
                addBatimentToFormationQuery.setInt(1, batimentFormation.getId());
                addBatimentToFormationQuery.setInt(2, object.getId());
                addBatimentToFormationQuery.executeUpdate();
            }
            for (Personnel personnel : object.getListePersonnel()) {
                PreparedStatement addPersonnelToFormationQuery = connect.prepareStatement("INSERT INTO formation_personnel (id_personnel, id_formation) VALUES (?,?)");
                addPersonnelToFormationQuery.setInt(1, personnel.getId());
                addPersonnelToFormationQuery.setInt(2, object.getId());
                addPersonnelToFormationQuery.executeUpdate();
            }

            object = find(object.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public void delete(Formation object) {
        try {
            PreparedStatement deleteEveryOccurenceOfIdFormationInBatimentTable = connect.prepareStatement("DELETE FROM formation_batiment WHERE id_formation = (?);");
            deleteEveryOccurenceOfIdFormationInBatimentTable.setInt(1,object.getId());
            deleteEveryOccurenceOfIdFormationInBatimentTable.executeUpdate();

            PreparedStatement deleteEveryOccurenceOfIdFormationInPersonnelTable = connect.prepareStatement("DELETE FROM formation_personnel WHERE id_formation = (?);");
            deleteEveryOccurenceOfIdFormationInPersonnelTable.setInt(1, object.getId());
            deleteEveryOccurenceOfIdFormationInPersonnelTable.executeUpdate();

            PreparedStatement deleteFormation = connect.prepareStatement("DELETE FROM formation WHERE id_formation = (?);");
            deleteFormation.setInt(1, object.getId());
            deleteFormation.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Formation> findAllFormationOfOneBatiment(int id) {
        ArrayList<Formation> formations = new ArrayList<>();
        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT DISTINCT * FROM formation_batiment fb JOIN formation f ON f.id_formation = fb.id_formation WHERE fb.id_batiment = (?) ;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
