package com.pensionfond;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EnterpriseProfileController {
    @FXML
    private TextField accountType;

    @FXML
    private TextField birthDate;

    @FXML
    private Text birthText;

    @FXML
    private TextField pib;

    @FXML
    private Text pibText;

    @FXML
    private Text welcomeText;

    @FXML
    private Button posadiButton;

    @FXML
    private Button goBackButton;

    public void initialize() {
        welcomeText.setText("Welcome, " + Main.work.name);
        pib.setText(Main.work.name);
        accountType.setText("Підприємство");
        birthDate.setText(Main.work.foundedAt);
        posadiButton.setOnAction(event -> {
            posadiButton.getScene().getWindow().hide();
            Main.setStage(new PosadiController(), "posadi.fxml");
        });
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.user = null;
            Main.work = null;
            Main.setStage(new LoginController(), "login.fxml");
        });
    }
}
