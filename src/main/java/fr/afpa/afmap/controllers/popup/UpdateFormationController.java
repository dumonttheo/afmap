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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UpdateFormationController {
    private Formation formation;
    @FXML
    private VBox vboxLayout;
    @FXML
    private TextField nameFormation;
    @FXML
    private ListView<Personnel> listViewPersonnel;
    @FXML
    private ListView<BatimentFormation> listViewBatiment;

    @FXML
    private Label labelNameFormation;
    private boolean firstTime = true;

    private final ObservableList<BatimentFormation> batiments = FXCollections.observableArrayList();
    private final ObservableList<Personnel> personnels = FXCollections.observableArrayList();

    private final DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
    private final DAOPersonnel daoPersonnel = new DAOPersonnel();
    private final DAOFormation daoFormation = new DAOFormation();


    public void initialize() {
        nameFormation.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && isFirstTime()) {
                vboxLayout.requestFocus(); // Delegate the focus to container
                setFirstTime(false); // Variable value changed for future references
            }
        });

    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
        nameFormation.setText(formation.getNom());
        batiments.addAll(daoBatimentFormation.findAll());
        listViewBatiment.setItems(batiments);
        listViewBatiment.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (int i = 0; i < listViewBatiment.getItems().size(); i++) {
            for (BatimentFormation batimentFormation : formation.getListeBatimentsFormation()) {
                if (listViewBatiment.getItems().get(i).getId() == (batimentFormation.getId())) {
                    listViewBatiment.getSelectionModel().select(i);
                }
            }
        }

        listViewPersonnel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        personnels.addAll(daoPersonnel.findAll());
        listViewPersonnel.setItems(personnels);
        for (int i = 0; i < listViewPersonnel.getItems().size(); i++) {
            for (Personnel personnel : formation.getListePersonnel()) {
                if (listViewPersonnel.getItems().get(i).getId() == (personnel.getId())) {
                    listViewPersonnel.getSelectionModel().select(i);
                }
            }
        }

        labelNameFormation.setText("Modification de " + formation.getNom());

    }

    public void updateFormationHandle() {
        ArrayList<BatimentFormation> batiments = new ArrayList<>(listViewBatiment.getSelectionModel().getSelectedItems());
        ArrayList<Personnel> personnelArrayList = new ArrayList<>(listViewPersonnel.getSelectionModel().getSelectedItems());

        if (batiments.size() > 0) {
            if (personnelArrayList.size() > 0) {
                if (!nameFormation.getText().isEmpty()){
                    if (AddFormation.matchToPattern(nameFormation.getText())){
                        formation = new Formation(formation.getId(), nameFormation.getText(), batiments, personnelArrayList);
                        daoFormation.update(formation);
                        RootFile.closePopUp();
                    }
                }
            }
        }
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
