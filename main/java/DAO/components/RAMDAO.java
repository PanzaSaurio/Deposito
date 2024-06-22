package DAO.components;

import DAO.DAO;
import controller.conexion.Conexion;
import model.components.RAM;
import model.Inventory;
import DAO.InventoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RAMDAO implements DAO<RAM> {

    @Override
    public void create(RAM ram) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String ramSql = "INSERT INTO RAM (id_Product, capacity, type, speed, formFactor, isInstalled, installationTime, " +
                        "installationGuideURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ramStmt = connection.prepareStatement(ramSql)) {

                checkSkuStmt.setInt(1, ram.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, ram.getSKU());
                productStmt.setString(2, ram.getBrand());
                productStmt.setString(3, ram.getModel());
                productStmt.setString(4, ram.getDescription());
                productStmt.setDouble(5, ram.getPrice());

                int affectedRows = productStmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Error al crear el producto, no se obtuvo ID.");
                }

                int productId;
                try (ResultSet generatedKeys = productStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productId = generatedKeys.getInt(1);
                    } else {
                        connection.rollback();
                        throw new SQLException("Error al crear el producto, no se obtuvo ID.");
                    }
                }

                ramStmt.setInt(1, productId);
                ramStmt.setString(2, ram.getCapacity());
                ramStmt.setString(3, ram.getType());
                ramStmt.setString(4, ram.getSpeed());
                ramStmt.setString(5, ram.getFormFactor());
                ramStmt.setBoolean(6, ram.isInstalled());
                ramStmt.setFloat(7, ram.getInstallationTime());
                ramStmt.setString(8, ram.getInstallationGuideURL());
                ramStmt.executeUpdate();

                Inventory inventory = new Inventory();
                inventory.addProduct(productId, 1);
                inventoryDAO.createInventory(inventory,connection);

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
    public List<RAM> list() {
        List<RAM> rams = new ArrayList<>();
        String sql = "SELECT p.*, r.capacity, r.type, r.speed, r.formFactor, r.isInstalled, r.installationTime, r.installationGuideURL " +
                     "FROM Product p JOIN RAM r ON p.id_Product = r.id_Product";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RAM ram = new RAM(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("capacity"),
                        rs.getString("type"),
                        rs.getString("speed"),
                        rs.getString("formFactor"),
                        rs.getBoolean("isInstalled"),
                        rs.getFloat("installationTime"),
                        rs.getString("installationGuideURL")
                );
                rams.add(ram);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rams;
    }

    @Override
    public RAM recover(String SKU) {
        String sql = "SELECT p.*, r.capacity, r.type, r.speed, r.formFactor, r.isInstalled, r.installationTime, r.installationGuideURL " +
                      "FROM Product p JOIN RAM r ON p.id_Product = r.id_Product WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new RAM(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("capacity"),
                        rs.getString("type"),
                        rs.getString("speed"),
                        rs.getString("formFactor"),
                        rs.getBoolean("isInstalled"),
                        rs.getFloat("installationTime"),
                        rs.getString("installationGuideURL")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(RAM ram) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String ramSql = "UPDATE RAM SET capacity = ?, type = ?, speed = ?, formFactor = ?, isInstalled = ?, installationTime = ?, installationGuideURL = ? WHERE id_Product = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement ramStmt = connection.prepareStatement(ramSql)) {

                productStmt.setString(1, ram.getBrand());
                productStmt.setString(2, ram.getModel());
                productStmt.setString(3, ram.getDescription());
                productStmt.setDouble(4, ram.getPrice());
                productStmt.setInt(5, ram.getId_Product());

                ramStmt.setString(1, ram.getCapacity());
                ramStmt.setString(2, ram.getType());
                ramStmt.setString(3, ram.getSpeed());
                ramStmt.setString(4, ram.getFormFactor());
                ramStmt.setBoolean(5, ram.isInstalled());
                ramStmt.setFloat(6, ram.getInstallationTime());
                ramStmt.setString(7, ram.getInstallationGuideURL());
                ramStmt.setInt(8, ram.getId_Product());

                productStmt.executeUpdate();
                ramStmt.executeUpdate();

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

        String ramSql = "DELETE FROM RAM WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ramStmt = connection.prepareStatement(ramSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                ramStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                ramStmt.executeUpdate();

                boolean inventoryDeleted = inventoryDAO.delete(SKU, connection);
                if (!inventoryDeleted) {
                    connection.rollback();
                    throw new SQLException("Error al eliminar el inventario del producto.");
                }

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
    public RAM recoverById(int id_Product) {
        String sql = "SELECT p.*, r.capacity, r.type, r.speed, r.formFactor, r.isInstalled, r.installationTime, r.installationGuideURL "
                + "FROM Product p JOIN RAM r ON p.id_Product = r.id_Product "
                + "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new RAM(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("capacity"),
                        rs.getString("type"),
                        rs.getString("speed"),
                        rs.getString("formFactor"),
                        rs.getBoolean("isInstalled"),
                        rs.getFloat("installationTime"),
                        rs.getString("installationGuideURL")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String getProductTypeBySKU(String SKU) {
        return "";
    }
}
