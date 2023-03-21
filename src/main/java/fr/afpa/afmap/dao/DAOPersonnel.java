package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.Personnel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPersonnel extends Dao_Common<Personnel> {


    @Override
    public Personnel find(long id) {
        Personnel personnel = new Personnel();
        try{
            ResultSet resultSet = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM personnel WHERE id_personnel = " + id);
            if(resultSet.first()){
                personnel= new Personnel(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("numeroTelephone"),resultSet.getString("mail"));
            }
        }catch (SQLException e){
        e.printStackTrace();
        }

        return personnel;
    }

    public ArrayList<Personnel> findAllPersonnelForOneFormation(long id){
        ArrayList<Personnel> personnels = new ArrayList<>();
        try{
            ResultSet resultSet = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM personnel WHERE id_personnel = " + id);
            if(resultSet.first()){
                Personnel personnel= new Personnel(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("numeroTelephone"),resultSet.getString("mail"));
                personnels.add(personnel);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  personnels;
    }

    @Override
    public ArrayList<Personnel> findAll() {
        return null;
    }

    @Override
    public Personnel create(Personnel object) {
        return null;
    }

    @Override
    public Personnel update(Personnel object) {
        return null;
    }

    @Override
    public void delete(Personnel object) {

    }
}