package Server.Repositories;

import Server.Models.Position;
import com.pensionfond.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionRepository
{
    private static String getPositionQuery = "SELECT * FROM positions WHERE id = ?";
    private static String getPositionsQuery = "SELECT * FROM positions";
    private static String getPositionsByUserLoginQuery = "SELECT * FROM positions WHERE userLogin = ?";
    private static String getPositionsByWorkLoginQuery = "SELECT * FROM positions WHERE workLogin = ?";
    private static String addPositionQuery = "INSERT INTO positions (userLogin, workLogin, name, acceptedAt, firedAt) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static String deletePositionQuery = "DELETE FROM positions WHERE id = ?";
    private static String updatePositionQuery = "UPDATE positions SET userLogin = ?, workLogin = ?, name = ?, " +
            "acceptedAt = ?, firedAt = ? WHERE id = ?";

    public static Connection connection;

    public static Position getPosition(int id) {
        Main.writeMessage("getPosition|" + id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPositionQuery);
            preparedStatement.setInt(1, id);
            return readPositions(preparedStatement).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Position> getPositions() {
        Main.writeMessage("getPositions|");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPositionsQuery);
            return readPositions(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Position> getPositionsByUserLogin(String userLogin) {
        Main.writeMessage("getPositionsByUserLogin|" + userLogin);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPositionsByUserLoginQuery);
            preparedStatement.setString(1, userLogin);
            return readPositions(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Position> getPositionsByWorkId(String workLogin) {
        Main.writeMessage("getPositionsByWorkLogin|" + workLogin);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPositionsByWorkLoginQuery);
            preparedStatement.setString(1, workLogin);
            return readPositions(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addPosition(Position position) {
        Main.writeMessage("addPosition|" + position);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addPositionQuery);
            constructCommand(preparedStatement, position);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePosition(int id) {
        Main.writeMessage("deletePosition|" + id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deletePositionQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePosition(Position position) {
        Main.writeMessage("updatePosition|" + position);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updatePositionQuery);
            constructCommand(preparedStatement, position);
            preparedStatement.setInt(6, position.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void constructCommand(PreparedStatement preparedStatement, Position position) throws SQLException {
        preparedStatement.setString(1, position.userLogin);
        preparedStatement.setString(2, position.workLogin);
        preparedStatement.setString(3, position.name);
        preparedStatement.setString(4, position.acceptedAt);
        preparedStatement.setString(5, position.firedAt);
    }

    private static List<Position> readPositions(PreparedStatement preparedStatement) throws SQLException {
        List<Position> list = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Position position = new Position(
                    resultSet.getInt("id"),
                    resultSet.getString("userLogin"),
                    resultSet.getString("workLogin"),
                    resultSet.getString("name"),
                    resultSet.getString("acceptedAt"),
                    resultSet.getString("firedAt")
            );
            list.add(position);
        }
        return list;
    }
}