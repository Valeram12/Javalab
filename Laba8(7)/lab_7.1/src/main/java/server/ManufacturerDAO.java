package server;

import connection.CustomConnection;
import dto.ManufacturerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO {
    public static ManufacturerDTO findById(long id) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql =
                    "SELECT * FROM manufacturer WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ManufacturerDTO manufacturer = null;
            if (resultSet.next()) {
                manufacturer = new ManufacturerDTO();
                manufacturer.setId(resultSet.getLong(1));
                manufacturer.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return manufacturer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ManufacturerDTO findByName(String name) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM manufacturer WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            ManufacturerDTO manufacturer = null;
            if (resultSet.next()) {
                manufacturer = new ManufacturerDTO();
                manufacturer.setId(resultSet.getLong(1));
                manufacturer.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return manufacturer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(ManufacturerDTO manufacturer) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "UPDATE manufacturer SET name = ? WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setLong(2, manufacturer.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(ManufacturerDTO manufacturer) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "INSERT INTO manufacturer (name) VALUES (?) RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, manufacturer.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(ManufacturerDTO manufacturer) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "DELETE FROM manufacturer WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, manufacturer.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ManufacturerDTO> findAll() {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM manufacturer";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ManufacturerDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                ManufacturerDTO manufacturer = new ManufacturerDTO();
                manufacturer.setId(resultSet.getLong(1));
                manufacturer.setName(resultSet.getString(2));
                list.add(manufacturer);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

