package Server.Repositories;

import Server.Models.Salary;
import com.pensionfond.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepository
{
    private static String getSalaryQuery = "SELECT * FROM salaries WHERE id = ?";
    private static String getSalaryByPosIdQuery = "SELECT * FROM salaries WHERE posId = ?";
    private static String addSalaryQuery = "INSERT INTO salaries (posId, salary, receivedAt, coefficient, taxes, isTaxesPaid) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static String deleteSalaryQuery = "DELETE FROM salaries WHERE id = ?";
    private static String updateSalaryQuery = "UPDATE salaries SET posId = ?, salary = ?, " +
            "receivedAt = ?, coefficient = ?, taxes = ?, isTaxesPaid = ? WHERE id = ?";
    private static String paidTaxQuery = "UPDATE salaries SET isTaxesPaid = \"true\" WHERE id = ?";

    public static Connection connection;

    public static Salary getSalary(int id) {
        Main.writeMessage("getSalary|" + id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSalaryQuery);
            preparedStatement.setInt(1, id);
            return readSalaries(preparedStatement).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Salary> getSalariesByPosId(int posId) {
        Main.writeMessage("getSalariesByPosId|" + posId);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSalaryByPosIdQuery);
            preparedStatement.setInt(1, posId);
            return readSalaries(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addSalary(Salary salary) {
        Main.writeMessage("addSalary|" + salary);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addSalaryQuery);
            constructAddCommand(preparedStatement, salary);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSalary(int id) {
        Main.writeMessage("deleteSalary|" + id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSalaryQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSalary(Salary salary) {
        Main.writeMessage("updateSalary|" + salary);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSalaryQuery);
            constructUpdateCommand(preparedStatement, salary);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void paidTax(Salary salary) {
        Main.writeMessage("paidTax|" + salary);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(paidTaxQuery);
            preparedStatement.setInt(1, salary.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void constructAddCommand(PreparedStatement preparedStatement, Salary salary) throws SQLException {
        preparedStatement.setInt(1, salary.posId);
        preparedStatement.setInt(2, salary.salary);
        preparedStatement.setString(3, salary.receivedAt);
        preparedStatement.setString(4, salary.coefficient);
        preparedStatement.setInt(5, salary.taxes);
        preparedStatement.setBoolean(6, salary.isTaxesPaid);
    }

    private static void constructUpdateCommand(PreparedStatement preparedStatement, Salary salary) throws SQLException {
        preparedStatement.setInt(7, salary.id);
        preparedStatement.setInt(1, salary.posId);
        preparedStatement.setInt(2, salary.salary);
        preparedStatement.setString(3, salary.receivedAt);
        preparedStatement.setString(4, salary.coefficient);
        preparedStatement.setInt(5, salary.taxes);
        preparedStatement.setBoolean(6, salary.isTaxesPaid);
    }

    private static List<Salary> readSalaries(PreparedStatement preparedStatement) throws SQLException {
        List<Salary> list = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Salary salary = new Salary(
                    resultSet.getInt("posId"),
                    resultSet.getInt("salary"),
                    resultSet.getString("receivedAt"),
                    resultSet.getString("coefficient"),
                    resultSet.getInt("taxes"),
                    Boolean.parseBoolean(resultSet.getString("isTaxesPaid"))
            );
            salary.setId(resultSet.getInt("id"));
            list.add(salary);
        }
        return list;
    }
}