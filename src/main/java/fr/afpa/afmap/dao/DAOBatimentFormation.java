package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formation;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DAOBatimentFormation extends Dao_Common<BatimentFormation> {

    private DAOFormation daoFormation = new DAOFormation();

    @Override
    public BatimentFormation find(long id) {
        try {
            ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM batiment b JOIN formation_batiment fb ON fb.id_batiment= b.id_batiment WHERE fb.id_formation = " + id);
            if (res.first()) {
                Array allpoints = res.getArray("allpoints");
                if (allpoints == null) {
                    return new BatimentFormation(res.getInt("id_batiment"), Integer.parseInt(res.getString("numero")), res.getString("nom_batiment"), res.getDouble("x1"), res.getDouble("x2"), res.getDouble("width"), res.getDouble("heigth"), Color.BLACK);
                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                    return new BatimentFormation(res.getInt("id_batiment"), Integer.parseInt(res.getString("numero")), res.getString("nom_batiment"), allPointsToDouble, Color.BLACK);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<BatimentFormation> findAllBatimentForOneFormation(long id) {
        ArrayList<BatimentFormation> batimentFormations = new ArrayList<>();
        try {
            ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM formation_batiment fb JOIN batiment ON batiment.id_batiment = fb.id_batiment WHERE id_formation = " + id);
            while (res.next()) {
                Array allpoints = res.getArray("allpoints");
                if (allpoints == null) {
                    BatimentFormation batiment = new BatimentFormation(res.getInt("id_batiment"), Integer.parseInt(res.getString("numero")), res.getString("nom_batiment"), res.getDouble("x1"), res.getDouble("x2"), res.getDouble("width"), res.getDouble("heigth"), givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(res.getString("color")));
                    batimentFormations.add(batiment);
                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);


                    BatimentFormation batiment = new BatimentFormation(res.getInt("id_batiment"), Integer.parseInt(res.getString("numero")), res.getString("nom_batiment"), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(res.getString("color")));
                    batimentFormations.add(batiment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return batimentFormations;
    }

    @Override
    public ArrayList<BatimentFormation> findAll() {
        ArrayList<BatimentFormation> batimentFormations = new ArrayList<>();
        try {
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM formation_batiment fb JOIN batiment b ON b.id_batiment = fb.id_batiment");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Array allpoints = rs.getArray("allpoints");
                ArrayList<Formation> formations = daoFormation.findAllFormationOfOneBatiment(rs.getInt("id_batiment"));
                if (allpoints == null) {
                    BatimentFormation batiment = new BatimentFormation(rs.getInt("id_batiment"), Integer.parseInt(rs.getString("numero")), rs.getString("nom_batiment"), rs.getDouble("x1"), rs.getDouble("x2"), rs.getDouble("width"), rs.getDouble("heigth"), givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(rs.getString("color")));
                    for (Formation formation : formations) {
                        batiment.addFormation(formation);
                    }
                    batimentFormations.add(batiment);

                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);


                    BatimentFormation batiment = new BatimentFormation(rs.getInt("id_batiment"), Integer.parseInt(rs.getString("numero")), rs.getString("nom_batiment"), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(rs.getString("color")));
                    for (Formation formation : formations) {
                        batiment.addFormation(formation);
                    }
                    batimentFormations.add(batiment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return batimentFormations;
    }

    @Override
    public BatimentFormation create(BatimentFormation object) {
        return null;
    }

    @Override
    public BatimentFormation update(BatimentFormation object) {
        return null;
    }

    @Override
    public void delete(BatimentFormation object) {

    }


}
