package fr.afpa.afmap.controllers;

import fr.afpa.afmap.Main;
import fr.afpa.afmap.controllers.popup.ModiyPersonnelController;
import fr.afpa.afmap.controllers.popup.UpdateFormationController;
import fr.afpa.afmap.controllers.popup.UpdateService;
import fr.afpa.afmap.model.Batiment;
import fr.afpa.afmap.model.Formation;
import fr.afpa.afmap.model.Personnel;
import fr.afpa.afmap.model.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RootFile {
    private static final Stage popup = new Stage();
    private static Scene scene;
    private boolean isMaximized;

    public void setStage(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main-view2"));


        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (!isMaximized) {
                stage.setHeight(observable.getValue().doubleValue() / 1.51);
            } else {
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
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
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

    public static void openPopupAddPersonnel(double width, double height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popup/popupAddPersonnel.fxml"));
            Scene scenepopup = new Scene(fxmlLoader.load(), width, height);
            popup.setTitle("AFMAP - Ajout d'un Personnel");
            popup.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
            popup.setMinWidth(width);
            popup.setMinHeight(height);
            if (!popup.getModality().equals(Modality.APPLICATION_MODAL)) {
                popup.initModality(Modality.APPLICATION_MODAL);
            }
            popup.setScene(scenepopup);
            popup.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void openPopupFormationAdd(double width, double height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popup/addFormation.fxml"));
            Scene scenepopup = new Scene(fxmlLoader.load(), width, height);
            popup.setTitle("AFMAP - Ajout d'une Formation");
            popup.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
            popup.setMinWidth(width);
            popup.setMinHeight(height);
            if (!popup.getModality().equals(Modality.APPLICATION_MODAL)) {
                popup.initModality(Modality.APPLICATION_MODAL);
            }
            popup.setScene(scenepopup);
            popup.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openPopupModifyPersonnel(double width, double height, Personnel personnel) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popup/popupModifyPersonnel.fxml"));
            Scene scenepopup = new Scene(fxmlLoader.load(), width, height);

            //            Sett the personnel into the controller
            ModiyPersonnelController controller = fxmlLoader.getController();
            controller.setPersonnel(personnel);

            popup.setTitle("AFMAP - Modification de " + personnel.getNom() + " " + personnel.getPrenom());
            popup.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
            popup.setMinWidth(width);
            popup.setMinHeight(height);
            if (!popup.getModality().equals(Modality.APPLICATION_MODAL)) {
                popup.initModality(Modality.APPLICATION_MODAL);
            }
            popup.setScene(scenepopup);
            popup.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openPopupUpdateFormation(double width, double height, Formation batiment) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popup/updateFormation.fxml"));
            Scene scenepopup = new Scene(fxmlLoader.load(), width, height);

            //            Sett the personnel into the controller
            UpdateFormationController controller = fxmlLoader.getController();
            controller.setFormation(batiment);

            popup.setTitle("AFMAP - Modification de " + batiment.getNom() );
            popup.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
            popup.setMinWidth(width);
            popup.setMinHeight(height);
            if (!popup.getModality().equals(Modality.APPLICATION_MODAL)) {
                popup.initModality(Modality.APPLICATION_MODAL);
            }
            popup.setScene(scenepopup);
            popup.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void openPopupAddService(double width, double height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popup/addService.fxml"));
            Scene scenepopup = new Scene(fxmlLoader.load(), width, height);


            popup.setTitle("AFMAP - Ajout d'un service");
            popup.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
            popup.setMinWidth(width);
            popup.setMinHeight(height);
            if (!popup.getModality().equals(Modality.APPLICATION_MODAL)) {
                popup.initModality(Modality.APPLICATION_MODAL);
            }
            popup.setScene(scenepopup);
            popup.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openPopupUpdateService(double width, double height, Service service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popup/updateService.fxml"));
            Scene scenepopup = new Scene(fxmlLoader.load(), width, height);


            UpdateService controller = fxmlLoader.getController();
            controller.setService(service);

            popup.setTitle("AFMAP - Modification de " + service.getNom());
            popup.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("assets/logo.png")).openStream()));
            popup.setMinWidth(width);
            popup.setMinHeight(height);
            if (!popup.getModality().equals(Modality.APPLICATION_MODAL)) {
                popup.initModality(Modality.APPLICATION_MODAL);
            }
            popup.setScene(scenepopup);
            popup.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openUpdateBatiment(Batiment batiment) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("updateBatiment.fxml"));
            Parent parent = fxmlLoader.load();

            UpdateBatimentController controller = fxmlLoader.getController();
            controller.setBatiment(batiment);

            scene.setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closePopUp() {
        popup.close();
        try {
            setRoot("arrayAdmin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
