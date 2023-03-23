package fr.afpa.afmap.controllers;

import fr.afpa.afmap.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RootFile {
    private static Scene scene;
    private boolean isMaximized;
    public void setStage(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main-view2"));



        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(!isMaximized){
                stage.setHeight(observable.getValue().doubleValue()/1.51);
            }else{
                stage.setHeight((double) observable.getValue() / 1.51);
                stage.setWidth((double) observable.getValue());
            }
        });

//        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if(!isMaximized){
//                MainController controller = new MainController();
//                Double value = controller.getWidthHeigth();
//                stage.setHeight(value);
//            }
//        });


        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> isMaximized = newValue);

        stage.setMaximized(true);
        stage.setTitle("AFMAP");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("logo.png")).openStream()));
        String css = Objects.requireNonNull(Main.class.getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


}
