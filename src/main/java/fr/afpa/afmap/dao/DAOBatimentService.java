package fr.afpa.afmap.dao;

import fr.afpa.afmap.Main;
import fr.afpa.afmap.model.BatimentAdministratif;
import fr.afpa.afmap.model.Service;

import java.sql.*;
import java.util.ArrayList;

public class DAOBatimentService extends Dao_Common<BatimentAdministratif> {

    public DAOBatimentService() {
    }

    private final DAOService daoService = new DAOService();

    @Override
    public BatimentAdministratif find(long id) {
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM batiment WHERE id = " + id);
            if (result.first()) {
                Array allpoints = result.getArray("allpoints");
                Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                return new BatimentAdministratif(result.getInt("id_batiment"), Integer.parseInt(result.getString("numero")), result.getString("nom"), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(result.getString("color")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<BatimentAdministratif> findAll() {
        ArrayList<BatimentAdministratif> batimentAdministratifs = new ArrayList<>();
        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM batiment b LEFT JOIN batiment_service bs ON bs.id_batiment = b.id_batiment WHERE is_formation = false;");
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                ArrayList<Service> services = daoService.findServicesByOneBatiments(r.getInt("id_batiment"));
                Array allpoints = r.getArray("allpoints");

                Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                BatimentAdministratif batiment = new BatimentAdministratif(r.getInt("id_batiment"), Integer.parseInt(r.getString("numero")), r.getString("nom_batiment"), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(r.getString("color")));
                for (Service service : services) {
                    batiment.addService(service);
                }
                batimentAdministratifs.add(batiment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return batimentAdministratifs;
    }

    @Override
    public BatimentAdministratif create(BatimentAdministratif object) {
        try {
            PreparedStatement addBatimentQuery = connect.prepareStatement("INSERT INTO batiment (numero, nom_batiment, color, allpoints, is_formation) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            addBatimentQuery.setString(1, String.valueOf(object.getNumero()));
            addBatimentQuery.setString(2, object.getNom());
            addBatimentQuery.setString(3, Main.colorToHex(object.getColor()));
            addBatimentQuery.setObject(4, object.getAllPoints());
            addBatimentQuery.setBoolean(5, false);
            addBatimentQuery.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public BatimentAdministratif update(BatimentAdministratif object) {
        try {
            PreparedStatement updateBatimentQuery = connect.prepareStatement("UPDATE batiment SET numero = (?), nom_batiment = (?),color = (?), allpoints = (?), is_formation = (?) WHERE id_batiment = (?); ");
            updateBatimentQuery.setString(1, String.valueOf(object.getNumero()));
            updateBatimentQuery.setString(2, object.getNom());
            updateBatimentQuery.setString(3, Main.colorToHex(object.getColor()));
            updateBatimentQuery.setObject(4, object.getAllPoints());
            updateBatimentQuery.setBoolean(5, false);
            updateBatimentQuery.setInt(6, object.getId());
            updateBatimentQuery.executeUpdate();

            object = find(object.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public void delete(BatimentAdministratif object) {
        try {
            PreparedStatement deleteAllOccurenceInFormationWhitThisBatiment = connect.prepareStatement("DELETE FROM formation_batiment WHERE id_batiment = (?);");
            deleteAllOccurenceInFormationWhitThisBatiment.setInt(1, object.getId());
            deleteAllOccurenceInFormationWhitThisBatiment.executeUpdate();

            PreparedStatement deleteBatimentQuery = connect.prepareStatement("DELETE FROM batiment WHERE id_batiment = (?);");
            deleteBatimentQuery.setInt(1, object.getId());
            deleteBatimentQuery.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<BatimentAdministratif> getArrayListOfBatimentAdministratifAboutOneService(long id) {
        ArrayList<BatimentAdministratif> batimentAdministratifs = new ArrayList<>();

        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM batiment b JOIN batiment_service bs ON bs.id_batiment = b.id_batiment WHERE bs.id_service = (?)");
            ps.setLong(1, id);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                Array allpoints = r.getArray("allpoints");

                Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                BatimentAdministratif batiment = new BatimentAdministratif(r.getInt("id_batiment"), Integer.parseInt(r.getString("numero")), r.getString("nom_batiment"), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(r.getString("color")));
                batimentAdministratifs.add(batiment);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return batimentAdministratifs;
    }


}
