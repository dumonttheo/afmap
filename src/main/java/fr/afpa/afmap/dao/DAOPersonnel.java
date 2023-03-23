package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.Personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPersonnel extends Dao_Common<Personnel> {


    @Override
    public Personnel find(long id) {
        Personnel personnel = new Personnel();
        try {
            ResultSet resultSet = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM personnel WHERE id_personnel = " + id);
            if (resultSet.first()) {
                personnel = new Personnel(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("numeroTelephone"), resultSet.getString("mail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personnel;
    }

    public ArrayList<Personnel> findAllPersonnelForOneFormation(long id) {
        ArrayList<Personnel> personnels = new ArrayList<>();
        try {
            ResultSet resultSet = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM personnel JOIN formation_personnel fp ON fp.id_personnel = personnel.id_personnel WHERE id_formation = " + id);
            if (resultSet.first()) {
                Personnel personnel = new Personnel(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("numeroTelephone"), resultSet.getString("mail"));
                personnels.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnels;
    }

    @Override
    public ArrayList<Personnel> findAll() {
        ArrayList<Personnel> personnels = new ArrayList<>();

        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM personnel;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Personnel personnel = new Personnel(rs.getInt("id_personnel"),rs.getString("nom"), rs.getString("prenom"),rs.getString("numerotelephone"), rs.getString("mail"));
                personnels.add(personnel);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personnels;
    }

    @Override
    public Personnel create(Personnel object) {
        try {
                PreparedStatement ps = this.connect.prepareStatement("INSERT INTO personnel (nom, prenom, numerotelephone, mail) VALUES(?,?,?,?");
                ps.setString(1,object.getNom());
                ps.setString(2,object.getPrenom());
                ps.setString(3,object.getNumeroTelephone());
                ps.setString(4,object.getMail());
                ps.executeUpdate();
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return object;
    }

    @Override
    public Personnel update(Personnel object) {
        return null;
    }

    @Override
    public void delete(Personnel object) {

    }

    public ArrayList<Personnel> getArrayListOfPersonnelAboutOneService(long id) {
        ArrayList<Personnel> personnels = new ArrayList<>();

        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM personnel p JOIN personnel_service ps ON p.id_personnel = ps.id_personnel WHERE id_service = (?)");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personnel personnel = new Personnel(rs.getString("nom"), rs.getString("prenom"), rs.getString("numeroTelephone"), rs.getString("mail"));
                personnels.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personnels;
    }
}
