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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculatorController {

    @FXML
    private Text allTimeDohid;

    @FXML
    private Text alltime;

    @FXML
    private Text alltime1;

    @FXML
    private Button calculateButton;

    @FXML
    private Text currentDohid;

    @FXML
    private Text currentime;

    @FXML
    private Button goBackButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text pencion;

    @FXML
    private Text availableAt;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text welcomeText;

    private int allTime = 0;
    private int currentTime = 0;
    private int allcount = 0;
    private int currentcount = 0;
    public static User user;

    private void showDohodi() {
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
                text = new Text();
                allTime += salary.salary;
                allcount++;
                if(!position.firedAt.equals("")) {
                    currentcount++;
                    currentTime += salary.salary;
                }
                text.setText(UserRepository.getUser(position.userLogin).fullname + ", " + WorkRepository.getWork(position.workLogin).name + ", " + position.name + ", " +
                        salary.salary + ", " + salary.receivedAt + ", " + salary.coefficient + ", " + salary.taxes + ", " +
                        salary.isTaxesPaid + ", " + (position.firedAt.equals("") ? "true" : "false"));
                gridPane.add(text, 0, row++);
            }
        }
        allTimeDohid.setText(String.valueOf(allTime));
        currentDohid.setText(String.valueOf(currentTime));
    }

    public void initialize() {
        availableAt.setVisible(false);
        showDohodi();
        double pensiya = 0;
        if(user.isPensioner) {
            if(allcount == 0) {
                pensiya = 0;
            } else {
                pensiya = allTime / allcount * 0.3;
            }
        } else {
            if(currentcount == 0) {
                pensiya = 0;
            } else {
                pensiya = currentTime / currentcount * 0.3;
            }
        }
        if(user.isPensioner) {
            welcomeText.setText("Перерахунок пенсії:");
            pencion.setText(String.valueOf(pensiya));
            calculateButton.setText("Перерахувати пенсію");

            try{
                LocalDate next = LocalDate.parse(user.nextChangeDate);
                if(user.nextChangeDate == "" || ChronoUnit.DAYS.between(LocalDate.now(), next) < 1) {
                    calculateButton.setDisable(false);
                    availableAt.setVisible(false);
                } else {
                    if(ChronoUnit.DAYS.between(LocalDate.now(), next) >= 1) {
                        calculateButton.setDisable(true);
                        availableAt.setVisible(true);
                        availableAt.setText("Повтроний перерахунок буде доступний: " + next);
                    }
                }
            } catch (Exception ignored) {}

            calculateButton.setOnAction(event -> {
                if(user.isPensioner) {
                    if(allcount == 0) {
                        user.pension = (int)(0);
                    } else {
                        user.pension = (int)(allTime / allcount * 0.3);
                    }
                } else {
                    if(currentcount == 0) {
                        user.pension = (int)(0);
                    } else {
                        user.pension = (int)(currentTime / currentcount * 0.3);
                    }
                }
                user.nextChangeDate = LocalDate.now().plusDays(1).toString();
                UserRepository.updateUser(user);
                calculateButton.getScene().getWindow().hide();
                Main.setStage(new ProfileController(), "profile.fxml");
            });
        } else {
            pencion.setText("");
            calculateButton.setOnAction(event -> {
                if(user.isPensioner) {
                    if(allcount == 0) {
                        pencion.setText(String.valueOf((int)(0)));
                    } else {
                        pencion.setText(String.valueOf((int)(allTime / allcount * 0.3)));
                    }
                } else {
                    if(currentcount == 0) {
                        pencion.setText(String.valueOf((int)(0)));
                    } else {
                        pencion.setText(String.valueOf((int)(currentTime / currentcount * 0.3)));
                    }
                }
            });
        }
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new ProfileController(), "profile.fxml");
        });
    }
}
