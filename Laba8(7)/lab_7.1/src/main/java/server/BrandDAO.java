package server;

import connection.CustomConnection;
import dto.BrandDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {
    public static BrandDTO findById(long id) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "SELECT * FROM brand WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            BrandDTO brand = null;
            if (resultSet.next()) {
                brand = new BrandDTO();
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

    public static BrandDTO findByName(String name) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "SELECT * FROM brand WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            BrandDTO brand = null;
            if (resultSet.next()) {
                brand = new BrandDTO();
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

    public static boolean update(BrandDTO brand) {
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

    public static boolean insert(BrandDTO brand) {
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

    public static boolean delete(BrandDTO brand) {
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

    public static List<BrandDTO> findAll() {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM brand";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BrandDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                BrandDTO brand = new BrandDTO();
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

    public static List<BrandDTO> findByAuthorId(Long id) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM brand WHERE author_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BrandDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                BrandDTO brand = new BrandDTO();
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

