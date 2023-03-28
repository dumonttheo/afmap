package fr.afpa.afmap;

import fr.afpa.afmap.controllers.RootFile;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

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