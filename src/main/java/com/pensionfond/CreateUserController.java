package com.pensionfond;

import Server.Models.User;
import Server.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class CreateUserController {

    @FXML
    private TextField accountType;

    @FXML
    private Button add;

    @FXML
    private TextField address;

    @FXML
    private TextField birthdate;

    @FXML
    private TextField email;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField isPensioner;

    @FXML
    private TextField login;

    @FXML
    private TextField passId;

    @FXML
    private PasswordField password;

    @FXML
    private TextField pensionNumber;

    @FXML
    private TextField pensionType;

    @FXML
    private TextField pib;

    @FXML
    private TextField sex;

    @FXML
    private Text welcomeText;

    public void initialize() {
        isPensioner.setEditable(false);
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new UsersController(), "users.fxml");
        });
        birthdate.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                LocalDate date = LocalDate.parse(newValue);
                long age = java.time.temporal.ChronoUnit.YEARS.between( date , LocalDate.now());
                if(age >= 65 && sex.getText().equals("male")) {
                    isPensioner.setText("true");
                } else if(age >= 60 && sex.getText().equals("female")) {
                    isPensioner.setText("true");
                } else {
                    isPensioner.setText("false");
                }
            } catch (Exception ignored) {}
                });
        sex.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                long age = java.time.temporal.ChronoUnit.YEARS.between( LocalDate.parse(birthdate.getText()) , LocalDate.now());
                if(age >= 65 && sex.getText().equals("male")) {
                    isPensioner.setText("true");
                } else if(age >= 60 && sex.getText().equals("female")) {
                    isPensioner.setText("true");
                } else {
                    isPensioner.setText("false");
                }
            } catch (Exception ignored) {}
        });
        add.setOnAction(event -> {
            User user = new User(
                    login.getText(),
                    Main.getHashCode(password.getText()),
                    pib.getText(),
                    birthdate.getText(),
                    accountType.getText(),
                    email.getText(),
                    0,
                    address.getText(),
                    pensionNumber.getText(),
                    pensionType.getText(),
                    Boolean.parseBoolean(isPensioner.getText()),
                    sex.getText(), "", "",
                    passId.getText()
            );
            User user1 = null;
            try {
                user1 = UserRepository.getUser(login.getText());
            } catch (Exception ignored) {}
            if(user1 == null && !login.getText().equals("")
                    && !password.getText().equals("")
                    && !pib.getText().equals("")
                    && !birthdate.getText().equals("")
                    && !accountType.getText().equals("")
                    && !email.getText().equals("")
                    && !address.getText().equals("")
                    && !pensionNumber.getText().equals("")
                    && !pensionType.getText().equals("")
                    && !isPensioner.getText().equals("")
                    && !sex.getText().equals("")
                    && !passId.getText().equals("")) {

                try{
                    LocalDate date = LocalDate.parse(birthdate.getText());
                    long age = java.time.temporal.ChronoUnit.YEARS.between( date , LocalDate.now());
                    if(age >= 65 && sex.getText().equals("male")) {
                        user.setPensioner(true);
                    } else if(age >= 60 && sex.getText().equals("female")) {
                        user.setPensioner(true);
                    } else {
                        user.setPensioner(false);
                    }
                } catch (Exception ignored) {}
                UserRepository.addUser(user);
                goBackButton.getScene().getWindow().hide();
                Main.setStage(new UsersController(), "users.fxml");
            }
        });
    }
}
