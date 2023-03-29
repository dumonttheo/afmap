package fr.afpa.afmap.dao;

import fr.afpa.afmap.Main;
import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formation;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.sql.*;
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
            PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM batiment b LEFT JOIN formation_batiment fb ON b.id_batiment = fb.id_batiment WHERE is_formation = true");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Array allpoints = rs.getArray("allpoints");
                ArrayList<Formation> formations = daoFormation.findAllFormationOfOneBatiment(rs.getInt("id_batiment"));
                if (allpoints == null) {
                    BatimentFormation batiment = new BatimentFormation(rs.getInt("id_batiment"), Integer.parseInt(rs.getString("numero")), rs.getString("nom_batiment"), rs.getDouble("x1"), rs.getDouble("x2"), rs.getDouble("width"), rs.getDouble("heigth"), givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(rs.getString("color")));
                    for (Formation formation : formations) {
                        batiment.addFormation(formation);
                    }
                    boolean isAlreadyIn = false;
                    for (BatimentFormation batimentFormation : batimentFormations){
                        if (batimentFormation.getNom().equals(batiment.getNom())) {
                            isAlreadyIn = true;
                            break;
                        }
                    }

                    if (!isAlreadyIn){
                        batimentFormations.add(batiment);
                    }

                } else {
                    Float[] allPointsToFloat = (Float[]) allpoints.getArray();
                    Double[] allPointsToDouble = floatToDouble(allPointsToFloat);


                    BatimentFormation batiment = new BatimentFormation(rs.getInt("id_batiment"), Integer.parseInt(rs.getString("numero")), rs.getString("nom_batiment"), allPointsToDouble, givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(rs.getString("color")));
                    for (Formation formation : formations) {
                        batiment.addFormation(formation);
                    }
                    boolean isAlreadyIn = false;
                    for (BatimentFormation batimentFormation : batimentFormations){
                        if (batimentFormation.getNom().equals(batiment.getNom())) {
                            isAlreadyIn = true;
                            break;
                        }
                    }

                    if (!isAlreadyIn){
                        batimentFormations.add(batiment);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return batimentFormations;
    }

    @Override
    public BatimentFormation create(BatimentFormation object) {
        try {
            PreparedStatement addBatimentQuery = connect.prepareStatement("INSERT INTO batiment (numero, nom_batiment, color, allpoints, is_formation) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            addBatimentQuery.setString(1, String.valueOf(object.getNumero()));
            addBatimentQuery.setString(2, object.getNom());
            addBatimentQuery.setString(3, Main.colorToHex(object.getColor()));
            addBatimentQuery.setObject(4,object.getAllPoints());
            addBatimentQuery.setBoolean(5, true);
            addBatimentQuery.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public BatimentFormation update(BatimentFormation object) {

        try {
            PreparedStatement updateBatimentQuery = connect.prepareStatement("UPDATE batiment SET numero = (?), nom_batiment = (?),color = (?), allpoints = (?), is_formation = (?) WHERE id_batiment = (?); ");
            updateBatimentQuery.setString(1, String.valueOf(object.getNumero()));
            updateBatimentQuery.setString(2, object.getNom());
            updateBatimentQuery.setString(3, Main.colorToHex(object.getColor()));
            updateBatimentQuery.setObject(4, object.getAllPoints());
            updateBatimentQuery.setBoolean(5, true);
            updateBatimentQuery.setInt(6, object.getId());
            updateBatimentQuery.executeUpdate();

            object = find(object.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public void delete(BatimentFormation object) {

    }


}
