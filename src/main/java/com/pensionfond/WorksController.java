package com.pensionfond;

import Server.Models.Work;
import Server.Repositories.WorkRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;
import java.util.List;
import java.util.Optional;

public class WorksController {
    @FXML
    private Button add;

    @FXML
    private Button goBackButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text welcomeText;

    private void showWorks() {
        List<Work> workList = WorkRepository.getWorks();
        gridPane.getChildren().clear();
        int row = 1;

        Text text = new Text();
        text.setText("Назва, " + "Хешований код доступа, " + "Дата заснування");
        text.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(text, 0, row++);
        row++;

        for(Work work : workList) {
            Button button = new Button();
            button.setText(work.name + ", " + work.passwordHash + ", " + work.foundedAt);
            button.setMinWidth(750);
            button.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText("Видалення підприємства");
                alert.setContentText("Ви впевнені щодо видалення підприємства?");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent() || result.get() != ButtonType.OK) {
                    alert.close();
                } else {
                    WorkRepository.deleteWork(work.name);
                    showWorks();
                }
            });
            gridPane.add(button, 0, row++);
        }
    }

    public void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new ProfileController(), "profile.fxml");
        });
        add.setOnAction(event -> {
            add.getScene().getWindow().hide();
            Main.setStage(new CreateWorkController(), "createWork.fxml");
        });
        showWorks();
    }
}
