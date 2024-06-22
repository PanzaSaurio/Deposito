package DAO.components;

import DAO.DAO;
import controller.conexion.Conexion;
import model.components.Motherboard;
import model.Inventory;
import DAO.InventoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotherboardDAO implements DAO<Motherboard> {

    @Override
    public void create(Motherboard motherboard) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String motherboardSql = "INSERT INTO Motherboard (id_Product, formFactor, chipset, socketType, memoryType, maxMemory, isInstalled, installationTime, installationGuideURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement motherboardStmt = connection.prepareStatement(motherboardSql)) {

                checkSkuStmt.setInt(1, motherboard.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, motherboard.getSKU());
                productStmt.setString(2, motherboard.getBrand());
                productStmt.setString(3, motherboard.getModel());
                productStmt.setString(4, motherboard.getDescription());
                productStmt.setDouble(5, motherboard.getPrice());

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

                motherboardStmt.setInt(1, productId);
                motherboardStmt.setString(2, motherboard.getFormFactor());
                motherboardStmt.setString(3, motherboard.getChipset());
                motherboardStmt.setString(4, motherboard.getSocketType());
                motherboardStmt.setString(5, motherboard.getMemoryType());
                motherboardStmt.setInt(6, motherboard.getMaxMemory());
                motherboardStmt.setBoolean(7, motherboard.isInstalled());
                motherboardStmt.setFloat(8, motherboard.getInstallationTime());
                motherboardStmt.setString(9, motherboard.getInstallationGuideURL());
                motherboardStmt.executeUpdate();

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
    public List<Motherboard> list() {
        List<Motherboard> motherboards = new ArrayList<>();
        String sql = "SELECT p.*, m.formFactor, m.chipset, m.socketType, m.memoryType, m.maxMemory, m.isInstalled, m.installationTime, m.installationGuideURL FROM Product p JOIN Motherboard m ON p.id_Product = m.id_Product";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Motherboard motherboard = new Motherboard(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("formFactor"),
                        rs.getString("chipset"),
                        rs.getString("socketType"),
                        rs.getString("memoryType"),
                        rs.getInt("maxMemory"),
                        rs.getBoolean("isInstalled"),
                        rs.getFloat("installationTime"),
                        rs.getString("installationGuideURL")
                );
                motherboards.add(motherboard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return motherboards;
    }

    @Override
    public Motherboard recover(String SKU) {
        String sql = "SELECT p.*, m.formFactor, m.chipset, m.socketType, m.memoryType, m.maxMemory, m.isInstalled, m.installationTime, m.installationGuideURL FROM Product p JOIN Motherboard m ON p.id_Product = m.id_Product WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Motherboard(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("formFactor"),
                        rs.getString("chipset"),
                        rs.getString("socketType"),
                        rs.getString("memoryType"),
                        rs.getInt("maxMemory"),
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
    public boolean update(Motherboard motherboard) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String motherboardSql = "UPDATE Motherboard SET formFactor = ?, chipset = ?, socketType = ?, memoryType = ?, " +
                                 "maxMemory = ?, isInstalled = ?, installationTime = ?, installationGuideURL = ? WHERE id_Product = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement motherboardStmt = connection.prepareStatement(motherboardSql)) {

                productStmt.setString(1, motherboard.getBrand());
                productStmt.setString(2, motherboard.getModel());
                productStmt.setString(3, motherboard.getDescription());
                productStmt.setDouble(4, motherboard.getPrice());
                productStmt.setInt(5, motherboard.getId_Product());

                motherboardStmt.setString(1, motherboard.getFormFactor());
                motherboardStmt.setString(2, motherboard.getChipset());
                motherboardStmt.setString(3, motherboard.getSocketType());
                motherboardStmt.setString(4, motherboard.getMemoryType());
                motherboardStmt.setInt(5, motherboard.getMaxMemory());
                motherboardStmt.setBoolean(6, motherboard.isInstalled());
                motherboardStmt.setFloat(7, motherboard.getInstallationTime());
                motherboardStmt.setString(8, motherboard.getInstallationGuideURL());
                motherboardStmt.setInt(9, motherboard.getId_Product());

                productStmt.executeUpdate();
                motherboardStmt.executeUpdate();

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

        String motherboardSql = "DELETE FROM Motherboard WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement motherboardStmt = connection.prepareStatement(motherboardSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                motherboardStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                motherboardStmt.executeUpdate();

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
    public Motherboard recoverById(int id_Product) {
        String sql = "SELECT p.*, m.formFactor, m.chipset, m.socketType, m.memoryType, m.maxMemory, m.isInstalled, m.installationTime, m.installationGuideURL "
                + "FROM Product p JOIN Motherboard m ON p.id_Product = m.id_Product "
                + "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Motherboard(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("formFactor"),
                        rs.getString("chipset"),
                        rs.getString("socketType"),
                        rs.getString("memoryType"),
                        rs.getInt("maxMemory"),
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