package fr.afpa.afmap.controllers;

import fr.afpa.afmap.dao.DAOBatimentFormation;
import fr.afpa.afmap.dao.DAOBatimentService;
import fr.afpa.afmap.dao.DAOFormation;
import fr.afpa.afmap.dao.DAOPersonnel;
import fr.afpa.afmap.model.Batiment;
import fr.afpa.afmap.model.Formation;
import fr.afpa.afmap.model.Personnel;
import fr.afpa.afmap.model.Service;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ArrayAdminController {

    @FXML
    private ListView<Batiment> batimentListView;
    @FXML
    private TableView<Formation> formationTableView;

    @FXML
    private TableColumn<Formation, String> idFormationColumn;

    @FXML
    private TableColumn<Formation, String>nameFormationColumn;
    @FXML
    private ListView servicesListView;
    @FXML
    private TableView personnelTableView;
    @FXML
    private TableColumn<Personnel, String> idPersonnelColumn;
    @FXML
    private TableColumn<Personnel, String>nomPersonnelColumn;
    @FXML
    private TableColumn<Personnel, String> prenomPersonnelColumn;
    @FXML
    private TableColumn<Personnel, String>phonePersonnelColumn;
    @FXML
    private TableColumn<Personnel, String>emailPersonnelColumn;

    private final ObservableList<Batiment> batiments = FXCollections.observableArrayList();
    private final ObservableList<Personnel> personnels = FXCollections.observableArrayList();
    private final ObservableList<Formation> formations = FXCollections.observableArrayList();
    private final ObservableList<Service> services = FXCollections.observableArrayList();

    private  final DAOFormation daoFormation = new DAOFormation();
    private final DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
    private final DAOBatimentService daoBatimentService = new DAOBatimentService();
    private final DAOPersonnel daoPersonnel = new DAOPersonnel();



    public void initialize(){
        nameFormationColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        idFormationColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        phonePersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
        emailPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));



        formations.addAll(daoFormation.findAll());
        formationTableView.setItems(formations);



        personnels.addAll(daoPersonnel.findAll());
        personnelTableView.setItems(personnels);

//        batiments.addAll(daoBatimentFormation.findAll());
//        batiments.addAll(daoBatimentService.findAll());
//        batimentListView.setItems(batiments);
    }
}
