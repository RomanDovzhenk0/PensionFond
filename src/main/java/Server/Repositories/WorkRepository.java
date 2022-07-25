package Server.Repositories;

import Server.Models.Work;
import com.pensionfond.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkRepository
{
    private static String getWorkQuery = "SELECT * FROM works WHERE name = ?";
    private static String getWorksQuery = "SELECT * FROM works";
    private static String addWorkQuery = "INSERT INTO works (name, passwordHash, foundedAt) VALUES (?, ?, ?)";
    private static String deleteWorkQuery = "DELETE FROM works WHERE name = ?";
    private static String updateWorkQuery = "UPDATE works SET passwordHash = ?, foundedAt = ? WHERE name = ?";

    public static Connection connection;

    public static Work getWork(String name) {
        Main.writeMessage("getWork|" + name);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getWorkQuery);
            preparedStatement.setString(1, name);
            return readWork(preparedStatement).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Work> getWorks() {
        Main.writeMessage("getWorks|");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getWorksQuery);
            return readWork(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addWork(Work work) {
        Main.writeMessage("addWork|" + work);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addWorkQuery);
            constructWork(preparedStatement, work);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWork(String name) {
        Main.writeMessage("deleteWork|" + name);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteWorkQuery);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateWork(Work work) {
        Main.writeMessage("updateWork|" + work);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateWorkQuery);
            preparedStatement.setString(3, work.name);
            preparedStatement.setString(1, work.passwordHash);
            preparedStatement.setString(2, work.foundedAt);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void constructWork(PreparedStatement preparedStatement, Work work) throws SQLException {
        preparedStatement.setString(1, work.name);
        preparedStatement.setString(2, work.passwordHash);
        preparedStatement.setString(3, work.foundedAt);
    }

    private static List<Work> readWork(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Work> workList = new ArrayList<>();
        Work work = null;
        while (resultSet.next()) {
            work = new Work(
                    resultSet.getString("name"),
                    resultSet.getString("passwordHash"),
                    resultSet.getString("foundedAt")
            );
            workList.add(work);
        }
        return workList;
    }
}