package com.pensionfond;

import Server.Models.User;
import Server.Repositories.UserRepository;
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

public class UsersController {

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

    private void showUsers() {
        List<User> userList = UserRepository.getUsers();
        gridPane.getChildren().clear();
        int row = 1;

        Text text = new Text();
        text.setText("Логін, " + "ПІБ, " +
                "Дата народження, " + "Тип аккаунта, " + "Пошта, " + "Пенсія, " + "Адреса, " + "Пенсійний номер, "
                + "Тип пенсії, " + "Чи пенсіонер?, " + "Стать, " + "passId");
        text.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(text, 0, row++);
        row++;

        for(User user : userList) {
            Button button = new Button();
            button.setText(user.login + ", " + user.fullname + ", " + user.birthDate + ", " + user.typeOfAccount + ", " +
                    user.email + ", " + user.pension + ", " + user.address + ", " + user.pensionNumber + ", " +
                    user.typeOfPension + ", " + user.isPensioner + ", " + user.sex + ", " + user.passId);
            button.setMinWidth(750);
            button.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText("Видалення користувача");
                alert.setContentText("Ви впевнені щодо видалення користувача?");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent() || result.get() != ButtonType.OK) {
                    alert.close();
                } else {
                    UserRepository.deleteUser(user.login);
                    showUsers();
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
            Main.setStage(new CreateUserController(), "createUser.fxml");
        });
        showUsers();
    }

}

