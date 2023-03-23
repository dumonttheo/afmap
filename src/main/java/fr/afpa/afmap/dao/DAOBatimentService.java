package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentAdministratif;
import fr.afpa.afmap.model.BatimentFormation;
import javafx.scene.paint.Color;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOBatimentService extends Dao_Common<BatimentAdministratif> {
    @Override
    public BatimentAdministratif find(long id) {
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM batiment WHERE id = " + id);
            if (result.first()) {
                Array allpoints = result.getArray("allpoints");
                if (allpoints == null) {
                    return new BatimentAdministratif(Integer.parseInt(result.getString("numero")), result.getDouble("x1"), result.getDouble("x2"), result.getDouble("width"), result.getDouble("heigth"), givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(result.getString("color")));
                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                    return new BatimentAdministratif(Integer.parseInt(result.getString("numero")), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(result.getString("color")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<BatimentAdministratif> findAll() {
        return null;
    }

    @Override
    public BatimentAdministratif create(BatimentAdministratif object) {
        return null;
    }

    @Override
    public BatimentAdministratif update(BatimentAdministratif object) {
        return null;
    }

    @Override
    public void delete(BatimentAdministratif object) {

    }

    public ArrayList<BatimentAdministratif> getArrayListOfBatimentAdministratifAboutOneService(long id){
        ArrayList<BatimentAdministratif> batimentAdministratifs = new ArrayList<>();

        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM batiment b JOIN batiment_service bs ON bs.id_batiment = b.id_batiment WHERE bs.id_service = (?)");
            ps.setLong(1, id);
            ResultSet r = ps.executeQuery();
            while (r.next()){
                Array allpoints = r.getArray("allpoints");
                if (allpoints == null) {
                    BatimentAdministratif batiment = new BatimentAdministratif(Integer.parseInt(r.getString("numero")), r.getDouble("x1"), r.getDouble("x2"), r.getDouble("width"), r.getDouble("heigth"), givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(r.getString("color")));
                    batimentAdministratifs.add(batiment);
                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                    BatimentAdministratif batiment = new BatimentAdministratif(Integer.parseInt(r.getString("numero")), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(r.getString("color")));
                    batimentAdministratifs.add(batiment);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  batimentAdministratifs;
    }
}
