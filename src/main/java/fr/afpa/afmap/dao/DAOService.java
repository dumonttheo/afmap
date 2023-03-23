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

                Service service = new Service(rs.getInt("id_service"), rs.getString("nom"), batimentAdministratifs, personnels);
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

    public ArrayList<Service> findServicesByOneBatiments(int id){
        ArrayList<Service>services = new ArrayList<>();

        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM batiment_service bs JOIN service s ON s.id_service = bs.id_service WHERE id_batiment = (?)");
            ps.setInt(1,id);
            ResultSet r = ps.executeQuery();
            while (r.next()){
                DAOBatimentService daoBatimentService = new DAOBatimentService();
                ArrayList<BatimentAdministratif> batimentAdministratifs = daoBatimentService.getArrayListOfBatimentAdministratifAboutOneService(r.getLong("id_service"));

                DAOPersonnel daoPersonnel = new DAOPersonnel();
                ArrayList<Personnel> personnels = daoPersonnel.getArrayListOfPersonnelAboutOneService(r.getLong("id_service"));
                Service service = new Service(r.getInt("id_service"), r.getString("nom"), batimentAdministratifs, personnels);
                services.add(service);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return services;
    }
}
