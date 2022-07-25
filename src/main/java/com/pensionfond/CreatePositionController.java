package com.pensionfond;

import Server.Models.Position;
import Server.Models.User;
import Server.Repositories.PositionRepository;
import Server.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class CreatePositionController {

    @FXML
    private Button add;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField positionName;

    @FXML
    private TextField userLogin;

    @FXML
    private Text welcomeText;

    @FXML
    private TextField workNam;

    public void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new PosadiController(), "posadi.fxml");
        });
        workNam.setText(Main.work.name);

        add.setOnAction(event -> {
            if(!userLogin.getText().equals("")
                    && !workNam.getText().equals("")) {
                User user = UserRepository.getUser(userLogin.getText());
                if(user != null) {
                    PositionRepository.addPosition(new Position(userLogin.getText(),
                            workNam.getText(),
                            positionName.getText(),
                            LocalDate.now().toString(),
                            ""));
                    goBackButton.getScene().getWindow().hide();
                    Main.setStage(new PosadiController(), "posadi.fxml");
                }
            }
        });
    }

}
