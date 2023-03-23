package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentAdministratif;
import fr.afpa.afmap.model.Personnel;
import fr.afpa.afmap.model.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOService extends Dao_Common<Service> {
    @Override
    public Service find(long id) {
        return null;
    }

    @Override
    public ArrayList<Service> findAll() {
        ArrayList<Service> services = new ArrayList<>();

        try{

            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM service");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                DAOBatimentService daoBatimentService = new DAOBatimentService();
                ArrayList<BatimentAdministratif> batimentAdministratifs = daoBatimentService.getArrayListOfBatimentAdministratifAboutOneService(rs.getLong("id_service"));

                DAOPersonnel daoPersonnel = new DAOPersonnel();
                ArrayList<Personnel> personnels = daoPersonnel.getArrayListOfPersonnelAboutOneService(rs.getLong("id_service"));

                Service service = new Service(rs.getString("nom"), batimentAdministratifs, personnels);
                services.add(service);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public Service create(Service object) {
        return null;
    }

    @Override
    public Service update(Service object) {
        return null;
    }

    @Override
    public void delete(Service object) {

    }
}
