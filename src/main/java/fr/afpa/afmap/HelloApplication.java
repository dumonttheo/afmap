package fr.afpa.afmap;

import fr.afpa.afmap.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        MainController controller = fxmlLoader.getController();


        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            Double value = controller.getWidthHeigth();
            stage.setHeight(value);
            System.out.println(value);
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            Double value = controller.getWidthHeigth();
            stage.setHeight(value);
            System.out.println(value);
        });

        stage.setTitle("AFMAP");
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}