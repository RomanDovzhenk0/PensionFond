package com.pensionfond;

import Server.Repositories.UserRepository;
import Server.Repositories.WorkRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    public void initialize() {
        signInButton.setOnAction(event -> {
            try{
                Main.user = UserRepository.getUser(loginField.getText());
            } catch (Exception ignored){}
            if(Main.user != null) {
                if(Main.user.password_hash.equals(Main.getHashCode(passwordField.getText()))) {
                    signInButton.getScene().getWindow().hide();
                    Main.setStage(new ProfileController(), "profile.fxml");
                }
            } else {
                Main.work = WorkRepository.getWork(loginField.getText());
                if(Main.work != null) {
                    if(Main.work.passwordHash.equals(Main.getHashCode(passwordField.getText()))) {
                        signInButton.getScene().getWindow().hide();
                        Main.setStage(new EnterpriseProfileController(), "enterpriseProfile.fxml");
                    }
                }
            }
        });
    }
}
