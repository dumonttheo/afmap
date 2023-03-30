package fr.afpa.afmap;

import fr.afpa.afmap.controllers.RootFile;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            RootFile main = new RootFile();
            main.setStage(stage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }


    public static String colorToHex(Color color) {
        return String.format( "%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public static ArrayList<ArrayList<Double>> stringToArrayList(String input) {
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        String[] entries = input.split("[{}]+");
        for (String entry : entries) {
            if (entry.trim().length() == 0) {
                continue;
            }
            String[] values = entry.split(",");
            ArrayList<Double> coordinates = new ArrayList<Double>();
            for (String value : values) {
                coordinates.add(Double.parseDouble(value.trim()));
            }
            result.add(coordinates);
        }
        return result;
    }

}