package DAO.components;

import DAO.DAO;
import controller.conexion.Conexion;
import model.components.CPU;
import model.Inventory;
import DAO.InventoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CPUDAO implements DAO<CPU> {

    @Override
    public void create(CPU cpu) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String cpuSql = "INSERT INTO CPU (id_Product, clockSpeed, coreCount, threadCount, socketType, isInstalled, installationTime, installationGuideURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement cpuStmt = connection.prepareStatement(cpuSql)) {

                checkSkuStmt.setInt(1, cpu.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, cpu.getSKU());
                productStmt.setString(2, cpu.getBrand());
                productStmt.setString(3, cpu.getModel());
                productStmt.setString(4, cpu.getDescription());
                productStmt.setDouble(5, cpu.getPrice());

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

                cpuStmt.setInt(1, productId);
                cpuStmt.setString(2, cpu.getClockSpeed());
                cpuStmt.setInt(3, cpu.getCoreCount());
                cpuStmt.setInt(4, cpu.getThreadCount());
                cpuStmt.setString(5, cpu.getSocketType());
                cpuStmt.setBoolean(6, cpu.isInstalled());
                cpuStmt.setFloat(7, cpu.getInstallationTime());
                cpuStmt.setString(8, cpu.getInstallationGuideURL());
                cpuStmt.executeUpdate();

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
    public List<CPU> list() {
        List<CPU> cpus = new ArrayList<>();
        String sql = "SELECT p.*, c.clockSpeed, c.coreCount, c.threadCount, c.socketType, c.isInstalled, c.installationTime, c.installationGuideURL FROM Product p JOIN CPU c ON p.id_Product = c.id_Product";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CPU cpu = new CPU(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("clockSpeed"),
                        rs.getInt("coreCount"),
                        rs.getInt("threadCount"),
                        rs.getString("socketType"),
                        rs.getBoolean("isInstalled"),
                        rs.getFloat("installationTime"),
                        rs.getString("installationGuideURL")
                );
                cpus.add(cpu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpus;
    }

    @Override
    public CPU recover(String SKU) {
        String sql = "SELECT p.*, c.clockSpeed, c.coreCount, c.threadCount, c.socketType, c.isInstalled, c.installationTime, c.installationGuideURL FROM Product p JOIN CPU c ON p.id_Product = c.id_Product WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CPU(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("clockSpeed"),
                        rs.getInt("coreCount"),
                        rs.getInt("threadCount"),
                        rs.getString("socketType"),
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
    public boolean update(CPU cpu) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String cpuSql = "UPDATE CPU SET clockSpeed = ?, coreCount = ?, threadCount = ?, socketType = ?, isInstalled = ?, installationTime = ?, installationGuideURL = ? WHERE id_Product = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement cpuStmt = connection.prepareStatement(cpuSql)) {

                productStmt.setString(1, cpu.getBrand());
                productStmt.setString(2, cpu.getModel());
                productStmt.setString(3, cpu.getDescription());
                productStmt.setDouble(4, cpu.getPrice());
                productStmt.setInt(5, cpu.getId_Product());

                cpuStmt.setString(1, cpu.getClockSpeed());
                cpuStmt.setInt(2, cpu.getCoreCount());
                cpuStmt.setInt(3, cpu.getThreadCount());
                cpuStmt.setString(4, cpu.getSocketType());
                cpuStmt.setBoolean(5, cpu.isInstalled());
                cpuStmt.setFloat(6, cpu.getInstallationTime());
                cpuStmt.setString(7, cpu.getInstallationGuideURL());
                cpuStmt.setInt(8, cpu.getId_Product());

                productStmt.executeUpdate();
                cpuStmt.executeUpdate();

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

        String cpuSql = "DELETE FROM CPU WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement cpuStmt = connection.prepareStatement(cpuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                cpuStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                cpuStmt.executeUpdate();

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

    public CPU recoverById(int id_Product) {
        String sql = "SELECT p.*, c.clockSpeed, c.coreCount, c.threadCount, c.socketType, c.isInstalled, c.installationTime, c.installationGuideURL "
                + "FROM Product p JOIN CPU c ON p.id_Product = c.id_Product "
                + "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CPU(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("clockSpeed"),
                        rs.getInt("coreCount"),
                        rs.getInt("threadCount"),
                        rs.getString("socketType"),
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