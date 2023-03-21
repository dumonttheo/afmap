package fr.afpa.afmap;

import fr.afpa.afmap.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private boolean isMaximized;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        MainController controller = fxmlLoader.getController();


        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(!isMaximized){
                Double value = controller.getWidthHeigth();
                stage.setHeight(observable.getValue().doubleValue()/1.51);
                System.out.println(observable.getValue().doubleValue()/1.51);
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
        });


        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            isMaximized = newValue;
        });

        stage.setMaximized(true);
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