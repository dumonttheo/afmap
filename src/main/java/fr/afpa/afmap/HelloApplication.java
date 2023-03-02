package fr.afpa.afmap;

import fr.afpa.afmap.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private boolean isMaximized;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        MainController controller = fxmlLoader.getController();



        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double width = bounds.getWidth();
        double height = bounds.getHeight();

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {

            if(!isMaximized){
                Double value = controller.getWidthHeigth();
                stage.setHeight(value);
            }else{
                stage.setHeight(height);
                stage.setWidth(width);
            }
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            if(!isMaximized){
                Double value = controller.getWidthHeigth();
                stage.setHeight(value);
            }else{
                stage.setHeight(height);
                stage.setWidth(width);
            }
        });


        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                isMaximized = true;
            }else{
                isMaximized = false;
                Double value = controller.getWidthHeigth();
            }
            System.out.println(isMaximized);
        });


        System.out.println("Largeur d'affichage: " + width + " pixels");
        System.out.println("Hauteur d'affichage: " + height + " pixels");
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