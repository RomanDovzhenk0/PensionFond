package com.pensionfond;

import Server.Models.Work;
import Server.Repositories.WorkRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class CreateWorkController {
    @FXML
    private Button add;

    @FXML
    private TextField birthdate;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Text welcomeText;

    public void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new WorksController(), "works.fxml");
        });
        add.setOnAction(event -> {
            Work work = new Work(
                login.getText(),
                Main.getHashCode(password.getText()),
                birthdate.getText()
            );
            Work work1 = null;
            try {
                work1 = WorkRepository.getWork(login.getText());
            } catch (Exception ignored) {}
            if(work1 == null
                    && !login.getText().equals("")
                    && !password.getText().equals("")
                    && !birthdate.getText().equals("")) {
                WorkRepository.addWork(work);
                goBackButton.getScene().getWindow().hide();
                Main.setStage(new WorksController(), "works.fxml");
            }
        });
    }
}
