package view;

import DAO.InventoryDAO;
import DAO.ProductDAO;
import controller.Validations;
import model.Inventory;
import model.Product;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InventoryCRUD {

    public static void listInventory() {
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Inventory> inventoryList = inventoryDAO.list();

        if (inventoryList.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Inventory inventory : inventoryList) {
                for (Map.Entry<Integer, Integer> entry : inventory.getProducts().entrySet()) {
                    int id_Product = entry.getKey();
                    int stock = entry.getValue();

                    ProductDAO productDAO = new ProductDAO();
                    Product product = productDAO.recoverById(id_Product);
                    if (product != null) {
                        System.out.println(product.toString() + ", Stock: " + stock);
                    }
                }
            }
        }
    }

    public static void searchStockBySKU(Scanner scanner) {
        System.out.print("Ingrese el SKU del producto: ");
        String SKU = scanner.nextLine();

        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory inventory = inventoryDAO.recover(SKU);

        if (inventory != null) {
            ProductDAO productDAO = new ProductDAO();
            for (Map.Entry<Integer, Integer> entry : inventory.getProducts().entrySet()) {
                int id_Product = entry.getKey();
                int stock = entry.getValue();

                Product product = productDAO.recoverById(id_Product);
                if (product != null) {
                    System.out.println(product.toString() + ", Stock: " + stock);
                }
            }
        } else {
            System.out.println("No se encontró el producto con el SKU proporcionado.");
        }
    }

    public static void updateStockBySKU(Scanner scanner) {
        System.out.print("Ingrese el SKU del producto: ");
        String SKU = scanner.nextLine();

        InventoryDAO inventoryDAO = new InventoryDAO();
        Inventory inventory = inventoryDAO.recover(SKU);

        if (inventory != null) {
            int newStock = Validations.getIntInput(scanner, "Ingrese el nuevo stock");

            for (Map.Entry<Integer, Integer> entry : inventory.getProducts().entrySet()) {
                int id_Product = entry.getKey();
                inventory.updateStock(id_Product, newStock - entry.getValue());
            }

            inventoryDAO.updateStockBySKU(SKU,newStock);
            System.out.println("El stock del producto con SKU " + SKU + " ha sido actualizado.");
        } else {
            System.out.println("No se encontró el producto con el SKU proporcionado.");
        }
    }


}
