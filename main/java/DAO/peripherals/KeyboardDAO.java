package DAO.peripherals;

import DAO.DAO;
import controller.conexion.Conexion;
import model.peripherals.Keyboard;
import model.Inventory;
import DAO.InventoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeyboardDAO implements DAO<Keyboard> {

    @Override
    public void create(Keyboard keyboard) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String peripheralSql = "INSERT INTO Peripheral (id_Product, type, connectionType, isWireless) VALUES (?, ?, ?, ?)";
        String keyboardSql = "INSERT INTO Keyboard (id_Peripheral, switchType, isMechanical) VALUES (?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement keyboardStmt = connection.prepareStatement(keyboardSql)) {


                checkSkuStmt.setInt(1, keyboard.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, keyboard.getSKU());
                productStmt.setString(2, keyboard.getBrand());
                productStmt.setString(3, keyboard.getModel());
                productStmt.setString(4, keyboard.getDescription());
                productStmt.setDouble(5, keyboard.getPrice());

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

                peripheralStmt.setInt(1, productId);
                peripheralStmt.setString(2, keyboard.getType());
                peripheralStmt.setString(3, keyboard.getConnectionType());
                peripheralStmt.setBoolean(4, keyboard.isWireless());

                affectedRows = peripheralStmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Error al crear el periférico, no se obtuvo ID.");
                }

                int peripheralId;
                try (ResultSet generatedKeys = peripheralStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        peripheralId = generatedKeys.getInt(1);
                    } else {
                        connection.rollback();
                        throw new SQLException("Error al crear el periférico, no se obtuvo ID.");
                    }
                }

                keyboardStmt.setInt(1, peripheralId);
                keyboardStmt.setString(2, keyboard.getSwitchType());
                keyboardStmt.setBoolean(3, keyboard.isMechanical());
                keyboardStmt.executeUpdate();


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
    public List<Keyboard> list() {
        List<Keyboard> keyboardList = new ArrayList<>();
        String sql = "SELECT p.*, pr.id_Peripheral, pr.type, pr.connectionType, pr.isWireless, k.switchType, k.isMechanical " +
                     "FROM Product p JOIN Peripheral pr ON p.id_Product = pr.id_Product JOIN Keyboard k ON pr.id_Peripheral = k.id_Peripheral";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Keyboard keyboard = new Keyboard(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("id_Peripheral"),
                        rs.getString("type"),
                        rs.getString("connectionType"),
                        rs.getBoolean("isWireless"),
                        rs.getBoolean("isMechanical"),
                        rs.getString("switchType")
                );
                keyboardList.add(keyboard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keyboardList;
    }

    @Override
    public Keyboard recover(String SKU) {
        String sql = "SELECT p.*, pr.type, pr.connectionType, pr.isWireless,k.id_Peripheral, k.switchType, k.isMechanical " +
                     "FROM Product p JOIN Peripheral pr ON p.id_Product = pr.id_Product JOIN Keyboard k ON pr.id_Peripheral = k.id_Peripheral WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Keyboard(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("id_Peripheral"),
                        rs.getString("type"),
                        rs.getString("connectionType"),
                        rs.getBoolean("isWireless"),
                        rs.getBoolean("isMechanical"),
                        rs.getString("switchType")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Keyboard keyboard) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String peripheralSql = "UPDATE Peripheral SET type = ?, connectionType = ?, isWireless = ? WHERE id_Product = ?";
        String keyboardSql = "UPDATE Keyboard SET isMechanical = ?, switchType = ? WHERE id_Peripheral = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql);
                 PreparedStatement keyboardStmt = connection.prepareStatement(keyboardSql)) {

                productStmt.setString(1, keyboard.getBrand());
                productStmt.setString(2, keyboard.getModel());
                productStmt.setString(3, keyboard.getDescription());
                productStmt.setDouble(4, keyboard.getPrice());
                productStmt.setInt(5, keyboard.getId_Product());

                peripheralStmt.setString(1, keyboard.getType());
                peripheralStmt.setString(2, keyboard.getConnectionType());
                peripheralStmt.setBoolean(3, keyboard.isWireless());
                peripheralStmt.setInt(4, keyboard.getId_Product());

                keyboardStmt.setBoolean(1, keyboard.isMechanical());
                keyboardStmt.setString(2, keyboard.getSwitchType());
                keyboardStmt.setInt(3, keyboard.getId_Peripheral());

                productStmt.executeUpdate();
                peripheralStmt.executeUpdate();
                keyboardStmt.executeUpdate();

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

        String keyboardSql = "DELETE FROM Keyboard WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?))";
        String peripheralSql = "DELETE FROM Peripheral WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement keyboardStmt = connection.prepareStatement(keyboardSql);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                keyboardStmt.setString(1, SKU);
                peripheralStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                keyboardStmt.executeUpdate();
                peripheralStmt.executeUpdate();

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

    public Keyboard recoverById(int id_Product) {
        String sql = "SELECT p.*, pe.id_Peripheral, pe.type, pe.connectionType, pe.isWireless, k.isMechanical, k.switchType " +
                "FROM Product p " +
                "JOIN Peripheral pe ON p.id_Product = pe.id_Product " +
                "JOIN Keyboard k ON pe.id_Peripheral = k.id_Peripheral " +
                "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Keyboard(
                        rs.getInt("id_Product"),
                        rs.getInt("SKU"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("id_Peripheral"),
                        rs.getString("type"),
                        rs.getString("connectionType"),
                        rs.getBoolean("isWireless"),
                        rs.getBoolean("isMechanical"),
                        rs.getString("switchType")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
