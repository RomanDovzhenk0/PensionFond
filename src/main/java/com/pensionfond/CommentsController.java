package com.pensionfond;

import Server.Models.Comment;
import Server.Repositories.CommentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

public class CommentsController {

    @FXML
    private Button goBackButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text welcomeText;

    private void showComments() {
        List<Comment> commentList = CommentRepository.getComments();
        gridPane.getChildren().clear();
        int row = 1;

        for (Comment comment : commentList) {
            TextArea textArea = new TextArea();
            textArea.setText("Автор: " + comment.userLogin + "\n"
                    + "Відгук: " + comment.comment + "\n"
                    + "Час створення: " + comment.createdAt + "\n");
            textArea.setWrapText(true);
            textArea.setMinWidth(590);
            textArea.setMaxHeight(60);

            gridPane.add(textArea, 0, row++);
        }
    }

    public void initialize() {
        showComments();
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            Main.setStage(new ProfileController(), "profile.fxml");
        });
    }
}
