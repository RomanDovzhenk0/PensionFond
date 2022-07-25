package com.pensionfond;

import Server.Models.Position;
import Server.Models.Salary;
import Server.Models.User;
import Server.Repositories.PositionRepository;
import Server.Repositories.SalaryRepository;
import Server.Repositories.UserRepository;
import Server.Repositories.WorkRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DohodiController {

    @FXML
    private DatePicker fromPicker;

    @FXML
    private DatePicker toPicker;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text allTimeDohid;

    @FXML
    private Text alltime;

    @FXML
    private Text currentime;

    @FXML
    private Text currentDohid;

    @FXML
    private Button goBackButton;

    @FXML
    private Text welcomeText;

    private int allTime = 0;
    private int currentTime = 0;

    private LocalDate fromLocalDate;
    private LocalDate toLocalDate;

    public static User user;

    private void showDohodi() {
        allTime = 0;
        currentTime = 0;
        List<Position> positionList = null;
        if(user != null) {
            positionList = PositionRepository.getPositionsByUserLogin(user.login);
        } else {
            positionList = PositionRepository.getPositions();
            allTimeDohid.setVisible(false);
            currentDohid.setVisible(false);
            alltime.setVisible(false);
            currentime.setVisible(false);
        }
        gridPane.getChildren().clear();
        int row = 1;

        Text text = new Text();
        text.setText("Отримувач, " + "Підприємство, " + "Посада, " +
                "Зарплата, " + "Дата видачі, " + "Коефіцієнт, " + "Податок, " + "Чи сплачен податок?, " + "Чи працює зараз?");
        gridPane.add(text, 0, row++);
        row++;

        for(Position position : positionList) {
            List<Salary> salaryList = SalaryRepository.getSalariesByPosId(position.id);
            for(Salary salary : salaryList) {
                LocalDate receivedDate = LocalDate.parse(salary.receivedAt);
                if(receivedDate.isAfter(fromLocalDate != null ? fromLocalDate : LocalDate.MIN)
                        && receivedDate.isBefore(toLocalDate != null ? toLocalDate : LocalDate.MAX)){
                    text = new Text();
                    allTime += salary.salary;
                    if (position.firedAt.equals("")) {
                        currentTime += salary.salary;
                    }
                    text.setText(UserRepository.getUser(position.userLogin).fullname + ", " + WorkRepository.getWork(position.workLogin).name + ", " + position.name + ", " +
                            salary.salary + ", " + salary.receivedAt + ", " + salary.coefficient + ", " + salary.taxes + ", " +
                            salary.isTaxesPaid + ", " + (position.firedAt.equals("") ? "true" : "false"));
                    gridPane.add(text, 0, row++);
                }
            }
        }
        allTimeDohid.setText(String.valueOf(allTime));
        currentDohid.setText(String.valueOf(currentTime));
    }

    public void initialize() {
        showDohodi();
        fromPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(toPicker.getValue() == null || ChronoUnit.DAYS.between(newValue, toPicker.getValue()) > 0) {
                    fromLocalDate = fromPicker.getValue();
                    showDohodi();
                } else {
                    fromLocalDate = null;
                    fromPicker.setValue(null);
                    showDohodi();
                }
            } catch (Exception ignored) {}
        });
        toPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(fromPicker.getValue() == null || ChronoUnit.DAYS.between(fromPicker.getValue(), newValue) > 0) {
                    toLocalDate = toPicker.getValue();
                    showDohodi();
                } else {
                    toLocalDate = null;
                    toPicker.setValue(null);
                    showDohodi();
                }
            } catch (Exception ignored) {}
        });
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new ProfileController(), "profile.fxml");
            user = null;
        });
    }
}
