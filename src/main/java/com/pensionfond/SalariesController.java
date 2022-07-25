package com.pensionfond;

import Server.Models.Position;
import Server.Models.Salary;
import Server.Repositories.PositionRepository;
import Server.Repositories.SalaryRepository;
import Server.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SalariesController {

    @FXML
    private Button addSalary;

    @FXML
    private Button deletePosada;

    @FXML
    private Button goBackButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text welcomeText;

    public static Position position;

    private void showSalaries() {
        List<Salary> salaries = SalaryRepository.getSalariesByPosId(position.id);
        gridPane.getChildren().clear();
        int row = 1;

        Text text = new Text();
        text.setText("ПІБ, Посада, Зарплата, Дата отримання, Коефіцієнт, Податок, Чи сплачен податок?");
        gridPane.add(text, 0, row++);

        for(Salary salary : salaries) {
            Button button = new Button();
            button.setText(UserRepository.getUser(position.userLogin).fullname + ", " + position.name + ", " +
                    salary.salary + ", " + salary.receivedAt + ", " + salary.coefficient + ", " + salary.taxes +
                    ", " + salary.isTaxesPaid);
            button.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText("Видалення зарплати");
                alert.setContentText("Ви впевнені щодо видалення зарплати?");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent() || result.get() != ButtonType.OK) {
                    alert.close();
                } else {
                    SalaryRepository.deleteSalary(salary.id);
                    showSalaries();
                }
            });
            button.setMinWidth(680);
            button.setBackground(Background.EMPTY);
            gridPane.add(button, 0, row++);
        }
    }

    public void initialize() {
        addSalary.setOnAction(event -> {
            AdSalaryController.position = position;
            addSalary.getScene().getWindow().hide();
            Main.setStage(new AdSalaryController(), "createSalary.fxml");
            AdSalaryController.position = null;
        });
        showSalaries();
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new PosadiController(), "posadi.fxml");
        });
        Position position1 = position;
        deletePosada.setOnAction(event -> {
            position1.setFiredAt(LocalDate.now().toString());
            PositionRepository.updatePosition(position1);
            deletePosada.getScene().getWindow().hide();
            Main.setStage(new PosadiController(), "posadi.fxml");
        });
    }

}

