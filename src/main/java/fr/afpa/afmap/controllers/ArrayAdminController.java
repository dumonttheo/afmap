package fr.afpa.afmap.controllers;

import fr.afpa.afmap.dao.*;
import fr.afpa.afmap.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ArrayAdminController {

    @FXML
    private TableView<Batiment> batimentTableView;

    @FXML
    private TableColumn<Batiment, String> nameBatimentColumn;

    @FXML
    private TableColumn<Batiment, String> numeroBatimentColumn;
    @FXML
    private TableColumn<Object, String> formationServiceBatimentColumn;
    @FXML
    private TableColumn<Batiment, String> idBatimentColumn;
    @FXML
    private TableView<Formation> formationTableView;

    @FXML
    private TableColumn<Formation, String> idFormationColumn;

    @FXML
    private TableColumn<Formation, String> nameFormationColumn;
    @FXML
    private TableColumn<Formation, String> formateursFormationColumn;
    @FXML
    private TableView<Service> serviceListView;

    @FXML
    private TableColumn<Service, String> idServiceColumn;
    @FXML
    private TableColumn<Service, String> nameServiceColumn;
    @FXML
    private TableColumn<Service, String> personnelServiceColumn;
    @FXML
    private TableView<Personnel> personnelTableView;
    @FXML
    private TableColumn<Personnel, String> idPersonnelColumn;
    @FXML
    private TableColumn<Personnel, String> nomPersonnelColumn;
    @FXML
    private TableColumn<Personnel, String> prenomPersonnelColumn;
    @FXML
    private TableColumn<Personnel, String> phonePersonnelColumn;
    @FXML
    private TableColumn<Personnel, String> emailPersonnelColumn;

    private final ObservableList<Batiment> batiments = FXCollections.observableArrayList();
    private final ObservableList<Personnel> personnels = FXCollections.observableArrayList();
    private final ObservableList<Formation> formations = FXCollections.observableArrayList();
    private final ObservableList<Service> services = FXCollections.observableArrayList();

    private final DAOFormation daoFormation = new DAOFormation();
    private final DAOBatimentFormation daoBatimentFormation = new DAOBatimentFormation();
    private final DAOBatimentService daoBatimentService = new DAOBatimentService();
    private final DAOPersonnel daoPersonnel = new DAOPersonnel();
    private final DAOService daoService = new DAOService();


    public void initialize() {

        idFormationColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameFormationColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        formateursFormationColumn.setCellValueFactory(c -> {
            Formation object = c.getValue();
            StringProperty str = new SimpleStringProperty();
            StringBuilder string = new StringBuilder();
            int counterPersonnel = 0;
            for (Personnel personnel : object.getListePersonnel()) {
                counterPersonnel++;
                string.append(personnel.getNom()).append(" ").append(personnel.getPrenom());
                if (!(counterPersonnel == object.getListePersonnel().size())) {
                    string.append(", ");
                }
            }
            str.set(string.toString());
            return str;
        });

        idPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idPersonnelColumn.setSortType(TableColumn.SortType.ASCENDING);
        nomPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        phonePersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
        emailPersonnelColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));

        personnelTableView.getSortOrder().add(idPersonnelColumn);


        idServiceColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameServiceColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        personnelServiceColumn.setCellValueFactory(c -> {
            Service object = c.getValue();
            StringProperty str = new SimpleStringProperty();
            StringBuilder string = new StringBuilder();
            int counterPersonnel = 0;
            for (Personnel personnel : object.getListePersonnel()) {
                counterPersonnel++;
                string.append(personnel.getNom()).append(" ").append(personnel.getPrenom());
                if (counterPersonnel != object.getListePersonnel().size()) {
                    string.append(", ");
                }
            }
            str.set(string.toString());
            return str;
        });


        idBatimentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeroBatimentColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nameBatimentColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        formationServiceBatimentColumn.setCellValueFactory(c -> {
            Object objet = c.getValue();
            StringProperty str = new SimpleStringProperty();
            if (objet instanceof BatimentFormation) {
                ArrayList<Formation> formations1 = ((BatimentFormation) objet).getListeFormations();
                StringBuilder string = new StringBuilder();
                int counterFormation = 0;
                for (Formation formation : formations1) {
                    string.append(formation.getNom());
                    counterFormation++;
                    if (counterFormation != formations1.size()) {
                        string.append(", ");
                    }
                }
                str.set(String.valueOf(string));
            } else if (objet instanceof BatimentAdministratif) {
                ArrayList<Service> services1 = ((BatimentAdministratif) objet).getListeServices();
                StringBuilder string = new StringBuilder();
                int counterService = 0;
                for (Service service : services1) {
                    string.append(service.getNom());
                    counterService++;
                    if (counterService != services1.size()) {
                        string.append(", ");
                    }
                }
                str.set(String.valueOf(string));
            } else {
                str.set("Null");
            }
            return str;
        });


        formations.addAll(daoFormation.findAll());
        formationTableView.setItems(formations);

        services.addAll(daoService.findAll());
        serviceListView.setItems(services);


        personnels.addAll(daoPersonnel.findAll());
        Collections.sort(personnels);
        personnelTableView.setItems(personnels);


        batiments.addAll(daoBatimentFormation.findAll());
        batiments.addAll(daoBatimentService.findAll());
        Collections.sort(batiments);

        batimentTableView.setItems(batiments);

    }

    @FXML
    public void updateFormation() {
        if (formationTableView.getSelectionModel().getSelectedItem() != null) {
            RootFile.openPopupUpdateFormation(600, 450, formationTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void deleteFormation() {
        if (formationTableView.getSelectionModel().getSelectedItem() != null) {
            daoFormation.delete(formationTableView.getSelectionModel().getSelectedItem());
            formations.clear();
            formations.addAll(daoFormation.findAll());
            batiments.clear();
            batiments.addAll(daoBatimentFormation.findAll());
            batiments.addAll(daoBatimentService.findAll());
        }
    }

    @FXML
    public void addPersonnelPopUp() {
        RootFile.openPopupAddPersonnel(450, 400);
    }

    @FXML
    public void modifyPersonnelPopUp() {
        if (personnelTableView.getSelectionModel().getSelectedItem() != null) {
            RootFile.openPopupModifyPersonnel(450, 400, personnelTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void deletePersonnelPopup() {
        if (personnelTableView.getSelectionModel().getSelectedItem() != null) {
            daoPersonnel.delete(personnelTableView.getSelectionModel().getSelectedItem());
            personnels.clear();
            personnels.addAll(daoPersonnel.findAll());
            batiments.clear();
            batiments.addAll(daoBatimentFormation.findAll());
            batiments.addAll(daoBatimentService.findAll());
        }
    }

    @FXML
    public void addFormation() {
        RootFile.openPopupFormationAdd(600, 450);
    }

    @FXML
    public void addServicePopup() {
        RootFile.openPopupAddService(600, 450);
    }

    @FXML
    public void updateServicePopup() {
        if (serviceListView.getSelectionModel().getSelectedItem() != null) {
            RootFile.openPopupUpdateService(600, 450, serviceListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void deleteService() {
        if (serviceListView.getSelectionModel().getSelectedItem() != null) {
            daoService.delete(serviceListView.getSelectionModel().getSelectedItem());
            services.clear();
            batiments.clear();
            batiments.addAll(daoBatimentFormation.findAll());
            batiments.addAll(daoBatimentService.findAll());
            services.addAll(daoService.findAll());
        }
    }

    @FXML
    public void addBatiment() {
        try {
            RootFile.setRoot("admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void updateBatiment() {
        if (batimentTableView.getSelectionModel().getSelectedItem() != null) {
            RootFile.openUpdateBatiment(batimentTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void deleteBatiment() {
        Batiment batiment = batimentTableView.getSelectionModel().getSelectedItem();
        if (batiment != null) {
            if (batiment instanceof BatimentFormation) {
                daoBatimentFormation.delete((BatimentFormation) batiment);
            } else if (batiment instanceof BatimentAdministratif) {
                daoBatimentService.delete((BatimentAdministratif) batiment);
            }
            batiments.clear();
            batiments.addAll(daoBatimentFormation.findAll());
            batiments.addAll(daoBatimentService.findAll());
            Collections.sort(batiments);
        }
    }
}
