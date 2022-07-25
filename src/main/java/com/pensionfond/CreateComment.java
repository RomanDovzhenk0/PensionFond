package com.pensionfond;

import Server.Models.Comment;
import Server.Repositories.CommentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class CreateComment {
    @FXML
    private Button add;

    @FXML
    private Button goBackButton;

    @FXML
    private TextArea textField;

    @FXML
    private Text welcomeText;

    public void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new ProfileController(), "profile.fxml");
        });
        add.setOnAction(event -> {
            if(!textField.getText().equals("")) {
                CommentRepository.addComment(new Comment(Main.user.login, textField.getText(), LocalDate.now().toString()));
                goBackButton.getScene().getWindow().hide();
                Main.setStage(new ProfileController(), "profile.fxml");
            }
        });
    }

}
