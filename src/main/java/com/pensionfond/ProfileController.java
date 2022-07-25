package com.pensionfond;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ProfileController {
    @FXML
    private TextField accountType;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField address;

    @FXML
    private Text addressText;

    @FXML
    private TextField birthDate;

    @FXML
    private Text birthText;

    @FXML
    private TextField email;

    @FXML
    private Text emailText;

    @FXML
    private TextField passId;

    @FXML
    private Text passIdText;

    @FXML
    private TextField pensionNumber;

    @FXML
    private Text pensionNumberText;

    @FXML
    private TextField pensionType;

    @FXML
    private Text pensionTypeText;

    @FXML
    private TextField pib;

    @FXML
    private Text pibText;

    @FXML
    private TextField pension;

    @FXML
    private Text welcomeText;

    @FXML
    private Button calculatorButton;

    @FXML
    private Button createComment;

    @FXML
    private Button dohodiButton;

    @FXML
    private Text rozmir;

    @FXML
    private Button podatkiButton;


    public void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.user = null;
            Main.work = null;
            Main.setStage(new LoginController(), "login.fxml");
        });
        accountType.setText(Main.user.typeOfAccount);
        welcomeText.setText("Welcome, " + Main.user.login);
        pib.setText(Main.user.fullname);
        address.setText(Main.user.address);
        birthDate.setText(Main.user.birthDate);
        if(!Main.user.isPensioner) {
            pensionType.setDisable(true);
            pensionNumber.setDisable(true);
            pension.setDisable(true);
        } else {
            pension.setText(String.valueOf(Main.user.pension));
            calculatorButton.setText("Перерахунок пенсії");

            pensionNumber.setText(Main.user.pensionNumber);
            pensionType.setText(Main.user.typeOfPension);
        }
        calculatorButton.setOnAction(event -> {
            calculatorButton.getScene().getWindow().hide();
            CalculatorController.user = Main.user;
            Main.setStage(new CalculatorController(), "calculator.fxml");
        });
        email.setText(Main.user.email);
        passId.setText(Main.user.passId);

        dohodiButton.setOnAction(event -> {
            dohodiButton.getScene().getWindow().hide();
            DohodiController.user = Main.user;
            Main.setStage(new DohodiController(), "dohodi.fxml");
        });
        podatkiButton.setOnAction(event -> {
            podatkiButton.getScene().getWindow().hide();
            Main.setStage(new PodatkiController(), "podatki.fxml");
        });

        createComment.setOnAction(event -> {
            createComment.getScene().getWindow().hide();
            Main.setStage(new CreateComment(), "createComment.fxml");
        });

        if(Main.user.typeOfAccount.equals("admin")) {
            addressText.setVisible(false);
            birthText.setVisible(false);
            pensionTypeText.setVisible(false);
            pensionNumberText.setVisible(false);
            emailText.setVisible(false);
            passIdText.setVisible(false);
            address.setVisible(false);
            birthDate.setVisible(false);
            pensionType.setVisible(false);
            pensionNumber.setVisible(false);
            email.setVisible(false);
            passId.setVisible(false);
            rozmir.setVisible(false);
            pension.setVisible(false);
            calculatorButton.setText("Всі доходи");
            calculatorButton.setOnAction(event -> {
                calculatorButton.getScene().getWindow().hide();
                Main.setStage(new DohodiController(), "dohodi.fxml");
            });
            dohodiButton.setText("Користувачі");
            dohodiButton.setOnAction(event -> {
                dohodiButton.getScene().getWindow().hide();
                Main.setStage(new UsersController(), "users.fxml");
            });
            podatkiButton.setText("Підприємства");
            podatkiButton.setOnAction(event -> {
                podatkiButton.getScene().getWindow().hide();
                Main.setStage(new WorksController(), "works.fxml");
            });
            createComment.setText("Прочитати відгуки");
            createComment.setOnAction(event -> {
                createComment.getScene().getWindow().hide();
                Main.setStage(new CommentsController(), "comments.fxml");
            });
        }
    }
}
