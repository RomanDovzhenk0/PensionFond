package com.pensionfond;

import Server.Models.Position;
import Server.Repositories.PositionRepository;
import Server.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

public class PosadiController {

    @FXML
    private Button addPosad;

    @FXML
    private Text alltime;

    @FXML
    private Button goBackButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text posadCount;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text welcomeText;

    private void showPosadi() {
        List<Position> positionList  = PositionRepository.getPositionsByWorkId(Main.work.name);

        gridPane.getChildren().clear();
        int row = 1;

        Text text = new Text();
        text.setText("ПІБ, Підприємство, Посада, Прийнятий, Звільнений");
        gridPane.add(text, 0, row++);

        for(Position position : positionList) {
            Button button = new Button();
            button.setText(UserRepository.getUser(position.userLogin).fullname + ", " + Main.work.name + ", "
            + position.name + ", " + position.acceptedAt + ", " + position.firedAt);
            button.setOnAction(event -> {
                gridPane.getScene().getWindow().hide();
                SalariesController.position = position;
                Main.setStage(new SalariesController(), "salaries.fxml");
            });
            button.setMinWidth(680);
            button.setBackground(Background.EMPTY);
            gridPane.add(button, 0, row++);
        }
        posadCount.setText(String.valueOf(positionList.size()));
    }

    public void initialize() {
        showPosadi();
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new EnterpriseProfileController(), "enterpriseProfile.fxml");
        });
        addPosad.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new CreatePositionController(), "createPosition.fxml");
        });
    }

}

