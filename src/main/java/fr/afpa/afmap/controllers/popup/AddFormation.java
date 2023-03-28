package fr.afpa.afmap.controllers.popup;

import fr.afpa.afmap.controllers.RootFile;
import fr.afpa.afmap.dao.DAOBatimentFormation;
import fr.afpa.afmap.dao.DAOFormation;
import fr.afpa.afmap.dao.DAOPersonnel;
import fr.afpa.afmap.model.BatimentFormation;
import fr.afpa.afmap.model.Formation;
import fr.afpa.afmap.model.Personnel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddFormation {
    @FXML
    private VBox vboxLayout;
    @FXML
    private TextField nameFormation;
    @FXML
    private ListView<Personnel> listViewPersonnel;
    @FXML
    private ListView<BatimentFormation> listViewBatiment;
    private boolean firstTime = true;

    private final ObservableList<BatimentFormation> batiments = FXCollections.observableArrayList();
    private final ObservableList<Personnel> personnels = FXCollections.observableArrayList();

    public void initialize() {
        DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
        DAOPersonnel daoPersonnel = new DAOPersonnel();

        listViewBatiment.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewPersonnel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        nameFormation.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && getFirstTime()) {
                vboxLayout.requestFocus(); // Delegate the focus to container
                setFirstTime(false); // Variable value changed for future references
            }
        });

        batiments.addAll(daoBatimentFormation.findAll());
        listViewBatiment.setItems(batiments);

        personnels.addAll(daoPersonnel.findAll());
        listViewPersonnel.setItems(personnels);


    }

    @FXML
    public void addFormation() {
        if (listViewBatiment.getSelectionModel().getSelectedItems().size() > 0) {
            if (listViewPersonnel.getSelectionModel().getSelectedItems().size() > 0) {
                if (!nameFormation.getText().isEmpty()){
                    String formationName = nameFormation.getText();
                    if (matchToPattern(nameFormation.getText())){
                        ArrayList<BatimentFormation> batimentFormations = new ArrayList<>(listViewBatiment.getSelectionModel().getSelectedItems());
                        ArrayList<Personnel> personnelArrayList = new ArrayList<>(listViewPersonnel.getSelectionModel().getSelectedItems());

                        DAOFormation daoFormation = new DAOFormation();
                        Formation formation = new Formation(formationName, batimentFormations, personnelArrayList);
                        daoFormation.create(formation);
                        RootFile.closePopUp();
                    }
                }
            }
        }
    }

    public Boolean getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Boolean firstTime) {
        this.firstTime = firstTime;
    }

    public static boolean matchToPattern(String str){
        Pattern pattern = Pattern.compile("^[a-zA-Z ]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
