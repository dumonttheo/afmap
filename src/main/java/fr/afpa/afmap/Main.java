package fr.afpa.afmap;

import fr.afpa.afmap.controllers.RootFile;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            RootFile main = new RootFile();
            main.setStage(stage);

        } catch (IOException e) {
            System.err.println(e);
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

}