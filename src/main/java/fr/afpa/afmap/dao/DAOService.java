package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentAdministratif;
import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Personnel;
import fr.afpa.afmap.model.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOService extends Dao_Common<Service> {



    @Override
    public Service find(long id) {
        Service service = new Service();
        try {
            PreparedStatement findOneServiceQuery = connect.prepareStatement("SELECT * FROM service WHERE id_service = (?);", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            findOneServiceQuery.setLong(1,id);
            ResultSet resultQueryService = findOneServiceQuery.executeQuery();
            if (resultQueryService.first()){
                int identifiant = resultQueryService.getInt("id_service");
                DAOPersonnel daoPersonnel = new DAOPersonnel();
                DAOBatimentService daoBatimentService = new DAOBatimentService();
                ArrayList<BatimentAdministratif> batimentAdministratifs = daoBatimentService.getArrayListOfBatimentAdministratifAboutOneService(identifiant);
                ArrayList<Personnel> personnels = daoPersonnel.getArrayListOfPersonnelAboutOneService(identifiant);
                service = new Service(identifiant, resultQueryService.getString("nom"), batimentAdministratifs, personnels);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return service;
    }

    @Override
    public ArrayList<Service> findAll() {
        ArrayList<Service> services = new ArrayList<>();
        try{

            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM service");
            ResultSet rs = ps.executeQuery();
            DAOPersonnel daoPersonnel = new DAOPersonnel();
            DAOBatimentService daoBatimentService = new DAOBatimentService();
            while (rs.next()){

                ArrayList<BatimentAdministratif> batimentAdministratifs = daoBatimentService.getArrayListOfBatimentAdministratifAboutOneService(rs.getLong("id_service"));


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
        try {
            PreparedStatement prepared = connect.prepareStatement("INSERT INTO service (nom) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            prepared.setString(1, object.getNom());
            prepared.executeUpdate();
            ResultSet resultSet = prepared.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                for (BatimentAdministratif batimentAdministratif : object.getListBatiment()) {
                    PreparedStatement ps = connect.prepareStatement("INSERT INTO batiment_service (id_batiment , id_service) VALUES (?,?);");
                    ps.setInt(1, batimentAdministratif.getId());
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                for (Personnel personnel : object.getListePersonnel()) {
                    PreparedStatement ps = connect.prepareStatement("INSERT INTO personnel_service (id_personnel , id_service) VALUES (?,?);");
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
    public Service update(Service object) {


        try{
            PreparedStatement deleteAllOccurenceOfServiceInBatiment = connect.prepareStatement("DELETE FROM batiment_service WHERE id_service = (?);");
            deleteAllOccurenceOfServiceInBatiment.setInt(1,object.getId());
            deleteAllOccurenceOfServiceInBatiment.executeUpdate();

            PreparedStatement deleteAllOccurenceOfServiceInPersonnel = connect.prepareStatement("DELETE FROM personnel_service WHERE id_service = (?)");
            deleteAllOccurenceOfServiceInPersonnel.setInt(1, object.getId());
            deleteAllOccurenceOfServiceInPersonnel.executeUpdate();

            PreparedStatement updateNameOfService = connect.prepareStatement("UPDATE service SET nom = (?) WHERE id_service = (?);");
            updateNameOfService.setString(1,object.getNom());
            updateNameOfService.setInt(2,object.getId());
            updateNameOfService.executeUpdate();

            for (BatimentAdministratif batimentAdministratif : object.getListBatiment()){
                PreparedStatement addAllBatimentToTableBatimentService = connect.prepareStatement("INSERT INTO batiment_service (id_batiment, id_service) VALUES (?,?); ");
                addAllBatimentToTableBatimentService.setInt(1,batimentAdministratif.getId());
                addAllBatimentToTableBatimentService.setInt(2,object.getId());
                addAllBatimentToTableBatimentService.executeUpdate();
            }

            for (Personnel personnel : object.getListePersonnel()){
                PreparedStatement addAllPersonnelToTablePersonnelService = connect.prepareStatement("INSERT INTO personnel_service (id_personnel, id_service) VALUES (?,?); ");
                addAllPersonnelToTablePersonnelService.setInt(1,personnel.getId());
                addAllPersonnelToTablePersonnelService.setInt(2,object.getId());
                addAllPersonnelToTablePersonnelService.executeUpdate();
            }

            object = find(object.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public void delete(Service object) {
        try {
            PreparedStatement deleteServiceBatiment = connect.prepareStatement("DELETE FROM batiment_service WHERE id_service = (?);");
            deleteServiceBatiment.setInt(1,object.getId());
            deleteServiceBatiment.executeUpdate();

            PreparedStatement deleteServicePersonnel = connect.prepareStatement("DELETE FROM personnel_service WHERE id_service = (?);");
            deleteServicePersonnel.setInt(1,object.getId());
            deleteServicePersonnel.executeUpdate();

            PreparedStatement deleteService = connect.prepareStatement("DELETE FROM service WHERE id_service = (?);");
            deleteService.setInt(1,object.getId());
            deleteService.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Service> findServicesByOneBatiments(int id){
        ArrayList<Service>services = new ArrayList<>();

        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM batiment_service bs JOIN service s ON s.id_service = bs.id_service WHERE id_batiment = (?);");
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
