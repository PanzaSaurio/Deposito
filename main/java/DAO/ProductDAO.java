package DAO;

import DAO.components.*;
import DAO.peripherals.*;
import DAO.videoGame.*;
import controller.conexion.Conexion;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ProductDAO implements DAO<Product>  {

    private static final Map<String, DAO<?>> daoMap = new HashMap<>();

    static {
        daoMap.put("CPU", new CPUDAO());
        daoMap.put("GPU", new GPUDAO());
        daoMap.put("Motherboard", new MotherboardDAO());
        daoMap.put("RAM", new RAMDAO());
        daoMap.put("Console", new ConsoleDAO());
        daoMap.put("Headphones", new HeadphonesDAO());
        daoMap.put("Keyboard", new KeyboardDAO());
        daoMap.put("Mouse", new MouseDAO());
    }


    @Override
    public String getProductTypeBySKU(String SKU) {
        String sql = "SELECT 'CPU' AS type " +
                       "FROM CPU WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)"
                + " UNION ALL SELECT 'GPU' " +
                               "FROM GPU WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)"
                + " UNION ALL SELECT 'Motherboard' " +
                               "FROM Motherboard WHERE id_Product = (SELECT id_Product " +
                                                                      "FROM Product WHERE SKU = ?)"
                + " UNION ALL SELECT 'RAM' " +
                               "FROM RAM WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?)"
                + " UNION ALL SELECT 'Console' " +
                               "FROM Console WHERE id_Product = (SELECT id_Product " +
                                                                  "FROM Product WHERE SKU = ?)"
                + " UNION ALL SELECT 'Headphones' " +
                               "FROM Headphones WHERE id_Peripheral = (SELECT id_Peripheral " +
                                                                        "FROM Peripheral " +
                                                                       "WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?))"
                + " UNION ALL SELECT 'Keyboard' " +
                               "FROM Keyboard WHERE id_Peripheral = (SELECT id_Peripheral " +
                                                                     "FROM Peripheral " +
                                                                     "WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?))"
                + " UNION ALL SELECT 'Mouse' " +
                               "FROM Mouse WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral " +
                                                                  "WHERE id_Product = (SELECT id_Product FROM Product WHERE SKU = ?))";


        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, SKU);
            stmt.setString(2, SKU);
            stmt.setString(3, SKU);
            stmt.setString(4, SKU);
            stmt.setString(5, SKU);
            stmt.setString(6, SKU);
            stmt.setString(7, SKU);
            stmt.setString(8, SKU);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean delete(String SKU) {
        String productType = getProductTypeBySKU(SKU);
        if (productType != null) {
            DAO<?> dao = daoMap.get(productType);
            if (dao != null) {
                boolean success = dao.delete(SKU);
                if (success) {
                    System.out.println("Producto eliminado con éxito.");
                } else {
                    System.out.println("Error al eliminar el producto.");
                }
            } else {
                System.out.println("No se encontró el tipo de producto: " + productType);
            }
        } else {
            System.out.println("No se encontró el producto con el SKU proporcionado.");
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        return true;
    }



    @Override
    public Product recover(String SKU) {
        String productType = getProductTypeBySKU(SKU);
        if (productType != null) {
            DAO<?> dao = daoMap.get(productType);
            if (dao != null) {
                return (Product) dao.recover(SKU);
            }
        }
        return null;
    }

    public List<Product> listPeripherals() {
        List<Product> peripherals = new ArrayList<>();
        peripherals.addAll((List<Product>) daoMap.get("Headphones").list());
        peripherals.addAll((List<Product>) daoMap.get("Keyboard").list());
        peripherals.addAll((List<Product>) daoMap.get("Mouse").list());
        return peripherals;
    }

    public List<Product> listComponents() {
        List<Product> components = new ArrayList<>();
        components.addAll((List<Product>) daoMap.get("CPU").list());
        components.addAll((List<Product>) daoMap.get("GPU").list());
        components.addAll((List<Product>) daoMap.get("Motherboard").list());
        components.addAll((List<Product>) daoMap.get("RAM").list());
        return components;
    }

    public List<Product> listConsoles() {
        return (List<Product>) daoMap.get("Console").list();
    }

    public List<Product> listAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(listConsoles());
        allProducts.addAll(listComponents());
        allProducts.addAll(listPeripherals());
        return allProducts;
    }


    public Product recoverById(int id_Product) {
        String productType = getProductTypeById(id_Product);
        if (productType != null) {
            DAO<?> dao = daoMap.get(productType);
            if (dao != null) {
                return (Product) dao.recoverById(id_Product);
            }
        }
        return null;
    }

    private String getProductTypeById(int id_Product) {
        String sql = "SELECT 'CPU' AS type FROM CPU WHERE id_Product = ?"
                + " UNION ALL SELECT 'GPU' FROM GPU WHERE id_Product = ?"
                + " UNION ALL SELECT 'Motherboard' FROM Motherboard WHERE id_Product = ?"
                + " UNION ALL SELECT 'RAM' FROM RAM WHERE id_Product = ?"
                + " UNION ALL SELECT 'Console' FROM Console WHERE id_Product = ?"
                + " UNION ALL SELECT 'Headphones' FROM Headphones WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral WHERE id_Product = ?)"
                + " UNION ALL SELECT 'Keyboard' FROM Keyboard WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral WHERE id_Product = ?)"
                + " UNION ALL SELECT 'Mouse' FROM Mouse WHERE id_Peripheral = (SELECT id_Peripheral FROM Peripheral WHERE id_Product = ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_Product);
            stmt.setInt(2, id_Product);
            stmt.setInt(3, id_Product);
            stmt.setInt(4, id_Product);
            stmt.setInt(5, id_Product);
            stmt.setInt(6, id_Product);
            stmt.setInt(7, id_Product);
            stmt.setInt(8, id_Product);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void create(Product product) {

    }

    @Override
    public List<Product> list() {
        return new ArrayList<>();
    }


}
