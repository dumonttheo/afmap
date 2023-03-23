package fr.afpa.afmap;

import fr.afpa.afmap.controllers.MainController;
import fr.afpa.afmap.controllers.RootFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage)  {
        try{
            RootFile main = new RootFile();
            main.setStage(stage);

        }catch (IOException e){
            System.err.println(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }


}