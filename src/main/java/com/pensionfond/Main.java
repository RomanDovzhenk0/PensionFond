package com.pensionfond;

import Server.Models.User;
import Server.Models.Work;
import Server.Repositories.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    private static Stage stage;
    private static Scene scene;
    private static DataOutputStream oos;
    public static User user;
    public static Work work;
    @Override
    public void start(Stage stage) throws IOException {
        setStage(new LoginController(), "login.fxml");
    }

    public static void setStage(Object object, String filePath) {
        FXMLLoader fxmlLoader = new FXMLLoader(object.getClass().getResource(filePath));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("PensionFond");
        stage.show();
    }

    public static String getHashCode(String s){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void writeMessage(String message) {
        try {
            oos.writeUTF(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Socket socket = new Socket("localhost", 4305);
        oos = new DataOutputStream(socket.getOutputStream());

        connectServer();
        launch();
    }
    static void connectServer() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        PositionRepository.connection = connection;
        SalaryRepository.connection = connection;
        WorkRepository.connection = connection;
        CommentRepository.connection = connection;
        UserRepository.connection = connection;
    }
}