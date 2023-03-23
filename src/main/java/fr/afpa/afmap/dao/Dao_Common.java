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

    public Color givenHexCode_whenConvertedToRgb_thenCorrectRgbValuesAreReturned(String color) {
        int resultRed = Integer.valueOf(color.substring(0, 2), 16);
        int resultGreen = Integer.valueOf(color.substring(2, 4), 16);
        int resultBlue = Integer.valueOf(color.substring(4, 6), 16);

        return Color.rgb(resultRed, resultGreen, resultBlue);

    }

}
