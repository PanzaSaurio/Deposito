package DAO.components;

import DAO.DAO;
import controller.conexion.Conexion;
import model.components.GPU;
import model.Inventory;
import DAO.InventoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GPUDAO implements DAO<GPU> {

    @Override
    public void create(GPU gpu) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String gpuSql = "INSERT INTO GPU (id_Product, memorySize, memoryType, coreClock, isInstalled, " +
                         "installationTime, installationGuideURL) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement gpuStmt = connection.prepareStatement(gpuSql)) {

                checkSkuStmt.setInt(1, gpu.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, gpu.getSKU());
                productStmt.setString(2, gpu.getBrand());
                productStmt.setString(3, gpu.getModel());
                productStmt.setString(4, gpu.getDescription());
                productStmt.setDouble(5, gpu.getPrice());

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

                gpuStmt.setInt(1, productId);
                gpuStmt.setString(2, gpu.getMemorySize());
                gpuStmt.setString(3, gpu.getMemoryType());
                gpuStmt.setString(4, gpu.getCoreClock());
                gpuStmt.setBoolean(5, gpu.isInstalled());
                gpuStmt.setFloat(6, gpu.getInstallationTime());
                gpuStmt.setString(7, gpu.getInstallationGuideURL());
                gpuStmt.executeUpdate();

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
    public List<GPU> list() {
        List<GPU> gpus = new ArrayList<>();
        String sql = "SELECT p.*, g.memorySize, g.memoryType, g.coreClock, g.isInstalled, g.installationTime, " +
                "g.installationGuideURL FROM Product p JOIN GPU g ON p.id_Product = g.id_Product";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GPU gpu = new GPU(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("memorySize"),
                        rs.getString("memoryType"),
                        rs.getString("coreClock"),
                        rs.getBoolean("isInstalled"),
                        rs.getFloat("installationTime"),
                        rs.getString("installationGuideURL")
                );
                gpus.add(gpu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gpus;
    }

    @Override
    public GPU recover(String SKU) {
        String sql = "SELECT p.*, g.memorySize, g.memoryType, g.coreClock, g.isInstalled, g.installationTime, " +
                "g.installationGuideURL FROM Product p JOIN GPU g ON p.id_Product = g.id_Product WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new GPU(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("memorySize"),
                        rs.getString("memoryType"),
                        rs.getString("coreClock"),
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
    public boolean update(GPU gpu) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String gpuSql = "UPDATE GPU SET memorySize = ?, memoryType = ?, coreClock = ?, isInstalled = ?, installationTime = ?, installationGuideURL = ? WHERE id_Product = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement gpuStmt = connection.prepareStatement(gpuSql)) {

                productStmt.setString(1, gpu.getBrand());
                productStmt.setString(2, gpu.getModel());
                productStmt.setString(3, gpu.getDescription());
                productStmt.setDouble(4, gpu.getPrice());
                productStmt.setInt(5, gpu.getId_Product());

                gpuStmt.setString(1, gpu.getMemorySize());
                gpuStmt.setString(2, gpu.getMemoryType());
                gpuStmt.setString(3, gpu.getCoreClock());
                gpuStmt.setBoolean(4, gpu.isInstalled());
                gpuStmt.setFloat(5, gpu.getInstallationTime());
                gpuStmt.setString(6, gpu.getInstallationGuideURL());
                gpuStmt.setInt(7, gpu.getId_Product());

                productStmt.executeUpdate();
                gpuStmt.executeUpdate();

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

        String gpuSql = "DELETE FROM GPU WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement gpuStmt = connection.prepareStatement(gpuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                gpuStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                gpuStmt.executeUpdate();

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
    public String getProductTypeBySKU(String SKU) {
        return "";
    }

    @Override
    public GPU recoverById(int id_Product) {
        String sql = "SELECT p.*, g.memorySize, g.memoryType, g.coreClock, g.isInstalled, g.installationTime, g.installationGuideURL "
                + "FROM Product p JOIN GPU g ON p.id_Product = g.id_Product "
                + "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new GPU(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("memorySize"),
                        rs.getString("memoryType"),
                        rs.getString("coreClock"),
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


}