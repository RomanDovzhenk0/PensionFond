package Server.Repositories;

import Server.Models.User;
import com.pensionfond.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static String getUserQuery = "SELECT * FROM users WHERE login = ?";
    private static String getUsersQuery = "SELECT * FROM users";
    private static String addUserQuery = "INSERT INTO users (login, password_hash, fullname, birthDate, typeOfAccount, " +
                    "email, pension, address, pensionNumber, typeOfPension, isPensioner, sex, nextChangeDate, passSeries, passId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static String deleteUserQuery = "DELETE FROM users WHERE login = ?";
    private static String updateUserQuery = "UPDATE users SET password_hash = ?, fullname = ?, " +
                    "birthDate = ?, typeOfAccount = ?, email = ?, pension = ?, address = ?, pensionNumber = ?, " +
                    "typeOfPension = ?, isPensioner = ?, sex = ?, nextChangeDate = ?, passSeries = ?, passId = ? WHERE login = ?";

    public static Connection connection;

    public static User getUser(String login) {
        Main.writeMessage("getUser|" + login);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUserQuery);
            preparedStatement.setString(1, login);
            return readUser(preparedStatement).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<User> getUsers() {
        Main.writeMessage("getUsers|");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUsersQuery);
            return readUser(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(User user) {
        Main.writeMessage("addUser|" + user);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery);
            constructCommand(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String login) {
        Main.writeMessage("deleteUser|" + login);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery);
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        Main.writeMessage("updateUser|" + user);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, user.password_hash);
            preparedStatement.setString(2, user.fullname);
            preparedStatement.setString(3, user.birthDate);
            preparedStatement.setString(4, user.typeOfAccount);
            preparedStatement.setString(5, user.email);
            preparedStatement.setInt(6, user.pension);
            preparedStatement.setString(7, user.address);
            preparedStatement.setString(8, user.pensionNumber);
            preparedStatement.setString(9, user.typeOfPension);
            preparedStatement.setString(10, user.isPensioner == true ? "true" : "false");
            preparedStatement.setString(11, user.sex);
            preparedStatement.setString(12, user.nextChangeDate);
            preparedStatement.setString(13, user.passSeries);
            preparedStatement.setString(14, user.passId);
            preparedStatement.setString(15, user.login);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void constructCommand(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.login);
        preparedStatement.setString(2, user.password_hash);
        preparedStatement.setString(3, user.fullname);
        preparedStatement.setString(4, user.birthDate);
        preparedStatement.setString(5, user.typeOfAccount);
        preparedStatement.setString(6, user.email);
        preparedStatement.setInt(7, user.pension);
        preparedStatement.setString(8, user.address);
        preparedStatement.setString(9, user.pensionNumber);
        preparedStatement.setString(10, user.typeOfPension);
        preparedStatement.setString(11, user.isPensioner == true ? "true" : "false");
        preparedStatement.setString(12, user.sex);
        preparedStatement.setString(13, user.nextChangeDate);
        preparedStatement.setString(14, user.passSeries);
        preparedStatement.setString(15, user.passId);
    }

    private static List<User> readUser(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> usersList = new ArrayList<>();
        User user = null;
        while (resultSet.next()) {
            user = new User(
                    resultSet.getString("login"),
                    resultSet.getString("password_hash"),
                    resultSet.getString("fullname"),
                    resultSet.getString("birthDate"),
                    resultSet.getString("typeOfAccount"),
                    resultSet.getString("email"),
                    resultSet.getInt("pension"),
                    resultSet.getString("address"),
                    resultSet.getString("pensionNumber"),
                    resultSet.getString("typeOfPension"),
                    Boolean.parseBoolean(resultSet.getString("isPensioner")),
                    resultSet.getString("sex"),
                    resultSet.getString("nextChangeDate"),
                    resultSet.getString("passSeries"),
                    resultSet.getString("passId")
                    );
            usersList.add(user);
        }
        return usersList;
    }
}