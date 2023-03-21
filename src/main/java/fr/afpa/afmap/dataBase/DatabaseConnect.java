package fr.afpa.afmap.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnect {
    private static DatabaseConnect instance;
    public Connection connection;

    private DatabaseConnect() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/afmap", "postgres", "Antonin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnect getInstance(){
        if(instance == null){
            instance = new DatabaseConnect();
        }
        return  instance;
    }

}
