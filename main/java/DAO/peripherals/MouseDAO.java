package DAO.peripherals;

import DAO.DAO;
import controller.conexion.Conexion;
import model.Inventory;
import DAO.InventoryDAO;
import model.peripherals.Mouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MouseDAO implements DAO<Mouse> {

    @Override
    public void create(Mouse mouse) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String peripheralSql = "INSERT INTO Peripheral (id_Product, type, connectionType, isWireless) VALUES (?, ?, ?, ?)";
        String mouseSql = "INSERT INTO Mouse (id_Peripheral, dpi, isGamer) VALUES (?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement mouseStmt = connection.prepareStatement(mouseSql)) {


                checkSkuStmt.setInt(1, mouse.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, mouse.getSKU());
                productStmt.setString(2, mouse.getBrand());
                productStmt.setString(3, mouse.getModel());
                productStmt.setString(4, mouse.getDescription());
                productStmt.setDouble(5, mouse.getPrice());

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
                peripheralStmt.setString(2, mouse.getType());
                peripheralStmt.setString(3, mouse.getConnectionType());
                peripheralStmt.setBoolean(4, mouse.isWireless());

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

                mouseStmt.setInt(1, peripheralId);
                mouseStmt.setInt(2, mouse.getDpi());
                mouseStmt.setBoolean(3, mouse.isGamer());
                mouseStmt.executeUpdate();


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
    public List<Mouse> list() {
        List<Mouse> mouseList = new ArrayList<>();
        String sql = "SELECT p.*, pr.id_Peripheral, pr.type, pr.connectionType, pr.isWireless, m.dpi, m.isGamer " +
                     "FROM Product p JOIN Peripheral pr ON p.id_Product = pr.id_Product JOIN Mouse m ON pr.id_Peripheral = m.id_Peripheral";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mouse mouse = new Mouse(
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
                        rs.getInt("dpi"),
                        rs.getBoolean("isGamer")
                );
                mouseList.add(mouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mouseList;
    }

    @Override
    public Mouse recover(String SKU) {
        String sql = "SELECT p.*, pr.type,m.id_Peripheral, pr.connectionType, pr.isWireless, m.dpi, m.isGamer " +
                       "FROM Product p JOIN Peripheral pr ON p.id_Product = pr.id_Product " +
                       "JOIN Mouse m ON pr.id_Peripheral = m.id_Peripheral WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Mouse(
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
                        rs.getInt("dpi"),
                        rs.getBoolean("isGamer")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Mouse mouse) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String peripheralSql = "UPDATE Peripheral SET type = ?, connectionType = ?, isWireless = ? WHERE id_Product = ?";
        String mouseSql = "UPDATE Mouse SET dpi = ?, isGamer = ? WHERE id_Peripheral = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql);
                 PreparedStatement mouseStmt = connection.prepareStatement(mouseSql)) {

                productStmt.setString(1, mouse.getBrand());
                productStmt.setString(2, mouse.getModel());
                productStmt.setString(3, mouse.getDescription());
                productStmt.setDouble(4, mouse.getPrice());
                productStmt.setInt(5, mouse.getId_Product());

                peripheralStmt.setString(1, mouse.getType());
                peripheralStmt.setString(2, mouse.getConnectionType());
                peripheralStmt.setBoolean(3, mouse.isWireless());
                peripheralStmt.setInt(4, mouse.getId_Product());

                mouseStmt.setInt(1, mouse.getDpi());
                mouseStmt.setBoolean(2, mouse.isGamer());
                mouseStmt.setInt(3, mouse.getId_Peripheral());

                productStmt.executeUpdate();
                peripheralStmt.executeUpdate();
                mouseStmt.executeUpdate();

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

        String mouseSql = "DELETE FROM Mouse WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?))";
        String peripheralSql = "DELETE FROM Peripheral WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement mouseStmt = connection.prepareStatement(mouseSql);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                mouseStmt.setString(1, SKU);
                peripheralStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                mouseStmt.executeUpdate();
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

    public Mouse recoverById(int id_Product) {
        String sql = "SELECT p.*, pe.*, m.* FROM Product p " +
                "JOIN Peripheral pe ON p.id_Product = pe.id_Product " +
                "JOIN Mouse m ON pe.id_Peripheral = m.id_Peripheral " +
                "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Mouse(
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
                        rs.getInt("dpi"),
                        rs.getBoolean("isGamer")
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
