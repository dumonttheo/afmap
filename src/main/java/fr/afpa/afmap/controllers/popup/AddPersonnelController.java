package fr.afpa.afmap.controllers.popup;

import fr.afpa.afmap.controllers.RootFile;
import fr.afpa.afmap.dao.DAOPersonnel;
import fr.afpa.afmap.model.Personnel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddPersonnelController {
    DAOPersonnel daoPersonnel = new DAOPersonnel();

    @FXML
    private TextField nameInput;
    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField emailInput;
    @FXML
    private TextField phoneNumberInput;

    @FXML
    private Label nameError;
    @FXML
    private Label firstNameError;
    @FXML
    private Label emailError;
    @FXML
    private Label phoneError;

    public void initialize(){
        nameError.setVisible(false);
        emailError.setVisible(false);
        firstNameError.setVisible(false);
        phoneError.setVisible(false);
    }

    @FXML
    public void createAPersonnel() {
        boolean nameGood;
        boolean firstNameGood;
        boolean emailGood;
        boolean phoneGood;

        if (!nameInput.getText().isEmpty()) {
            String pattern = "^[a-zA-Z\\sàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð]+";
            Pattern pattern1 = Pattern.compile(pattern);
            Matcher matcher = pattern1.matcher(nameInput.getText());
            if (matcher.matches()){
                nameInput.setStyle("-fx-border-color:transparent;");
                nameGood = true;
                nameError.setVisible(false);
            }else {
                nameGood = false;
                nameInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
                nameError.setVisible(true);
                nameError.setText("Nom incorrect");
            }
        } else {
            nameGood = false;
            nameInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
            nameError.setVisible(true);
            nameError.setText("Merci de rentrer un nom");
        }

        if (!firstnameInput.getText().isEmpty()) {
            String pattern = "^[a-zA-Z\\sàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð]+";
            Pattern pattern1 = Pattern.compile(pattern);
            Matcher matcher = pattern1.matcher(firstnameInput.getText());
            if (matcher.matches()){
                firstnameInput.setStyle("-fx-border-color:transparent;");
                firstNameGood = true;
                firstNameError.setVisible(false);
            }else {
                firstNameGood = false;
                firstnameInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
                firstNameError.setVisible(true);
                firstNameError.setText("Prénom incorrect");
            }
        } else {
            firstNameGood = false;
            firstnameInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
            firstNameError.setVisible(true);
            firstNameError.setText("Merci de rentrer un prénom");
        }


        if (!emailInput.getText().isEmpty()) {
            String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(emailInput.getText());
            if (matcher.matches()) {
                emailInput.setStyle("-fx-border-color:transparent;");
                emailGood = true;
                emailError.setVisible(false);
            } else {
                emailError.setVisible(true);
                emailError.setText("L'email n'est pas conforme");
                emailGood = false;
            }
        } else {
            emailInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
            emailGood = false;
            emailError.setVisible(true);
            emailError.setText("Merci de rentrer un email");
        }

        if (!phoneNumberInput.getText().isEmpty()) {
            String patterns
                    = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3} ?){2}\\d{3}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3} ?)(\\d{2} ?){2}\\d{2}$";
            Pattern pattern = Pattern.compile(patterns);
            Matcher matcher = pattern.matcher(phoneNumberInput.getText());
            if (matcher.matches()){
                phoneGood = true;
                phoneError.setVisible(false);
                phoneNumberInput.setStyle("-fx-border-color:transparent;");
            }else {
                phoneNumberInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
                phoneGood = false;
                phoneError.setVisible(true);
                phoneError.setText("Numéro incorrect");
            }
        } else {
            phoneNumberInput.setStyle("-fx-border-color:red; -fx-border-radius:3;");
            phoneGood = false;
            phoneError.setVisible(true);
            phoneError.setText("Numéro incorrect");
        }

        if (phoneGood && emailGood && firstNameGood && nameGood){
            Personnel personnel = new Personnel(nameInput.getText(),firstnameInput.getText(), phoneNumberInput.getText(), emailInput.getText());
            daoPersonnel.create(personnel);
            RootFile.closePopUp();
        }

    }

}
