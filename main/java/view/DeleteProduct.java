package view;

import DAO.ProductDAO;

import java.util.Scanner;

public class DeleteProduct {

    public static void deleteProduct(Scanner scanner){
        ProductDAO productDAO = new ProductDAO();

        System.out.print("Ingrese el SKU del producto a eliminar: ");
        String SKU = scanner.nextLine();

        boolean result = productDAO.delete(SKU);
        if (result) {
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("No se encontró el producto con el SKU proporcionado.");
        }
    }


}
