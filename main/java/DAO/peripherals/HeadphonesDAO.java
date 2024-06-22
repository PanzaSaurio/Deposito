package DAO.peripherals;

import DAO.DAO;
import controller.conexion.Conexion;
import model.peripherals.Headphones;
import model.Inventory;
import DAO.InventoryDAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeadphonesDAO implements DAO<Headphones> {

    @Override
    public void create(Headphones headphones) {
        String productSql = "INSERT INTO Product (SKU, brand, model, description, price) VALUES (?, ?, ?, ?, ?)";
        String peripheralSql = "INSERT INTO Peripheral (id_Product, type, connectionType, isWireless) VALUES (?, ?, ?, ?)";
        String headphonesSql = "INSERT INTO Headphones (id_Peripheral, type, weight, microphone, inputImpedance, sensitivity, frequencyResponse, " +
                "micSensitivity, micFrequencyResponse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String checkSkuSql = "SELECT COUNT(*) FROM Product WHERE SKU = ?";

        InventoryDAO inventoryDAO = new InventoryDAO();

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement checkSkuStmt = connection.prepareStatement(checkSkuSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement headphonesStmt = connection.prepareStatement(headphonesSql)) {


                checkSkuStmt.setInt(1, headphones.getSKU());
                ResultSet rs = checkSkuStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("El SKU ya existe en la base de datos.");
                }

                productStmt.setInt(1, headphones.getSKU());
                productStmt.setString(2, headphones.getBrand());
                productStmt.setString(3, headphones.getModel());
                productStmt.setString(4, headphones.getDescription());
                productStmt.setDouble(5, headphones.getPrice());

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
                peripheralStmt.setString(2, headphones.getType());
                peripheralStmt.setString(3, headphones.getConnectionType());
                peripheralStmt.setBoolean(4, headphones.isWireless());

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

                headphonesStmt.setInt(1, peripheralId);
                headphonesStmt.setString(2, headphones.getType());
                headphonesStmt.setDouble(3, headphones.getWeight());
                headphonesStmt.setBoolean(4, headphones.isMicrophone());
                headphonesStmt.setInt(5, headphones.getInputImpedance());
                headphonesStmt.setInt(6, headphones.getSensitivity());
                headphonesStmt.setInt(7, headphones.getFrequencyResponse());
                headphonesStmt.setBoolean(8, headphones.isMicSensitivity());
                headphonesStmt.setBoolean(9, headphones.isMicFrequencyResponse());
                headphonesStmt.executeUpdate();


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
    public List<Headphones> list() {
        List<Headphones> headphonesList = new ArrayList<>();
        String sql = "SELECT p.*, pr.id_Peripheral, pr.type, pr.connectionType, pr.isWireless, h.type, " +
                     "h.weight, h.microphone, h.inputImpedance, h.sensitivity, h.frequencyResponse, h.micSensitivity," +
                     "h.micFrequencyResponse FROM Product p JOIN Peripheral pr ON p.id_Product = pr.id_Product JOIN Headphones h ON pr.id_Peripheral = h.id_Peripheral";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Headphones headphones = new Headphones(
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
                        rs.getString("type"),
                        rs.getDouble("weight"),
                        rs.getBoolean("microphone"),
                        rs.getInt("inputImpedance"),
                        rs.getInt("sensitivity"),
                        rs.getInt("frequencyResponse"),
                        rs.getBoolean("micSensitivity"),
                        rs.getBoolean("micFrequencyResponse")
                );
                headphonesList.add(headphones);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return headphonesList;
    }


    @Override
    public Headphones recover(String SKU) {
        String sql = "SELECT p.*, pr.type, pr.connectionType, pr.isWireless,h.id_Peripheral, h.type as 'typeHeadphones', " +
                     "h.weight, h.microphone, h.inputImpedance, " +
                     " h.sensitivity, h.frequencyResponse, h.micSensitivity, h.micFrequencyResponse " +
                     "FROM Product p JOIN Peripheral pr ON p.id_Product = pr.id_Product " +
                     "JOIN Headphones h ON pr.id_Peripheral = h.id_Peripheral WHERE p.SKU = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Headphones(
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
                        rs.getString("typeHeadphones"),
                        rs.getDouble("weight"),
                        rs.getBoolean("microphone"),
                        rs.getInt("inputImpedance"),
                        rs.getInt("sensitivity"),
                        rs.getInt("frequencyResponse"),
                        rs.getBoolean("micSensitivity"),
                        rs.getBoolean("micFrequencyResponse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Headphones headphones) {
        String productSql = "UPDATE Product SET brand = ?, model = ?, description = ?, price = ? WHERE id_Product = ?";
        String peripheralSql = "UPDATE Peripheral SET type = ?, connectionType = ?, isWireless = ? WHERE id_Product = ?";
        String headphonesSql = "UPDATE Headphones SET weight = ?, microphone = ?, inputImpedance = ?, sensitivity = ?, " +
                "frequencyResponse = ?, micSensitivity = ?, micFrequencyResponse = ? WHERE id_Peripheral = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement productStmt = connection.prepareStatement(productSql);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql);
                 PreparedStatement headphonesStmt = connection.prepareStatement(headphonesSql)) {

                productStmt.setString(1, headphones.getBrand());
                productStmt.setString(2, headphones.getModel());
                productStmt.setString(3, headphones.getDescription());
                productStmt.setDouble(4, headphones.getPrice());
                productStmt.setInt(5, headphones.getId_Product());

                peripheralStmt.setString(1, headphones.getType());
                peripheralStmt.setString(2, headphones.getConnectionType());
                peripheralStmt.setBoolean(3, headphones.isWireless());
                peripheralStmt.setInt(4, headphones.getId_Product());

                headphonesStmt.setDouble(1, headphones.getWeight());
                headphonesStmt.setBoolean(2, headphones.isMicrophone());
                headphonesStmt.setInt(3, headphones.getInputImpedance());
                headphonesStmt.setInt(4, headphones.getSensitivity());
                headphonesStmt.setInt(5, headphones.getFrequencyResponse());
                headphonesStmt.setBoolean(6, headphones.isMicSensitivity());
                headphonesStmt.setBoolean(7, headphones.isMicFrequencyResponse());
                headphonesStmt.setInt(8, headphones.getId_Peripheral());

                productStmt.executeUpdate();
                peripheralStmt.executeUpdate();
                headphonesStmt.executeUpdate();

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

        String headphonesSql = "DELETE FROM Headphones WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?))";
        String peripheralSql = "DELETE FROM Peripheral WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)";
        String productSql = "DELETE FROM Product WHERE SKU = ?";

        try (Connection connection = Conexion.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement headphonesStmt = connection.prepareStatement(headphonesSql);
                 PreparedStatement peripheralStmt = connection.prepareStatement(peripheralSql);
                 PreparedStatement productStmt = connection.prepareStatement(productSql)) {

                headphonesStmt.setString(1, SKU);
                peripheralStmt.setString(1, SKU);
                productStmt.setString(1, SKU);

                headphonesStmt.executeUpdate();
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


    public Headphones recoverById(int id_Product) {
        String sql = "SELECT p.*, pe.*, h.* FROM Product p " +
                "JOIN Peripheral pe ON p.id_Product = pe.id_Product " +
                "JOIN Headphones h ON pe.id_Peripheral = h.id_Peripheral " +
                "WHERE p.id_Product = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Headphones(
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
                        rs.getString("type"),
                        rs.getDouble("weight"),
                        rs.getBoolean("microphone"),
                        rs.getInt("inputImpedance"),
                        rs.getInt("sensitivity"),
                        rs.getInt("frequencyResponse"),
                        rs.getBoolean("micSensitivity"),
                        rs.getBoolean("micFrequencyResponse")
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