package com.pensionfond;

import Server.Models.Position;
import Server.Models.Salary;
import Server.Repositories.SalaryRepository;
import Server.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class AdSalaryController {

    @FXML
    private Button add;

    @FXML
    private TextField coef;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField pib;

    @FXML
    private TextField posada;

    @FXML
    private TextField salary;

    @FXML
    private TextField taxe;

    @FXML
    private Text welcomeText;

    public static Position position;

    public void initialize() {
        pib.setText(UserRepository.getUser(position.userLogin).fullname);
        posada.setText(position.name);
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new SalariesController(), "salaries.fxml");
        });
        Position position1 = position;
        add.setOnAction(event -> {
            if(!salary.getText().equals("") && !coef.getText().equals("") && !taxe.getText().equals("")) {
                Salary salary = new Salary(
                        position1.id,
                        Integer.parseInt(this.salary.getText()),
                        LocalDate.now().toString(),
                        coef.getText(),
                        Integer.parseInt(taxe.getText()),
                        false);

                SalaryRepository.addSalary(salary);
                goBackButton.getScene().getWindow().hide();
                Main.setStage(new SalariesController(), "salaries.fxml");
            }
        });
    }

}
