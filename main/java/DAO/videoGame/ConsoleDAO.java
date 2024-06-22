package DAO.videoGame;

import DAO.DAO;
import controller.conexion.Conexion;
import model.Inventory;
import DAO.InventoryDAO;
import model.Product;
import model.videoGame.Console;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleDAO implements DAO<Console> {

    @Override
    public void create(Console console) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String consoleSql = "INSERT INTO Console (id_Product, storageCapacity, includesGames, isSpecialEdition) VALUES (?, ?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement consoleStmt = connection.prepareStatement(consoleSql)) {


                checkSkuStmt.setInt(1, console.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, console.getSKU());
                productStmt.setString(2, console.getBrand());
                productStmt.setString(3, console.getModel());
                productStmt.setString(4, console.getDescription());
                productStmt.setDouble(5, console.getPrice());

                int affectedRows = productStmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Error al crear el producto, no se obtuvo ID.");
                }

                try (ResultSet generatedKeys = productStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int productId = generatedKeys.getInt(1);
                        consoleStmt.setInt(1, productId);
                        consoleStmt.setString(2, console.getStorageCapacity());
                        consoleStmt.setBoolean(3, console.isIncludesGames());
                        consoleStmt.setBoolean(4, console.isSpecialEdition());
                        consoleStmt.executeUpdate();


                        Inventory inventory = new Inventory();
                        inventory.addProduct(productId, 1);
                        inventoryDAO.createInventory(inventory,connection);
                    } else {
                        connection.rollback();
                        throw new SQLException("Error al crear el producto, no se obtuvo ID.");
                    }
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Console> list() {
        List<Console> consoles = new ArrayList<>();
        String sql = "SELECT p.*, c.storageCapacity, c.includesGames, c.isSpecialEdition FROM Product p JOIN Console c ON p.id_Product = c.id_Product";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Console console = new Console(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("storageCapacity"),
                        rs.getBoolean("includesGames"),
                        rs.getBoolean("isSpecialEdition")
                );
                consoles.add(console);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consoles;
    }

    @Override
    public Console recover(String SKU) {
        String sql = "SELECT p.*, c.storageCapacity, c.includesGames, c.isSpecialEdition FROM Product p JOIN Console c ON p.id_Product = c.id_Product WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Console(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("storageCapacity"),
                        rs.getBoolean("includesGames"),
                        rs.getBoolean("isSpecialEdition")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Console console) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String consoleSql = "UPDATE Console SET storageCapacity = ?, includesGames = ?, isSpecialEdition = ? WHERE id_Product = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement consoleStmt = connection.prepareStatement(consoleSql)) {

                productStmt.setString(1, console.getBrand());
                productStmt.setString(2, console.getModel());
                productStmt.setString(3, console.getDescription());
                productStmt.setDouble(4, console.getPrice());
                productStmt.setInt(5, console.getId_Product());

                consoleStmt.setString(1, console.getStorageCapacity());
                consoleStmt.setBoolean(2, console.isIncludesGames());
                consoleStmt.setBoolean(3, console.isSpecialEdition());
                consoleStmt.setInt(4, console.getId_Product());

                productStmt.executeUpdate();
                consoleStmt.executeUpdate();

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    @Override
    public boolean delete(String SKU) {
        InventoryDAO inventoryDAO = new InventoryDAO();

        String productSql = "DELETE FROM Product WHERE SKU = ?";
        String consoleSql = "DELETE FROM Console WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement consoleStmt = connection.prepareStatement(consoleSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                consoleStmt.setString(1, SKU);
                consoleStmt.executeUpdate();

                boolean inventoryDeleted = inventoryDAO.delete(SKU);
                if (!inventoryDeleted) {
                    connection.rollback();
                    throw new SQLException("Error al eliminar el inventario del producto.");
                }

                productStmt.setString(1, SKU);
                productStmt.executeUpdate();

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getProductTypeBySKU(String SKU) {
        return "";
    }

    public Console recoverById(int id_Product) {
        String sql = "SELECT p.*, c.storageCapacity, c.includesGames, c.isSpecialEdition "
                + "FROM Product p JOIN Console c ON p.id_Product = c.id_Product "
                + "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Console(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("storageCapacity"),
                        rs.getBoolean("includesGames"),
                        rs.getBoolean("isSpecialEdition")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
