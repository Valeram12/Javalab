package dao;

import connection.CustomConnection;
import dto.brandDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class brandDAO {
    public static brandDTO findById(long id) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "SELECT * FROM brand WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            brandDTO brand = null;
            if (resultSet.next()) {
                brand = new brandDTO();
                brand.setBrandId(resultSet.getLong(1));
                brand.setName(resultSet.getString(2));
                brand.setReleaseYear(resultSet.getInt(3));
                brand.setManufacturerId(resultSet.getLong(4));
            }
            preparedStatement.close();
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static brandDTO findByName(String name) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "SELECT * FROM brand WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            brandDTO brand = null;
            if (resultSet.next()) {
                brand = new brandDTO();
                brand.setBrandId(resultSet.getLong(1));
                brand.setName(resultSet.getString(2));
                brand.setReleaseYear(resultSet.getInt(3));
                brand.setManufacturerId(resultSet.getLong(4));
            }
            preparedStatement.close();
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(brandDTO brand) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "UPDATE brand SET name = ?, release_year = ?, author_id = ? WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, brand.getName());
            preparedStatement.setInt(2, brand.getReleaseYear());
            preparedStatement.setLong(3, brand.getManufacturerId());
            preparedStatement.setLong(4, brand.getBrandId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(brandDTO brand) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "INSERT INTO brand (name, release_year, author_id) VALUES (?,?,?) RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, brand.getName());
            preparedStatement.setInt(2, brand.getReleaseYear());
            preparedStatement.setLong(3, brand.getManufacturerId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                brand.setBrandId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(brandDTO brand) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "DELETE FROM brand WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, brand.getBrandId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<brandDTO> findAll() {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM brand";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<brandDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                brandDTO brand = new brandDTO();
                brand.setBrandId(resultSet.getLong(1));
                brand.setName(resultSet.getString(2));
                brand.setReleaseYear(resultSet.getInt(3));
                brand.setManufacturerId(resultSet.getLong(4));
                list.add(brand);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<brandDTO> findByAuthorId(Long id) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM brand WHERE author_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<brandDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                brandDTO brand = new brandDTO();
                brand.setBrandId(resultSet.getLong(1));
                brand.setName(resultSet.getString(2));
                brand.setReleaseYear(resultSet.getInt(3));
                brand.setManufacturerId(resultSet.getLong(4));
                list.add(brand);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
