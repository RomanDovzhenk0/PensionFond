package Server.Repositories;

import Server.Models.Comment;
import com.pensionfond.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository
{
    private static String getCommentQuery = "SELECT * FROM comments WHERE id = ?";
    private static String getCommentsQuery = "SELECT * FROM comments";
    private static String addCommentQuery = "INSERT INTO comments (userLogin, comment) VALUES (?, ?)";
    private static String deleteCommentQuery = "DELETE FROM comments WHERE id = ?";
    private static String updateCommentQuery = "UPDATE comments SET userLogin = ?, comment = ? WHERE id = ?";

    public static Connection connection;

    public static Comment getComment(int id) {
        Main.writeMessage("getComment|" + id);
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(getCommentQuery);
            preparedStatement.setInt(1, id);
            List<Comment> comments = readComments(preparedStatement);

            return comments.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Comment> getComments() {
        Main.writeMessage("getComments|");
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(getCommentsQuery);
            List<Comment> comments = readComments(preparedStatement);

            return comments;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addComment(Comment comment) {
        Main.writeMessage("addComment|" + comment);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addCommentQuery);
            constructCommand(preparedStatement, comment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteComment(int id) {
        Main.writeMessage("deleteComment|" + id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCommentQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateComment(Comment comment) {
        Main.writeMessage("updateComment|" + comment);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateCommentQuery);
            preparedStatement.setInt(3, comment.id);
            constructCommand(preparedStatement, comment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void constructCommand(PreparedStatement preparedStatement, Comment comment) throws SQLException {
        preparedStatement.setString(1, comment.userLogin);
        preparedStatement.setString(2, comment.comment);
    }

    private static List<Comment> readComments(PreparedStatement preparedStatement) throws SQLException {
        List<Comment> list = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Comment comment = new Comment(
                    resultSet.getString("userLogin"),
                    resultSet.getString("comment"),
                    resultSet.getString("createdAt")
            );
            list.add(comment);
        }
        return list;
    }
}