package fr.afpa.afmap.controllers.popup;

import fr.afpa.afmap.controllers.RootFile;
import fr.afpa.afmap.dao.DAOBatimentService;
import fr.afpa.afmap.dao.DAOPersonnel;
import fr.afpa.afmap.dao.DAOService;
import fr.afpa.afmap.model.BatimentAdministratif;
import fr.afpa.afmap.model.Personnel;
import fr.afpa.afmap.model.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AddService {
    @FXML
    private VBox vboxLayout;
    @FXML
    private TextField nameService;
    @FXML
    private ListView<Personnel> listViewPersonnel;
    @FXML
    private ListView<BatimentAdministratif> listViewBatiment;
    private boolean firstTime = true;

    private final ObservableList<BatimentAdministratif> batiments = FXCollections.observableArrayList();
    private final ObservableList<Personnel> personnels = FXCollections.observableArrayList();

    private final DAOBatimentService daoBatimentService = new DAOBatimentService();
    private final DAOPersonnel daoPersonnel = new DAOPersonnel();

    private final DAOService daoService = new DAOService();

    public void initialize() {
        nameService.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && getFirstTime()) {
                vboxLayout.requestFocus(); // Delegate the focus to container
                setFirstTime(false); // Variable value changed for future references
            }
        });

        listViewPersonnel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewBatiment.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        personnels.addAll(daoPersonnel.findAll());
        listViewPersonnel.setItems(personnels);

        batiments.addAll(daoBatimentService.findAll());
        listViewBatiment.setItems(batiments);
    }

    public Boolean getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Boolean firstTime) {
        this.firstTime = firstTime;
    }

    public void addService() {
        ArrayList<BatimentAdministratif> batimentAdministratifs = new ArrayList<>(listViewBatiment.getSelectionModel().getSelectedItems());
        ArrayList<Personnel> personnelArrayList = new ArrayList<>(listViewPersonnel.getSelectionModel().getSelectedItems());
        String serviceName = nameService.getText();
        if (batimentAdministratifs.size() > 0) {
            if (personnelArrayList.size() > 0) {
                if (!serviceName.isEmpty()) {
                    if (AddFormation.matchToPattern(serviceName)) {
                        daoService.create(new Service(serviceName, batimentAdministratifs, personnelArrayList));
                        RootFile.closePopUp();
                    }
                }
            }
        }
    }
}
