package com.pensionfond;

import Server.Models.Position;
import Server.Models.Salary;
import Server.Repositories.PositionRepository;
import Server.Repositories.SalaryRepository;
import Server.Repositories.WorkRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Optional;

public class PodatkiController {
    @FXML
    private Text allTimeDohid;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button goBackButton;

    @FXML
    private Text welcomeText;

    private void showPodatki() {
        List<Position> positionList = PositionRepository.getPositionsByUserLogin(Main.user.login);
        gridPane.getChildren().clear();
        int row = 1;
        int podatki = 0;
        Text text = new Text();
        text.setText("Підприємство, " + "Посада, " +
                "Зарплата, " + "Дата видачі, " + "Коефіцієнт, " + "Податок");
        text.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(text, 0, row++);
        row++;

        for(Position position : positionList) {
            List<Salary> salaryList = SalaryRepository.getSalariesByPosId(position.id);
            for(Salary salary : salaryList) {
                if(!salary.isTaxesPaid) {
                    Button button = new Button();
                    podatki += salary.taxes;
                    button.setText(WorkRepository.getWork(position.workLogin).name + ", " + position.name + ", " +
                            salary.salary + ", " + salary.receivedAt + ", " + salary.coefficient + ", " + salary.taxes);
                    button.setMinWidth(520);
                    button.setBackground(Background.EMPTY);
                    button.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.setHeaderText("Оплата податка");
                        alert.setContentText("Ви впевнені щодо оплати податка?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(!result.isPresent() || result.get() != ButtonType.OK) {
                            alert.close();
                        } else {
                            salary.isTaxesPaid = true;
                            SalaryRepository.paidTax(salary);
                            showPodatki();
                        }
                    });
                    gridPane.add(button, 0, row++);
                }
            }
        }
        allTimeDohid.setText(String.valueOf(podatki));
    }

    public void initialize() {
        showPodatki();
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new ProfileController(), "profile.fxml");
        });
    }
}
