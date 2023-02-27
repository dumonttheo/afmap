package fr.afpa.afmap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("AFMAP");
        String css=this.getClass().getResource("style.css").toExternalForm();
scene.getStylesheets().add(css);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}