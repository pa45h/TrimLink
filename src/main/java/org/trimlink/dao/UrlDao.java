package org.trimlink.dao;

import org.trimlink.model.Url;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class UrlDao {
    private static Properties props = new Properties();

    static {
        try (InputStream inputStream = UrlDao.class.getClassLoader().getResourceAsStream("db.properties");) {
            props.load(inputStream);
            Class.forName(props.getProperty("DB_DRIVER"));
            System.out.println("Driver Imported...");
        } catch (Exception e) {
            throw new RuntimeException("DB init failed -- " + e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("DB_URL"),
                props.getProperty("DB_USER"),
                props.getProperty("DB_PASS")
        );
    }

    public static String generate() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String code = "";

        for (int i = 0; i < 5; i++) {
            code += chars.charAt(random.nextInt(chars.length()));
        }
        return code;
    }

    public boolean generateShortUrl(String longUrl, String slug, int userId) {
        String query = "INSERT INTO urls (long_url, short_url, user_id) VALUES (?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            if (slug.equals("") || slug.equals(null)) {
                slug = generate();
            }

            preparedStatement.setString(1, longUrl);
            preparedStatement.setString(2, slug);
            preparedStatement.setInt(3, userId);

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.out.println("Url didnt generated..");
                return false;
            }

            System.out.println("URL generated.. " + rows + " rows Updated..");
            return true;
        } catch (Exception e) {
            System.out.println("Error during generateShortUrl -- " + e.getMessage());
        }
        return false;
    }

    public List<Url> getUrlsByUserId(int userId){
        List<Url> urls = new ArrayList<>();
        String query = "SELECT * FROM urls WHERE user_id=?";

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);){

            preparedStatement.setInt(1,userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Url url = new Url();
                url.setUrlId(resultSet.getInt("url_id"));
                url.setLongUrl(resultSet.getString("long_url"));
                url.setShortUrl(resultSet.getString("short_url"));
                url.setUserId(resultSet.getInt("user_id"));
                urls.add(url);
            }

        } catch (Exception e) {
            System.out.println("Error fetching urls -- "+e.getMessage());
        }
        return urls;
    }

    public Url findBySlug(String slug){
        String query = "SELECT * FROM urls WHERE short_url=?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);){

            Url url = new Url();

            preparedStatement.setString(1,slug);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                url.setUrlId(resultSet.getInt("url_id"));
                url.setUserId(resultSet.getInt("user_id"));
                url.setShortUrl(resultSet.getString("short_url"));
                url.setLongUrl(resultSet.getString("long_url"));
            }
            return url;
        } catch (Exception e) {
            System.out.println("Error fetching urls -- "+e.getMessage());
        }
        return null;
    }

    public void deleteUrlById(int urlId){
        String query = "DELETE FROM urls WHERE url_id=?";

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);){
            preparedStatement.setInt(1,urlId);
            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.out.println("Url didnt generated..");
            }

            System.out.println("URL Deleted.. " + rows + " rows Updated..");

        } catch (Exception e) {
            System.out.println("Error deleting url by id -- "+e.getMessage());
        }
    }

}
