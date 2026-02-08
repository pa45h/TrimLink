package org.trimlink.dao;

import com.mysql.cj.jdbc.Driver;
import org.trimlink.model.User;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class UserDao {

    private static Properties props = new Properties();

    static {
        try (InputStream inputStream = UserDao.class.getClassLoader().getResourceAsStream("db.properties");) {

            props.load(inputStream);

            Class.forName(props.getProperty("DB_DRIVER"));
            System.out.println("Driver Imported..");
        } catch (Exception e) {
            throw new RuntimeException("DB init failed -- " + e);
        }
    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                props.getProperty("DB_URL"),
                props.getProperty("DB_USER"),
                props.getProperty("DB_PASS")
        );
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO users (user_name, password) VALUES (?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.out.println("User didnt registered..");
                return false;
            }

            System.out.println("User registered.. " + rows + " rows Updated..");
            return true;
        } catch (Exception e) {
            System.out.println("Error during registerUser -- " + e.getMessage());
            return false;
        }
    }

    public User loginUser(String userName, String password) {
        String query = "SELECT * FROM users WHERE user_name=? and password=?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                return user;
            }

        } catch (Exception e) {
            System.out.println("Error during loginUser -- " + e.getMessage());
        }
        return null;
    }

}
