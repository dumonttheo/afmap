package fr.afpa.afmap.dao;

import fr.afpa.afmap.model.BatimentFormation;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DAOBatimentFormation extends Dao_Common<BatimentFormation> {


    @Override
    public BatimentFormation find(long id) {
        try {
            ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM batiment WHERE id_batiment = " + id);
            if (res.first()) {
                Array allpoints = res.getArray("allpoints");
                if (allpoints == null) {
                    return new BatimentFormation(Integer.parseInt(res.getString("numero")), res.getDouble("x1"), res.getDouble("x2"), res.getDouble("width"), res.getDouble("heigth"), Color.BLACK);
                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);
                    return new BatimentFormation(Integer.parseInt(res.getString("numero")), allPointsToDouble, Color.BLACK);
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
                    BatimentFormation batiment = new BatimentFormation(Integer.parseInt(res.getString("numero")), res.getDouble("x1"), res.getDouble("x2"), res.getDouble("width"), res.getDouble("heigth"), givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(res.getString("color")));
                    batimentFormations.add(batiment);
                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);


                    BatimentFormation batiment = new BatimentFormation(Integer.parseInt(res.getString("numero")), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned("FF9933"));
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
        return null;
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

    private Double[] floatToDouble(Float[] floats) {
        Double[] doubles = new Double[floats.length];
        for (int i = 0; i < floats.length; i++) {
            doubles[i] = Double.valueOf(floats[i]);
        }
        return doubles;
    }

    private Color givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(String color) {
        int resultRed = Integer.valueOf(color.substring(0, 2), 16);
        int resultGreen = Integer.valueOf(color.substring(2, 4), 16);
        int resultBlue = Integer.valueOf(color.substring(4, 6), 16);

        return Color.rgb(resultRed, resultGreen, resultBlue);

    }
}
