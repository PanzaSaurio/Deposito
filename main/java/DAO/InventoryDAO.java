package DAO;

import controller.conexion.Conexion;
import model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryDAO implements DAO<Inventory> {

    public void createInventory(Inventory inventory, Connection connection) throws SQLException {
        String sql = "INSERT INTO Inventory (id_Product, stock) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Map.Entry<Integer, Integer> entry : inventory.getProducts().entrySet()) {
                int id_Product = entry.getKey();
                int stock = entry.getValue();

                stmt.setInt(1, id_Product);
                stmt.setInt(2, stock);
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void create(Inventory inventory) {

    }

    @Override
    public List<Inventory> list() {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_Product = rs.getInt("id_Product");
                int stock = rs.getInt("stock");

                Inventory inventory = new Inventory();
                inventory.addProduct(id_Product, stock);
                inventoryList.add(inventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }


    @Override
    public Inventory recover(String SKU) {
        String sql = "SELECT * FROM Inventory WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            Inventory inventory = new Inventory();
            if (rs.next()) {
                int id_Product = rs.getInt("id_Product");
                int stock = rs.getInt("stock");
                inventory.addProduct(id_Product, stock);
                return inventory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Inventory inventory) {
        return true;
    }


    public boolean delete(String SKU, Connection connection) {
        String sql = "DELETE FROM Inventory WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, SKU);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateStockBySKU(String SKU, int newStock) {
        String sql = "UPDATE Inventory SET stock = ? WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setString(2, SKU);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(String SKU) {
        return true;
    }

    @Override
    public String getProductTypeBySKU(String SKU) {
        return "";
    }

    @Override
    public Inventory recoverById(int id_Product) {
        return null;
    }
}
