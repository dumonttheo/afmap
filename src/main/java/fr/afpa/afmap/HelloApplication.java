package fr.afpa.afmap;

import fr.afpa.afmap.controllers.MainController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private boolean isMaximized;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        MainController controller = fxmlLoader.getController();


        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(!isMaximized){
                Double value = controller.getWidthHeigth();
                stage.setHeight(value);
            }else{
                stage.setHeight((double) observable.getValue() / 1.51);
                stage.setWidth((double) observable.getValue());
            }
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {

            if(!isMaximized){
                Double value = controller.getWidthHeigth();
                stage.setHeight(value);
            }
        });        scene.heightProperty().addListener((observable, oldValue, newValue) -> {

            if(!isMaximized){
                Double value = controller.getWidthHeigth();
                stage.setHeight(value);
            }
        });


        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            isMaximized = newValue;
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