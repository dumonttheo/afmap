/*
 * Copyright (c) 2023
 *
 * Do by Antonin LOPEZ , https://gitlab.com/NeoXuS18
 */

package fr.afpa.afmap.dao;

import fr.afpa.afmap.dataBase.DatabaseConnect;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class Dao_Common<T> {
    public Connection connect = DatabaseConnect.getInstance().connection;

    public abstract T find(long id);
    public abstract ArrayList<T> findAll();
    public abstract T create(T object);
    public abstract T update(T object);
    public abstract void delete(T object);

    public Double[] floatToDouble(Float[] floats) {
        Double[] doubles = new Double[floats.length];
        for (int i = 0; i < floats.length; i++) {
            doubles[i] = Double.valueOf(floats[i]);
        }
        return doubles;
    }

    public ArrayList<ArrayList<Double>> floatToArrayListDouble(Float[][] floats) {
        ArrayList<ArrayList<Double>> arrayLists = new ArrayList<>();
        for (Float[] aFloat : floats) {
            ArrayList<Double> arrayList = new ArrayList<>();
            arrayList.add(aFloat[0].doubleValue());
            arrayList.add(aFloat[1].doubleValue());
            arrayLists.add(arrayList);
        }
        return arrayLists;
    }



    public Color givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(String color) {
        int resultRed = Integer.valueOf(color.substring(0, 2), 16);
        int resultGreen = Integer.valueOf(color.substring(2, 4), 16);
        int resultBlue = Integer.valueOf(color.substring(4, 6), 16);

        return Color.rgb(resultRed, resultGreen, resultBlue);

    }

    protected Double[][] givenArrayListOfArrayListOfDoubleToArrayTwoDimensionOfDouble(ArrayList<ArrayList<Double>> points){
        Double[][] doubles = new Double[points.size()][2];
        int i = 0;
        for (ArrayList<Double> doubl : points){
            doubles[i][0] = doubl.get(0);
            doubles[i][1] = doubl.get(1);
            i++;
        }
        return  doubles;
    }

}
