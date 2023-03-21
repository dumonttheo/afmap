package fr.afpa.afmap.dao;

import fr.afpa.afmap.dataBase.DatabaseConnect;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class Dao_Common<T> {
    public Connection connect = DatabaseConnect.getInstance().connection;

    public abstract T find(long id);
    public abstract ArrayList<T> findAll();
    public abstract T create(T object);
    public abstract T update(T object);
    public abstract void delete(T object);

}
